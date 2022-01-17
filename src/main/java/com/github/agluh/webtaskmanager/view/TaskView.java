package com.github.agluh.webtaskmanager.view;

import com.github.agluh.webtaskmanager.model.domain.Task;

/**
 * Task view model.
 */
public class TaskView {
    private final String id;
    private final String title;
    private final String description;
    private final String status;

    protected TaskView(String id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    /**
     * Constructs view from domain model.
     */
    public static TaskView fromModel(Task task) {
        return new TaskView(
            task.getId().asString(),
            task.getTitle().asString(),
            task.getDescription().asString(),
            task.getStatus().name()
        );
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
