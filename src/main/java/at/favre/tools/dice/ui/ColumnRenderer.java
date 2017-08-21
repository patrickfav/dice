package at.favre.tools.dice.ui;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

public class ColumnRenderer {
    private static final int MAX_WIDTH = 80;
    private static final char SEPARATOR = ' ';

    private final int targetWidth;

    public ColumnRenderer() {
        this(MAX_WIDTH);
    }

    public ColumnRenderer(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public void render(List<String> outputList, PrintStream outStream) {
        if (!outputList.isEmpty()) {
            final int maxLength = outputList.stream().max(Comparator.comparingInt(String::length)).get().length();
            int columns = Math.max(1, targetWidth / maxLength);

            while (maxLength * columns + columns > targetWidth) {
                columns--;
            }

            int columnCounter = columns;
            for (String randomString : outputList) {

                if (columns == 1) {
                    outStream.print(randomString);
                } else {
                    outStream.print(String.format("%-" + maxLength + "s", randomString));
                }

                columnCounter--;

                if (columnCounter == 0) {
                    columnCounter = columns;
                    outStream.print("\n");
                } else {
                    outStream.print(SEPARATOR);
                }
            }
        }
    }
}
