package Byzantine;

class Cloner {

    private static com.rits.cloning.Cloner cloner = new com.rits.cloning.Cloner();

    private Cloner() {
    }

    static <T> T deepClone(T o) {
        return cloner.deepClone(o);
    }
}
