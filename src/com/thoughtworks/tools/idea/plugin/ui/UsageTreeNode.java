package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.thoughtworks.tools.idea.plugin.model.Usage;

public class UsageTreeNode extends PatchedDefaultMutableTreeNode {

    private Usage usage;

    public UsageTreeNode(Usage usage) {
        super(usage.getTemplate().getName());
        this.usage = usage;
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