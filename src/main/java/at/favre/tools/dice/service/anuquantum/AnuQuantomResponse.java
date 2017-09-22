package at.favre.tools.dice.service.anuquantum;

import java.util.List;

public class AnuQuantomResponse {
    public final String type;
    public final Integer length;
    public final Integer size;
    public final List<String> data;
    public final boolean success;

    public AnuQuantomResponse(String type, Integer length, Integer size, List<String> data, boolean success) {
        this.type = type;
        this.length = length;
        this.size = size;
        this.data = data;
        this.success = success;
    }
}
