package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.thoughtworks.tools.idea.plugin.model.Template;
import com.thoughtworks.tools.idea.plugin.ui.UsageTree;

import javax.swing.*;
import java.util.List;

import static com.intellij.openapi.wm.ToolWindowAnchor.RIGHT;

class AnalyseTemplateUsageTask extends Task.Backgroundable {
    public static final String TOOL_WINDOW_ID = "Template Hierarchy";
    private final Project project;

    public AnalyseTemplateUsageTask(Project project) {
        super(project, "show template hierarchy", false);
        this.project = project;
    }

    @Override
    public void run(ProgressIndicator indicator) {
        indicator.setText("Analysing template hierarchy...");
        indicator.setIndeterminate(true);

        UsageBuilder builder = new UsageBuilder(project);
        List<Template> templates = builder.build();
        showHierarchyToolWindow(project, templates);
    }

    private void showHierarchyToolWindow(final Project project, final List<Template> templates) {
        final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);

        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                ToolWindow toolWindow = toolWindowManager.getToolWindow(TOOL_WINDOW_ID);
                if (toolWindow == null) {
                    toolWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, true, RIGHT, project, true);
                }

                ContentManager contentManager = toolWindow.getContentManager();
                JComponent component = new JBScrollPane(new UsageTree(templates, project));
                Content usageContent = contentManager.getFactory().createContent(component, "Usage", false);
                contentManager.addContent(usageContent);

                toolWindow.show(null);
            }
        });
    }
}