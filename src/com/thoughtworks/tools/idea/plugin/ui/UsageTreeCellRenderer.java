package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;

import javax.swing.*;

import static com.thoughtworks.tools.idea.plugin.model.Usage.Type.Using;

class UsageTreeCellRenderer extends ColoredTreeCellRenderer {
    @Override
    public void customizeCellRenderer(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof TemplateTreeNode || value instanceof UsageTreeNode) {
            setIcon(Icons.TemplateIcon);
        }
        if (value instanceof UsageTypeTreeNode) {
            Icon icon = ((UsageTypeTreeNode) value).getUsageType() == Using ? Icons.Using : Icons.UsedBy;
            setIcon(icon);
        }

        PatchedDefaultMutableTreeNode node = (PatchedDefaultMutableTreeNode) value;
        append(node.getText());
    }
}
