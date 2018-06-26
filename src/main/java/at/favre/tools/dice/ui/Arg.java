/*
 *  Copyright 2017 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package at.favre.tools.dice.ui;

import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.PrintStream;

@AutoValue
public abstract class Arg {
    static final int DEFAULT_LENGTH = 16;
    public static final long DEFAULT_COUNT = 32;
    public static final String DEFAULT_ENCODING = "hex";

    public static Arg create(PrintStream cmdLinePrintStream, String encoding, String seed, int length, Long count, boolean offline, boolean enableAnuQuantum, boolean urlencode, boolean debug, boolean padding, boolean robot, boolean crc32, File outFile) {
        return builder()
                .cmdLinePrintStream(cmdLinePrintStream)
                .encoding(encoding)
                .seed(seed)
                .length(length)
                .count(count)
                .offline(offline)
                .enableAnuQuantum(enableAnuQuantum)
                .urlencode(urlencode)
                .debug(debug)
                .padding(padding)
                .robot(robot)
                .crc32(crc32)
                .outFile(outFile)
                .build();
    }

    public abstract PrintStream cmdLinePrintStream();

    public abstract String encoding();

    @Nullable
    public abstract String seed();

    public abstract int length();

    @Nullable
    public abstract Long count();

    public abstract boolean debug();

    public abstract boolean offline();

    public abstract boolean enableAnuQuantum();

    public abstract boolean urlencode();

    public abstract boolean padding();

    public abstract boolean robot();

    public abstract boolean crc32();

    @Nullable
    public abstract File outFile();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_Arg.Builder()
                .cmdLinePrintStream(System.out)
                .encoding(DEFAULT_ENCODING)
                .length(DEFAULT_LENGTH)
                .debug(false)
                .offline(false)
                .enableAnuQuantum(false)
                .urlencode(false)
                .padding(false)
                .robot(false)
                .crc32(false)
                .outFile(null);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cmdLinePrintStream(PrintStream value);

        public abstract Builder encoding(String value);

        public abstract Builder seed(String value);

        public abstract Builder length(int value);

        public abstract Builder count(Long value);

        public abstract Builder debug(boolean value);

        public abstract Builder offline(boolean value);

        public abstract Builder enableAnuQuantum(boolean value);

        public abstract Builder urlencode(boolean value);

        public abstract Builder padding(boolean value);

        public abstract Builder robot(boolean value);

        public abstract Builder crc32(boolean value);

        public abstract Builder outFile(File value);

        public abstract Arg build();
    }
}
