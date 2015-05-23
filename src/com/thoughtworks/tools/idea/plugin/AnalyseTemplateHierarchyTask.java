package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.thoughtworks.tools.idea.plugin.model.Template;

import javax.swing.*;
import java.util.List;

import static com.intellij.openapi.wm.ToolWindowAnchor.RIGHT;

class AnalyseTemplateHierarchyTask extends Task.Backgroundable {
    public static final String TOOL_WINDOW_ID = "Template Hierarchy";
    private final Project project;

    public AnalyseTemplateHierarchyTask(Project project) {
        super(project, "show template hierarchy", false);
        this.project = project;
    }

    @Override
    public void run(ProgressIndicator indicator) {
        indicator.setText("Analysing template hierarchy...");
        indicator.setIndeterminate(true);

        HierarchyBuilder builder = new HierarchyBuilder(project);
        List<Template> templates = builder.build();
        showHierarchyToolWindow(project);
    }

    private void showHierarchyToolWindow(final Project project) {
        final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);

        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                ToolWindow toolWindow = toolWindowManager.getToolWindow(TOOL_WINDOW_ID);
                if (toolWindow == null) {
                    toolWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, true, RIGHT, false);
                    JLabel label = new JLabel("Show hierarchy here.");
                    toolWindow.getComponent().add(label);
                }
                toolWindow.show(null);
            }
        });
    }
}