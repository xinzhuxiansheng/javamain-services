package com.yzhou.logrecord.log03.namespace;

import com.yzhou.logrecord.log03.ILogTemplate;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

public enum NamespaceLogTemplate implements ILogTemplate {
    CREATE("用户「%(username)」创建 Namespace「ID：%(id)」成功");

    private String template;

    private NamespaceLogTemplate(String template){
        this.template = template;
    }

    @Override
    public String format(Map<String, Object> params) {
        String result = template;
        if (isEmpty(params)) {
            return result;
        }
        StringSubstitutor sub = new StringSubstitutor(params, "%(", ")");
        return sub.replace(result);
    }
}
