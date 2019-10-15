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

    void setChars(ByzChar[] chars) {
        this.chars = chars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MixedChar mixedChar = (MixedChar) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(chars, mixedChar.chars);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(chars);
        return result;
    }

    @Override
    public String toString() {
        return "MixedChar{" +
                "chars=" + Arrays.toString(chars) +
                "} " + super.toString();
    }

    @Override
    public void accept(Engine engine) {
        Arrays.stream(chars).forEach(uChar -> uChar.accept(engine));
    }
}
