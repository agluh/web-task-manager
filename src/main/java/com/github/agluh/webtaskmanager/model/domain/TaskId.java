package com.github.agluh.webtaskmanager.model.domain;

import java.util.Objects;

/**
 * Represents task identity.
 */
public class TaskId {
    private final String id;

    protected TaskId(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public static TaskId as(String id) {
        return new TaskId(id);
    }

    public String asString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskId taskId = (TaskId) o;
        return Objects.equals(id, taskId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
