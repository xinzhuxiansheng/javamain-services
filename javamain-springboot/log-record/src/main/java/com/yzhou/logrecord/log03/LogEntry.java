package com.yzhou.logrecord.log03;

import com.yzhou.logrecord.log03.namespace.NamespaceStrategy;
import com.yzhou.logrecord.modal.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LogEntry {
    private String bizNo; // 业务ID
    private User user; // 用户信息
    private long createTime; // 创建时间
    private ModuleType moduleType; // 模块
    private ILogTemplate summaryTemplate; // 描述模板
    private LogStrategy logStrategy;
    private Map<String, Object> params = new HashMap<>(); // 模板参数


    public static LogEntry build() {
        return new LogEntry();
    }

    public LogEntry bizNo(String bizNo) {
        this.bizNo = bizNo;
        return this;
    }

    public LogEntry user(User user) {
        this.user = user;
        return this;
    }

    public LogEntry createTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public LogEntry summaryTemplate(ILogTemplate summaryTemplate) {
        this.summaryTemplate = summaryTemplate;
        return this;
    }

    public LogEntry param(String key, String value) {
        params.put(key, value);
        return this;
    }


    public LogEntry moduleType(ModuleType moduleType) {
        switch (moduleType.name()) {
            case "NAMESPACE":
                this.logStrategy = new NamespaceStrategy();
                break;
            default:
                throw new IllegalArgumentException("Unknown targetType: " + moduleType.name());
        }
        return this;
    }

    public ILogTemplate getSummaryTemplate(){
        return summaryTemplate;
    }

    // Save method which will have logic to write log based on targetType
    public void save() {
        if (logStrategy == null) {
            System.out.println("aaaa");
        }
        logStrategy.save(this);
    }

}
