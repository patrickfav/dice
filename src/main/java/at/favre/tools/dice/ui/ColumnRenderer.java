package at.favre.tools.dice.ui;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

public class ColumnRenderer {
    private static final int MAX_WIDTH = 80;
    private static final char SEPERATOR = ' ';

    private final int targetWidth;

    public ColumnRenderer() {
        this(MAX_WIDTH);
    }

    public ColumnRenderer(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public void render(List<String> outputList, PrintStream outStream) {
        if (!outputList.isEmpty()) {
            final int exampleLength = outputList.stream().max(Comparator.comparingInt(String::length)).get().length();
            final int columns = Math.max(1, targetWidth / exampleLength);

            int columnCounter = columns;
            for (String randomString : outputList) {

                outStream.print(randomString);
                columnCounter--;

                if (columnCounter == 0) {
                    columnCounter = columns;
                    outStream.print("\n");
                } else {
                    outStream.print(SEPERATOR);
                }
            }
        }
    }
}
