package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.util.containers.MultiMap;
import com.intellij.util.containers.SortedList;
import com.thoughtworks.tools.idea.plugin.model.Template;
import com.thoughtworks.tools.idea.plugin.model.TemplateComparator;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class UsageBuilder {
    private Project project;
    private UsageAnalyser usageAnalyser;
    private List<Template> templates = new SortedList<>(TemplateComparator.INSTANCE);

    public UsageBuilder(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("null project");
        }
        this.project = project;
        this.usageAnalyser = new UsageAnalyser();
    }

    public List<Template> build() {
        List<File> templateFiles = getTemplateFiles();
        return process(templateFiles);
    }

    private List<File> getTemplateFiles() {
        return new DefaultTemplateProvider(project).list();
    }

    private List<Template> process(List<File> templates) {
        for (File templateFile : templates) {
            this.templates.add(createTemplate(templateFile.getPath()));
        }

        for (File file : templates) {
            Template parent = findTemplate(file.getPath());
            MultiMap<String, Integer> usage = usageAnalyser.analyse(file);
            for (String templateName : usage.keySet()) {
                Template child = findTemplate(String.format("%s%s", File.separator, templateName));
                if (child == null) {
                    continue;
                }

                for (Integer line : usage.get(templateName)) {
                    parent.use(child).inLine(line);
                }
            }
        }
        return this.templates;
    }

    @Nullable
    private Template createTemplate(String path) {
        String url = VirtualFileManager.constructUrl("file", path);
        VirtualFile virtualFile = VirtualFileManager.getInstance().findFileByUrl(url);
        if (virtualFile == null) {
            return null;
        }
        return new Template(virtualFile);
    }

    private Template findTemplate(String path) {
        for (Template template : templates) {
            if (template.getPath().toLowerCase().contains(path.toLowerCase())) {
                return template;
            }
        }
        return null;
    }

}