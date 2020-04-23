package Byzantine;

import Byzantine.Annotations.NotSupported;
import Byzantine.Exceptions.NotSupportedException;
import com.google.common.base.Charsets;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.audiveris.proxymusic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static Byzantine.Scale.KeyFromPitches;
import static org.audiveris.proxymusic.util.Marshalling.getContext;

public final class Engine {
    static final String JSON_CHARS_FILE = "chars.json";
    // measure division must be at least 2, or else I 'll have to implement the case of division change, in the argo case as well..
    // division must be <= 16383
    int division;
    private Step initialStep;
    private BigDecimal durationSum;
    private int timeBeats;
    List<Note> noteList;
    private List<ByzChar> docChars;
    private final XWPFDocument docx;
    private final String fileName;
    private static final List<ByzChar> charList = Collections.unmodifiableList(getCharList());
    private static final Map<String, ByzClass> byzClassMap = getByzClassMap();
    BiMap<String, Integer> noteTypeMap = HashBiMap.create();
    Scale scale = Scale.HARD_DIATONIC.byStep(Step.A);
    final BiMap<ByzStep, Step> STEPS_MAP = HashBiMap.create(7);
    private final ByzScale currentByzScale = ByzScale.get2OctavesScale();
    /**
    * Map that holds fthoras as keys mapping to positions in the <code>noteList</code> */
    private final Map<ByzScale, Integer> fthoraScalesMap = new LinkedHashMap<>();

    public Engine(int division) {
        this.division = division;
        mapValuesInsert();
        noteList = new ArrayList<>();
        this.docx = null;
        fileName = null;
    }

    public Engine(String filePath, Step initialStep, int division) throws IOException {
        Matcher matcher = Pattern.compile("(.*/)*(.*)(\\.docx?)").matcher(filePath);
        if (matcher.find())
            this.fileName = matcher.group(2);
        else this.fileName = null;
        this.division = division;
        noteTypeMap.clear();
        // 1024th, 512th, 256th, 128th, 64th, 32nd, 16th, eighth, quarter, half, whole, breve, long, and maxima
        noteTypeMap.put("maxima", division * 28);
        noteTypeMap.put("long", division * 16);
        noteTypeMap.put("breve", division * 8);
        noteTypeMap.put("whole", division * 4);
        noteTypeMap.put("half", division * 2);
        noteTypeMap.put("quarter", division);
        if (division % 2 == 0) noteTypeMap.put("eighth", division / 2);
        if (division % 4 == 0) noteTypeMap.put("16th", division / 4);
        if (division % 8 == 0) noteTypeMap.put("32nd", division / 8);
        if (division % 16 == 0) noteTypeMap.put("64th", division / 16);
        if (division % 32 == 0) noteTypeMap.put("128th", division / 32);
        if (division % 64 == 0) noteTypeMap.put("256th", division / 64);
        if (division % 128 == 0) noteTypeMap.put("512th", division / 128);
        if (division % 256 == 0) noteTypeMap.put("1024th", division / 256);
        this.durationSum = BigDecimal.ZERO;
        this.docx = new XWPFDocument(new FileInputStream(filePath));
        this.noteList = new ArrayList<>();
        this.docChars = new ArrayList<>();
        // Note 0 ---
        Note note = new ExtendedNote(true, true);
        noteList.add(note);

        // Pitch
        Pitch pitch = new Pitch();
        note.setPitch(pitch);
        pitch.setStep(this.initialStep = initialStep);
        pitch.setOctave(4);

        // Duration
        note.setDuration(new BigDecimal(division));

        // Type
        NoteType type = new NoteType();
        type.setValue("quarter");
        note.setType(type);

        STEPS_MAP.put(ByzStep.NH, Step.G);
        STEPS_MAP.put(ByzStep.PA, Step.A);
        STEPS_MAP.put(ByzStep.BOU, Step.B);
        STEPS_MAP.put(ByzStep.GA, Step.C);
        STEPS_MAP.put(ByzStep.DI, Step.D);
        STEPS_MAP.put(ByzStep.KE, Step.E);
        STEPS_MAP.put(ByzStep.ZW, Step.F);
    }

    public Engine setTimeBeats(int timeBeats) {
        this.timeBeats = timeBeats;
        return this;
    }

    void setDivision(int division) {
        this.division = division;
        mapValuesInsert();
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
        for (int i = 0, noteListSize = noteList.size(); i < noteListSize; i++) {
            Note note1 = noteList.get(i);
            durationSum = note1.getDuration().add(durationSum);
            System.out.println(i + " " + note1);
        }
        noteList.forEach(note -> {
            String value = note.getType().getValue();
            if (value != null && value.charAt(value.length()-1) == '.')
                note.getType().setValue(value.replace(".", ""));
        });
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".xml")) {
            ScorePartwise scorePartwise = toScorePartwise();
            Marshaller marshaller = getContext(ScorePartwise.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(scorePartwise, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // DEBUG
//        this.noteTypeMap.forEach((str, integer) -> System.out.println(str + " : " + integer));
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
                if (tChar.getDivisions()>1 && !tChar.getArgo()) {
                    int index = getIndex();
                    // notes that can be divided
                    int notesD = noteList.size() - index + 1;
                    moves = new ArrayList<>();
                    while(notesD < tChar.getDivisions()+1) {
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
                    List<Note> notes = noteList.subList(noteList.size() - moves.size(), noteList.size());
                    for (int i1 = 0; i1 < notes.size(); i1++) {
                        Note note = notes.get(i1);
                        if (note instanceof ExtendedNote) {
                            ExtendedNote exNote = (ExtendedNote) note;
                            exNote.setTime(moves.get(i1).getTime());
                        }
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
                    if(c>0xEFFF) // && <0xF8FF because Unicode Private Use Area is, U+E000 to U+F8FF
                        c-=0xF000;
                    System.out.println(String.format("%5d", (int)c) + " The character at " + String.format("%4d", pos) + " is " + c + "   " + fontName + "  " + byzClass);
                    // if char is greek letter append to the lyric StringBuilder
                    if (c>=0x0370 && c<=0x03FF) {
                        sb.append(c);
                        continue;
                    }
                    // if there is lyrics in the StringBuilder and a QuantityChar was added then add the lyrics in the last QChar
                    if (sb.length() > 0 && (qCharAdded || mCharAdded)) {
                        @SuppressWarnings("rawtypes")
                        Class clas = qCharAdded ? QuantityChar.class : MixedChar.class;
                        qCharAdded = mCharAdded = false;
                        // find the last QChar which may also be in a MixedChar
                        Lists.reverse(docChars).stream()
                                .filter(clas::isInstance)
                                .findFirst()
                                .ifPresent(character -> {
                                    // if MixedChar then find the last QChar in the MixedChar
                                    if (clas == MixedChar.class) {
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
                        } catch (NoSuchFieldException e) { e.printStackTrace(); }
                        // if c<33 || c>255 means the Char isn't in the area of ByzChars in the Unicode
                        if(c<33 || c>255) continue;
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
                        ByzChar prev = docChars.get(docChars.size()-1);
                        if (prev.getCodePoint() == 115 && prev.getByzClass() == ByzClass.B) {
                            byzChar = charList.stream()
                                    .filter(character -> character.getCodePoint() == 100 && character.getByzClass() == ByzClass.B)
                                    .findAny()
                                    .orElse(null);
                            Objects.requireNonNull(byzChar);
                            ByzChar clone = byzChar.clone();//Cloner.deepClone(byzChar);
                            clone.setText(prev.getText());
                            docChars.set(docChars.size()-1, clone);
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
                        mCharAdded =  Arrays.stream(((MixedChar) byzChar).getChars())
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
        // Generated factory for all proxymusic elements
        ObjectFactory factory = new ObjectFactory();

        // Allocate the score partwise
        ScorePartwise scorePartwise = factory.createScorePartwise();

        // PartList
        PartList partList = factory.createPartList();
        scorePartwise.setPartList(partList);

        // Scorepart in partList
        ScorePart scorePart = factory.createScorePart();
        partList.getPartGroupOrScorePart().add(scorePart);
        scorePart.setId("P1");

        PartName partName = factory.createPartName();
        scorePart.setPartName(partName);
        partName.setValue("Music");

        // ScorePart in scorePartwise
        ScorePartwise.Part part = factory.createScorePartwisePart();
        scorePartwise.getPart().add(part);
        part.setId(scorePart);
        //workingTest1Measure(factory, part);

        addMeasures(factory, part, timeBeats);

        return scorePartwise;
    }

    /*private void workingTest1Measure(ObjectFactory factory, ScorePartwise.Part part) {
        // Measure
        ScorePartwise.Part.Measure measure = factory.createScorePartwisePartMeasure();
        part.getMeasure().add(measure);
        measure.setNumber("1");

        // Attributes
        Attributes attributes = factory.createAttributes();
        measure.getNoteOrBackupOrForward().add(attributes);

        // Divisions
        attributes.setDivisions(new BigDecimal(division));

        // Key
        Key key = factory.createKey();
        attributes.getKey().add(key);
        key.setFifths(new BigInteger("-1"));
        key.setMode("minor");

        // Time
        Time time = factory.createTime();
        attributes.getTime().add(time);
        time.getTimeSignature().add(factory.createTimeBeats(String.valueOf(noteList.size())));
        time.getTimeSignature().add(factory.createTimeBeatType("4"));

        // Clef
        Clef clef = factory.createClef();
        attributes.getClef().add(clef);
        clef.setSign(ClefSign.G);
        clef.setLine(new BigInteger("2"));

        for (Note note : noteList) {
            measure.getNoteOrBackupOrForward().add(note);
        }
    }*/

    private void addMeasures(ObjectFactory factory, ScorePartwise.Part part, int timeBeats) throws Exception {
        // make lists of Notes according to the given timeBeats
        ArrayList<List<Note>> noteLists = new ArrayList<>();
        if (timeBeats > 0)
            for (int i = 0, noteListSize = noteList.size(), index = i, durations = 0; i < noteListSize; i++) {
                //measure.getNoteOrBackupOrForward().add(note);
                Note note = noteList.get(i);
                durations += note.getDuration().intValue();
                if (durations == (division * timeBeats)) {
                    noteLists.add(noteList.subList(index, i+1));
                    index = i+1;
                    durations = 0;
                }else if (durations > (division * timeBeats)){
                    throw new Exception("error in noteLists");
                }
            }
        else { // TODO finish this, find the right notetypes
            for (int i = 0, noteListSize = noteList.size(), index = i, durations = 0; i < noteListSize; i++) {
                if (i == 200)
                    System.out.println();
                Note note = noteList.get(i);
                durations += note.getDuration().intValue();
                for (int j = 2; j <= 12; j++) {
                    if (durations == (division * j)) {
                        noteLists.add(noteList.subList(index, i+1));
                        index = i+1;
                        durations = 0;
                        break;
                    }
                }
                /*if (durations == (TimeChar.division * 2) ||
                        durations == (TimeChar.division * 3) ||
                        durations == (TimeChar.division * 4) ||
                        durations == (TimeChar.division * 5) ||
                        durations == (TimeChar.division * 6) ||
                        durations == (TimeChar.division * 7) ||
                        durations == (TimeChar.division * 8) ) {
                    noteLists.add(noteList.subList(index, i+1));
                    index = i+1;
                    durations = 0;
                } else */if (durations > (division * 12)) {
                    throw new Exception("error in dividing measures, i=" + i + ", durations=" + durations);
                }
            }
        }

        ArrayList<ScorePartwise.Part.Measure> measures = new ArrayList<>(noteLists.size());
        for (int i = 1, noteListsSize = noteLists.size(); i < noteListsSize; i++) {
            List<Note> notesList = noteLists.get(i);
            ScorePartwise.Part.Measure partMeasure = factory.createScorePartwisePartMeasure();
            partMeasure.setNumber(String.valueOf(i+1));

            Attributes attributes = factory.createAttributes();
            partMeasure.getNoteOrBackupOrForward().add(attributes);
            Integer reduce = notesList.stream().map(note -> note.getDuration().intValue()).reduce(0, Integer::sum);
            // Time
            Time time = factory.createTime();
            attributes.getTime().add(time);
            if (reduce % division != 0) throw new Exception("wrong measure size, i=" + i + ", " + reduce + "/" + division);
            time.getTimeSignature().add(factory.createTimeBeats(String.valueOf(reduce/ division)));
            time.getTimeSignature().add(factory.createTimeBeatType("4"));

            partMeasure.getNoteOrBackupOrForward().addAll(notesList);
            measures.add(partMeasure);
        }

        addFirstMeasure(factory, part, noteLists.get(0));
        part.getMeasure().addAll(measures);
    }

    private void addFirstMeasure(ObjectFactory factory, ScorePartwise.Part part, List<Note> notes) {
        // Measure
        ScorePartwise.Part.Measure measure = factory.createScorePartwisePartMeasure();
        part.getMeasure().add(measure);
        measure.setNumber("1");

        // Attributes
        Attributes attributes = factory.createAttributes();
        measure.getNoteOrBackupOrForward().add(attributes);

        // Divisions
        attributes.setDivisions(new BigDecimal(division));

        /*// Key
        Key key = factory.createKey();
        attributes.getKey().add(key);
        //key.setFifths(new BigInteger("-1"));
        key.getNonTraditionalKey().addAll(Arrays.asList(Step.B, BigDecimal.valueOf(-1), AccidentalValue.FLAT));*/

        scale.applyFthora(Scale.HARD_CHROMATIC);
        //List<PitchEntry> thisC = PitchEntry.cloneScale(PitchEntry.ListByStep(HARD_DIATONIC, Step.A));
        //PitchEntry.FthoraApply(thisC, HARD_CHROMATIC);
        Key key = KeyFromPitches(scale.scale);
        attributes.getKey().add(key);

        // Time
        Time time = factory.createTime();
        attributes.getTime().add(time);
        time.getTimeSignature().add(factory.createTimeBeats(String.valueOf(4)));
        time.getTimeSignature().add(factory.createTimeBeatType("4"));

        // Clef
        Clef clef = factory.createClef();
        attributes.getClef().add(clef);
        clef.setSign(ClefSign.G);
        clef.setLine(new BigInteger("2"));

        measure.getNoteOrBackupOrForward().addAll(notes);
    }

    private void mapValuesInsert() {
        noteTypeMap.clear();
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
            noteTypeMap.put("half..", (int) (3.5*division));
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

    void changeDivision(int num) {
        division *= num;
        // reInsert the values in the map to add those supported by the new measure division
        mapValuesInsert();
        // change the duration of all notes according to the new corresponding to the new division value
        noteList.forEach(note -> {
            int newValue = note.getDuration().intValue();
            newValue *= num;
            note.setDuration(new BigDecimal(newValue));
        });
    }

    int getIndex() {
        ListIterator<Note> it = noteList.listIterator(noteList.size());
        while (it.hasPrevious())
            if (((ExtendedNote) it.previous()).canGetTime())
                return it.nextIndex();
        throw new NullPointerException("Couldn't find a Note that canGetTime()");
    }

    @NotNull
    private static @UnmodifiableView Map<String, ByzClass> getByzClassMap() {
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

    @Nullable Step getLastNoteStep() {
        if (noteList.size()<1) return null;
        ListIterator<Note> noteListIterator = noteList.listIterator(noteList.size());
        while (noteListIterator.hasPrevious()) {
            Note note = noteListIterator.previous();
            if (note.getPitch() != null)
                if (note.getPitch().getStep() != null)
                    return note.getPitch().getStep();
        }
        return null;
    }

    Step getInitialStep() {
        return initialStep;
    }

    Integer putFthoraScale(ByzScale k, Integer v) {
        return fthoraScalesMap.put(k, v);
    }

    int getNoteListSize() {
        return noteList.size();
    }
}
