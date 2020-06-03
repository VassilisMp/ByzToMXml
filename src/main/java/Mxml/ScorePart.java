package Mxml;

public class ScorePart extends org.audiveris.proxymusic.ScorePart {

    private ScorePart() {
        super();
    }

    public static class Builder {

        private final String id;
        private final String partName;

        public Builder(String id, String partName) {
            this.id = id;
            this.partName = partName;
        }

        public ScorePart build() {
            final ScorePart scorePart = new ScorePart();
            scorePart.setId(id);
            PartName partName = new PartName.Builder(this.partName).build();
            scorePart.setPartName(partName);
            return scorePart;
        }
    }
}
