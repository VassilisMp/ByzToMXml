package Byzantine;

import org.jetbrains.annotations.Contract;

class UnicodeChars {
    @Contract("null -> false")
    static boolean isGorgonOrArgo(UnicodeChar Char) {
        if (Char instanceof TimeChar) {
            TimeChar tChar = (TimeChar) Char;
            return tChar.getDivisions()>0;
        }
        return false;
    }

    @Contract(value = "null, _, _ -> false", pure = true)
    static boolean equals(UnicodeChar Char, int codePoint, ByzClass byzClass) {
        if (Char instanceof ByzChar) {
            ByzChar byzChar = (ByzChar) Char;
            return byzChar.codePoint == codePoint && byzChar.ByzClass == byzClass;
        }
        return false;
    }
}
