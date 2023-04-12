package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public String findAllTask(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "task/tasks";
    }

    @GetMapping("/addTaskForm")
    public String addTaskForm() {
        return "task/addTask";
    }

    @PostMapping("/addNewTask")
    public String addNewTask(@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:tasks";
    }

    @GetMapping("/setTaskDone/{id}")
    public String setTaskDone(Model model, @PathVariable("id") int id) {
        taskService.setTaskDone(id);
        model.addAttribute("tasks", taskService.findAll());
        return "task/tasks";
    }

    @GetMapping("/updateTaskForm/{id}")
    public String updateTaskForm(Model model, @PathVariable("id") int id) {
        var optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty()) {
            System.out.println("Task by id is not found this is coming from controller");
        }
        model.addAttribute("task", optionalTask.get());
        return "task/updateTask";
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(Model model, @PathVariable("id") int id) {
        taskService.delete(id);
        model.addAttribute("tasks", taskService.findAll());
        return "task/tasks";
    }

    @PostMapping("/updateTask")
    public String updateTask(Model model, @ModelAttribute Task task) {
        taskService.update(task);
        model.addAttribute("tasks", taskService.findAll());
        return "task/tasks";
    }

    @GetMapping("/pendingTask")
    public String undoneTask(Model model) {
        var tasks = taskService.findByStatusPending();
        model.addAttribute("tasks", tasks);
        return "task/undoneTask";
    }

    @GetMapping("/completedTask")
    public String doneTask(Model model) {
        var tasks = taskService.findByStatusDone();
        model.addAttribute("tasks", tasks);
        return "task/doneTask";
    }

}
