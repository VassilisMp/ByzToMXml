package Mxml;

public class PartList extends org.audiveris.proxymusic.PartList{

    private PartList() {
        super();
    }

    public static class Builder {

        private final PartList partList;

        public Builder() {
            this.partList = new PartList();
        }

        public PartList build() {
            return partList;
        }
    }
}
