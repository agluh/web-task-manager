package com.github.agluh.webtaskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agluh.webtaskmanager.app.service.TaskService;
import com.github.agluh.webtaskmanager.model.domain.TaskRepository;
import com.github.agluh.webtaskmanager.ports.persistence.InMemoryTaskRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Controller for task-related operations.
 */
@WebServlet("/tasks/*")
public class TaskController extends HttpServlet {
    private TaskRepository taskRepository;

    @Override
    public void init() throws ServletException {
        super.init();

        taskRepository = new InMemoryTaskRepository();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    /**
     * Handles PATCH requests.
     */
    public void doPatch(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        TaskService service = new TaskService(taskRepository);
        ObjectMapper mapper = new ObjectMapper();
        String path = req.getPathInfo();

        String[] parts = path.split("/");
        if (parts.length >= 2) {
            String id = parts[1];

            Map<String, String> map = mapper.readValue(req.getInputStream(), Map.class);

            service.updateTask(
                id,
                map.get("title"),
                map.get("description"),
                map.get("status")
            );
        }
    }

    /**
     * Handles GET requests.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        TaskService service = new TaskService(taskRepository);
        ObjectMapper mapper = new ObjectMapper();
        String path = req.getPathInfo();

        if (path == null) {
            // list all tasks
            resp.setContentType("application/json");
            mapper.writeValue(resp.getOutputStream(), service.getAllTasks().toList());
            return;
        }

        if ("/".equals(path)) {
            resp.sendRedirect("/tasks");
            return;
        }

        String[] parts = path.split("/");
        if (parts.length >= 2) {
            String id = parts[1];

            resp.setContentType("application/json");
            mapper.writeValue(resp.getOutputStream(), service.getTask(id));
        }
    }

    /**
     * Handles POST requests.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        TaskService service = new TaskService(taskRepository);
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> map = mapper.readValue(req.getInputStream(), Map.class);

        String title = map.get("title");
        String desc = map.get("description");
        String status = map.get("status");

        service.addTask(title, desc, status.toUpperCase());
    }

    /**
     * Handles PUT requests.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        TaskService service = new TaskService(taskRepository);
        String path = req.getPathInfo();

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length >= 3 && "status".equals(parts[2])) {
                String id = parts[1];
                String status = req.getParameter("status");
                service.updateTaskStatus(id, status);
            }
        }
    }

    /**
     * Handles DELETE requests.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        TaskService service = new TaskService(taskRepository);
        String path = req.getPathInfo();

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length >= 2) {
                String id = parts[1];
                service.removeTask(id);
            }
        }
    }
}
