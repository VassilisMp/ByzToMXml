package Byzantine.Exceptions;

import Byzantine.Exceptions.ByzantineException;

public class NotSupportedException extends ByzantineException {
    private static final long serialVersionUID = 5195511250079656443L;

    public NotSupportedException() {
    }

    public NotSupportedException(String var1) {
        super(var1);
    }
}
