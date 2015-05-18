package com.thoughtworks.tools.idea.plugin.model;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.SortedList;

import java.util.List;

public class Template {
    private VirtualFile file;

    private List<Template> includingEntries = new SortedList<Template>(TemplateComparator.INSTANCE);
    private List<Template> includedEntries = new SortedList<Template>(TemplateComparator.INSTANCE);

    public Template(VirtualFile file) {
        if (file == null) {
            throw new IllegalArgumentException("null virtual file");

        }
        this.file = file;
    }

    public void addIncluding(Template template) {
        if (template == null) {
            throw new IllegalArgumentException("null entry");
        }

        this.includingEntries.add(template);
    }

    public void addIncluded(Template template) {
        if (template == null) {
            throw new IllegalArgumentException("null entry");
        }

        this.includedEntries.add(template);
    }

    public VirtualFile getVirtualFile() {
        return file;
    }

    public String getPath() {
        return file.getPath();
    }

    public String getName() {
        return file.getName();
    }
}
