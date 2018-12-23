package Byzantine;

import com.google.common.collect.ArrayListMultimap;
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

import static org.audiveris.proxymusic.util.Marshalling.getContext;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static List<Note> noteList;

    @Test
    void QuantityCharTest() throws NotSupportedException {
        List<UnicodeChar> docChars = new ArrayList<>();

        Main.noteList = new ArrayList<>();
        noteList = Main.noteList;

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

        XWPFDocument docx = null;
        try {
            docx = new XWPFDocument(new FileInputStream("elpiza.docx"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Coudln't open document");
            return;
        }
        // 0 - 1264 chars using toCharArray
        // TODO maybe case of surrogate pair chars
        int pos = 0;
        for (XWPFParagraph paragraph : docx.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                /*for (int i = 0; i < run.text().length(); i++) {
                    int codePoint = run.text().codePointAt(i);
                    System.out.println(codePoint + "  " + pos);
                    if (codePoint > 0xFFFF) {
                        System.out.println("surrogate");
                        ++i;
                        return;
                    }
                    pos++;
                }*/
                for (char c : run.text().toCharArray()) {
                    String fontName = run.getFontName();
                    int charInt = (int)c;
                    ByzClass byzClass = map.get(fontName);
                    if (byzClass != null) {
                        try {
                            boolean annotationPresent = ByzClass.class.getField(byzClass.toString()).isAnnotationPresent(NotSupported.class);
                            //System.out.println(byzClass + " annotation isPresent: " + annotationPresent);
                            if (annotationPresent) {
                                continue;
                                //throw new NotSupportedException("document contains ByzClass." + byzClass + " character which is not supported");
                            }
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        if(charInt>0xEFFF) // && <0xF8FF because Unicode Private Use Area is, U+E000 to U+F8FF
                            charInt-=0xF000;
                        if(charInt<33 || charInt>255)
                            continue;
                    } else {
                        continue;
                    }
                    int finalCharInt = charInt;
                    UnicodeChar unicodeChar = charList.stream()
                            .filter(Char -> Char.codePoint == finalCharInt && ((ByzChar) Char).ByzClass == byzClass)
                            .findAny()
                            .orElse(null);
                    if (unicodeChar == null) {
                        System.out.println(String.format("%5d", charInt) + " The character at " + String.format("%4d", pos) + " is " + c + "   " + fontName + " " + unicodeChar);
                        continue;
                    }
                    unicodeChar.setFont(String.valueOf(c));
                    docChars.add(unicodeChar);
                    //System.out.println(String.format("%5d", charInt) + " The character at " + String.format("%4d", pos) + " is " + c + "   " + fontName + " " + unicodeChar);
                    /*if (unicodeChar instanceof QuantityChar) {
                        unicodeChar.run();
                        //System.out.println(unicodeChar);
                    }*/
                    //System.out.println(String.format("%5d", charInt) + " The character at " + String.format("%4d", pos) + " is " + c + "   " + fontName);
                    //System.out.println(String.format("%5d", charInt) + " The character at " + String.format("%4d", pos) + " is " + c + "   " + unicodeChar);
                    pos++;
                }
            }
        }
        // case ISON -> KENTIMA replace with one char
        QuantityChar qCharKentima = new QuantityChar(67, "\uF061", ByzClass.B, new Move(2, false, false));
        QuantityChar qCharOligon = new QuantityChar(115, "\uF073", ByzClass.B, new Move(1, true, true));
        for (int i = 0, docCharsSize = docChars.size(); i < docCharsSize; i++) {
            UnicodeChar docChar = docChars.get(i);
            if (docChar.equals(qCharKentima)) {
                /* TODO docChars.get(i-1) won't work when the list has all types of chars, need to find previous,
                 * QuantityChar and then check if equals(qCharOligon)  */
                if (docChars.get(i-1).equals(qCharOligon)) {
                    docChars.set(i-1, new QuantityChar(100, "\uF064", ByzClass.B, new Move(2, true, true)));
                    docChars.remove(i);
                    docCharsSize = docChars.size();
                }
            }
        }
        // 81 ison before high Nh martyria
        int thesi = 0;
        for (int i = 0; i < docChars.size(); i++) {
            UnicodeChar Char = docChars.get(i);
                System.out.println(thesi + " " + Char);
                thesi++;
                Char.run();
            //System.out.println(unicodeChar);
            //System.out.println(i + " " + Char);
        }
        for (int i = 0, noteListSize = noteList.size(); i < noteListSize; i++) {
            Note note1 = noteList.get(i);
            System.out.println(i + " " + note1);
        }

        noteList.remove(0);

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream("test.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            ScorePartwise scorePartwise = toScorePartwise();
            Marshaller marshaller = getContext(ScorePartwise.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(scorePartwise, fileOutputStream);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* TODO Palaia fonts not supported
    * because of different character matching to Byzantine fonts
    * maybe I'll have to make the matching*/
    @Contract(" -> new")
    @NotNull
    private static Map<String, ByzClass> getMap() {
        ByzClass B = ByzClass.B; // Byzantine
        ByzClass F = ByzClass.F; // Fthores
        ByzClass I = ByzClass.I; // Ison
        ByzClass L = ByzClass.L; // Loipa
        ByzClass P = ByzClass.P; // Palaia
        ByzClass X = ByzClass.X; // Xronos
        ByzClass A = ByzClass.A; // Arxigramma
        ByzClass N = ByzClass.N; // I dont remember, font with various chars..
        ByzClass T = ByzClass.T; // Text fonts
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
    private static ScorePartwise toScorePartwise() {
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
        key.setFifths(new BigInteger("0"));

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

        return scorePartwise;
    }

}