package com.thoughtworks.tools.idea.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.containers.SortedList;
import com.intellij.util.indexing.FileBasedIndex;
import com.thoughtworks.tools.idea.plugin.model.Template;
import com.thoughtworks.tools.idea.plugin.model.TemplateComparator;

import java.util.Collection;
import java.util.List;

public class EntryHierarchyBuilder {

    public List<Template> build(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("null project");
        }

        List<Template> templates = new SortedList<Template>(TemplateComparator.INSTANCE);
        Collection<VirtualFile> files = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, TemplateFileType.INSTANCE, GlobalSearchScope.allScope(project));
        return templates;
    }
}
