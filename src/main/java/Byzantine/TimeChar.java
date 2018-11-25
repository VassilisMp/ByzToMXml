package Byzantine;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.audiveris.proxymusic.EmptyPlacement;
import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.NoteType;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;

public class TimeChar extends ByzChar{
    private static final long serialVersionUID = 6353312089523385239L;
    // measure division must be at least 2, or else I 'll have to implement the case of division change, in the argo case as well..
    // division must be <= 16383
    static int division = 2;
    private final static BiMap<String, Integer> noteTypeMap = HashBiMap.create(14);
    static {
        mapValuesInsert();
    }

    int dotPlace;
    int divisions;
    Boolean argo;

    TimeChar(int codePoint, String font, Byzantine.ByzClass byzClass, int dotPlace, int divisions, Boolean argo) {
        super(codePoint, font, byzClass);
        this.dotPlace = dotPlace;
        this.divisions = divisions;
        this.argo = argo;
    }

    @Override
    public String toString() {
        return "TimeChar{" +
                "dotPlace=" + dotPlace +
                ", divisions=" + divisions +
                ", argo=" + argo +
                ", ByzClass=" + ByzClass +
                ", codePoint=" + codePoint +
                '}';
    }

    @Override
    public void run() throws NullPointerException {
        if (divisions > 0 && !argo) {
            int index = getIndex();
            List<Note> subList = Main.noteList.subList(index - 1, index + divisions);
            // If division is even, it means there aren't dotted notes
            // implementation for case of gorgon, trigorgon, pentagorgon...
            if (divisions % 2 == 1 && dotPlace == 0) {
                for (Note n : subList) {
                    int duration = n.getDuration().intValue();
                    int timeUnit = division/(divisions+1);
                    duration -= division;
                    duration += timeUnit;
                    // in this case it means the division being used in the measure isn't enough to describe smaller NoteTypes
                    // so division change follows
                    if (timeUnit == 0) {
                        // change the measure division, so it can describe smaller notes
                        division *= divisions+1;
                        // reInsert the values in the map to add those supported by the new measure division
                        mapValuesInsert();
                        // change the duration of all notes according to the new corresponding values of the NoteTypes
                        int finalDuration = duration;
                        Main.noteList.forEach(N -> {
                            NoteType noteType = N.getType();
                            String newNoteType = noteTypeMap.inverse().get(finalDuration);
                            if (newNoteType == null)
                                for (int i = 1; newNoteType == null; i++)
                                    newNoteType = noteTypeMap.inverse().get(finalDuration - i);
                            noteType.setValue(newNoteType);
                            int newValue = noteTypeMap.get(noteType.getValue());
                            if (N.getDot().size() > 0) {
                                int nextDotDuration = newValue/2;
                                int TotalDotsDuration = 0;
                                for (int i = 0; i < N.getDot().size(); i++) {
                                    TotalDotsDuration += nextDotDuration;
                                    nextDotDuration /= 2;
                                }
                                newValue += TotalDotsDuration;
                            }
                            N.setDuration(new BigDecimal(newValue));
                        });
                        System.out.println(Main.noteList);
                        // the duration is recalculated after measure division has changed and affected this note too.
                        duration = n.getDuration().intValue();
                        timeUnit = division/(divisions+1);
                        duration -= division;
                        duration += timeUnit;
                    }
                    // Set the new Duration
                    n.setDuration(new BigDecimal(duration));
                    // Get current NoteType
                    NoteType noteType = n.getType();
                    // Set the new NoteType
                    // get the type from the corresponding value of the inverse map given the duration
                    String newNoteType = noteTypeMap.inverse().get(duration);
                    noteType.setValue(newNoteType);
                }
            }
            System.out.println(Main.noteList);
        } else if (argo) {
            int index = getIndex();
            List<Note> subList = Main.noteList.subList(index - 1, index + 1);
            //System.out.println(subList);
            Note note1 = subList.get(0);
            int duration = note1.getDuration().intValue();
            duration /= 2;
            note1.setDuration(new BigDecimal(duration));
            // Get current NoteType
            NoteType noteType1 = note1.getType();
            // Set the new NoteType
            // get the type from the corresponding value of the inverse map given the duration
            String newNoteType1 = noteTypeMap.inverse().get(duration);
            noteType1.setValue(newNoteType1);

            //note2
            Note note2 = subList.get(1);
            duration = note2.getDuration().intValue();
            duration /= 2;
            duration += divisions * division;
            note2.setDuration(new BigDecimal(duration));
            // Get current NoteType
            NoteType noteType2 = note2.getType();
            // Set the new NoteType
            // get the type from the corresponding value of the inverse map given the duration
            String newNoteType2 = noteTypeMap.inverse().get(duration);
            if (newNoteType2 == null) {
                for (int i = 1; newNoteType2 == null; i++) {
                    newNoteType2 = noteTypeMap.inverse().get(duration - i);
                }
                if (note2.getDot().size() == 0)
                    note2.getDot().add(new EmptyPlacement());
            }
            noteType2.setValue(newNoteType2);
        }

    }

    private int getIndex() {
        ListIterator<Note> iterator = Main.noteList.listIterator(Main.noteList.size());
        Note note = null;
        int index = 0;
        while (iterator.hasPrevious()) {
            index = iterator.previousIndex();
            ExtendedNote exNote = (ExtendedNote) iterator.previous();
            if (exNote.canGetTime()) {
                note = exNote;
                break;
            }
        }
        if (note == null)
            throw new NullPointerException("Couldn't find a Note that canGetTime()");
        return index;
    }

    private static void mapValuesInsert() {
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
    }
}
