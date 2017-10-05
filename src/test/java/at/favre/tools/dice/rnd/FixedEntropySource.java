package at.favre.tools.dice.rnd;

import java.util.Arrays;

public class FixedEntropySource implements ExpandableEntropySource {
    private final byte[] entropy;

    public FixedEntropySource(byte[] entropy) {
        this.entropy = entropy;
    }

    @Override
    public byte[] generateEntropy(int lengthByte) {
        return entropy;
    }

    @Override
    public String getInformation() {
        return "FixedEntropySource (" + Arrays.hashCode(entropy) + ")";
    }
}
