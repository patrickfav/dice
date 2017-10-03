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
