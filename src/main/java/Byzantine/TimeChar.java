package Byzantine;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.audiveris.proxymusic.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckReturnValue;
import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public class TimeChar extends ByzChar{
    private static final long serialVersionUID = 6353312089523385239L;
    // measure division must be at least 2, or else I 'll have to implement the case of division change, in the argo case as well..
    // division must be <= 16383
    static int division = 1;
    private final static BiMap<String, Integer> noteTypeMap = HashBiMap.create(14);
    static {
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

    private int dotPlace;
    private int divisions;
    private Boolean argo;
    private static Integer tupletNum = 0;

    TimeChar(int codePoint, String font, Byzantine.ByzClass byzClass, int dotPlace, int divisions, Boolean argo) {
        super(codePoint, font, byzClass);
        this.dotPlace = dotPlace;
        this.divisions = divisions;
        this.argo = argo;
    }

    public int getDotPlace() {
        return dotPlace;
    }

    public int getDivisions() {
        return divisions;
    }

    public Boolean getArgo() {
        return argo;
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
            // get index of the last note that canGetTime
            int index = getIndex();
            List<Note> subList = Main.noteList.subList(index - 1, index + divisions);
            int addedTime;
            // in this case it means the division being used in the measure isn't enough to describe smaller NoteTypes
            // so variables are recalculated
            if (division%(divisions+1) != 0)
                addedTime = getNewAddedTime();
            else
                addedTime = division/(divisions+1);
            // If division is even, it means there aren't dotted notes
            // implementation for case of gorgon, trigorgon, pentagorgon...
            Note tieNote = null;
            if (dotPlace == 0) {
                for (int i = 0; i < subList.size(); i++) {
                    Note n = subList.get(i);
                    // get and set duration
                    int duration = setNewDuration(addedTime, n);
                    if (duration > division) {
                        tieNote = (Note) ((ExtendedNote)n).clone();
                        int durat = (duration/division) * division;
                        tieNote.setDuration(new BigDecimal(durat));
                        tieNote.getType().setValue(noteTypeMap.inverse().get(durat));
                        Tie tie = new Tie();
                        tie.setType(StartStop.START);
                        tieNote.getTie().add(tie);
                        tie = new Tie();
                        tie.setType(StartStop.STOP);
                        subList.get(i+1).getTie().add(tie);
                    }
                    // in this case tuplets are used
                    if (256 % (divisions + 1) != 0) {
                        TimeModification timeModification = new TimeModification();
                        timeModification.setActualNotes(BigInteger.valueOf(divisions + 1));
                        timeModification.setNormalNotes(BigInteger.valueOf(divisions));
                        n.setTimeModification(timeModification);
                        if (i == 0) {
                            Tuplet tuplet = new Tuplet();
                            tuplet.setBracket(YesNo.YES);
                            tuplet.setNumber(++tupletNum);
                            tuplet.setPlacement(AboveBelow.ABOVE);
                            tuplet.setType(StartStop.START);
                            List<Notations> notationsList = n.getNotations();
                            Notations notations = new Notations();
                            notations.getTiedOrSlurOrTuplet().add(tuplet);
                            notationsList.add(notations);
                        }
                        if (i == subList.size() - 1) {
                            List<Notations> notationsList = n.getNotations();
                            Notations notations = new Notations();
                            Tuplet tuplet = new Tuplet();
                            tuplet.setNumber(tupletNum);
                            tuplet.setType(StartStop.STOP);
                            notations.getTiedOrSlurOrTuplet().add(tuplet);
                            notationsList.add(notations);
                        }
                        String newNoteType = null;
                        /*BiMap<Integer, String> typeMapInverse = noteTypeMap.inverse();
                        for (int j = 0; newNoteType == null; j++)
                            newNoteType = typeMapInverse.get(duration - j);*/
                        /*if (divisions == 2)
                            newNoteType = "eighth";*/
                        newNoteType = noteTypeMap.inverse().get(division/(divisions));
                        if (newNoteType == null) {
                            changeDivision(divisions);
                            addedTime = division/(divisions+1);
                            newNoteType = noteTypeMap.inverse().get(division/(divisions));
                            if (newNoteType == null) {
                                throw new NullPointerException("String noteType doesn't exist");
                            }
                        }
                        n.getType().setValue(newNoteType);
                        Optional<String> opt = Optional.ofNullable(noteTypeMap.inverse().get(addedTime));
                        opt.ifPresent(type -> n.getDot().clear());
                        continue;
                    }
                    Optional<String> opt = Optional.ofNullable(noteTypeMap.inverse().get(addedTime));
                    opt.ifPresent(type -> n.getDot().clear());
                    // Get current NoteType
                    NoteType noteType = n.getType();
                    // Set the new NoteType
                    // get the type from the corresponding value of the inverse map given the duration
                    String newNoteType = noteTypeMap.inverse().get(duration);
                    noteType.setValue(newNoteType);
                    //System.out.println(newNoteType);
                }
            }
            if (tieNote != null) {
                Main.noteList.add(index-1, tieNote);
            }
            //System.out.println(Main.noteList);
            // TODO case of dotted TimeChar
        } else if (argo) { // TODO case of argo in TimeChar
            int index = getIndex();
            List<Note> subList = Main.noteList.subList(index - 1, index + 1);
            //System.out.println(subList);
            Note note1 = subList.get(0);
            int addedTime = division/2;
            int duration = setNewDuration(addedTime, note1);
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

    // returns the new settled duration
    private int setNewDuration(int addedTime, @NotNull Note n) {
        int duration = n.getDuration().intValue();
        // subtract one time unit from duration
        duration -= division;
        // and add the new addedTime instead
        duration += addedTime;
        // Set the new Duration
        n.setDuration(new BigDecimal(duration));
        return duration;
    }

    private int getNewAddedTime() {
        changeDivision();
        // the newAddedTime value is returned
        return division/(divisions+1);
    }

    private void changeDivision() {
        division *= divisions+1;
        applyChanges();
        System.out.println(Main.noteList);
    }

    private void changeDivision(int num) {
        division *= num;
        applyChanges(num);
        System.out.println(Main.noteList);
    }

    private void applyChanges() {
        // reInsert the values in the map to add those supported by the new measure division
        mapValuesInsert();
        // change the duration of all notes according to the new corresponding to the new division value
        Main.noteList.forEach(N -> {
            int newValue = N.getDuration().intValue();
            newValue *= divisions+1;
            N.setDuration(new BigDecimal(newValue));
        });
    }

    private void applyChanges(int num) {
        // reInsert the values in the map to add those supported by the new measure division
        mapValuesInsert();
        // change the duration of all notes according to the new corresponding to the new division value
        Main.noteList.forEach(N -> {
            int newValue = N.getDuration().intValue();
            newValue *= num;
            N.setDuration(new BigDecimal(newValue));
        });
    }

    static int getIndex() {
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

    static void mapValuesInsert() {
        noteTypeMap.clear();
        // 1024th, 512th, 256th, 128th, 64th, 32nd, 16th, eighth, quarter, half, whole, breve, long, and maxima
        noteTypeMap.put("maxima", division * 28);
        noteTypeMap.put("long", division * 16);
        noteTypeMap.put("breve", division * 8);
        noteTypeMap.put("whole", division * 4);
        noteTypeMap.put("half", division * 2);
        noteTypeMap.put("quarter", division);
        if (division % 2 == 0) noteTypeMap.put("eighth", division / 2);
        //else changeDivision(2);
        if (division % 4 == 0) noteTypeMap.put("16th", division / 4);
        //else changeDivision(2);
        if (division % 8 == 0) noteTypeMap.put("32nd", division / 8);
        //else changeDivision(2);
        if (division % 16 == 0) noteTypeMap.put("64th", division / 16);
        //else changeDivision(2);
        if (division % 32 == 0) noteTypeMap.put("128th", division / 32);
        //else changeDivision(2);
        if (division % 64 == 0) noteTypeMap.put("256th", division / 64);
        if (division % 128 == 0) noteTypeMap.put("512th", division / 128);
        if (division % 256 == 0) noteTypeMap.put("1024th", division / 256);
    }
}
