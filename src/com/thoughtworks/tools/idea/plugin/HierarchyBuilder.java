package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.util.containers.MultiMap;
import com.intellij.util.containers.SortedList;
import com.thoughtworks.tools.idea.plugin.model.Template;
import com.thoughtworks.tools.idea.plugin.model.TemplateComparator;
import com.thoughtworks.tools.idea.plugin.utils.FileUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public class HierarchyBuilder {

    private static final String EXTENSION = "hbs.html";
    private Project project;
    private UsageParser usageParser;
    private List<Template> templates = new SortedList<Template>(TemplateComparator.INSTANCE);

    public HierarchyBuilder(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("null project");
        }
        this.project = project;
        this.usageParser = new UsageParser();
    }

    public List<Template> build() {
        List<File> templateFiles = getTemplateFiles();
        return process(templateFiles);
    }

    private List<Template> process(List<File> templateFiles) {
        for (File templateFile : templateFiles) {
            templates.add(createTemplate(templateFile.getPath()));
        }

        for (File file : templateFiles) {
            Template parent = findTemplate(file.getPath());
            MultiMap<String, Integer> usage = usageParser.parse(file);
            for (String templateName : usage.keySet()) {
                Template child = findTemplate(String.format("%s.%s", templateName, EXTENSION));
                if (child == null) {
                    continue;
                }

                for (Integer line : usage.get(templateName)) {
                    parent.use(child).inLine(line);
                }
            }
        }
        return templates;
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
            if (template.getPath().toLowerCase().endsWith(path.toLowerCase())) {
                return template;
            }
        }
        return null;
    }

    private List<File> getTemplateFiles() {
        File rootDir = new File(project.getBasePath());
        return FileUtils.recursiveListFile(rootDir, new TemplateFileFilter());
    }

    private class TemplateFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File file, String s) {
            return file.isFile() && s.toLowerCase().endsWith(EXTENSION);
        }
    }
}