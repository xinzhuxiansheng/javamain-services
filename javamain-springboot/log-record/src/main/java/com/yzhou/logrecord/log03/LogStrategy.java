package com.yzhou.logrecord.log03;

import com.yzhou.logrecord.modal.User;

import java.util.Map;

public interface LogStrategy {
    default String getBizNo(LogEntry logEntry){
        return logEntry.getBizNo();
    }
    default User getUser(LogEntry logEntry){
        return logEntry.getUser();
    }
    default long getCreateTime(LogEntry logEntry){
        return logEntry.getCreateTime();
    }
    default ILogTemplate getSummaryTemplate(LogEntry logEntry){
        return logEntry.getSummaryTemplate();
    }
    default Map<String,Object> getParams(LogEntry logEntry){
        return logEntry.getParams();
    }

    void save(LogEntry logEntry);
}
