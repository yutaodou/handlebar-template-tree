package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.thoughtworks.tools.idea.plugin.model.Template;

import javax.swing.tree.TreeNode;
import java.util.List;

class RootTreeNode extends PatchedDefaultMutableTreeNode {
    private List<Template> templateList;

    public RootTreeNode(List<Template> templateList) {
        this.templateList = templateList;
    }

    @Override
    public String getText() {
        return "root";
    }

    @Override
    public int getChildCount() {
        return templateList.size();
    }

    @Override
    public TreeNode getChildAt(int i) {
        return new TemplateTreeNode(templateList.get(i));
    }

    @Override
    public String toString() {
        return getText();
    }
}