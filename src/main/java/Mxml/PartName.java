package Mxml;

public class PartName extends org.audiveris.proxymusic.PartName {

    private PartName() {
        super();
    }

    public static class Builder {
        private final String name;

        public Builder(String partName) {
            this.name = partName;
        }

        public PartName build() {
            final PartName partName = new PartName();
            partName.setValue(name);
            return partName;
        }
    }
}
