package Byzantine;

import com.google.gson.annotations.Expose;
import org.audiveris.proxymusic.Note;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class UnicodeChar implements Serializable, Consumer<Engine> {

    private static final long serialVersionUID = 7662794995100881459L;

    @Expose
    int codePoint;
    @Expose(serialize = false, deserialize = false)
    String font;
    @Expose(serialize = false, deserialize = false)
    String text;
    @Expose
    protected String classType = this.getClass().getSimpleName();

    UnicodeChar(int codePoint) {
        this.codePoint = codePoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnicodeChar)) return false;
        UnicodeChar that = (UnicodeChar) o;
        return codePoint == that.codePoint &&
                Objects.equals(font, that.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codePoint, font);
    }

    boolean exactEquals(UnicodeChar o) {
        return this == o;
    }

    @Override
    public String toString() {
        return "UnicodeChar{" +
                "codePoint=" + codePoint +
                ", font='" + font + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public void accept(Engine engine) {

    }
}
