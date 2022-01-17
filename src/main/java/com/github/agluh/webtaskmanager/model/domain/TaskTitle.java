package com.github.agluh.webtaskmanager.model.domain;

import java.util.Objects;

/**
 * Represents task description.
 */
public class TaskTitle {
    private final String title;

    protected TaskTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title should not be empty");
        }

        this.title = title;
    }

    public static TaskTitle as(String title) {
        return new TaskTitle(title);
    }

    public String asString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskTitle taskTitle = (TaskTitle) o;
        return Objects.equals(title, taskTitle.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
