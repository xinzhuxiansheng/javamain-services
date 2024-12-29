package extension.examples2;

public class KV {
    private Column key;
    private String operator;
    private String value;

    public KV() {
        this.key = new Column();
    }

    public Column getKey() {
        return key;
    }

    public void setKey(Column key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
