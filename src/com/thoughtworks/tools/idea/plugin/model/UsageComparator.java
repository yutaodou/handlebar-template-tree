package com.thoughtworks.tools.idea.plugin.model;

import java.util.Comparator;

public class UsageComparator implements Comparator<Usage> {

    public static final UsageComparator INSTANCE = new UsageComparator();

    private UsageComparator() {
    }

    @Override
    public int compare(Usage one, Usage other) {
        return TemplateComparator.INSTANCE.compare(one.getTemplate(), other.getTemplate());
    }
}
