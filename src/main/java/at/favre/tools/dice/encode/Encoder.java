package at.favre.tools.dice.encode;

/**
 * Interface for encoding byte arrays to string
 */
public interface Encoder {

    /**
     * Applies the internal text-to-byte encoding and returns the result string.
     * This will not pad in encodings if the output does not match the input byte array (null bytes)
     *
     * @param array to encode
     * @return encoded byte array
     */
    String encode(byte[] array);

    /**
     * Applies the internal text-to-byte encoding and adds padding if necessary
     *
     * @param array to encode
     * @return encoded and padded byte array
     */
    String encodePadded(byte[] array);

    /**
     * Given a string returned by one of the encode methods, this will return the raw byte array.
     * The used encoding is implementation detail.
     *
     * @param encodedString
     * @return the string in byte array representation
     */
    byte[] asBytes(String encodedString);

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
