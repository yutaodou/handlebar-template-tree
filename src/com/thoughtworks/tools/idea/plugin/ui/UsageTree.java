package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.Tree;
import com.thoughtworks.tools.idea.plugin.model.Template;

import java.util.List;

public class UsageTree extends Tree {

    private List<Template> templateUsage;
    private Project project;

    public UsageTree(List<Template> templateUsage, Project project) {
        super(new RootTreeNode(templateUsage));
        this.templateUsage = templateUsage;
        this.project = project;
        this.setCellRenderer(new UsageTreeCellRenderer());
        this.setRootVisible(false);
        setupEventListener();
    }

    public Project getProject() {
        return project;
    }

    private void setupEventListener() {
        this.addMouseListener(new TreeMouseClickHandler(this));
    }

}