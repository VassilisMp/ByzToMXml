package Mxml;

import org.audiveris.proxymusic.ScorePartwise;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Part extends ScorePartwise.Part {

    private Part() {
        super();
    }

    private void addMeasures(List<Measure> measures) {
        getMeasure().addAll(measures);
    }

    public static class Builder {
        private ScorePart scorePart;
        private final List<Note> notes;
        private final Integer division;
        private final Integer timeBeats;

        public Builder(List<Mxml.Note> notes, Integer division, Integer timeBeats) {
            this.notes = notes;
            this.division = division;
            this.timeBeats = timeBeats;
        }

        public Builder setId(ScorePart scorePart) {
            this.scorePart = scorePart;
            return this;
        }

        public Part build() throws Exception {
            Part part = new Part();
            if (scorePart != null)
                setId(scorePart);
            // TODO use accidentals from fthoraScalesMap while inserting in measures
//                addFirstMeasure(part, noteLists.get(0));
            final ArrayList<List<Note>> lists = getLists(timeBeats);
            ArrayList<Measure> measures = new ArrayList<>(lists.size());
            // iterate noteLists to create measures
            for (int i = 0, noteListsSize = lists.size(); i < noteListsSize; i++) {
                List<Mxml.Note> notesList = lists.get(i);
                // create measure
                Measure partMeasure = new Mxml.Measure.Builder(i + 1, division)
                        .setTimeSignature(timeBeats, 4)
                        .setNotes(notesList)
                        .build();
                // put measure in measures list
                measures.add(partMeasure);
            }
            part.addMeasures(measures);
            return part;
        }

        @NotNull
        private ArrayList<List<Note>> getLists(int timeBeats) throws Exception {
            ArrayList<List<Note>> lists = new ArrayList<>();
            if (timeBeats > 0)
                for (int i = 0, noteListSize = notes.size(), index = i, durations = 0; i < noteListSize; i++) {
                    Note note = notes.get(i);
                    durations += note.getDuration().intValue();
                    if (durations == (division * timeBeats)) {
                        lists.add(notes.subList(index, i + 1));
                        index = i + 1;
                        durations = 0;
                    } else if (durations > (division * timeBeats)) {
                        throw new Exception("error in noteLists");
                    }
                }
            else { // TODO finish this, find the right notetypes
                for (int i = 0, noteListSize = notes.size(), index = i, durations = 0; i < noteListSize; i++) {
                    if (i == 200)
                        System.out.println();
                    Note note = notes.get(i);
                    durations += note.getDuration().intValue();
                    for (int j = 2; j <= 12; j++) {
                        if (durations == (division * j)) {
                            lists.add(notes.subList(index, i + 1));
                            index = i + 1;
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
                } else */
                    if (durations > (division * 12)) {
                        throw new Exception("error in dividing measures, i=" + i + ", durations=" + durations);
                    }
                }
            }
            return lists;
        }

        /*private void addFirstMeasure(org.audiveris.proxymusic.ScorePartwise.Part part, List<Note> notes) throws Exception {
            // add Key Signature
            final ByzScale firstScale = fthoraScalesQueue.peek().getKey();
            Key key = firstScale.getKey(STEPS_MAP, ByzStep.DI, null);
            // Measure
            org.audiveris.proxymusic.ScorePartwise.Part.Measure measure = new Measure.Builder()
                    .setMeasureNumber(1)
                    .setDivision(division)
                    .setTimeSignature(timeBeats, 4)
                    .setDefaultClef()
                    .setKey(key)
                    .setNotes(notes)
                    .build();
            part.getMeasure().add(measure);
        }*/

        /*// position in engine.notelist
                Integer position = -1;
                // current scale in iteration
                ByzScale currentScale = Objects.requireNonNull(fthoraScalesQueue.poll()).getKey();
                // next fthora position in engine.notelist
                final AbstractMap.Entry<ByzScale, Integer> next = fthoraScalesQueue.peek();
                Integer nextFthoraPos = next != null ? next.getValue() : null;*/

        /*for (int j = 0; j < noteList.size(); j++) {
                        Note note = noteList.get(j);
                        position++;
                        // move to next scale if position equals nextFthoraPosition
                        if (Objects.equals(position, nextFthoraPos)) {
                            final AbstractMap.Entry<ByzScale, Integer> poll = fthoraScalesQueue.poll();
                            currentScale = poll != null ? poll.getKey() : null;
                        }
                        // get accidental commas from currentScale
                        Integer accidentalCommas; {
                            final Martyria martyria = Objects.requireNonNull(currentScale, "next scale is null in position: " + position)
                                    .getMartyria(toByzStep(note), note.getPitch().getOctave() - 5);
                            accidentalCommas = martyria != null ? martyria.getAccidentalCommas() : null;
                        }
                        if (Objects.requireNonNull(accidentalCommas, "next scale is null in position: " + position) != 0) {
                            note.setAccidental(commasToAccidental(accidentalCommas));
                        }
        //                fthoraScalesQueue.
                    }*/

    }
}
