package com.github.agluh.webtaskmanager.model.domain;

import java.util.Objects;

/**
 * Represents a task.
 */
public class Task {
    private final TaskId id;
    private TaskTitle title;
    private TaskDescription description;
    private TaskStatus status;

    /**
     * Constructs a new task.
     */
    public Task(TaskId id, TaskTitle title,
        TaskDescription description, TaskStatus status) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.status = Objects.requireNonNull(status);
    }

    public TaskId getId() {
        return id;
    }

    public TaskTitle getTitle() {
        return title;
    }

    public void setTitle(TaskTitle title) {
        this.title = Objects.requireNonNull(title);
    }

    public TaskDescription getDescription() {
        return description;
    }

    public void setDescription(TaskDescription description) {
        this.description = Objects.requireNonNull(description);
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
