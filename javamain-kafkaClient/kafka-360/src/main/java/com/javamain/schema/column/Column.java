package com.javamain.schema.column;

import com.javamain.schema.convert.FlinkDataType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
// 字段对象
public class Column implements Serializable {

    // 名称
    private String name;

    // Flink类型
    private FlinkDataType type;

    // 默认值
    private String defaultValue;

    // 标度
    private Long scale;

    // 描述
    private String comment;

    // 精度
    private Long precision;

    // 是否为null
    private boolean notNull;

    // 是否为主键
    private boolean key;

    // 是否忽略字段展示
    private boolean ignore;

    // 字符集名称
    private String characterSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        return new EqualsBuilder()
                .append(notNull, column.notNull)
                .append(key, column.key)
                .append(ignore, column.ignore)
                .append(name, column.name)
                .append(type, column.type)
                .append(defaultValue, column.defaultValue)
                .append(scale, column.scale)
                .append(comment, column.comment)
                .append(precision, column.precision)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(type)
                .append(defaultValue)
                .append(scale)
                .append(comment)
                .append(precision)
                .append(notNull)
                .append(key)
                .append(ignore)
                .toHashCode();
    }
}
