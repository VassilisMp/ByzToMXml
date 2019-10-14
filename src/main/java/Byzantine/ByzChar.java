package Byzantine;

import com.google.gson.annotations.Expose;

public class ByzChar extends UnicodeChar {

    private static final long serialVersionUID = 7706296349475294817L;

    @Expose
    protected ByzClass ByzClass;

    public ByzChar(int codePoint, Byzantine.ByzClass byzClass) {
        super(codePoint);
        ByzClass = byzClass;
        classType = this.getClass().getSimpleName();
    }

    ByzClass getByzClass() {
        return ByzClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ByzChar)) return false;
        if (!super.equals(o)) return false;

        ByzChar byzChar = (ByzChar) o;

        return ByzClass == byzChar.ByzClass && codePoint == byzChar.codePoint;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ByzClass != null ? ByzClass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ByzChar{" +
                "ByzClass=" + ByzClass +
                ", codePoint=" + codePoint +
                '}';
    }

    String getCodePointClass() {
        return ByzClass + String.format("%03d", codePoint);
    }

    @Override
    public void accept(Engine engine) {}
}
