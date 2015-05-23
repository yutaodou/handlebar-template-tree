package com.thoughtworks.tools.idea.plugin.model;

public class Usage {
    private final int lineNum;
    private final Template template;

    public Usage(Template template, int lineNum) {
        this.lineNum = lineNum;
        this.template = template;
    }

    public int getLineNum() {
        return lineNum;
    }

    public Template getTemplate() {
        return template;
    }
}
