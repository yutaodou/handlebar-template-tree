package com.thoughtworks.tools.idea.plugin.model;

import java.util.Comparator;

public class UsageComparator implements Comparator<Usage> {

    public static final UsageComparator INSTANCE = new UsageComparator();

    private UsageComparator() {
    }

    @Override
    public int compare(Usage one, Usage another) {
        return one.getTemplate().getPath().compareTo(another.getTemplate().getPath());
    }
}
