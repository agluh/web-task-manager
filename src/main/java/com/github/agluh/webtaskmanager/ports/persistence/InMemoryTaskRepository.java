package com.github.agluh.webtaskmanager.ports.persistence;

import com.github.agluh.webtaskmanager.model.domain.Task;
import com.github.agluh.webtaskmanager.model.domain.TaskId;
import com.github.agluh.webtaskmanager.model.domain.TaskRepository;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * In memory implementation for task repository.
 */
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<TaskId, Task> tasks;

    public InMemoryTaskRepository() {
        tasks = Collections.synchronizedMap(new LinkedHashMap<>());
    }

    @Override
    public Optional<Task> getById(TaskId id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public Stream<Task> getAll() {
        return tasks.values().stream();
    }

    @Override
    public void save(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void remove(TaskId id) {
        tasks.remove(id);
    }

    @Override
    public TaskId nextIdentity() {
        return TaskId.as(UUID.randomUUID().toString());
    }
}
