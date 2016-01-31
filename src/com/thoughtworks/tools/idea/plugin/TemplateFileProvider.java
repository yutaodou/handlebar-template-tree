package com.thoughtworks.tools.idea.plugin;

import java.io.File;
import java.util.List;

interface TemplateFileProvider {
    List<File> list();
}
