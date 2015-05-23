package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;

import javax.swing.*;

class AnalyseTemplateHierarchyTask extends Task.Backgroundable {
    private final Project project;

    public AnalyseTemplateHierarchyTask(Project project) {
        super(project, "show template hierarchy", false);
        this.project = project;
    }

    @Override
    public void run(ProgressIndicator indicator) {
        indicator.setText("Analysing template hierarchy...");
        indicator.setIndeterminate(true);
        showHierarchyToolWindow(project);
    }

    private void showHierarchyToolWindow(final Project project) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(ShowTemplateHierarchyAction.TOOL_WINDOW_ID);
                if (toolWindow == null) {
                    toolWindow = ToolWindowManager.getInstance(project).registerToolWindow(ShowTemplateHierarchyAction.TOOL_WINDOW_ID, true, ToolWindowAnchor.RIGHT, false);
                    JLabel label = new JLabel("Show hierarchy here.");
                    toolWindow.setTitle("Template hierarchy");
                    toolWindow.getComponent().add(label);
                }
                toolWindow.show(null);
            }
        });
    }
}