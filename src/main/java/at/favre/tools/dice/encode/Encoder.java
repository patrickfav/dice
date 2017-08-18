package at.favre.tools.dice.encode;

/**
 * Interface for encoding byte arrays to string
 */
public interface Encoder {
    String encode(byte[] array);

    /**
     * The names or alias for a user select the encoder
     *
     * @return all available names
     */
    String[] names();

    /**
     * User readable description what this encoder does
     *
     * @return description
     */
    String getDescription();
}
