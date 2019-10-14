package Byzantine.Exceptions;

import Byzantine.Exceptions.ByzantineException;

public class WrongTimeBeatException extends ByzantineException {

    public WrongTimeBeatException() {
    }

    public WrongTimeBeatException(String s) {
        super(s);
    }
}
