package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.UnknownFileType;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.thoughtworks.tools.idea.plugin.model.Template;

import javax.swing.*;
import java.util.stream.Stream;

import static com.thoughtworks.tools.idea.plugin.model.Usage.Type.Using;

class UsageTreeCellRenderer extends ColoredTreeCellRenderer {

    private static final Icon USING_ICON = IconLoader.findIcon("/using.png");
    private static final Icon USED_BY_ICON = IconLoader.findIcon("/used-by.png");
    private static final Icon TEMPLATE_ICON = resolveIcon();

    @Override
    public void customizeCellRenderer(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof TemplateTreeNode || value instanceof UsageTreeNode) {
            setIcon(TEMPLATE_ICON);
        }
        if (value instanceof UsageTypeTreeNode) {
            Icon icon = ((UsageTypeTreeNode) value).getUsageType() == Using ? USING_ICON : USED_BY_ICON;
            setIcon(icon);
        }

        PatchedDefaultMutableTreeNode node = (PatchedDefaultMutableTreeNode) value;
        append(node.getText());
    }

    private static Icon resolveIcon() {
        return Stream.of(Template.EXTENSIONS)
            .map(ext -> FileTypeManager.getInstance().getFileTypeByExtension(ext))
            .filter(fileType -> !(fileType instanceof UnknownFileType))
            .findFirst()
            .map(FileType::getIcon)
            .orElse(null);
    }
}