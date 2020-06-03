package Mxml;

import org.jetbrains.annotations.NotNull;

public class ScorePartwise extends org.audiveris.proxymusic.ScorePartwise {

    private ScorePartwise() {
        super();
    }

    private void addPart(org.audiveris.proxymusic.ScorePartwise.Part part) {
        getPart().add(part);
    }

    public static class Builder {

        private final ScorePartwise scorePartwise = new ScorePartwise();
        private final PartList partList = new PartList.Builder().build();
        private Part part = null;
        private String id = null;
        private String partName = null;

        public Builder() {}

        public Builder setPart(@NotNull Part part, @NotNull String id, @NotNull String partName) {
            this.part = part;
            this.id = id;
            this.partName = partName;
            return this;
        }

        public org.audiveris.proxymusic.ScorePartwise build() {
            // set PartList
            scorePartwise.setPartList(partList);
            if (part != null && id != null && partName != null) {
                final ScorePart scorePart = new ScorePart.Builder(id, partName)
                        .build();
                partList.getPartGroupOrScorePart().add(scorePart);
                part.setId(scorePart);
                scorePartwise.addPart(part);
            }
            return scorePartwise;
        }
    }

}
