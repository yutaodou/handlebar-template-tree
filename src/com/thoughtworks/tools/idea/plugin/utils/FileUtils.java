package com.thoughtworks.tools.idea.plugin.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<File> recursiveListFile(File root, FilenameFilter fileter) {
        List<File> result = new ArrayList();
        File[] children = root.listFiles();
        for (File child : children) {
            if (child.isDirectory()) {
                result.addAll(recursiveListFile(child, fileter));
            } else {
                if (fileter.accept(child, child.getName())) {
                    result.add(child);
                }
            }
        }
        return result;
    }
}
