package at.favre.tools.dice.ui;

import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.Nullable;

import java.io.File;

@AutoValue
public abstract class Arg {
    static final int DEFAULT_LENGTH = 16;
    public static final long DEFAULT_COUNT = 32;
    public static final String DEFAULT_ENCODING = "hex";

    public static Arg create(String encoding, String seed, int length, Long count, boolean offline, boolean urlencode, boolean debug, boolean padding, boolean robot, boolean crc32, File outFile) {
        return builder()
                .encoding(encoding)
                .seed(seed)
                .length(length)
                .count(count)
                .offline(offline)
                .urlencode(urlencode)
                .debug(debug)
                .padding(padding)
                .robot(robot)
                .crc32(crc32)
                .outFile(outFile)
                .build();
    }

    public abstract String encoding();

    @Nullable
    public abstract String seed();

    public abstract int length();

    @Nullable
    public abstract Long count();

    public abstract boolean debug();

    public abstract boolean offline();

    public abstract boolean urlencode();

    public abstract boolean padding();

    public abstract boolean robot();

    public abstract boolean crc32();

    @Nullable
    public abstract File outFile();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_Arg.Builder()
                .encoding(DEFAULT_ENCODING)
                .length(DEFAULT_LENGTH)
                .debug(false)
                .offline(false)
                .urlencode(false)
                .padding(false)
                .robot(false)
                .crc32(false)
                .outFile(null);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder encoding(String value);

        public abstract Builder seed(String value);

        public abstract Builder length(int value);

        public abstract Builder count(Long value);

        public abstract Builder debug(boolean value);

        public abstract Builder offline(boolean value);

        public abstract Builder urlencode(boolean value);

        public abstract Builder padding(boolean value);

        public abstract Builder robot(boolean value);

        public abstract Builder crc32(boolean value);

        public abstract Builder outFile(File value);

        public abstract Arg build();
    }
}
