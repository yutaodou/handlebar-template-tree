package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class Context {
    private final Project project;
    private final VirtualFile virtualFile;

    public Context(Project project, VirtualFile virtualFile) {
        this.project = project;
        this.virtualFile = virtualFile;
    }

    public Project getProject() {
        return project;
    }

    public VirtualFile getVirtualFile() {
        return virtualFile;
    }
}
