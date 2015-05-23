package com.thoughtworks.tools.idea.plugin;

import com.google.common.io.Files;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.util.containers.SortedList;
import com.thoughtworks.tools.idea.plugin.model.Template;
import com.thoughtworks.tools.idea.plugin.model.TemplateComparator;
import com.thoughtworks.tools.idea.plugin.utils.FileUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HierarchyBuilder {

    private static final String EXTENSION = "hbs.html";
    private Project project;
    private List<Template> templates = new SortedList<Template>(TemplateComparator.INSTANCE);
    private static Pattern PATTERN = Pattern.compile("\\{\\{\\s*\\>\\s*(\\S*)\\s*\\}\\}");

    public HierarchyBuilder(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("null project");
        }
        this.project = project;
    }

    public List<Template> build() {
        // TODO: try this FilenameIndex.getAllFilesByExt()
        List<File> templateFiles = getTemplateFiles();
        return process(templateFiles);
    }

    private List<Template> process(List<File> templateFiles) {
        for (File templateFile : templateFiles) {
            templates.add(createTemplate(templateFile.getPath()));
        }

        for (File file : templateFiles) {
            Template current = findTemplate(file.getPath());
            Map<String, Integer> usage = parseTemplate(file);
            for (Map.Entry<String, Integer> entry : usage.entrySet()) {
                Template template = findTemplate(String.format("%s.%s", entry.getKey(), EXTENSION));
                if (template != null) {
                    current.use(template).inLine(entry.getValue());
                }
            }
        }
        return templates;
    }

    private Map<String, Integer> parseTemplate(File file) {
        Map<String, Integer> usages = new HashMap<String, Integer>();
        try {
            List<String> lines = Files.readLines(file, Charset.forName("utf-8"));
            for (int line = 0; line < lines.size(); line++) {
                String content = lines.get(line);
                Matcher matcher = PATTERN.matcher(content);
                if (!matcher.find()) {
                    continue;
                }
                String templateName = matcher.group(1);
                usages.put(templateName, line + 1);
            }
        } catch (IOException e) {
            Logger.getLogger(HierarchyBuilder.class.getSimpleName()).warning("Failed to parse tempate usage.");
        }

        return usages;
    }

    @Nullable
    private Template createTemplate(String path) {
        String url = VirtualFileManager.constructUrl("file", path);
        VirtualFile virtualFile = VirtualFileManager.getInstance().findFileByUrl(url);
        if (virtualFile != null) {
            return new Template(virtualFile);
        }
        return null;
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
