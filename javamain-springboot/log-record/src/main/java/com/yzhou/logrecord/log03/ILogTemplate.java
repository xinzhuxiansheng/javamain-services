package com.yzhou.logrecord.log03;

import java.util.Map;

public interface ILogTemplate {
    String format(Map<String,Object> params);
}
