package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.thoughtworks.tools.idea.plugin.utils.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DefaultTemplateProvider implements TemplateFileProvider {

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
        return FileUtils.recursiveListFile(dir, new TemplateFileFilter()).stream();
    }

    private static class TemplateFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File file, String name) {
            return file.isFile() &&
                name.toLowerCase().endsWith(TemplateFileType.INSTANCE.getDefaultExtension());
        }
    }
}
