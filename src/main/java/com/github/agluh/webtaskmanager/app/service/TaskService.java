package com.github.agluh.webtaskmanager.app.service;

import com.github.agluh.webtaskmanager.model.domain.Task;
import com.github.agluh.webtaskmanager.model.domain.TaskDescription;
import com.github.agluh.webtaskmanager.model.domain.TaskId;
import com.github.agluh.webtaskmanager.model.domain.TaskRepository;
import com.github.agluh.webtaskmanager.model.domain.TaskStatus;
import com.github.agluh.webtaskmanager.model.domain.TaskTitle;
import com.github.agluh.webtaskmanager.view.TaskView;
import java.util.stream.Stream;

/**
 * Application service for task management.
 */
public class TaskService {
    private final TaskRepository tasks;

    public TaskService(TaskRepository tasks) {
        this.tasks = tasks;
    }

    /**
     * Creates a new task.
     */
    public void addTask(String title, String description, String status) {
        Task task = new Task(
            tasks.nextIdentity(),
            TaskTitle.as(title),
            TaskDescription.as(description),
            TaskStatus.valueOf(status));

        tasks.save(task);
    }

    /**
     * Removes a task.
     */
    public void removeTask(String id) {
        tasks.remove(TaskId.as(id));
    }

    /**
     * Updates task status.
     */
    public void updateTaskStatus(String id, String status) {
        Task task = tasks.getById(TaskId.as(id)).orElseThrow();

        task.setStatus(TaskStatus.valueOf(status.toUpperCase()));

        tasks.save(task);
    }

    /**
     * Updates entire task.
     */
    public void updateTask(String id, String title, String description, String status) {
        Task task = tasks.getById(TaskId.as(id)).orElseThrow();

        task.setTitle(TaskTitle.as(title));
        task.setDescription(TaskDescription.as(description));
        task.setStatus(TaskStatus.valueOf(status.toUpperCase()));

        tasks.save(task);
    }

    public Stream<TaskView> getAllTasks() {
        return tasks.getAll().map(TaskView::fromModel);
    }

    public TaskView getTask(String id) {
        Task task = tasks.getById(TaskId.as(id)).orElseThrow();
        return TaskView.fromModel(task);
    }
}
