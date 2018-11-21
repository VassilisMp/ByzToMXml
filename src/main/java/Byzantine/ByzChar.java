package Byzantine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ByzChar extends UnicodeChar {

    private static final long serialVersionUID = 7706296349475294817L;

    protected ByzClass ByzClass;

    public ByzChar(int codePoint, String font, Byzantine.ByzClass byzClass) {
        super(codePoint, font);
        ByzClass = byzClass;
    }
}
