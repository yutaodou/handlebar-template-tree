package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;

import javax.swing.*;

public class ShowTemplateHierarchyAction extends DumbAwareAction {

    public static final String TOOL_WINDOW_ID = "templateHierarchy";

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(DataKeys.PROJECT);
        if (project == null || project.isDefault()) {
            return;
        }

        EntryHierarchyBuilder builder = new EntryHierarchyBuilder();
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(TOOL_WINDOW_ID);
        if (toolWindow == null) {
            toolWindow = ToolWindowManager.getInstance(project).registerToolWindow(TOOL_WINDOW_ID, true, ToolWindowAnchor.RIGHT, false);
            toolWindow.getComponent().add(new JLabel("Show hierarchy here."));
        }
        toolWindow.show(null);
    }
}
