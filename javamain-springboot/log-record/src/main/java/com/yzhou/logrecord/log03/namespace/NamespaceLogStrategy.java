package com.yzhou.logrecord.log03.namespace;

import com.yzhou.logrecord.log03.LogEntry;
import com.yzhou.logrecord.log03.LogStrategy;
import org.springframework.stereotype.Component;

@Component
public class NamespaceLogStrategy implements LogStrategy {
    @Override
    public void save(LogEntry logEntry) {
        System.out.println(logEntry.getSummaryTemplate().format(logEntry.getParams()));
    }
}
