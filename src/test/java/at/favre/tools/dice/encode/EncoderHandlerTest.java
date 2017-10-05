package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.AByteEncoder;
import at.favre.tools.dice.encode.languages.AProgrammingLanguagesEncoder;
import at.favre.tools.dice.ui.Arg;
import at.favre.tools.dice.util.ByteUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EncoderHandlerTest {
    private final byte[] exampleBytes = ByteUtils.unsecureRandomBytes(7);

    @Test
    public void load() throws Exception {
        List<Encoder> encoders = new EncoderHandler().load();

        System.out.println(Arrays.toString(encoders.toArray()));

        for (Encoder encoder : encoders) {
            assertNotNull(encoder.names());
            assertNotNull(encoder.encode(new byte[8]));
            assertTrue(encoder.names().length > 0);
        }

        assertNotNull(encoders);
        assertTrue(encoders.size() > 10);
    }

    @Test
    public void testFindByName() {
        assertNotNull(new EncoderHandler().findByName(Arg.DEFAULT_ENCODING));
    }

    @Test
    public void testDescription() {
        String description = new EncoderHandler().getFullSupportedEncodingList();
        assertNotNull(description);
        assertTrue(description.length() > 100);
        System.out.println(description);
    }

    @Test
    public void testEncoderTable() {
        String description = getByteEncoderMarkdownTable();
        assertNotNull(description);
        assertTrue(description.length() > 100);
        System.out.println(description);
    }

    @Test
    public void testSupportedEncodings() {
        String description = new EncoderHandler().getSupportedEncodingList();
        assertNotNull(description);
        assertTrue(description.length() > 100);
        System.out.println(description);
    }

    @Test
    public void testProgrammingEncoderTable() {
        String description = getLanguageEncoderMarkdownTable();
        assertNotNull(description);
        assertTrue(description.length() > 100);
        System.out.println(description);
    }

    private String getByteEncoderMarkdownTable() {
        StringBuilder sb = new StringBuilder();

        //Header
        sb.append("| ").append("Name").append(" | ");
        sb.append("Example").append(" | ");
        sb.append("Efficiency").append(" | ");
        sb.append("Padding").append(" | ");
        sb.append("Description").append(" |").append("\n");

        //Header divider
        sb.append("| ").append("-------------").append(" | ");
        sb.append("-------------").append(" | ");
        sb.append("-------------:").append(" | ");
        sb.append(":-------------:").append(" | ");
        sb.append("-------------").append(" |").append("\n");

        //Body divider
        for (Encoder encoder : EncoderHandler.ENCODERS) {
            if (encoder instanceof AByteEncoder) {
                AByteEncoder aByteEncoder = (AByteEncoder) encoder;

                sb.append("| ").append(String.format("%-12s", aByteEncoder.names()[0])).append(" | ");
                sb.append(String.format("%-20s", "`" + aByteEncoder.encode(exampleBytes) + "`")).append(" | ");
                sb.append(String.format(Locale.US, "%.1f", aByteEncoder.spaceEfficiency() * 100)).append(" %").append(" | ");
                sb.append(aByteEncoder.mayNeedPadding()).append(" | ");
                sb.append(aByteEncoder.getDescription()).append(" |").append("\n");
            }
        }
        return sb.toString();
    }

    private String getLanguageEncoderMarkdownTable() {
        StringBuilder sb = new StringBuilder();

        //Header
        sb.append("| ").append("Name").append(" | ");
        sb.append("Example").append(" | ").append("\n");

        //Header divider
        sb.append("| ").append(":-------------:").append(" | ");
        sb.append("-------------").append(" |").append("\n");

        //Body divider
        for (Encoder encoder : EncoderHandler.ENCODERS) {
            if (encoder instanceof AProgrammingLanguagesEncoder) {
                AProgrammingLanguagesEncoder progEncoder = (AProgrammingLanguagesEncoder) encoder;

                sb.append("| ").append(String.format("%-12s", progEncoder.names()[0])).append(" | ");
                sb.append("`").append(progEncoder.encode(exampleBytes)).append("`").append(" | ").append("\n");
            }
        }
        return sb.toString();
    }
}