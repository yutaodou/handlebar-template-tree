package com.thoughtworks.tools.idea.plugin.ui;

import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class Icons {
    public static final Icon Using = IconLoader.findIcon("/using.png");
    public static final Icon UsedBy = IconLoader.findIcon("/used-by.png");
    public static final Icon TemplateIcon = FileTypeManager.getInstance().getFileTypeByExtension(".hbs.html").getIcon();
}
