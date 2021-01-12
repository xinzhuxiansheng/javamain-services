package com.javamain.designPatterns.uml.composition;

/**
 * 组合关系
 */
public class Html {
    private Header header;
    private Content content;
    private Footer footer;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }
}
