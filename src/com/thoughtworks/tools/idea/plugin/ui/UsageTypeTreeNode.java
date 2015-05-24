package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.thoughtworks.tools.idea.plugin.model.Template;
import com.thoughtworks.tools.idea.plugin.model.Usage;

import javax.swing.tree.TreeNode;
import java.util.List;

import static com.thoughtworks.tools.idea.plugin.model.Usage.Type.Using;

public class UsageTypeTreeNode extends PatchedDefaultMutableTreeNode {

    private Template template;
    private Usage.Type type;

    public UsageTypeTreeNode(Template template, Usage.Type type) {
        super(type.getText());
        this.template = template;
        this.type = type;
    }

    @Override
    public Template getUserObject() {
        return template;
    }

    public Usage.Type getUsageType() {
        return type;
    }

    @Override
    public String getText() {
        return type.getText();
    }

    @Override
    public int getChildCount() {
        return getUsages().size();
    }

    public List<Usage> getUsages() {
        return type == Using ? template.getUsingList() : template.getUsedByList();
    }

    @Override
    public TreeNode getChildAt(int index) {
        return new UsageTreeNode(template, type, getUsages().get(index));
    }

    @Override
    public String toString() {
        return getText();
    }
}