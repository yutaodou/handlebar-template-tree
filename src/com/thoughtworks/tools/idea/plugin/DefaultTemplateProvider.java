package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DefaultTemplateProvider implements TemplateFileProvider {

    private static final Predicate<File> isTemplate = file ->
        file.getPath().toLowerCase().endsWith(TemplateFileType.INSTANCE.getDefaultExtension());

    private final Project project;
    private final List<File> templateFiles;

    public DefaultTemplateProvider(Project project) {
        this.project = project;
        this.templateFiles = listTemplates();
    }

    private List<File> listTemplates() {
        VirtualFile[] sourceRoots = ProjectRootManager.getInstance(project).getContentSourceRoots();
        return Stream.of(sourceRoots)
            .flatMap(DefaultTemplateProvider::listTemplateFrom)
            .collect(toList());
    }

    @Override
    public List<File> list() {
        return templateFiles;
    }

    private static Stream<File> listTemplateFrom(VirtualFile vf) {
        File dir = new File(vf.getPath());
        if (!dir.exists()) {
            return Stream.empty();
        }
        return recursiveListFile(dir, isTemplate).stream();
    }

    private static List<File> recursiveListFile(File root, Predicate<File> isTemplate) {
        List<File> result = new ArrayList();
        File[] children = root.listFiles();
        for (File child : children) {
            if (child.isDirectory()) {
                result.addAll(recursiveListFile(child, isTemplate));
            } else {
                if (isTemplate.test(child)) {
                    result.add(child);
                }
            }
        }
        return result;
    }

}
