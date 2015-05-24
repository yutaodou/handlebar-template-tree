package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.thoughtworks.tools.idea.plugin.model.Template;
import com.thoughtworks.tools.idea.plugin.model.Usage;

public class UsageTreeNode extends PatchedDefaultMutableTreeNode {

    private final Usage usage;
    private final Usage.Type type;
    private final Template template;

    public UsageTreeNode(Template template, Usage.Type type, Usage usage) {
        super(usage.getTemplate().getName());
        this.usage = usage;
        this.type = type;
        this.template = template;
    }

    public Usage.Type getUsageType() {
        return type;
    }

    public Template getUsageSource() {
        return template;
    }

    @Override
    public String getText() {
        return String.format("%s (Line: %s)", usage.getTemplate().getName(), usage.getLineNum());
    }

    @Override
    public Usage getUserObject() {
        return usage;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public String toString() {
        return getText();
    }
}