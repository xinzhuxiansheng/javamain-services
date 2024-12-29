package extension.examples2;

public class Column {
    private String tableName;
    private String fieldName;

    public Column(){}

    public Column(String fieldName){
        this.fieldName = fieldName;
    }

    public Column(String tableName, String fieldName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
