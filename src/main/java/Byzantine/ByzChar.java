package Byzantine;

public class ByzChar extends UnicodeChar {

    private static final long serialVersionUID = 7706296349475294817L;

    protected ByzClass ByzClass;

    public ByzChar(int codePoint, String font, Byzantine.ByzClass byzClass) {
        super(codePoint, font);
        ByzClass = byzClass;
    }

    public Byzantine.ByzClass getByzClass() {
        return ByzClass;
    }

    public String getCodePointClass() {
        return ByzClass + String.format("%03d", codePoint);
    }
}
