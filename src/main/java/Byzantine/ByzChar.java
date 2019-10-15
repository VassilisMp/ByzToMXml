package Byzantine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class ByzChar implements Consumer<Engine>, Cloneable {

    private static final long serialVersionUID = 7706296349475294817L;

    @Expose
    private int codePoint;
    @Expose(serialize = false, deserialize = false)
    private String font;
    @Expose(serialize = false, deserialize = false)
    private String text;
    @Expose
    protected String classType = this.getClass().getSimpleName();
    @Expose
    private ByzClass byzClass;

    ByzChar(int codePoint, Byzantine.ByzClass byzClass) {
        this.codePoint = codePoint;
        this.byzClass = byzClass;
    }

    public int getCodePoint() {
        return codePoint;
    }

    public String getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    public ByzClass getByzClass() {
        return byzClass;
    }

    public void setCodePoint(int codePoint) {
        this.codePoint = codePoint;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setByzClass(ByzClass byzClass) {
        this.byzClass = byzClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ByzChar byzChar = (ByzChar) o;

        if (codePoint != byzChar.codePoint) return false;
        if (!Objects.equals(font, byzChar.font)) return false;
        if (!Objects.equals(text, byzChar.text)) return false;
        return byzClass == byzChar.byzClass;
    }

    @Override
    public int hashCode() {
        int result = codePoint;
        result = 31 * result + (font != null ? font.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + byzClass.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ByzChar{" +
                "codePoint=" + codePoint +
                ", font='" + font + '\'' +
                ", text='" + text + '\'' +
                ", byzClass=" + byzClass +
                '}';
    }

    String getCodePointClass() {
        return byzClass + String.format("%03d", codePoint);
    }

    @Contract("null -> false")
    static boolean isGorgonOrArgo(ByzChar Char) {
        if (Char instanceof TimeChar) {
            TimeChar tChar = (TimeChar) Char;
            return tChar.getDivisions()>0;
        }
        return false;
    }

    boolean equals(int codePoint, ByzClass byzClass) {
        return this.codePoint == codePoint && this.byzClass == byzClass;
    }

}
