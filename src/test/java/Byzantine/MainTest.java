package Byzantine;

import com.google.common.collect.Lists;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.audiveris.proxymusic.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import javax.xml.bind.Marshaller;
import java.io.*;
import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static Byzantine.FthoraChar.HARD_DIATONIC;
import static Byzantine.FthoraChar.HARD_CHROMATIC;
import static org.audiveris.proxymusic.util.Marshalling.getContext;

class MainTest {

    private static List<Note> noteList;
    List<UnicodeChar> docChars = new ArrayList<>();

    @Test
    void QuantityCharTest() throws NotSupportedException {

        Main.noteList = new ArrayList<>();
        noteList = Main.noteList;

        {
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

        Map<String, ByzClass> map = Collections.unmodifiableMap(getMap());

        List<UnicodeChar> charList;
        try {
            FileInputStream fileIn = new FileInputStream("lis.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            charList = (List<UnicodeChar>) in.readObject();
            in.close();
            fileIn.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        XWPFDocument docx;
        try {
            docx = new XWPFDocument(new FileInputStream("a.docx"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Coudln't open document");
            return;
        }
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
                    if (c>=0x0370 && c<=0x03FF) {
                        sb.append(c);
                        continue;
                    }
                    if (sb.length() > 0 && (qCharAdded || mCharAdded)) {
                        Class clas = qCharAdded ? QuantityChar.class : MixedChar.class;
                        qCharAdded = mCharAdded = false;
                        Lists.reverse(docChars).stream()
                                .filter(clas::isInstance)
                                .findFirst()
                                .ifPresent(Char -> {
                                    if (clas == MixedChar.class) {
                                        ByzChar[] chars = ((MixedChar) Char).getChars();
                                        for (int i = chars.length - 1; i >= 0; i--)
                                            if (chars[i] instanceof QuantityChar) {
                                                Char = chars[i];
                                                break;
                                            }
                                    }
                                    Char.text = sb.toString();
                                });
                        sb.setLength(0);
                    }
                    if (byzClass != null) {
                        try {
                            boolean annotationPresent = ByzClass.class.getField(byzClass.toString()).isAnnotationPresent(NotSupported.class);
                            //System.out.println(byzClass + " annotation isPresent: " + annotationPresent);
                            if (annotationPresent) {
                                continue;
                                //throw new NotSupportedException("document contains ByzClass." + byzClass + " character which is not supported");
                            }
                        } catch (NoSuchFieldException e) { e.printStackTrace(); }
                        if(c<33 || c>255) continue;
                    } else continue;
                    final char finalChar = c;
                    UnicodeChar unicodeChar = charList.stream()
                            .filter(Char -> Char.codePoint == finalChar && ((ByzChar) Char).ByzClass == byzClass)
                            .findAny()
                            .orElse(null);
                    //System.out.println(String.format("%5d", charInt) + " The character at " + String.format("%4d", pos) + " is " + c + "   " + fontName + "  " + byzClass);
                    if (unicodeChar == null) continue;
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
        IsonKentimaReplace(docChars);
        fixL116(docChars);
        TimeChar tChar = null;
        // if TimeChar divisions is biger than one which means that next QuantityChar must be added before running this TimeChar
        boolean isTcharDiv2p = false;
        for (int i = 0; i < docChars.size(); i++) {
            if (i == 192) {
                System.out.println("check");
            }
            UnicodeChar Char = docChars.get(i);
            if (Char instanceof TimeChar) {
                tChar = (TimeChar) Char;
                if (tChar.getDivisions()>1) {
                    while(!(Char instanceof QuantityChar)) {
                        i++;
                        Char = docChars.get(i);
                        if (Char instanceof QuantityChar) {
                            QuantityChar qChar = (QuantityChar) Char;
                            System.out.println(i + " " + Char);
                            Move[] movesClone = Cloner.deepClone(qChar.getMoves());
                            Arrays.stream(qChar.getMoves()).forEach(move -> move.setTime(false));
                            qChar.run();
                            tChar.run();
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
                        Char.run();
                    }
                }
            }
            System.out.println(i + " " + Char);
            Char.run();
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
    }

    // replaces L116 followed of two gorga, with working sequence
    private static void fixL116(@NotNull List<UnicodeChar> docChars) {
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

    private void IsonKentimaReplace(@NotNull List<UnicodeChar> docChars) {
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

    /* TODO Palaia fonts not supported
    * because of different character matching to Byzantine fonts
    * maybe I'll have to make the matching*/
    @Contract(" -> new")
    @NotNull
    private static Map<String, ByzClass> getMap() {
        final ByzClass B = ByzClass.B; // Byzantine
        final ByzClass F = ByzClass.F; // Fthores
        final ByzClass I = ByzClass.I; // Ison
        final ByzClass L = ByzClass.L; // Loipa
        final ByzClass P = ByzClass.P; // Palaia
        final ByzClass X = ByzClass.X; // Xronos
        final ByzClass A = ByzClass.A; // Arxigramma
        final ByzClass N = ByzClass.N; // I dont remember, font with various chars..
        final ByzClass T = ByzClass.T; // Text fonts
        // TODO test all fonts
        return new HashMap<String, ByzClass>(){
            {
                put("PFKonstantinople", A); //T // TODO Run method for Arxigramma (probably same code with text fonts)
                put("BZ Byzantina", B);
                put("BZ Byzantina2", B);
                put("DP_NRByzantina", B);
                put("MKByzantine", B);
                put("BZ Fthores", F);
                put("DP_NRFthores", F);
                put("MKFthores", F);
                put("BZ Ison", I);
                put("DP_NRIson", I);
                put("MK Ison", I);
                put("MK Ison2", I);
                put("MKNewIson", I);
                put("BZ Loipa", L);
                put("DP_NRLoipa", L);
                put("MKLoipa", L);
                put("MK", N);//N //T // TODO not supported, matching differs to standard font types (example Byzantine, Fthores...)
                put("MK2015", N);//N //T // TODO not supported
                put("MK2017 design", N);//N // T // TODO not supported
                put("BZ Palaia", P); // TODO Palaia not supported
                put("DP_NRPalaia", P);
                put("MK Palaia", P);
                put("Genesis", T); // TODO Run method for text fonts
                put("GFSDidotClassic", T); // same
                put("GFSNicefore", T); // same
                put("MgAgiaSofia UC Normal", T); // same
                put("MgByzantine UC Pol Normal", T); // same
                put("BZ Xronos", X);
                put("DP_NRXronos", X);
                put("MK Xronos2016", X);
                put("MKXronos", X);
            }
        };
    }

    @Test
    private static ScorePartwise toScorePartwise() throws Exception {
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

        addMeasures(factory, part);

        return scorePartwise;
    }

    private static void workingTest1Measure(ObjectFactory factory, ScorePartwise.Part part) {
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

    private static void addMeasures(ObjectFactory factory, ScorePartwise.Part part/*, int timeBeats*/) throws Exception {
        ArrayList<List<Note>> noteLists = new ArrayList<>();
        for (int i = 0, noteListSize = noteList.size(), index = i, durations = 0; i < noteListSize; i++) {
            //measure.getNoteOrBackupOrForward().add(note);
            Note note = noteList.get(i);
            durations += note.getDuration().intValue();
            if (durations == (TimeChar.division * 4)) {
                noteLists.add(noteList.subList(index, i+1));
                index = i+1;
                durations = 0;
            }else if (durations > (TimeChar.division * 4)){
                //throw new Exception("error in noteLists");
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