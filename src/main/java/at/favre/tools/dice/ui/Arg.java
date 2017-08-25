package at.favre.tools.dice.ui;


public class Arg {
    static final int DEFAULT_LENGTH = 16;
    public static final int DEFAULT_COUNT = 32;
    public static final String DEFAULT_ENCODING = "hex";

    public String encoding;
    public String seed;
    public int length;
    public Integer count;

    public boolean debug = false;
    public boolean offline = false;
    public boolean urlencode = false;

    public Arg() {
    }

    public Arg(String encoding, String seed, int length, Integer count, boolean offline, boolean urlencode, boolean debug) {
        this.encoding = encoding;
        this.seed = seed;
        this.length = length;
        this.count = count;
        this.offline = offline;
        this.urlencode = urlencode;
        this.debug = debug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arg arg = (Arg) o;

        if (length != arg.length) return false;
        if (debug != arg.debug) return false;
        if (offline != arg.offline) return false;
        if (urlencode != arg.urlencode) return false;
        if (encoding != null ? !encoding.equals(arg.encoding) : arg.encoding != null) return false;
        if (seed != null ? !seed.equals(arg.seed) : arg.seed != null) return false;
        return count != null ? count.equals(arg.count) : arg.count == null;
    }

    @Override
    public int hashCode() {
        int result = encoding != null ? encoding.hashCode() : 0;
        result = 31 * result + (seed != null ? seed.hashCode() : 0);
        result = 31 * result + length;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (debug ? 1 : 0);
        result = 31 * result + (offline ? 1 : 0);
        result = 31 * result + (urlencode ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Arg{" +
                "encoding='" + encoding + '\'' +
                ", seed='" + seed + '\'' +
                ", length=" + length +
                ", count=" + count +
                ", debug=" + debug +
                ", offline=" + offline +
                ", urlencode=" + urlencode +
                '}';
    }
}
