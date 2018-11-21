package Byzantine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class UnicodeChar implements Runnable, Serializable {

    private static final long serialVersionUID = 7662794995100881459L;

    protected int codePoint;

    protected String font;

    public UnicodeChar(int codePoint, String font) {
        this.codePoint = codePoint;
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
