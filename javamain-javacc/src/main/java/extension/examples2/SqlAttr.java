package extension.examples2;

import java.util.ArrayList;
import java.util.List;

public class SqlAttr {
    private String tableName;
    private List<Column> selectColumnList;
    private List<KV> whereColumList;

    public SqlAttr() {
        this.selectColumnList = new ArrayList<>();
        this.whereColumList = new ArrayList<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getSelectColumnList() {
        return selectColumnList;
    }

    public void setSelectColumnList(List<Column> selectColumnList) {
        this.selectColumnList = selectColumnList;
    }

    public List<KV> getWhereColumList() {
        return whereColumList;
    }

    public void setWhereColumList(List<KV> whereColumList) {
        this.whereColumList = whereColumList;
    }
}
