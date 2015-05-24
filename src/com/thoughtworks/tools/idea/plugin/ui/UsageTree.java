package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.OpenSourceUtil;
import com.thoughtworks.tools.idea.plugin.model.Template;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static com.thoughtworks.tools.idea.plugin.model.Usage.Type.Using;

public class UsageTree extends Tree {

    private List<Template> templateUsage;
    private Project project;

    public UsageTree(List<Template> templateUsage, Project project) {
        super(new RootTreeNode(templateUsage));
        this.templateUsage = templateUsage;
        this.project = project;
        this.setRootVisible(false);

        setupEventListener();
    }

    public Project getProject() {
        return project;
    }

    private void setupEventListener() {
        this.addMouseListener(new MyMouseAdapter(this));
    }

    private static class MyMouseAdapter extends MouseAdapter {
        private UsageTree tree;

        public MyMouseAdapter(UsageTree tree) {
            this.tree = tree;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() != 2) {
                return;
            }

            TreePath path = tree.getPathForLocation(e.getX(), e.getY());
            if (path == null) {
                return;
            }

            TreeNode node = (TreeNode) path.getLastPathComponent();
            if (node instanceof TemplateTreeNode) {
                TemplateTreeNode templateNode = (TemplateTreeNode) node;
                Template template = templateNode.getUserObject();
                OpenSourceUtil.navigate(true, new OpenFileDescriptor(tree.getProject(), template.getVirtualFile(), 0, 0));

            } else if (node instanceof UsageTreeNode) {
                UsageTreeNode usageNode = (UsageTreeNode) node;
                VirtualFile fileToOpen;
                fileToOpen = Using == usageNode.getUsageType() ?
                    usageNode.getUsageSource().getVirtualFile() :
                    usageNode.getUserObject().getTemplate().getVirtualFile();

                int logicLineNumber = usageNode.getUserObject().getLineNum() - 1;
                OpenSourceUtil.navigate(true, new OpenFileDescriptor(tree.getProject(), fileToOpen, logicLineNumber, 0));
            }
        }
    }
}