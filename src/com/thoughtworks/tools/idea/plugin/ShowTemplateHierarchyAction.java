package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;

public class ShowTemplateHierarchyAction extends DumbAwareAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getData(DataKeys.PROJECT);
        if (project == null || project.isDefault()) {
            return;
        }
        ProgressManager.getInstance().run(new AnalyseTemplateHierarchyTask(project));
    }
}