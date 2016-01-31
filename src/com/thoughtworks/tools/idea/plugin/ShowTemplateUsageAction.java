package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import static com.thoughtworks.tools.idea.plugin.model.Template.isTemplate;

public class ShowTemplateUsageAction extends DumbAwareAction {

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(DataKeys.PROJECT);
        final Editor editor = e.getData(DataKeys.EDITOR);
        if (project == null || project.isDefault() || editor == null) {
            e.getPresentation().setEnabledAndVisible(false);
        }

        String filePath = e.getData(DataKeys.VIRTUAL_FILE).getPath();
        e.getPresentation().setEnabledAndVisible(isTemplate.test(filePath));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getData(DataKeys.PROJECT);
        if (project == null || project.isDefault()) {
            return;
        }

        VirtualFile file = e.getData(DataKeys.VIRTUAL_FILE);
        ProgressManager.getInstance().run(new AnalyseTemplateUsageTask(new Context(project, file)));
    }
}