package com.github.agluh.webtaskmanager.model.domain;

import java.util.Objects;

/**
 * Represents task description.
 */
public class TaskDescription {
    private final String description;

    protected TaskDescription(String description) {
        this.description = Objects.requireNonNull(description);
    }

    public static TaskDescription as(String description) {
        return new TaskDescription(description);
    }

    public String asString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskDescription that = (TaskDescription) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
