package Byzantine;

import java.io.Serializable;
import java.util.Objects;

public class UnicodeChar implements Runnable, Serializable {

    private static final long serialVersionUID = 7662794995100881459L;

    int codePoint;

    protected String font;

    String text;

    public UnicodeChar(int codePoint, String font) {
        this.codePoint = codePoint;
        this.font = font;
    }

    public int getCodePoint() {
        return codePoint;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void run() {

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
}
