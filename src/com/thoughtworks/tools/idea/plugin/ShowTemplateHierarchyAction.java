package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.thoughtworks.tools.idea.plugin.model.Template;

import java.util.List;

public class ShowTemplateHierarchyAction extends DumbAwareAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(DataKeys.PROJECT);
        if (project == null || project.isDefault()) {
            return;
        }

        EntryHierarchyBuilder builder = new EntryHierarchyBuilder();
        List<Template> templates = builder.build(project);
        System.out.println(templates.size());
    }
}
