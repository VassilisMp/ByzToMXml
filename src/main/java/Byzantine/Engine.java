package Byzantine;

import Byzantine.Annotations.NotSupported;
import Byzantine.Exceptions.NotSupportedException;
import com.google.common.base.Charsets;
import com.google.common.collect.*;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.audiveris.proxymusic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.annotations.UnmodifiableView;

import javax.xml.bind.Marshaller;
import java.io.*;
import java.lang.String;
import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static org.audiveris.proxymusic.util.Marshalling.getContext;

public final class Engine {
    static final String JSON_CHARS_FILE = "chars.json";
    @UnmodifiableView
    static final ImmutableBiMap<ByzStep, Step> STEPS_MAP = getDefaultStepsMap();
    @UnmodifiableView
    private static final List<ByzChar> charList = Collections.unmodifiableList(getCharList());
    @UnmodifiableView
    private static final Map<String, ByzClass> byzClassMap = getByzClassMap();
    /**
     * The best mapping from European to Byzantine hard-diatonic scale, without the need of turkish accidentals
     */
    @UnmodifiableView
    private static final ImmutableMap<Step, ByzStep> STANDARD_MAP = getEuropeanToByzantineMap();
    /**
     * The relative European step for Byzantine step NH of this engine converted to byzStep again,
     * via use of STEPS_MAP and STANDARD_MAP, use of STANDARD_MAP is required because everything is calculated using ByzScales.
     * e.g. STEPS_MAP: NH->G, STANDARD_MAP: G->DI
     */
    private static final ByzStep relativeStandardStep = STANDARD_MAP.get(byzStepToStep(ByzStep.NH));
    private final XWPFDocument docx;
    private final String fileName;
    private final ByzScale currentByzScale = ByzScale.get2OctavesScale();
    /**
     * Queue that holds fthoras as keys mapping to positions in the <code>noteList</code>
     */
    private final LinkedList<SimpleImmutableEntry<ByzScale, Integer>> fthoraScalesQueue = new LinkedList<>();
    private final Step initialStep;
    private final List<ByzChar> docChars = new ArrayList<>();
    // measure division must be at least 2, or else I 'll have to implement the case of division change, in the argo case as well..
    // division must be <= 16383
    int division = 1;
    List<Mxml.Note> noteList = new ArrayList<>();
    BiMap<String, Integer> noteTypeMap = initializeNoteTypeMap();
    private BigDecimal durationSum = BigDecimal.ZERO;
    private Integer timeBeats = null;
    // instances of Engine are immutable
    public Engine() {
        this.docx = null;
        fileName = null;
        initialStep = Step.C;
        this.putFthoraScale(new ByzScale(this.currentByzScale), 0);
    }
    public Engine(String filePath, Step initialStep) throws IOException {
        Matcher matcher = Pattern.compile("(.*/)*(.*)(\\.docx?)").matcher(filePath);
        if (matcher.find())
            this.fileName = matcher.group(2);
        else throw new FileNotFoundException("couldn't match filename");
        this.initialStep = initialStep;
        this.docx = new XWPFDocument(new FileInputStream(filePath));
        // Note 0 ---
        Mxml.Note note = new Mxml.Note(true, true, this.initialStep, 4,
                division, Mxml.Note.NoteTypeEnum.QUARTER.getNoteType());
        /*new Mxml.Note.Builder(true, true, this.initialStep, 4,
                division, Mxml.Note.NoteTypeEnum.QUARTER).build();*/
        noteList.add(note);
        initAccidentalCommas();
        this.putFthoraScale(new ByzScale(this.currentByzScale), 0);
    }

    public Engine(String filePath, Step initialStep, int octave) throws IOException {
        this(filePath, initialStep);
        noteList.get(0).setOctave(octave);
    }

    /**
     * @return the Map to initialize STANDARD_MAP
     */
    private static @NotNull @UnmodifiableView ImmutableMap<Step, ByzStep> getEuropeanToByzantineMap() {
        return new ImmutableMap.Builder<Step, ByzStep>()
                .put(Step.C, ByzStep.NH)
                .put(Step.D, ByzStep.PA)
                .put(Step.E, ByzStep.BOU)
                .put(Step.F, ByzStep.GA)
                .put(Step.G, ByzStep.DI)
                .put(Step.A, ByzStep.KE)
                .put(Step.B, ByzStep.ZW)
                .build();
    }

    private static @NotNull @UnmodifiableView Map<String, ByzClass> getByzClassMap() {
        /* TODO Palaia fonts not supported
         * because of different character matching to Byzantine fonts
         * maybe I'll have to make the matching*/
        // TODO test all fonts
        Map<String, ByzClass> map = new HashMap<>();
        map.put("PFKonstantinople", ByzClass.A); //T // TODO Run method for Arxigramma (probably same code with text fonts)
        map.put("BZ Byzantina", ByzClass.B);
        map.put("BZ Byzantina2", ByzClass.B);
        map.put("DP_NRByzantina", ByzClass.B);
        map.put("MKByzantine", ByzClass.B);
        map.put("BZ Fthores", ByzClass.F);
        map.put("DP_NRFthores", ByzClass.F);
        map.put("MKFthores", ByzClass.F);
        map.put("BZ Ison", ByzClass.I);
        map.put("DP_NRIson", ByzClass.I);
        map.put("MK Ison", ByzClass.I);
        map.put("MK Ison2", ByzClass.I);
        map.put("MKNewIson", ByzClass.I);
        map.put("BZ Loipa", ByzClass.L);
        map.put("DP_NRLoipa", ByzClass.L);
        map.put("MKLoipa", ByzClass.L);
        map.put("MK", ByzClass.N);//N //T // TODO not supported, matching differs to standard font types (example Byzantine, Fthores...)
        map.put("MK2015", ByzClass.N);//N //T // TODO not supported
        map.put("MK2017 design", ByzClass.N);//N // T // TODO not supported
        map.put("BZ Palaia", ByzClass.P); // TODO Palaia not supported
        map.put("DP_NRPalaia", ByzClass.P);
        map.put("MK Palaia", ByzClass.P);
        map.put("Genesis", ByzClass.T); // TODO Run method for text fonts
        map.put("GFSDidotClassic", ByzClass.T); // same
        map.put("GFSNicefore", ByzClass.T); // same
        map.put("MgAgiaSofia UC Normal", ByzClass.T); // same
        map.put("MgByzantine UC Pol Normal", ByzClass.T); // same
        map.put("BZ Xronos", ByzClass.X);
        map.put("DP_NRXronos", ByzClass.X);
        map.put("MK Xronos2016", ByzClass.X);
        map.put("MKXronos", ByzClass.X);
        return Collections.unmodifiableMap(map);
    }

    static List<ByzChar> getCharList() {
        // deserialize chars.json to List<ByzChar>
        String json;
        try {
            json = FileUtils.readFileToString(new File(Engine.JSON_CHARS_FILE), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error in reading " + Engine.JSON_CHARS_FILE);
            //showAlertMessage("error in reading " + Engine.JSON_CHARS_FILE);
            System.exit(-1);
            return null;
        }
        //System.out.println(json);
        //ByzCharDeserializer deserializer = new ByzCharDeserializer();
        return new ByzCharDeSerialize().fromJson(json);
    }

    private static @NotNull HashBiMap<String, Integer> initializeNoteTypeMap() {
        // 1024th, 512th, 256th, 128th, 64th, 32nd, 16th, eighth, quarter, half, whole, breve, long, and maxima
        final HashBiMap<String, Integer> noteTypeMap = HashBiMap.create();
        noteTypeMap.put("maxima", /*division * */28);
        noteTypeMap.put("long", /*division * */16);
        noteTypeMap.put("breve", /*division * */8);
        noteTypeMap.put("whole", /*division * */4);
        noteTypeMap.put("half", /*division * */2);
        noteTypeMap.put("quarter", /*division*/1);
        /*if (division % 2 == 0) noteTypeMap.put("eighth", division / 2);
        if (division % 4 == 0) noteTypeMap.put("16th", division / 4);
        if (division % 8 == 0) noteTypeMap.put("32nd", division / 8);
        if (division % 16 == 0) noteTypeMap.put("64th", division / 16);
        if (division % 32 == 0) noteTypeMap.put("128th", division / 32);
        if (division % 64 == 0) noteTypeMap.put("256th", division / 64);
        if (division % 128 == 0) noteTypeMap.put("512th", division / 128);
        if (division % 256 == 0) noteTypeMap.put("1024th", division / 256);*/
        return noteTypeMap;
    }

    private static @NotNull @UnmodifiableView ImmutableBiMap<ByzStep, Step> getDefaultStepsMap() {
        return new ImmutableBiMap.Builder<ByzStep, Step>()
                .put(ByzStep.NH, Step.G)
                .put(ByzStep.PA, Step.A)
                .put(ByzStep.BOU, Step.B)
                .put(ByzStep.GA, Step.C)
                .put(ByzStep.DI, Step.D)
                .put(ByzStep.KE, Step.E)
                .put(ByzStep.ZW, Step.F)
                .build();
    }

    static Step byzStepToStep(ByzStep byzStep) {
        return STEPS_MAP.get(byzStep);
    }

    static ByzStep stepToByzStep(Step step) {
        return STEPS_MAP.inverse().get(step);
    }

    public static ByzStep toByzStep(@NotNull Mxml.Note note) {
        return STEPS_MAP.inverse().get(note.getStep());
    }

    public ByzScale getLastFthora() {
        return fthoraScalesQueue.getLast().getKey();
    }

    public int getDivision() {
        return division;
    }

    @TestOnly
    void setDivision(int division) {
        this.division = division;
        mapValuesUpdate();
    }

    void initAccidentalCommas() {
        ByzScale.initAccidentalCommas(currentByzScale, relativeStandardStep);
    }

    @Deprecated
    @TestOnly
    public Engine setTimeBeats(int timeBeats) {
        this.timeBeats = timeBeats;
        return this;
    }

    public ByzScale getCurrentByzScale() {
        return currentByzScale;
    }

    public void run() throws Exception {
        parseChars();
        fixL116();
//        this.docChars.forEach(System.out::println);
        createNotelist();
        // remove first note which was auxiliary
        noteList.remove(0);
        // DEBUG
        // calc duration sum
        durationSum = noteList.parallelStream()
                .map(note -> {
                    // replace dots in noteType String
                    String value = note.getType().getValue();
                    if (value != null && value.charAt(value.length() - 1) == '.')
                        note.getType().setValue(value.replace(".", ""));
                    return note.getDuration();
                })
                .reduce(durationSum, BigDecimal::add);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".xml")) {
            ScorePartwise scorePartwise = toScorePartwise();
            Marshaller marshaller = getContext(ScorePartwise.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(scorePartwise, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNotelist() throws Exception {
        TimeChar tChar;
        // if TimeChar divisions is bigger than one which means that next QuantityChar must be added before running this TimeChar
        for (int i = 0; i < docChars.size(); i++) {
            if (i == 195) {
                System.out.println("check");
            }
            ByzChar Char = docChars.get(i);
            if (Char instanceof TimeChar) {
                tChar = (TimeChar) Char;
                List<Move> moves = null;
                if (tChar.getDivisions() > 1 && !tChar.getArgo()) {
                    int index = getIndex();
                    // notes that can be divided
                    int notesD = noteList.size() - index + 1;
                    moves = new ArrayList<>();
                    while (notesD < tChar.getDivisions() + 1) {
                        i++;
                        Char = docChars.get(i);
                        if (Char instanceof TimeChar)
                            throw new Exception("TimeChar before running previous TimeChar, i=" + i);
                        if (Char instanceof QuantityChar) {
                            QuantityChar qChar = (QuantityChar) Char;
                            System.out.println(i + " " + Char);
                            // clone qChar moves to save Time value
                            // and reset after running tChar
                            Collections.addAll(moves, qChar.getMovesClone());
                            // set qChar Time values to false, so getIndex method can work properly
                            qChar.forEach(move -> move.setTime(false));
                            qChar.accept(this);
                            notesD = noteList.size() - index + 1;
                            continue;
                        }
                        Char.accept(this);
                        notesD = noteList.size() - index + 1;
                    }
                }
                System.out.println(i + " " + Char);
                tChar.accept(this);
                if (moves != null) {
                    // reset Time values on the notes
                    List<Mxml.Note> notes = noteList.subList(noteList.size() - moves.size(), noteList.size());
                    for (int i1 = 0; i1 < notes.size(); i1++) {
                        Mxml.Note note = notes.get(i1);
                        note.setTime(moves.get(i1).getTime());
                    }
                }
                continue;
            }
            System.out.println(i + " " + Char);
            Char.accept(this);
            //System.out.println(unicodeChar);
            //System.out.println(i + " " + Char);
        }
    }

    private void parseChars() throws NotSupportedException {
        // TODO start parsing lyrics with Arxigramma if exists
        // 0 - 1264 chars using toCharArray
        int pos = 0;
        // if there is lyric char before Quantity Char but belongs to that
        boolean qCharAdded = false;
        boolean mCharAdded = false;
        StringBuilder sb = new StringBuilder();
        FthoraChar lastFthora = null;
        int lastFthoraIndex = -1;
        for (XWPFParagraph paragraph : docx.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                for (char c : run.text().toCharArray()) {
                    pos++;
                    String fontName = run.getFontName();
                    ByzClass byzClass = byzClassMap.get(fontName);
                    if (c > 0xEFFF) // && <0xF8FF because Unicode Private Use Area is, U+E000 to U+F8FF
                        c -= 0xF000;
                    System.out.println(String.format("%5d", (int) c) + " The character at " + String.format("%4d", pos) + " is " + c + "   " + fontName + "  " + byzClass);
                    // if char is greek letter append to the lyric StringBuilder
                    if (c >= 0x0370 && c <= 0x03FF) {
                        sb.append(c);
                        continue;
                    }
                    // if there is lyrics in the StringBuilder and a QuantityChar was added then add the lyrics in the last QChar
                    if (sb.length() > 0 && (qCharAdded || mCharAdded)) {
                        @SuppressWarnings("rawtypes")
                        Class clazz = qCharAdded ? QuantityChar.class : MixedChar.class;
                        qCharAdded = mCharAdded = false;
                        // find the last QChar which may also be in a MixedChar
                        Lists.reverse(docChars).stream()
                                .filter(clazz::isInstance)
                                .findFirst()
                                .ifPresent(character -> {
                                    // if MixedChar then find the last QChar in the MixedChar
                                    if (clazz == MixedChar.class) {
                                        for (ByzChar m : ((MixedChar) character))
                                            if (m instanceof QuantityChar) {
                                                character = m;
                                                break;
                                            }
                                    }
                                    // set last Char lyrics
                                    character.setText(sb.toString());
                                });
                        // delete the StringBuilder's contents
                        sb.setLength(0);
                    }
                    // if ByzClass is exists, it means this char is a Byzantine one
                    if (byzClass != null) {
                        try {
                            // check if this char class is supported
                            boolean annotationPresent = ByzClass.class.getField(byzClass.toString()).isAnnotationPresent(NotSupported.class);
                            //System.out.println(byzClass + " annotation isPresent: " + annotationPresent);
                            // if not supported continue
                            if (annotationPresent) {
                                //continue;
                                throw new NotSupportedException("document contains ByzClass." + byzClass + " character which is not supported");
                            }
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        // if c<33 || c>255 means the Char isn't in the area of ByzChars in the Unicode
                        if (c < 33 || c > 255) continue;
                    } else continue;
                    if (c == 162 && byzClass == ByzClass.B) c = 100;
                    if (c == 52 && byzClass == ByzClass.F) {
                        c = 36;
                        if (lastFthora != null)
                            if (lastFthora.getCodePoint() == 36) {
                                if (lastFthoraIndex != -1) {
                                    int counter = (int) docChars.subList(lastFthoraIndex, docChars.size())
                                            .stream().filter(byzChar -> byzChar instanceof QuantityChar).count();
                                    if (counter < 2) docChars.remove(lastFthoraIndex);
                                }
                            }
                    }
                    final char finalChar = c;
                    // check if current Char is in the ByzCharList
                    ByzChar byzChar = charList.stream()
                            .filter(character -> character.getCodePoint() == finalChar && character.getByzClass() == byzClass)
                            .findAny()
                            .orElse(null);
                    //System.out.println(String.format("%5d", charInt) + " The character at " + String.format("%4d", pos) + " is " + c + "   " + fontName + "  " + byzClass);
                    // if doesn't exist continue to next
                    if (byzChar == null) continue;
                    else if (byzChar instanceof FthoraChar) {
                        lastFthora = (FthoraChar) byzChar;
                        lastFthoraIndex = docChars.size();
                    }
                    if (byzChar.getCodePoint() == 67 && byzChar.getByzClass() == ByzClass.B) {
                        ByzChar prev = docChars.get(docChars.size() - 1);
                        if (prev.getCodePoint() == 115 && prev.getByzClass() == ByzClass.B) {
                            byzChar = charList.stream()
                                    .filter(character -> character.getCodePoint() == 100 && character.getByzClass() == ByzClass.B)
                                    .findAny()
                                    .orElse(null);
                            Objects.requireNonNull(byzChar);
                            ByzChar clone = byzChar.clone();//Cloner.deepClone(byzChar);
                            clone.setText(prev.getText());
                            docChars.set(docChars.size() - 1, clone);
                            continue;
                        }
                    }
                    // else clone and add in the docChars
                    Objects.requireNonNull(byzChar);
                    ByzChar clone = Objects.requireNonNull(byzChar.clone());
                    clone.setFont(fontName);
                    docChars.add(clone);
                    if (byzChar instanceof QuantityChar) qCharAdded = true;
                    else if (byzChar instanceof MixedChar)
                        mCharAdded = Arrays.stream(((MixedChar) byzChar).getChars())
                                .anyMatch(Char -> Char instanceof QuantityChar);
                }
            }
        }
    }

    // replaces L116 followed of two gorga, with working sequence
    private void fixL116() {
        long start = System.nanoTime();
        for (int i = 0; i < docChars.size(); i++) {
            ByzChar Char = docChars.get(i);
            int index = i;
            if (Char.equals(116, ByzClass.L)) {
                ByzChar Char1 = docChars.get(++i);
                FthoraChar fthoraChar = null;
                if (Char1 instanceof FthoraChar) {
                    fthoraChar = (FthoraChar) Char1;
                    docChars.remove(i);
                    Char1 = docChars.get(i);
                }
                if (ByzChar.isGorgonOrArgo(Char1)) {
                    ByzChar Char2 = docChars.get(++i);
                    if (Char2 instanceof FthoraChar) Char2 = docChars.get(++i);
                    if (ByzChar.isGorgonOrArgo(Char2)) {
                        ByzChar q1 = new QuantityChar(39, ByzClass.B,
                                new Move(-1, true, true),
                                new Move(-1, true, false)
                        );
                        ByzChar q2 = new QuantityChar(120, ByzClass.B,
                                new Move(1, false, true)
                        );
                        docChars.remove(index);
                        int indexOf = getExactIndexOf(Char1, docChars);
                        docChars.add(indexOf, q1);
                        indexOf = getExactIndexOf(Char2, docChars);
                        docChars.add(indexOf, q2);
                        if (fthoraChar != null) docChars.add(indexOf + 2, fthoraChar);
                    }
                }
            }
        }
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println("timeElapsed" + timeElapsed);
    }

    private int getExactIndexOf(ByzChar char1, List<ByzChar> docChars) {
        return IntStream.range(0, docChars.size()).filter(i1 -> docChars.get(i1) == char1).findFirst().orElse(-1);
    }

    BigDecimal getDurationSum() {
        return new BigDecimal(String.valueOf(durationSum));
    }

    private ScorePartwise toScorePartwise() throws Exception {
        // ScorePart in scorePartwise
        final Mxml.Part part = new Mxml.Part.Builder(noteList, division, timeBeats)
                .build();
        return new Mxml.ScorePartwise.Builder()
                .setPart(part, "P1", "Music")
                .build();
        /*
         * TODO use byzantine measure splitters to calculate, if they exist.
         *  must create ByzChar subclass first*/
    }

    private void mapValuesUpdate() {
//        noteTypeMap.clear();
        // 1024th, 512th, 256th, 128th, 64th, 32nd, 16th, eighth, quarter, half, whole, breve, long, and maxima
        noteTypeMap.put("maxima..", division * 49);
        noteTypeMap.put("maxima.", division * 42);
        noteTypeMap.put("maxima", division * 28);
        //noteTypeMap.put("long..", division * 28);
        noteTypeMap.put("long.", division * 24);
        noteTypeMap.put("long", division * 16);
        noteTypeMap.put("breve..", division * 14);
        noteTypeMap.put("breve.", division * 12);
        noteTypeMap.put("breve", division * 8);
        noteTypeMap.put("whole..", division * 7);
        noteTypeMap.put("whole.", division * 6);
        noteTypeMap.put("whole", division * 4);
        if ((division * 2) % 4 == 0)
            noteTypeMap.put("half..", (int) (3.5 * division));
        noteTypeMap.put("half.", division * 3);
        noteTypeMap.put("half", division * 2);
        noteTypeMap.put("quarter", division);
        if (division % 2 == 0) {
            noteTypeMap.put("quarter.", (int) (division * 1.5));
            noteTypeMap.put("eighth", division / 2);
        }
        if (division % 4 == 0) {
            noteTypeMap.put("quarter..", (int) (division * 1.75));
            noteTypeMap.put("eighth.", (int) (division * 0.75));
            noteTypeMap.put("16th", division / 4);
        }
        if (division % 8 == 0) {
            noteTypeMap.put("eighth..", (int) (division * 0.875));
            noteTypeMap.put("16th.", (int) (division * 0.375));
            noteTypeMap.put("32nd", division / 8);
        }
        if (division % 16 == 0) {
            noteTypeMap.put("16th..", (int) (division * 0.4375));
            noteTypeMap.put("32nd.", (int) (division * 0.1875));
            noteTypeMap.put("64th", division / 16);
        }
        if (division % 32 == 0) {
            noteTypeMap.put("32nd..", (int) (division * 0.21875));
            noteTypeMap.put("64th.", (int) (division * 0.09375));
            noteTypeMap.put("128th", division / 32); // TODO continue to 128 dotted
        }
        if (division % 64 == 0) {
            noteTypeMap.put("64th..", (int) (division * 0.109375));
            noteTypeMap.put("256th", division / 64);
        }
        if (division % 128 == 0) {
            noteTypeMap.put("512th", division / 128);
        }
        if (division % 256 == 0) noteTypeMap.put("1024th", division / 256);
    }

    void changeDivision(int multiplier) {
        division *= multiplier;
        // change the duration of all notes according to the new corresponding to the new division value
        noteList.parallelStream().forEach(note -> note.updateDivision(multiplier));
        // reInsert the values in the map to add those supported by the new measure division
        mapValuesUpdate();
    }

    int getIndex() {
        ListIterator<Mxml.Note> iterator = noteList.listIterator(noteList.size());
        while (iterator.hasPrevious())
            if (iterator.previous().canGetTime())
                return iterator.nextIndex();
        throw new NullPointerException("Couldn't find a Note that canGetTime()");
    }

    @Nullable Step getLastNoteStep() {
        if (noteList.size() < 1) return null;
        ListIterator<Mxml.Note> noteListIterator = noteList.listIterator(noteList.size());
        while (noteListIterator.hasPrevious()) {
            org.audiveris.proxymusic.Note note = noteListIterator.previous();
            if (note.getPitch() != null)
                if (note.getPitch().getStep() != null)
                    return note.getPitch().getStep();
        }
        return null;
    }

    Step getInitialStep() {
        return initialStep;
    }

    boolean putFthoraScale(ByzScale k, Integer v) {
        return fthoraScalesQueue.add(new SimpleImmutableEntry<>(k, v));
    }

    int getNoteListSize() {
        return noteList.size();
    }

    private @Nullable Accidental commasToAccidental(int commas) {
        final AccidentalValue accidentalValue = Martyria.ACCIDENTALS_MAP.get(commas);
        if (accidentalValue == null) return null;
        final Accidental accidental = new Accidental();
        accidental.setValue(accidentalValue);
        return accidental;
    }

    private @NotNull Map<Step, AccidentalValue> keyToMap(@NotNull Key key) {
        // create Step to AccidentalValue map
        final Map<Step, AccidentalValue> stepToAccidental = new HashMap<>(7);
        // put all Steps as keys in map
        Arrays.stream(Step.values()).forEach(step -> stepToAccidental.put(step, null));
        // get
        List<Object> nonTraditionalKey = key.getNonTraditionalKey();
        for (int i = 0; i < nonTraditionalKey.size(); i += 3) {
            Step step = (Step) nonTraditionalKey.get(i);
            AccidentalValue accidentalValue = (AccidentalValue) nonTraditionalKey.get(i + 2);
            stepToAccidental.put(step, accidentalValue);
        }
        return stepToAccidental;
    }

    public static class Builder {

    }
}
