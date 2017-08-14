package at.favre.tools.dice.encode;

/**
 * Encodes with given alphabet
 */
public abstract class AlphabetEncoder implements Encoder {

    private final String alphabet;

    AlphabetEncoder(String alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public String encode(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(alphabet.charAt((array[i] & 0xFF) % alphabet.length()));
        }
        return sb.toString();
    }
}
