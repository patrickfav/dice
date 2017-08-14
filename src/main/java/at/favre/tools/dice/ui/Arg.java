package at.favre.tools.dice.ui;


public class Arg {
    static final int DEFAULT_LENGTH = 16;
    static final int DEFAULT_COUNT = 10;
    public static final String DEFAULT_ENCODING = "hex";

    public String encoding;
    public String seed;
    public int length;
    public int count;

    public boolean debug = false;

    public Arg() {
    }

    public Arg(String encoding, String seed, int length, int count, boolean debug) {
        this.encoding = encoding;
        this.seed = seed;
        this.length = length;
        this.count = count;
        this.debug = debug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arg arg = (Arg) o;

        if (length != arg.length) return false;
        if (count != arg.count) return false;
        if (debug != arg.debug) return false;
        if (encoding != null ? !encoding.equals(arg.encoding) : arg.encoding != null) return false;
        return seed != null ? seed.equals(arg.seed) : arg.seed == null;
    }

    @Override
    public int hashCode() {
        int result = encoding != null ? encoding.hashCode() : 0;
        result = 31 * result + (seed != null ? seed.hashCode() : 0);
        result = 31 * result + length;
        result = 31 * result + count;
        result = 31 * result + (debug ? 1 : 0);
        return result;
    }
}
