package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.thoughtworks.tools.idea.plugin.model.Template;

import javax.swing.tree.TreeNode;

import static com.thoughtworks.tools.idea.plugin.model.Usage.Type.UsedBy;
import static com.thoughtworks.tools.idea.plugin.model.Usage.Type.Using;

class TemplateTreeNode extends PatchedDefaultMutableTreeNode {
    private Template template;

    public TemplateTreeNode(Template template) {
        super(template.getName());
        this.template = template;
    }

    @Override
    public String getText() {
        return template.getName();
    }

    @Override
    public Template getUserObject() {
        return template;
    }

    @Override
    public TreeNode getChildAt(int index) {
        if (index >= getChildCount()) {
            return null;
        }

        if (index == 0 && !template.getUsingList().isEmpty()) {
            return new UsageTypeTreeNode(template, Using);
        }
        return new UsageTypeTreeNode(template, UsedBy);
    }

    @Override
    public int getChildCount() {
        int count = 0;
        if (!template.getUsingList().isEmpty()) {
            count++;
        }

        if (!template.getUsedByList().isEmpty()) {
            count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return getText();
    }
}
