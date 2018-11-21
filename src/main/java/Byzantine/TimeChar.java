package Byzantine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TimeChar extends ByzChar{

    private static final long serialVersionUID = 6353312089523385239L;

    int dotPlace;
    int divisions;
    Boolean argo;

    public TimeChar(int codePoint, String font, Byzantine.ByzClass byzClass, int dotPlace, int divisions, Boolean argo) {
        super(codePoint, font, byzClass);
        this.dotPlace = dotPlace;
        this.divisions = divisions;
        this.argo = argo;
    }

    @Override
    public String toString() {
        return "TimeChar{" +
                "dotPlace=" + dotPlace +
                ", divisions=" + divisions +
                ", argo=" + argo +
                ", ByzClass=" + ByzClass +
                ", codePoint=" + codePoint +
                '}';
    }
}
