package com.thoughtworks.tools.idea.plugin;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TemplateFileType extends LanguageFileType {

    public static TemplateFileType INSTANCE = new TemplateFileType();

    private TemplateFileType() {
        super(Language.ANY);
    }

    @NotNull
    @Override
    public String getName() {
        return "HBS.HTML";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Handlebar template files";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "hbs.html";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return null;
    }
}
