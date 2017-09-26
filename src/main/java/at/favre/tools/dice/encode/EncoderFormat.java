package at.favre.tools.dice.encode;

/**
 * Format configuration for the output of random strings.
 * This is usually used with {@link at.favre.tools.dice.ui.ColumnRenderer}
 */
public interface EncoderFormat {

    /**
     * Seperator used between randoms when outputting to command line
     *
     * @return separator, may be empty if should be omitted
     */
    String separatorCmdLine();

    /**
     * New line string, after a column of strings is complete
     * Used in command line output.
     *
     * @return new line string, may be empty if should be omitted
     */
    String newLineCmdLine();

    /**
     * Seperator used between randoms when outputting to file
     *
     * @return separator
     */
    String separatorFile();

    /**
     * New line string, after a column of strings is complete
     * Used in file output.
     *
     * @return new line string, may be empty if should be omitted
     */
    String newLineFile();

    /**
     * Paragraph between blocks of columns of randoms.
     * Used in command line output.
     *
     * @return new line string, may be empty if should be omitted
     */
    String paragraphCmdLine();

    /**
     * Paragraph between blocks of columns of randoms.
     * Used in file output.
     *
     * @return new line string, may be empty if should be omitted
     */
    String paragraphFile();

    /**
     * When printing, when the printing should have a line break after x amount of chars
     *
     * @return count of chars until line break (usually around 80 on command line)
     */
    int printWidth();

    /**
     * Given a string returned by one of the encode methods, this will return the raw byte array.
     * The used encoding is implementation detail.
     *
     * @param encodedString the string to convert to byte array
     * @return the string in byte array representation
     */
    byte[] asBytes(String encodedString);

}
