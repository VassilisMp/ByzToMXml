package Byzantine;

import java.util.function.Consumer;

public class ByzChar extends UnicodeChar implements Consumer<Engine> {

    private static final long serialVersionUID = 7706296349475294817L;

    protected ByzClass ByzClass;

    public ByzChar(int codePoint, String font, Byzantine.ByzClass byzClass) {
        super(codePoint, font);
        ByzClass = byzClass;
    }

    Byzantine.ByzClass getByzClass() {
        return ByzClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ByzChar)) return false;
        if (!super.equals(o)) return false;
        ByzChar byzChar = (ByzChar) o;
        return ByzClass == byzChar.ByzClass && codePoint == byzChar.codePoint;
    }

    String getCodePointClass() {
        return ByzClass + String.format("%03d", codePoint);
    }

    @Override
    public void accept(Engine engine) {

    }
}
