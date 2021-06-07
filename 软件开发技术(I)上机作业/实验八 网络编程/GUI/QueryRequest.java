package GUI;

import java.io.Serializable;

public class QueryRequest implements Serializable {
    public static enum TYPE {
        ID, NAME
    }

    private TYPE type;
    private String data;

    public QueryRequest(TYPE type, String data) {
        this.type = type;
        this.data = data;
    }

    public TYPE getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
