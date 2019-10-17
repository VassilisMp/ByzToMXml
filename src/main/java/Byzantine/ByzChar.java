package Byzantine;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.Contract;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class ByzChar implements Consumer<Engine>, Cloneable {

    @Expose
    private final int codePoint;
    @Expose(serialize = false, deserialize = false)
    private String font;
    @Expose(serialize = false, deserialize = false)
    private String text;
    @Expose
    private final ByzClass byzClass;

    /**
     * Constructs a new ByzChar Object, with the given values
     * @param  codePoint the <a href="Character.html#unicode">Unicode code point</a> of this Byzantine Character
     * @param  byzClass the Byzantine Class of this Byzantine Character
     * @see    ByzClass
     */
    ByzChar(int codePoint, ByzClass byzClass) {
        this.codePoint = codePoint;
        this.byzClass = byzClass;
    }

    /**
     * Returns this codePoint value
     */
    public int getCodePoint() {
        return codePoint;
    }

    public String getText() {
        return text;
    }

    public ByzClass getByzClass() {
        return byzClass;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setText(String text) {
        this.text = text;
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
        return "codePoint=" + codePoint +
                ", font='" + font + '\'' +
                ", text='" + text + '\'' +
                ", byzClass=" + byzClass +
                ", ";
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

    /**
     * Creates and returns a deep copy of this object, or null instead of throwing an Exception.
     * @return a deep clone of this instance.
     */
    @Override
    protected ByzChar clone() {
        try {
            return (ByzChar) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
