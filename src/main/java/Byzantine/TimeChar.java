package Byzantine;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.audiveris.proxymusic.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

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
    //public static Integer tupletNum = 0;

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
    public void run() {// TODO finish if statement code L124, it's dot after pause
        // varia-dot
        if (getByzClass() == Byzantine.ByzClass.L && codePoint == 92) {
            ExtendedNote note = new ExtendedNote(false, true);
            note.setDuration(BigDecimal.valueOf(division));
            NoteType noteType = new NoteType();
            noteType.setValue("quarter");
            note.setType(noteType);
            note.setRest(new Rest());
            Main.noteList.add(note);
        } else
        if (divisions > 0 && !argo) { // TODO argo
            int index = getIndex();
            List<Note> subList = Main.noteList.subList(index - 1, index + divisions);
            int addedTime;
            if (dotPlace == 0) {
                if (division % (divisions + 1) != 0) changeDivision(divisions + 1);
            } else {
                if (division % (divisions + 2) != 0) changeDivision(divisions + 2);
            }
            Note tieNote = null;
            int tieNoteIndex = 0;
            for (int i = 0; i < subList.size(); i++) {
                if (dotPlace == 0) {
                    addedTime = division / (divisions + 1);
                } else {
                    addedTime = division/(divisions+2);
                }
                Note note = subList.get(i);
                int duration = note.getDuration().intValue();
                String noteType;
                if (duration > division) {
                    int addedTime1 = addedTime;
                    int producedDuration = (duration -division) + addedTime1;
                    int x = (producedDuration / division) * division;
                    boolean flag = false;
                    if (x%2 == 0) {
                        int dotTarget = x + (x/2);
                        int doubleDotTarget = dotTarget + (x/4);
                        if (producedDuration == dotTarget || producedDuration == doubleDotTarget) {
                            note.getDot().add(new EmptyPlacement());
                            if (producedDuration == dotTarget)
                                noteType = noteTypeMap.inverse().get(dotTarget -(x /2));
                            else {
                                note.getDot().add(new EmptyPlacement());
                                noteType = noteTypeMap.inverse().get(doubleDotTarget -(x /2)-(x /4));
                            }
                            note.setDuration(new BigDecimal(producedDuration));
                            if (noteType == null)
                                throw new NullPointerException("String noteType doesn't exist");
                            note.getType().setValue(noteType);
                            flag = true;
                        }
                    }
                    if (!flag) {
                        if (i == dotPlace - 1) addedTime1 *= 2;
                        note.setDuration(new BigDecimal(addedTime1));
                        tieNote = (Note) ((ExtendedNote) note).clone();
                        int Tduration = ((duration / division) * division) - division;
                        tieNote.getType().setValue(noteTypeMap.inverse().get(Tduration));
                        Tie tie = new Tie();
                        Tie tie2 = new Tie();
                        if (dotPlace == 0) {
                            tie.setType(StartStop.START);
                            tie2.setType(StartStop.STOP);
                            if (divisions % 2 == 0) {
                                noteType = noteTypeMap.inverse().get(division / divisions);
                                if (noteType == null) {
                                    changeDivision(divisions);
                                    Tduration *= divisions;
                                    noteType = noteTypeMap.inverse().get(division / divisions);
                                    if (noteType == null) throw new NullPointerException("String noteType doesn't exist");
                                }
                                note.getType().setValue(noteType);
                                addTuplet(subList.size(), i, note, 1, noteType);
                            } else {
                                note.getType().setValue(noteTypeMap.inverse().get(division / (divisions + 1)));
                            }
                        } else {
                            StartStopContinue startStopContinue;
                            StartStopContinue startStopContinue2;
                            if (i == 0) {
                                tie.setType(StartStop.START);
                                startStopContinue = StartStopContinue.START;
                                tie2.setType(StartStop.STOP);
                                startStopContinue2 = StartStopContinue.STOP;
                            } else {
                                tie.setType(StartStop.STOP);
                                startStopContinue = StartStopContinue.STOP;
                                tie2.setType(StartStop.START);
                                startStopContinue2 = StartStopContinue.START;
                            }
                            setTiedInNote(tieNote, startStopContinue);
                            setTiedInNote(note, startStopContinue2);
                            int divideWith = divisions + 1;
                            if (i == dotPlace - 1) divideWith = divisions;
                            noteType = noteTypeMap.inverse().get(division / divideWith);
                            if (divisions % 2 != 0) {
                                if (noteType == null) {
                                    changeDivision(divisions + 1);
                                    Tduration *= (divisions + 1);
                                    noteType = getNoteType(i);
                                }
                                addTuplet(subList.size(), i, note, 2, noteType);
                            }
                            note.getType().setValue(noteType);
                        }
                        tieNote.getTie().add(tie);
                        note.getTie().add(tie2);
                        tieNote.setDuration(new BigDecimal(Tduration));
                    }
                    if (!(dotPlace == 0))
                        tieNoteIndex = i;
                } else {
                    if (dotPlace == 0) {
                        note.setDuration(new BigDecimal(addedTime));
                        if (divisions%2==0) {
                            noteType = noteTypeMap.inverse().get(division/(divisions));
                            if (noteType == null) {
                                changeDivision(divisions);
                                noteType = noteTypeMap.inverse().get(division/(divisions));
                            }
                            addTuplet(subList.size(), i, note, 1, noteType);
                        } else
                            noteType = noteTypeMap.inverse().get(division/(divisions+1));
                        if (noteType == null) throw new NullPointerException("String noteType doesn't exist");
                        note.getType().setValue(noteType);
                    } else {
                        int divideWith = divisions+1;
                        if (i == dotPlace-1) {
                            divideWith = divisions;
                            addedTime *= 2;
                        }
                        note.setDuration(new BigDecimal(addedTime));
                        if (divisions%2!=0) {
                            noteType = noteTypeMap.inverse().get(division/divideWith);
                            if (noteType == null) {
                                changeDivision(divisions+1);
                                if (tieNote != null) {
                                    int tieNoteDur = tieNote.getDuration().intValue();
                                    tieNote.setDuration(BigDecimal.valueOf(tieNoteDur*(divisions+1)));
                                }
                                noteType = getNoteType(i);
                            }
                            note.getType().setValue(noteType);
                            addTuplet(subList.size(), i, note, 2, noteType);
                        } else {
                            noteType = noteTypeMap.inverse().get(division/divideWith);
                            note.getType().setValue(noteType);
                        }
                    }
                }
            }
            if (tieNote != null) {
                if (tieNoteIndex == 0 || dotPlace == 0) {
                    Main.noteList.add(index-1, tieNote);
                } else {
                    Main.noteList.add(tieNote);
                }
            }
        } else if (divisions < 0) {
            Note note = Main.noteList.get(getIndex());
            int duration = note.getDuration().intValue() + (Math.abs(divisions) * division);
            int a = duration / division;
            NoteType noteType = new NoteType();
            if (a%2!=0) {
                if (a == 1) a++;
                int b = (a-1)*division;
                noteType.setValue(noteTypeMap.inverse().get(b));
                if (noteType.getValue() == null)
                    throw new NullPointerException("String noteType doesn't exist");
                int dotTarget = b + (b/2);
                int doubleDotTarget = dotTarget + (b/4);
                if (duration == dotTarget) {
                    note.getDot().add(new EmptyPlacement());
                } else if (duration == doubleDotTarget) {
                    note.getDot().addAll(Arrays.asList(new EmptyPlacement(), new EmptyPlacement()));
                }
            } else
                noteType.setValue(noteTypeMap.inverse().get(duration));
            note.setDuration(BigDecimal.valueOf(duration));
            note.setType(noteType);
        }
    }

    @NotNull
    private String getNoteType(int i) {
        int divideWith;
        String noteType;
        divideWith = divisions+1;
        if (i == dotPlace-1) {
            divideWith = divisions-1;
        }
        noteType = noteTypeMap.inverse().get(division/divideWith);
        if (noteType == null) throw new NullPointerException("String noteType doesn't exist");
        return noteType;
    }

    private Notations getNotations(@NotNull Note note) {
        Notations notations;
        if(note.getNotations().size() > 0)
            notations = note.getNotations().get(0);
        else {
            notations = new Notations();
            note.getNotations().add(notations);
        }
        return notations;
    }

    private void setTiedInNote(@NotNull Note note, StartStopContinue startStopContinue) {
        Notations notations = getNotations(note);
        Tied tied = new Tied();
        tied.setType(startStopContinue);
        notations.getTiedOrSlurOrTuplet().add(tied);
    }

    private void setTupletInNote(@NotNull Note note, Tuplet tuplet) {
        Notations notations = getNotations(note);
        notations.getTiedOrSlurOrTuplet().add(tuplet);
    }

    private void addTuplet(int subListSize, int i, @NotNull Note note, int num, String normalType) {
        TimeModification timeModification = new TimeModification();
        timeModification.setActualNotes(BigInteger.valueOf(divisions + num));
        timeModification.setNormalNotes(BigInteger.valueOf(divisions + (num-1)));
        timeModification.setNormalType(normalType);
        note.setTimeModification(timeModification);
        if (i == 0) {
            Tuplet tuplet = new Tuplet();
            tuplet.setBracket(YesNo.YES);
            tuplet.setPlacement(AboveBelow.ABOVE);
            tuplet.setType(StartStop.START);
            setTupletInNote(note, tuplet);
        }
        if (i == subListSize - 1) {
            Tuplet tuplet = new Tuplet();
            tuplet.setType(StartStop.STOP);
            setTupletInNote(note, tuplet);
        }
    }



    private void changeDivision(int num) {
        division *= num;
        // reInsert the values in the map to add those supported by the new measure division
        mapValuesInsert();
        // change the duration of all notes according to the new corresponding to the new division value
        Main.noteList.forEach(N -> {
            int newValue = N.getDuration().intValue();
            newValue *= num;
            N.setDuration(new BigDecimal(newValue));
        });
        System.out.println(Main.noteList);
    }

    static int getIndex() {
        ListIterator<Note> it = Main.noteList.listIterator(Main.noteList.size());
        while (it.hasPrevious())
            if (((ExtendedNote) it.previous()).canGetTime())
                return it.nextIndex();
        throw new NullPointerException("Couldn't find a Note that canGetTime()");
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

    public void run1() {// TODO finish if statement code L124, it's dot after pause
        // varia-dot
        if (getByzClass() == Byzantine.ByzClass.L && codePoint == 92) {
            ExtendedNote note = new ExtendedNote(false, true);
            note.setDuration(BigDecimal.valueOf(division));
            NoteType noteType = new NoteType();
            noteType.setValue("quarter");
            note.setType(noteType);
            note.setRest(new Rest());
            Main.noteList.add(note);
        } else
        if (divisions > 0 && !argo) { // TODO argo
            int index = getIndex();
            List<Note> subList = Main.noteList.subList(index - 1, index + divisions);
            // auxiliary variables
            int localDivisions = dotPlace > 0?divisions+2:divisions+1;
            int preDivision= division;
            while (division%localDivisions != 0)
                division += 1;
            if (preDivision != division) {
                // reInsert the values in the map to add those supported by the new measure division
                mapValuesInsert();
                if (division % preDivision != 0)
                    throw new NullPointerException("error");
                int factor = division / preDivision;
                // change the duration of all notes according to the new corresponding to the new division value
                Main.noteList.forEach(N -> {
                    int newValue = N.getDuration().intValue();
                    newValue *= factor;
                    N.setDuration(new BigDecimal(newValue));
                });
            }
            for (int i = 0; i < subList.size(); i++) {
                Note note = subList.get(i);
                int duration = note.getDuration().intValue();
                int addedTime;
                if (dotPlace == 0)
                    addedTime = division / (divisions+1);
                else {
                    if ((i+1) == dotPlace)
                        addedTime = division / (divisions+2) * 2;
                    else
                        addedTime = division / (divisions+2);
                }
                note.setDuration(BigDecimal.valueOf((duration-division)+addedTime));
            }
        } else if (divisions < 0) {
            Note note = Main.noteList.get(getIndex());
            int duration = note.getDuration().intValue() + (Math.abs(divisions) * division);
            int a = duration / division;
            NoteType noteType = new NoteType();
            if (a%2!=0) {
                int b = (a-1)*division;
                noteType.setValue(noteTypeMap.inverse().get(b));
                if (noteType.getValue() == null)
                    throw new NullPointerException("String noteType doesn't exist");
                int dotTarget = b + (b/2);
                int doubleDotTarget = dotTarget + (b/4);
                if (duration == dotTarget) {
                    note.getDot().add(new EmptyPlacement());
                } else if (duration == doubleDotTarget) {
                    note.getDot().addAll(Arrays.asList(new EmptyPlacement(), new EmptyPlacement()));
                }
            } else
                noteType.setValue(noteTypeMap.inverse().get(duration));
            note.setDuration(BigDecimal.valueOf(duration));
            note.setType(noteType);
        }
    }
}
