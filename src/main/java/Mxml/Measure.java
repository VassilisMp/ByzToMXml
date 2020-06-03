package Mxml;

import org.audiveris.proxymusic.*;

import java.lang.String;
import java.math.BigInteger;
import java.util.List;

public class Measure extends Part.Measure {

    public Measure() {
        super();
    }

    public static class Builder {

        private final int measureNumber;
        private final Integer division;
        private Key key = null;
        private Integer numerator = null;
        private Integer denominator = null;
        private boolean dynamicTimeSignature = false;
        private ClefSign clefSign = null;
        private BigInteger clefLine = null;
        private List<Note> noteList = null;

        private final ObjectFactory factory = new ObjectFactory();

        public Builder(int measureNumber, int division) {
            this.measureNumber = measureNumber;
            this.division = division;
        }

        /*public Builder setDivision(int division) {
            this.division = division;
            return this;
        }*/

        public Builder setTimeSignature(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
            if (numerator == 0) this.dynamicTimeSignature = true;
            return this;
        }

        public Builder setDefaultClef() {
            clefSign = ClefSign.G;
            clefLine = new BigInteger("2");
            return this;
        }

        public Builder setKey(Key key) {
            this.key = key;
            return this;
        }

        public Builder setNotes(List<Mxml.Note> noteList) {
            this.noteList = noteList;
            return this;
        }

        public Measure build() throws Exception {
            Measure measure = new Measure();
            // set measure number
            measure.setNumber(String.valueOf(measureNumber));
            Attributes attributes = factory.createAttributes();
            measure.getNoteOrBackupOrForward().add(attributes);
            // Clef
            if (clefSign != null) {
                Clef clef = factory.createClef();
                attributes.getClef().add(clef);
                clef.setSign(clefSign);
                if (clefLine != null)
                    clef.setLine(clefLine);
            }
            // set Key
            if (key != null)
                attributes.getKey().add(key);
            // set notes
            if (noteList != null) {
                measure.getNoteOrBackupOrForward().addAll(noteList);
                // calculate dynamic Time Signature based on the duration of the notes in the noteList
                if (dynamicTimeSignature) {
                    Integer reduce = noteList.stream()
                            .map(note -> note.getDuration().intValue()).reduce(0, Integer::sum);
                    if (reduce % division != 0)
                        throw new Exception("wrong measure size, i=" + 0 + ", " + reduce + "/" + division);
                    numerator = reduce / division;
                }
            }
            // set Time Signature
            if (numerator != null) {
                Time time = factory.createTime();
                attributes.getTime().add(time);
                time.getTimeSignature().add(factory.createTimeBeats(String.valueOf(numerator)));
                if (denominator != null)
                    time.getTimeSignature().add(factory.createTimeBeatType(String.valueOf(denominator)));
                else
                    time.getTimeSignature().add(factory.createTimeBeatType("4"));
            }
            return measure;
        }
    }
}
