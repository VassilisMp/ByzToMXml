package Byzantine;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import org.audiveris.proxymusic.Step;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ByzChar extends UnicodeChar {

    static BiMap<Step, Integer> stepMap = EnumHashBiMap.create(Step.class);
    static {
        stepMap.put(Step.C,0);
        stepMap.put(Step.D,1);
        stepMap.put(Step.E,2);
        stepMap.put(Step.F,3);
        stepMap.put(Step.G,4);
        stepMap.put(Step.A,5);
        stepMap.put(Step.B,6);
    }

    private static final long serialVersionUID = 7706296349475294817L;

    protected ByzClass ByzClass;

    public ByzChar(int codePoint, String font, Byzantine.ByzClass byzClass) {
        super(codePoint, font);
        ByzClass = byzClass;
    }
}
