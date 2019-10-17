package Byzantine;

final class Cloner {

    private static final com.rits.cloning.Cloner cloner = new com.rits.cloning.Cloner();
    static <T> T deepClone(T o) {
        return cloner.deepClone(o);
    }

}
