package Byzantine;

import org.audiveris.proxymusic.Note;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class UnicodeChar implements Serializable, Consumer<List<Note>> {

    private static final long serialVersionUID = 7662794995100881459L;

    int codePoint;
    protected String font;
    String text;
    protected Engine engine;

    public UnicodeChar(int codePoint, String font) {
        this.codePoint = codePoint;
        this.font = font;
    }

    public UnicodeChar(int codePoint, String font, Engine engine) {
        this(codePoint, font);
        this.engine = engine;
    }

    public int getCodePoint() {
        return codePoint;
    }

    public void setFont(String font) {
        this.font = font;
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

    @Override
    public void accept(List<Note> notes) {

    }
}
