package com.thoughtworks.tools.idea.plugin;

import com.google.common.io.Files;
import com.intellij.util.containers.MultiMap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsageAnalyser {

    public static final Pattern PATTERN = Pattern.compile("\\{\\{\\s*\\>\\s*(\\S*)\\s*\\}\\}");

    public MultiMap<String, Integer> analyse(File file) {
        MultiMap<String, Integer> usages = MultiMap.createLinked();
        try {
            List<String> lines = Files.readLines(file, Charset.forName("utf-8"));
            for (int line = 0; line < lines.size(); line++) {
                String content = lines.get(line);
                Matcher matcher = PATTERN.matcher(content);
                if (!matcher.find()) {
                    continue;
                }
                String templateName = matcher.group(1);
                usages.putValue(templateName, line);
            }
        } catch (IOException e) {
            Logger.getLogger(HierarchyBuilder.class.getSimpleName()).warning("Failed to parse template usage.");
        }

        return usages;
    }
}