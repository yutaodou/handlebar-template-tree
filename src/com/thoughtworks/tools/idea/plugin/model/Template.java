package com.thoughtworks.tools.idea.plugin.model;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.SortedList;

import java.util.List;

public class Template {
    private VirtualFile virtualFile;

    private List<Usage> usingList = new SortedList<Usage>(UsageComparator.INSTANCE);
    private List<Usage> usedByList = new SortedList<Usage>(UsageComparator.INSTANCE);

    public Template(VirtualFile virtualFile) {
        this.virtualFile = virtualFile;
    }

    public UsageBuilder use(Template template) {
        return new UsageBuilder(this).use(template);
    }

    public VirtualFile getVirtualFile() {
        return virtualFile;
    }

    public String getPath() {
        return virtualFile.getPath();
    }

    public String getName() {
        return virtualFile.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Template template = (Template) o;

        return virtualFile.equals(template.virtualFile);

    }

    @Override
    public int hashCode() {
        return virtualFile.hashCode();
    }

    private void use(Usage usage) {
        usingList.add(usage);
    }

    private void usedBy(Usage usage) {
        usedByList.add(usage);
    }

    public static class UsageBuilder {
        private Template parent;
        private Template child;

        public UsageBuilder(Template parent) {
            this.parent = parent;
        }

        private UsageBuilder use(Template child) {
            this.child = child;
            return this;
        }

        public void inLine(int line) {
            parent.use(new Usage(child, line));
            child.usedBy(new Usage(parent, line));
        }
    }
}