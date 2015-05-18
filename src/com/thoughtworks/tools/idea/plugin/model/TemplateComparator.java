package com.thoughtworks.tools.idea.plugin.model;

import java.util.Comparator;

public class TemplateComparator implements Comparator<Template> {
    public static final TemplateComparator INSTANCE = new TemplateComparator();

    private TemplateComparator() {
    }

    @Override
    public int compare(Template template, Template t1) {
        return template.getPath().compareTo(t1.getPath());
    }
}
