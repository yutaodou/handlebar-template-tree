package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.thoughtworks.tools.idea.plugin.model.Template;
import com.thoughtworks.tools.idea.plugin.ui.RootTreeNode;
import com.thoughtworks.tools.idea.plugin.ui.UsageTree;

import javax.swing.*;
import java.util.List;

import static com.intellij.openapi.wm.ToolWindowAnchor.RIGHT;

class AnalyseTemplateUsageTask extends Task.Backgroundable {
    public static final String TOOL_WINDOW_ID = "Templates";
    private final Context context;

    public AnalyseTemplateUsageTask(Context context) {
        super(context.getProject(), "show template Usage", false);
        this.context = context;
    }

    @Override
    public void run(ProgressIndicator indicator) {
        indicator.setText("Analysing template usage...");
        indicator.setIndeterminate(true);

        UsageBuilder builder = new UsageBuilder(context.getProject());
        List<Template> templates = builder.build();
        showHierarchyToolWindow(templates);
    }

    private void showHierarchyToolWindow(final List<Template> templates) {
        final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(context.getProject());

        ApplicationManager.getApplication().invokeLater(() -> {
            ToolWindow toolWindow = toolWindowManager.getToolWindow(TOOL_WINDOW_ID);
            if (toolWindow == null) {
                toolWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, true, RIGHT, context.getProject(), true);
            }


            Template current = findTemplateByVirtualFile(templates, context.getVirtualFile());
            UsageTree tree = new UsageTree(context.getProject(), new RootTreeNode(templates));
            tree.selectTemplate(current);

            JComponent component = new JBScrollPane(tree);
            ContentManager contentManager = toolWindow.getContentManager();
            Content usageContent = contentManager.getFactory().createContent(component, "", false);
            contentManager.removeAllContents(true);
            contentManager.addContent(usageContent);

            toolWindow.activate(null, true);
        });
    }

    private Template findTemplateByVirtualFile(List<Template> templateUsage, VirtualFile virtualFile) {
        for (Template template : templateUsage) {
            if (template.getVirtualFile().equals(virtualFile)) {
                return template;
            }
        }

        return null;
    }
}