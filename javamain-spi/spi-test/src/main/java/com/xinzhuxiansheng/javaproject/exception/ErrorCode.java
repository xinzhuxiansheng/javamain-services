package com.xinzhuxiansheng.sso.exception;

public interface ErrorCode {
    // 错误码编号
    String getCode();

    // 错误码描述
    String getDescription();

    /** 必须提供toString的实现
     *
     * <pre>
     * &#064;Override
     * public String toString() {
     * 	return String.format(&quot;Code:[%s], Description:[%s]. &quot;, this.code, this.describe);
     * }
     * </pre>
     *
     */
    String toString();
}
