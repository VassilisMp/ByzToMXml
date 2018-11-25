package Byzantine;

import java.io.*;
import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.audiveris.proxymusic.*;
import org.audiveris.proxymusic.ScorePartwise.Part;
import org.audiveris.proxymusic.ScorePartwise.Part.Measure;
import static org.audiveris.proxymusic.util.Marshalling.getContext;

import org.audiveris.proxymusic.util.Marshalling;
import org.audiveris.proxymusic.util.ProgramId;

import javax.xml.bind.Marshaller;


public class Main {
    static List<Note> noteList;

    public static void main(String[] args) {

        noteList = new ArrayList<>();

        // Note 0 ---
        Note note = new ExtendedNote(true, true);
        noteList.add(note);

        // Pitch
        Pitch pitch = new Pitch();
        note.setPitch(pitch);
        pitch.setStep(Step.C);
        pitch.setOctave(4);

        // Duration
        note.setDuration(new BigDecimal(TimeChar.division));

        // Type
        NoteType type = new NoteType();
        type.setValue("quarter");
        note.setType(type);

        Runnable qChar = new QuantityChar(225, "", ByzClass.B, Arrays.asList(
                new Move(2, false, true),
                new Move(3, true, false),
                new Move(3, true, false),
                new Move(3, true, false)
        ));
        qChar.run();

        System.out.println(noteList);

        Runnable tChar = new TimeChar(234, "", ByzClass.B, 0, 1, true);
        tChar.run();

        System.out.println(noteList);

        /*
        XWPFDocument docx = new XWPFDocument(new FileInputStream("a.docx"));
        int pos = 0;
        for (XWPFParagraph paragraph : docx.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                for (char c : run.text().toCharArray()) {
                    String fontName = run.getFontName();
                    int charInt = (int)c;
                    if(charInt>61439)
                        charInt-=61440;
                    if(charInt<33 || charInt>255)
                        continue;
                    //System.out.println(String.format("%5d", charInt) + " The character at " + String.format("%4d", pos) + " is " + c + "   " + fontName);
                    pos++;
                }
            }
        }
        */

        /*
        try {
            ScorePartwise scorePartwise = getScorePartwise();
            Marshaller marshaller = getContext(ScorePartwise.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(scorePartwise, System.out);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        */
    }

    private static void insertChars() {
        List<UnicodeChar> charList = new ArrayList<UnicodeChar>();


        try {
            FileInputStream fileIn = new FileInputStream("lis.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            charList = (List<UnicodeChar>) in.readObject();
            in.close();
            fileIn.close();
        }catch (IOException e) {
            e.printStackTrace();
            return;
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        System.out.println(charList);

        Boolean next;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print("new Char input: ");
            String charCode = sc.next();
            Boolean ok;
            do {
                ok = true;
                System.out.println("choose between: 'q'uantity, 'x'ronos, 'm'ixed");
                String option = sc.next();
                switch (option.charAt(0)) {
                    case 'q':
                        charList.add(QChar(sc, charCode));
                        break;
                    case 'x':
                        charList.add(TChar(sc, charCode));
                        break;
                    case 'm':
                        charList.add(MChar(sc, charCode));
                        break;
                    default:
                        System.out.println("option out of options");
                        ok = false;
                }
            } while (!ok);
            System.out.print("add more ByzChars(true or false) : ");
            next = sc.nextBoolean();
        } while(next);
        System.out.println(charList);

        try {
            FileOutputStream fileOut = new FileOutputStream("lis.obj");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(charList);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static QuantityChar QChar(Scanner sc, String charCode) {
        List<Move> moves = new ArrayList<Move>();
        Boolean choose;
        do {
            System.out.print("move(integer): ");
            int moveStep = sc.nextInt();
            System.out.print("lyric(true||false): ");
            Boolean lyric = sc.nextBoolean();
            System.out.print("time(true||false): ");
            Boolean time = sc.nextBoolean();
            Move move = new Move(moveStep, lyric, time);
            moves.add(move);
            System.out.print("add move(true||false): ");
            choose = sc.nextBoolean();
        } while (choose);
        int codePoint = Integer.parseInt(charCode.substring(1, charCode.length()));
        return new QuantityChar(codePoint, "", ByzClass.valueOf(charCode.charAt(0) + ""), moves);
    }

    private static TimeChar TChar(Scanner sc, String charCode) {
        System.out.print("argo: ");
        Boolean argo = sc.nextBoolean();
        System.out.print("divisions: ");
        int divisions = sc.nextInt();
        System.out.print("dotPlace: ");
        int dotPlace = sc.nextInt();
        int codePoint = Integer.parseInt(charCode.substring(1, charCode.length()));
        return new TimeChar(codePoint, "", ByzClass.valueOf(charCode.charAt(0) + ""), dotPlace, divisions, argo);
    }

    private static MixedChar MChar(Scanner sc, String charCode) {
        List<ByzChar> ByzChars = new ArrayList<ByzChar>();
        Boolean next;
        System.out.println("contents of MixedChar:");
        do {
            System.out.println("choose between: 'q'uantity, 'x'ronos");
            String option = sc.next();
            switch (option.charAt(0)) {
                case 'q':
                    ByzChars.add(QChar(sc, charCode));
                    break;
                case 'x':
                    ByzChars.add(TChar(sc, charCode));
                    break;
            }
            System.out.print("add more ByzChars in this MixedChar: true or false");
            next = sc.nextBoolean();
        } while(next);
        int codePoint = Integer.parseInt(charCode.substring(1, charCode.length()));
        return new MixedChar(codePoint, "", ByzClass.valueOf(charCode.charAt(0) + ""), ByzChars);
    }

    private static ScorePartwise getScorePartwise () {
        // Generated factory for all proxymusic elements
        ObjectFactory factory = new ObjectFactory();

        // Allocate the score partwise
        ScorePartwise scorePartwise = factory.createScorePartwise();

        /*{
            // Work
            Work work = factory.createWork();
            scorePartwise.setWork(work);
            work.setWorkTitle("Title for the work");
            work.setWorkNumber("Number for the work");

            // Work::Opus
            Opus opus = factory.createOpus();
            work.setOpus(opus);
            opus.setHref("Href to opus");
            opus.setType("simple");
            opus.setRole("Role of opus"); // Some text
            opus.setTitle("Title of opus"); // Some text
            opus.setShow("new"); // new, replace, embed, other, none
            opus.setActuate("onLoad"); // onRequest, onLoad, other, none
        }*/

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
        Measure measure = factory.createScorePartwisePartMeasure();
        part.getMeasure().add(measure);
        measure.setNumber("1");

        // Attributes
        Attributes attributes = factory.createAttributes();
        measure.getNoteOrBackupOrForward().add(attributes);

        // Divisions
        attributes.setDivisions(new BigDecimal(1));

        // Key
        Key key = factory.createKey();
        attributes.getKey().add(key);
        key.setFifths(new BigInteger("0"));

        // Time
        Time time = factory.createTime();
        attributes.getTime().add(time);
        time.getTimeSignature().add(factory.createTimeBeats("4"));
        time.getTimeSignature().add(factory.createTimeBeatType("4"));

        // Clef
        Clef clef = factory.createClef();
        attributes.getClef().add(clef);
        clef.setSign(ClefSign.G);
        clef.setLine(new BigInteger("2"));

        // Note 0 ---
        Note note = factory.createNote();
        measure.getNoteOrBackupOrForward().add(note);

        // Pitch
        Pitch pitch = factory.createPitch();
        note.setPitch(pitch);
        pitch.setStep(Step.C);
        pitch.setOctave(4);

        // Duration
        note.setDuration(new BigDecimal(4));

        // Type
        NoteType type = factory.createNoteType();
        type.setValue("whole");
        note.setType(type);

        // Note 1 ---
        note = factory.createNote();
        measure.getNoteOrBackupOrForward().add(note);
        note.setChord(new Empty());

        // Pitch
        pitch = factory.createPitch();
        note.setPitch(pitch);
        pitch.setStep(Step.E);
        pitch.setOctave(4);

        // Duration
        note.setDuration(new BigDecimal(4));

        // Type
        type = factory.createNoteType();
        type.setValue("whole");
        note.setType(type);

        // Note 2 ---
        note = factory.createNote();
        measure.getNoteOrBackupOrForward().add(note);
        note.setChord(new Empty());

        // Pitch
        pitch = factory.createPitch();
        note.setPitch(pitch);
        pitch.setStep(Step.G);
        pitch.setOctave(4);

        // Duration
        note.setDuration(new BigDecimal(4));

        // Type
        type = factory.createNoteType();
        type.setValue("whole");
        note.setType(type);

        return scorePartwise;
    }
}
