package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.openapi.graph.view.hierarchy.HierarchyTreeModel;
import com.thoughtworks.tools.idea.plugin.model.Template;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import java.util.Comparator;
import java.util.List;

public class TemplateHierarchyTreeModel implements HierarchyTreeModel {
    public static final String ROOT = "root";
    private List<Template> templates;

    public TemplateHierarchyTreeModel(List<Template> templates) {
        this.templates = templates;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void setChildComparator(Comparator comparator) {
    }

    @Override
    public Comparator getChildComparator() {
        return null;
    }

    @Override
    public void addTreeModelListener(TreeModelListener treeModelListener) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener treeModelListener) {
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (ROOT == parent) {
            return templates.get(index).getName();
        }

        if (templates.contains(parent)) {
            if (index == 1) {
                return "using";
            } else if (index == 2) {
                return "used by";
            }
        }

        return "using-used by";
    }

    @Override
    public int getChildCount(Object parent) {
        if (ROOT == parent) {
            return templates.size();
        }

        if (templates.contains(parent)) {
            return 2;
        }

        return 1;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (parent == ROOT) {
            return templates.indexOf(child);
        }

        if (child.equals("using")) {
            return 0;
        } else if (child.equals("used by")) {
            return 1;
        }
        return 1;
    }

    @Override
    public Object getRoot() {
        return ROOT;
    }

    @Override
    public boolean isLeaf(Object object) {
        return !(object == ROOT || templates.contains(object));
    }

    @Override
    public void valueForPathChanged(TreePath treePath, Object o) {

    }
}
