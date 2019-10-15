package Byzantine;

import com.google.gson.annotations.Expose;
import org.audiveris.proxymusic.*;
import org.jetbrains.annotations.NotNull;

import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class TimeChar extends ByzChar{
    private static final long serialVersionUID = 6353312089523385239L;
    // using static dot object to use it every time, to not create more objects
    private static EmptyPlacement dot = new EmptyPlacement();
    private static Tie tieStart = new Tie();
    private static Tie tieStop = new Tie();
    static {
        tieStart.setType(StartStop.START);
        tieStop.setType(StartStop.STOP);
    }

    @Expose
    private int dotPlace;
    @Expose
    private int divisions;
    @Expose
    private boolean argo;
    //public static Integer tupletNum = 0;

    TimeChar(int codePoint, Byzantine.ByzClass byzClass, int dotPlace, int divisions, boolean argo) {
        super(codePoint, byzClass);
        this.dotPlace = dotPlace;
        this.divisions = divisions;
        this.argo = argo;
        this.classType = this.getClass().getSimpleName();
    }

    int getDotPlace() {
        return dotPlace;
    }

    int getDivisions() {
        return divisions;
    }

    boolean getArgo() {
        return argo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TimeChar timeChar = (TimeChar) o;

        if (dotPlace != timeChar.dotPlace) return false;
        if (divisions != timeChar.divisions) return false;
        return argo == timeChar.argo;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + dotPlace;
        result = 31 * result + divisions;
        result = 31 * result + (argo ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TimeChar{" +
                "dotPlace=" + dotPlace +
                ", divisions=" + divisions +
                ", argo=" + argo +
                "} " + super.toString();
    }

    @Override
    public void accept(Engine engine) {// TODO finish if statement code L124, it's dot after pause
        List<Note> notes = engine.noteList;
        int argoDivs = 0;
        if (argo) {
            argoDivs = -divisions;
            divisions = 1;
        }
        // varia-dot
        if (this.getByzClass() == Byzantine.ByzClass.L && this.getCodePoint() == 92) {
            ExtendedNote note = new ExtendedNote(false, true);
            note.setDuration(BigDecimal.valueOf(engine.division));
            NoteType noteType = new NoteType();
            noteType.setValue("quarter");
            note.setType(noteType);
            note.setRest(new Rest());
            notes.add(note);
            return;
        }
        if (divisions > 0) {
            int index = engine.getIndex();
            List<Note> subList = notes.subList(index - 1, index + divisions);
            int addedTime;
            if (dotPlace == 0) {
                if (engine.division % (divisions + 1) != 0) engine.changeDivision(divisions + 1);
            } else if (engine.division % (divisions + 2) != 0) engine.changeDivision(divisions + 2);
            Note tieNote = null;
            int tieNoteIndex = 0;
            for (int i = 0; i < subList.size(); i++) {
                addedTime = dotPlace == 0 ? engine.division / (divisions + 1) : engine.division / (divisions + 2);
                Note note = subList.get(i);
                int duration = note.getDuration().intValue();
                String noteType;
                if (duration > engine.division) {
                    int addedTime1 = addedTime;
                    int producedDuration = (duration - engine.division) + addedTime1;
                    // new added code
                    noteType = engine.noteTypeMap.inverse().get(producedDuration);
                    if (noteType != null) {
                        setDurAndType(note, producedDuration, noteType);
                        checkDots(note);
                        continue;
                    }
                    if (i == dotPlace - 1) addedTime1 *= 2;
                    note.setDuration(new BigDecimal(addedTime1));
                    tieNote = (Note) ((ExtendedNote) note).clone();
                    tieNoteIndex = i;
                    int tDuration = duration - engine.division; //((duration / engine.division) * engine.division) - engine.division;
                    tieNote.getType().setValue(engine.noteTypeMap.inverse().get(tDuration));
                    Tie tie;
                    Tie tie2;
                    if (dotPlace == 0) {
                        tie = tieStart;
                        tie2 = tieStop;
                        if (divisions % 2 == 0) {
                            noteType = engine.noteTypeMap.inverse().get(engine.division / divisions);
                            if (noteType == null) {
                                engine.changeDivision(divisions);
                                tDuration *= divisions;
                                noteType = engine.noteTypeMap.inverse().get(engine.division / divisions);
                                if (noteType == null) throw new NullPointerException("String noteType doesn't exist");
                            }
                            note.getType().setValue(noteType);
                            addTuplet(subList.size(), i, note, 1, noteType);
                        } else note.getType().setValue(engine.noteTypeMap.inverse().get(engine.division / (divisions + 1)));
                    } else {
                        if (i == 0) {
                            tie = tieStart;
                            tie2 = tieStop;
                        } else {
                            tie = tieStop;
                            tie2 = tieStart;
                        }
                        setTiedInNote(tieNote, StartStopContinue.valueOf(tie.getType().value().toUpperCase()));
                        setTiedInNote(note, StartStopContinue.valueOf(tie2.getType().value().toUpperCase()));
                        int divideWith = divisions + 1;
                        if (i == dotPlace - 1) divideWith = divisions;
                        noteType = engine.noteTypeMap.inverse().get(engine.division / divideWith);
                        if (divisions % 2 != 0) {
                            if (noteType == null) {
                                engine.changeDivision(divisions + 1);
                                tDuration *= (divisions + 1);
                                noteType = getNoteType(i, engine);
                            }
                            addTuplet(subList.size(), i, note, 2, noteType);
                        }
                        note.getType().setValue(noteType);
                    }
                    tieNote.getTie().add(tie);
                    note.getTie().add(tie2);
                    tieNote.setDuration(new BigDecimal(tDuration));
                    checkDots(note);
                } else {
                    if (dotPlace == 0) {
                        note.setDuration(new BigDecimal(addedTime));
                        if (divisions%2==0) {
                            noteType = engine.noteTypeMap.inverse().get(engine.division/(divisions));
                            if (noteType == null) {
                                engine.changeDivision(divisions);
                                noteType = engine.noteTypeMap.inverse().get(engine.division/(divisions));
                            }
                            addTuplet(subList.size(), i, note, 1, noteType);
                        } else
                            noteType = engine.noteTypeMap.inverse().get(engine.division/(divisions+1));
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
                            noteType = engine.noteTypeMap.inverse().get(engine.division/divideWith);
                            if (noteType == null) {
                                engine.changeDivision(divisions+1);
                                if (tieNote != null) {
                                    int tieNoteDur = tieNote.getDuration().intValue();
                                    tieNote.setDuration(BigDecimal.valueOf(tieNoteDur*(divisions+1)));
                                }
                                noteType = getNoteType(i, engine);
                            }
                            note.getType().setValue(noteType);
                            addTuplet(subList.size(), i, note, 2, noteType);
                        } else {
                            noteType = engine.noteTypeMap.inverse().get(engine.division/divideWith);
                            if (noteType == null) noteType = engine.noteTypeMap.inverse().get(addedTime);
                            note.getType().setValue(noteType);
                        }
                    }
                }
            }
            if (tieNote != null) {
                if (tieNoteIndex > 0) notes.add(index+tieNoteIndex, tieNote);
                else notes.add(index-1+tieNoteIndex, tieNote);
            }
        }
        if (argo) {
            divisions = argoDivs;
        }
        if (divisions < 0) {
            Note note = argo ? notes.get(engine.getIndex() + 1) : notes.get(engine.getIndex());
            int duration = note.getDuration().intValue() + (Math.abs(divisions) * engine.division);
            String noteTypeS = engine.noteTypeMap.inverse().get(duration);
            if (noteTypeS != null) {
                setDurAndType(note, duration, noteTypeS);
                checkDots(note);
            } else {
                int b = (duration / engine.division) *engine.division;
                Note newNote = Cloner.deepClone(note);
                noteTypeS = engine.noteTypeMap.inverse().get(b);
                if (noteTypeS == null) throw new NullPointerException("String noteType doesn't exist");
                setDurAndType(newNote, b, noteTypeS);
                setTiedInNote(newNote, StartStopContinue.START);
                setTiedInNote(note, StartStopContinue.STOP);
                notes.add(notes.indexOf(note), newNote);
            }
        }
    }

    private static void setDurAndType(@NotNull Note note, int duration, String noteType) {
        note.setDuration(BigDecimal.valueOf(duration));
        NoteType type = new NoteType();
        type.setValue(noteType);
        note.setType(type);
    }
    @NotNull
    private String getNoteType(int i, Engine engine) {
        int divideWith;
        String noteType;
        divideWith = divisions+1;
        if (i == dotPlace-1) {
            divideWith = divisions-1;
        }
        noteType = engine.noteTypeMap.inverse().get(engine.division/divideWith);
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

    private static void checkDots(@NotNull Note note) {
        String noteType = note.getType().getValue();
        if (noteType.charAt(noteType.length()-1) == '.') {
            int matches = 1;
            if (noteType.charAt(noteType.length()-2) == '.')
                matches++;
            int size = note.getDot().size();
            if (matches > size)
                for (int j = 0; j < matches - size; j++) note.getDot().add(dot);
            else if (matches < size) note.getDot().remove(size-1);
        } else note.getDot().clear();
    }
}
