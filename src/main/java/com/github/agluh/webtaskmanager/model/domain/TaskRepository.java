package com.github.agluh.webtaskmanager.model.domain;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Used as a facade for DAO layer.
 */
public interface TaskRepository {

    /**
     * Returns task by its identity.
     */
    Optional<Task> getById(TaskId id);

    /**
     * Returns list of all tasks.
     */
    Stream<Task> getAll();

    /**
     * Saves task.
     */
    void save(Task task);

    /**
     * Removes task by its identity.
     */
    void remove(TaskId id);

    /**
     * Returns next available identity for a task.
     */
    TaskId nextIdentity();
}
