package Byzantine;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class MixedChar extends ByzChar implements Iterable<ByzChar> {

    @Expose
    private ByzChar[] chars;

    MixedChar(int codePoint, Byzantine.ByzClass byzClass, List<ByzChar> chars) {
        super(codePoint, byzClass);
        this.chars = chars.toArray(new ByzChar[0]);
    }

    ByzChar[] getChars() {
        return chars;
    }

    @Override
    public String toString() {
        return "MixedChar{" +
                super.toString() +
                "chars=" + Arrays.toString(chars) +
                "} ";
    }

    @Override
    protected ByzChar clone() {
        MixedChar clone = (MixedChar) super.clone();
        clone.chars = Arrays.stream(this.chars)
                .map(ByzChar::clone).toArray(ByzChar[]::new);
        return clone;
    }

    @Override
    public void accept(Engine engine) {
        Arrays.stream(chars).forEach(uChar -> uChar.accept(engine));
    }

    @NotNull
    @Override
    public Iterator<ByzChar> iterator() {
        return Arrays.stream(chars).iterator();
    }

    @Override
    public void forEach(Consumer<? super ByzChar> consumer) {
        Arrays.stream(chars).forEach(consumer);
    }
}
