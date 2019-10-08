package Byzantine;

import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.List;

public class MixedChar extends ByzChar {
    private static final long serialVersionUID = -3903289443515625540L;

    @Expose
    private ByzChar[] chars;

    MixedChar(int codePoint, Byzantine.ByzClass byzClass, List<ByzChar> chars) {
        super(codePoint, byzClass);
        this.chars = chars.toArray(new ByzChar[0]);
        this.classType = this.getClass().getSimpleName();
    }

    public MixedChar(int codePoint, Byzantine.ByzClass byzClass, ByzChar ... chars) {
        super(codePoint, byzClass);
        this.chars = chars;
        this.classType = this.getClass().getSimpleName();
    }

    ByzChar[] getChars() {
        return chars;
    }

    @Override
    public String toString() {
        return "MixedChar{" +
                "chars=" + Arrays.toString(chars) +
                '}';
    }

    @Override
    public void accept(Engine engine) {
        Arrays.stream(chars).forEach(uChar -> uChar.accept(engine));
    }
}
