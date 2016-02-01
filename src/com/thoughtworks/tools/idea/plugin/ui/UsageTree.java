package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.TreeSpeedSearch;
import com.intellij.ui.treeStructure.Tree;
import com.thoughtworks.tools.idea.plugin.model.Template;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class UsageTree extends Tree {

    private final RootTreeNode root;
    private Project project;

    public UsageTree(Project project, RootTreeNode root) {
        super(root);
        this.root = root;
        this.project = project;
        this.setCellRenderer(new UsageTreeCellRenderer());
        this.setRootVisible(false);
        this.setExpandsSelectedPaths(true);

        setupSpeedSearch();
        setupEventListener();
    }

    private void setupSpeedSearch() {
        new TreeSpeedSearch(this);
    }

    public Project getProject() {
        return project;
    }

    private void setupEventListener() {
        this.addMouseListener(new TreeMouseClickHandler(this));
    }

    public void selectTemplate(Template template) {
        TreeNode node = findNodeByTemplate(template);
        if (node == null) {
            return;
        }

        TreeNode[] nodes = ((DefaultTreeModel) getModel()).getPathToRoot(node);
        TreePath path = new TreePath(nodes);

        setSelectionPath(path);
        scrollPathToVisible(path);
    }

    private TreeNode findNodeByTemplate(Template template) {
        int total = root.getChildCount();
        for (int i = 0; i < total; i++) {
            TemplateTreeNode child = (TemplateTreeNode) root.getChildAt(i);
            if (child != null && child.getUserObject() == template) {
                return child;
            }
        }

        return null;
    }
}