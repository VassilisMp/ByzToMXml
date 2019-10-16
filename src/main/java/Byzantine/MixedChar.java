package Byzantine;

import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.List;

public class MixedChar extends ByzChar {

    @Expose
    private ByzChar[] chars;

    MixedChar(int codePoint, Byzantine.ByzClass byzClass, List<ByzChar> chars) {
        super(codePoint, byzClass);
        this.chars = chars.toArray(new ByzChar[0]);
    }

    public MixedChar(int codePoint, Byzantine.ByzClass byzClass, ByzChar ... chars) {
        super(codePoint, byzClass);
        this.chars = chars;
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
    protected ByzChar clone() throws CloneNotSupportedException {
        MixedChar clone = (MixedChar)super.clone();
        clone.chars = (ByzChar[]) Arrays.stream(this.chars)
                .map((ByzChar c) -> {
                    try {
                        return c.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).toArray(ByzChar[]::new);
        return clone;
    }

    @Override
    public void accept(Engine engine) {
        Arrays.stream(chars).forEach(uChar -> uChar.accept(engine));
    }
}
