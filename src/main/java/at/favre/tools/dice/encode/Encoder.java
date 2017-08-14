package at.favre.tools.dice.encode;

public interface Encoder {
    String encode(byte[] array);

    String[] names();
}
