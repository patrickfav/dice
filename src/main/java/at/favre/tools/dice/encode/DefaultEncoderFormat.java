package at.favre.tools.dice.encode;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Default encoder format
 */
public class DefaultEncoderFormat implements EncoderFormat {
    private static final int DEFAULT_WIDTH = 96;

    private final String sepCmdLine;
    private final String sepFile;
    private final String newLineCmdLine;
    private final String newLineFile;
    private final String paragraphCmdLine;
    private final String paragraphFile;
    private final Charset charset;
    private final int printWidth;

    public DefaultEncoderFormat() {
        this(" ", " ", System.lineSeparator(), System.lineSeparator(), System.lineSeparator(), "", StandardCharsets.UTF_8, DEFAULT_WIDTH);
    }

    public DefaultEncoderFormat(String sepCmdLine, String sepFile, String newLineCmdLine, String newLineFile, String paragraphCmdLine, String paragraphFile, Charset charset, int printWidth) {
        this.sepCmdLine = sepCmdLine;
        this.sepFile = sepFile;
        this.newLineCmdLine = newLineCmdLine;
        this.newLineFile = newLineFile;
        this.paragraphCmdLine = paragraphCmdLine;
        this.paragraphFile = paragraphFile;
        this.charset = charset;
        this.printWidth = printWidth;
    }

    @Override
    public String separatorCmdLine() {
        return sepCmdLine;
    }

    @Override
    public String newLineCmdLine() {
        return newLineCmdLine;
    }

    @Override
    public String separatorFile() {
        return sepFile;
    }

    @Override
    public String newLineFile() {
        return newLineFile;
    }

    @Override
    public String paragraphCmdLine() {
        return paragraphCmdLine;
    }

    @Override
    public String paragraphFile() {
        return paragraphFile;
    }

    @Override
    public int printWidth() {
        return printWidth;
    }

    @Override
    public byte[] asBytes(String encodedString) {
        return encodedString.getBytes(charset);
    }
}
