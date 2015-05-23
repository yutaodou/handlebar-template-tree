package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.thoughtworks.tools.idea.plugin.model.Template;

import javax.swing.tree.TreeNode;

import static com.thoughtworks.tools.idea.plugin.model.Usage.Type.UsedBy;
import static com.thoughtworks.tools.idea.plugin.model.Usage.Type.Using;

public class TemplateTreeNode extends PatchedDefaultMutableTreeNode {
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
        return index == 0 ? new UsageTypeTreeNode(template, Using) : new UsageTypeTreeNode(template, UsedBy);
    }

    @Override
    public int getChildCount() {
        return 2;
    }

    @Override
    public String toString() {
        return getText();
    }
}
