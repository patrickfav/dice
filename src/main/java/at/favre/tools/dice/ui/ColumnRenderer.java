package at.favre.tools.dice.ui;

import at.favre.tools.dice.encode.EncoderFormat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

public class ColumnRenderer {
    private static final int MAX_WIDTH = 80;
    private static final int LINE_BREAK_EVERY_LINES = 24;

    private final int targetWidth;
    private final EncoderFormat encoderFormat;

    public ColumnRenderer(EncoderFormat encoderFormat) {
        this(encoderFormat, MAX_WIDTH);
    }

    public ColumnRenderer(EncoderFormat encoderFormat, int targetWidth) {
        this.encoderFormat = encoderFormat;
        this.targetWidth = targetWidth;
    }

    /**
     * Will take a list and a target count and tries to create even columns
     *
     * @param targetCount approximate count you want to render (may be filled by the auto algorithm)
     * @param outputList  the list containing more elements than targetCount
     * @param outStream   to write the output to
     * @return the actual used count
     */
    public int renderAutoColumn(int targetCount, List<String> outputList, PrintStream outStream, boolean toFile) {
        final int columns = getColumnCount(getMaxLength(outputList));

        final int fill = columns - (targetCount % columns);
        return render(outputList.subList(0, Math.min(outputList.size(), targetCount + fill)), outStream, toFile);
    }

    public int renderSingleColumn(List<String> outputList, PrintStream outStream) {
        outputList.forEach(s -> outStream.print(s + System.lineSeparator()));
        return outputList.size();
    }

    public int renderNoColumns(List<String> outputList, PrintStream outStream) {
        outputList.forEach(s -> writeNoException(outStream, encoderFormat.asBytes(s)));
        return outputList.size();
    }

    private void writeNoException(OutputStream outputStream, byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new IllegalStateException("could not write random data to printstream", e);
        }
    }

    public int render(List<String> outputList, PrintStream outStream, boolean toFile) {
        if (!outputList.isEmpty()) {
            final int maxLength = getMaxLength(outputList);
            final int columns = getColumnCount(maxLength);
            int columnCounter = columns;
            int lineCount = 0;

            for (int i = 0; i < outputList.size(); i++) {
                String randomString = outputList.get(i);
                try {
                    if (columns == 1) {
                        outStream.write(encoderFormat.asBytes(randomString));
                    } else {
                        outStream.write(encoderFormat.asBytes(String.format("%-" + maxLength + "s", randomString)));
                    }

                    columnCounter--;

                    if (columnCounter == 0 && i + 1 != outputList.size()) {
                        columnCounter = columns;
                        outStream.write(encoderFormat.asBytes(toFile ? encoderFormat.newLineFile() : encoderFormat.newLineCmdLine()));
                        lineCount++;

                        if (lineCount % LINE_BREAK_EVERY_LINES == 0) {
                            outStream.write(encoderFormat.asBytes(toFile ? encoderFormat.paragraphFile() : encoderFormat.paragraphCmdLine()));
                        }

                    } else {
                        outStream.write(encoderFormat.asBytes(toFile ? encoderFormat.separatorFile() : encoderFormat.separatorFile()));
                    }
                } catch (Exception e) {
                    throw new IllegalStateException("could not write random to output stream", e);
                }
            }
        }
        return outputList.size();
    }

    private int getMaxLength(List<String> outputList) {
        return outputList.stream().max(Comparator.comparingInt(String::length)).get().length();
    }

    private int getColumnCount(int maxLength) {
        int columns = Math.max(1, targetWidth / maxLength);

        while (maxLength * columns + columns > targetWidth) {
            columns--;
        }
        return Math.max(1, columns);
    }
}
