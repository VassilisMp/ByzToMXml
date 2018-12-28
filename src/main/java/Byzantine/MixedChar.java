package Byzantine;

import java.util.Arrays;
import java.util.List;

public class MixedChar extends ByzChar {
    private static final long serialVersionUID = -3903289443515625540L;

    private ByzChar[] chars;

    public MixedChar(int codePoint, String font, Byzantine.ByzClass byzClass, List<ByzChar> chars) {
        super(codePoint, font, byzClass);
        this.chars = chars.toArray(new ByzChar[0]);
    }

    public MixedChar(int codePoint, String font, Byzantine.ByzClass byzClass, ByzChar ... chars) {
        super(codePoint, font, byzClass);
        this.chars = chars;
    }

    public ByzChar[] getChars() {
        return chars;
    }

    @Override
    public String toString() {
        return "MixedChar{" +
                "chars=" + Arrays.toString(chars) +
                '}';
    }

    @Override
    public void run() {
        Arrays.stream(chars).forEach(UnicodeChar::run);
    }
}
