package Byzantine;

import com.google.common.collect.Lists;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.audiveris.proxymusic.*;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.Marshaller;
import java.io.*;
import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static Byzantine.FthoraChar.HARD_CHROMATIC;
import static Byzantine.FthoraChar.HARD_DIATONIC;
import static org.audiveris.proxymusic.util.Marshalling.getContext;

public class Engine {
    private int timeBeats;
    private List<Note> noteList;
    private List<UnicodeChar> docChars;
    private final XWPFDocument docx;
    private final List<UnicodeChar> charList = getCharList();
    private final Map<String, ByzClass> map = getMap();

    @NotNull
    private static Map<String, ByzClass> getMap() {
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

    private static List<UnicodeChar> getCharList() {
        try (FileInputStream fileIn = new FileInputStream("lis.obj");
             ObjectInputStream in = new ObjectInputStream(fileIn)){
            return Collections.unmodifiableList((List<UnicodeChar>) in.readObject());
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }

    public Engine(String filePath) throws FileNotFoundException, IOException {
        this.docx = new XWPFDocument(new FileInputStream(filePath));
        this.noteList = new ArrayList<>();
        this.docChars = new ArrayList<>();
        // Note 0 ---
        Note note = new ExtendedNote(true, true);
        noteList.add(note);

        // Pitch
        Pitch pitch = new Pitch();
        note.setPitch(pitch);
        pitch.setStep(Step.A);
        pitch.setOctave(4);

        // Duration
        note.setDuration(new BigDecimal(TimeChar.division));

        // Type
        NoteType type = new NoteType();
        type.setValue("quarter");
        note.setType(type);
    }

    public Engine setTimeBeats(int timeBeats) {
        this.timeBeats = timeBeats;
        return this;
    }

    public void run() {
        // 0 - 1264 chars using toCharArray
        int pos = 0;
        // if there is lyric char before Quantity Char but belongs to that
        boolean qCharAdded = false;
        boolean mCharAdded = false;
        StringBuilder sb = new StringBuilder();
        for (XWPFParagraph paragraph : docx.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                for (char c : run.text().toCharArray()) {
                    pos++;
                    String fontName = run.getFontName();
                    ByzClass byzClass = map.get(fontName);
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
                        Class clas = qCharAdded ? QuantityChar.class : MixedChar.class;
                        qCharAdded = mCharAdded = false;
                        // find the last QChar which may also be in a MixedChar
                        Lists.reverse(docChars).stream()
                                .filter(clas::isInstance)
                                .findFirst()
                                .ifPresent(Char -> {
                                    // if MixedChar then find the last QChar in the MixedChar
                                    if (clas == MixedChar.class) {
                                        ByzChar[] chars = ((MixedChar) Char).getChars();
                                        for (int i = chars.length - 1; i >= 0; i--)
                                            if (chars[i] instanceof QuantityChar) {
                                                Char = chars[i];
                                                break;
                                            }
                                    }
                                    // set last Char lyrics
                                    Char.text = sb.toString();
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
                                continue;
                                //throw new NotSupportedException("document contains ByzClass." + byzClass + " character which is not supported");
                            }
                        } catch (NoSuchFieldException e) { e.printStackTrace(); }
                        // if c<33 || c>255 means the Char isn't in the area of ByzChars in the Unicode
                        if(c<33 || c>255) continue;
                    } else continue;
                    final char finalChar = c;
                    // check if current Char is in the ByzCharList
                    UnicodeChar unicodeChar = charList.stream()
                            .filter(Char -> Char.codePoint == finalChar && ((ByzChar) Char).ByzClass == byzClass)
                            .findAny()
                            .orElse(null);
                    //System.out.println(String.format("%5d", charInt) + " The character at " + String.format("%4d", pos) + " is " + c + "   " + fontName + "  " + byzClass);
                    // if doesn't exist continue to next
                    if (unicodeChar == null) continue;
                    // else clone and add in the docChars
                    UnicodeChar clone = Cloner.deepClone(unicodeChar);
                    clone.font = fontName;
                    docChars.add(clone);
                    if (unicodeChar instanceof QuantityChar) qCharAdded = true;
                    else if (unicodeChar instanceof MixedChar)
                        mCharAdded =  Arrays.stream(((MixedChar) unicodeChar).getChars())
                                .anyMatch(Char -> Char instanceof QuantityChar);
                }
            }
        }
        IsonKentimaReplace();
        fixL116();
        TimeChar tChar = null;
        // if TimeChar divisions is bigger than one which means that next QuantityChar must be added before running this TimeChar
        boolean isTcharDiv2p = false;
        for (int i = 0; i < docChars.size(); i++) {
            if (i == 192) {
                System.out.println("check");
            }
            UnicodeChar Char = docChars.get(i);
            if (Char instanceof TimeChar) {
                tChar = (TimeChar) Char;
                if (tChar.getDivisions()>1 && !tChar.getArgo()) {
                    while(!(Char instanceof QuantityChar)) {
                        i++;
                        Char = docChars.get(i);
                        if (Char instanceof QuantityChar) {
                            QuantityChar qChar = (QuantityChar) Char;
                            System.out.println(i + " " + Char);
                            Move[] movesClone = Cloner.deepClone(qChar.getMoves());
                            Arrays.stream(qChar.getMoves()).forEach(move -> move.setTime(false));
                            qChar.accept(noteList);
                            tChar.accept(noteList);
                            List<Note> notes = noteList.subList(noteList.size() - movesClone.length, noteList.size());
                            for (int i1 = 0; i1 < notes.size(); i1++) {
                                Note note = notes.get(i1);
                                if (note instanceof ExtendedNote) {
                                    ExtendedNote exNote = (ExtendedNote) note;
                                    exNote.setTime(movesClone[i1].getTime());
                                }
                            }
                            continue;
                        }
                        Char.accept(noteList);
                    }
                }
            }
            System.out.println(i + " " + Char);
            Char.accept(noteList);
            //System.out.println(unicodeChar);
            //System.out.println(i + " " + Char);
        }
        // remove first note which was auxiliary
        noteList.remove(0);
        for (int i = 0, noteListSize = noteList.size(); i < noteListSize; i++) {
            Note note1 = noteList.get(i);
            System.out.println(i + " " + note1);
        }


        try(FileOutputStream fileOutputStream = new FileOutputStream("test.xml")) {
            ScorePartwise scorePartwise = toScorePartwise();
            Marshaller marshaller = getContext(ScorePartwise.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(scorePartwise, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TimeChar.noteTypeMap.forEach((str, integer) -> System.out.println(str + " : " + integer));
    }

    // replaces L116 followed of two gorga, with working sequence
    private void fixL116() {
        long start = System.nanoTime();
        for (int i = 0; i < docChars.size(); i++) {
            UnicodeChar Char = docChars.get(i);
            if (UnicodeChars.equals(Char, 116, ByzClass.L)) {
                UnicodeChar Char1 = docChars.get(i+1);
                if (UnicodeChars.isGorgonOrArgo(Char1)) {
                    UnicodeChar Char2 = docChars.get(i + 2);
                    if (UnicodeChars.isGorgonOrArgo(Char2)) {
                        for (int j = 0; j < 3; j++) {
                            docChars.remove(i);
                        }
                        List<UnicodeChar> replace =
                                Arrays.asList(
                                        new QuantityChar(39, "", ByzClass.B,
                                                new Move(-1, true, true),
                                                new Move(-1, true, false)
                                        ),
                                        Char1,
                                        new QuantityChar(120, "", ByzClass.B,
                                                new Move(1, false, true)
                                        ),
                                        Char2
                                );
                        docChars.addAll(i, replace);
                    }
                }
            }
        }
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println("timeElapsed" + timeElapsed);
    }

    private void IsonKentimaReplace() {
        // case ISON -> KENTIMA replace with one char
        QuantityChar qCharKentima = new QuantityChar(67, "\uF061", ByzClass.B, new Move(2, false, false));
        QuantityChar qCharOligon = new QuantityChar(115, "\uF073", ByzClass.B, new Move(1, true, true));
        for (int i = 0, docCharsSize = docChars.size(); i < docCharsSize; i++) {
            UnicodeChar docChar = docChars.get(i);
            if (docChar.equals(qCharKentima)) {
                /* TODO docChars.get(i-1) might not work when the list has all types of chars, need to find previous,
                 * QuantityChar and then check if equals(qCharOligon)  */
                if (docChars.get(i-1).equals(qCharOligon)) {
                    docChars.set(i-1, new QuantityChar(100, "\uF064", ByzClass.B, new Move(2, true, true)));
                    docChars.remove(i);
                    docCharsSize = docChars.size();
                }
            }
        }
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

    private void workingTest1Measure(ObjectFactory factory, ScorePartwise.Part part) {
        // Measure
        ScorePartwise.Part.Measure measure = factory.createScorePartwisePartMeasure();
        part.getMeasure().add(measure);
        measure.setNumber("1");

        // Attributes
        Attributes attributes = factory.createAttributes();
        measure.getNoteOrBackupOrForward().add(attributes);

        // Divisions
        attributes.setDivisions(new BigDecimal(TimeChar.division));

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
    }

    private void addMeasures(ObjectFactory factory, ScorePartwise.Part part, int timeBeats) throws Exception {
        // make lists of Notes according to the given timeBeats
        ArrayList<List<Note>> noteLists = new ArrayList<>();
        if (timeBeats > 0)
            for (int i = 0, noteListSize = noteList.size(), index = i, durations = 0; i < noteListSize; i++) {
                //measure.getNoteOrBackupOrForward().add(note);
                Note note = noteList.get(i);
                durations += note.getDuration().intValue();
                if (durations == (TimeChar.division * timeBeats)) {
                    noteLists.add(noteList.subList(index, i+1));
                    index = i+1;
                    durations = 0;
                }else if (durations > (TimeChar.division * timeBeats)){
                    throw new Exception("error in noteLists");
                }
            }
        else { // TODO finish this, find the right notetypes
            Note note2 = null;
            List<Note> subList = null;
            boolean flag = true;
            for (int i = 0, noteListSize = noteList.size(), index = i, durations = 0; i < noteListSize; i++) {
                //measure.getNoteOrBackupOrForward().add(note);
                System.out.println(i);
                if (i == 209) {
                    System.out.println();
                }
                if (note2 != null && flag) {
                    durations += note2.getDuration().intValue();
                    subList = new ArrayList<>();
                    subList.add(note2);
                    flag = false;
                }
                Note note = noteList.get(i);
                durations += note.getDuration().intValue();
                if (durations == (TimeChar.division * 8)) {
                    if (subList == null) {
                        subList = noteList.subList(index, i+1);
                    } else subList.addAll(noteList.subList(index, i+1));
                    noteLists.add(subList);
                    index = i+1;
                    durations = 0;
                    note2 = null;
                    subList = null;
                }else if (durations > (TimeChar.division * 8)){
                    if (subList == null) {
                        subList = noteList.subList(index, i+1);
                    } else subList.addAll(noteList.subList(index, i+1));
                    Note note1 = subList.get(subList.size()-1);
                    String noteType1;
                    String noteType2;
                    int surplus;
                    while(true) {
                        int duration = note1.getDuration().intValueExact();
                        surplus = durations - (TimeChar.division * 8);//(TimeChar.division * 8) - (durations - duration);
                        note1.setDuration(BigDecimal.valueOf(duration - surplus));
                        noteType1 = TimeChar.noteTypeMap.inverse().get(duration - surplus);
                        noteType2 = TimeChar.noteTypeMap.inverse().get(surplus);
                        if (noteType1 == null || noteType2 == null) {
                            TimeChar.changeDivision(2, noteList);
                            durations *= 2;
                        } else break;
                    } // clone Note after loop because duration might change after potential call of TimeChar.changeDivision() method
                    note2 = Cloner.deepClone(note1);
                    note2.setDuration(BigDecimal.valueOf(surplus));
                    note1.getType().setValue(noteType1);
                    note2.getType().setValue(noteType2);
                    noteLists.add(subList);
                    index = i+1;
                    durations = 0;
                    flag = true;
                    subList = null;
                }
            }
        }

        ArrayList<ScorePartwise.Part.Measure> measures = new ArrayList<>(noteLists.size());
        for (int i = 1, noteListsSize = noteLists.size(); i < noteListsSize; i++) {
            List<Note> notesList = noteLists.get(i);
            ScorePartwise.Part.Measure partMeasure = factory.createScorePartwisePartMeasure();
            partMeasure.setNumber(String.valueOf(i+1));
            partMeasure.getNoteOrBackupOrForward().addAll(notesList);
            measures.add(partMeasure);
        }

        addFirstMeasure(factory, part, noteLists.get(0));
        part.getMeasure().addAll(measures);
    }

    private static void addFirstMeasure(ObjectFactory factory, ScorePartwise.Part part, List<Note> notes) throws Exception {
        // Measure
        ScorePartwise.Part.Measure measure = factory.createScorePartwisePartMeasure();
        part.getMeasure().add(measure);
        measure.setNumber("1");

        // Attributes
        Attributes attributes = factory.createAttributes();
        measure.getNoteOrBackupOrForward().add(attributes);

        // Divisions
        attributes.setDivisions(new BigDecimal(TimeChar.division));

        /*// Key
        Key key = factory.createKey();
        attributes.getKey().add(key);
        //key.setFifths(new BigInteger("-1"));
        key.getNonTraditionalKey().addAll(Arrays.asList(Step.B, BigDecimal.valueOf(-1), AccidentalValue.FLAT));*/

        List<PitchEntry> thisC = PitchEntry.ListByStep(HARD_DIATONIC, Step.A);
        PitchEntry.FthoraApply(thisC, HARD_CHROMATIC);
        Key key = PitchEntry.KeyFromPitches(HARD_DIATONIC);
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
}
