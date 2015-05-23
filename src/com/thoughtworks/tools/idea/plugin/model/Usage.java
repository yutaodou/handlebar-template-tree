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

    public enum Type {
        Using("Using"), UsedBy("Used by");

        private String type;

        Type(String type) {
            this.type = type;
        }

        public String getText() {
            return type;
        }
    }
}
