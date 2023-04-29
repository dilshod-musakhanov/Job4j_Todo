package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping
    public String findAllTask(Model model) {
        var tasks = taskService.findAll();
        if (tasks.isEmpty()) {
            model.addAttribute("message", "Your task bucket is empty. Please add new task");
            return "error/message";
        }
        model.addAttribute("tasks", tasks);
        return "task/tasks";
    }

    @GetMapping("/addForm")
    public String getTaskForm(Model model) {
        var priorities = priorityService.findAll();
        var categories = categoryService.findAll();
        model.addAttribute("priorities", priorities);
        model.addAttribute("categories", categories);
        return "task/add";
    }

    @PostMapping("/addNew")
    public String addNewTask(Model model, @ModelAttribute Task task, HttpSession session, @RequestParam(value = "category.id") List<Integer> ids) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        List<Category> categories = (List<Category>) categoryService.findAllByIds(ids);
        task.setCategories(categories);
        var optionalTask = taskService.save(task);
        if (optionalTask.isEmpty()) {
            model.addAttribute("message", "Unable to add new task. Please try again");
            return "error/message";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTaskById(Model model, @PathVariable("id") int id) {
        var optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty()) {
            model.addAttribute("message", "Unable to view the task. Please try again");
            return "error/message";
        }
        model.addAttribute("task", optionalTask.get());
        return "task/task";
    }

    @GetMapping("/updateForm/{id}")
    public String updateTaskFormById(Model model, @PathVariable("id") int id) {
        var optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty()) {
            model.addAttribute("message", "Unable to open the task. Please try again");
            return "error/message";
        }
        var priorities = priorityService.findAll();
        var categories = categoryService.findAll();
        var taskCategories = optionalTask.get().getCategories();
        model.addAttribute("priorities", priorities);
        model.addAttribute("categories", categories);
        model.addAttribute("taskCategories", taskCategories);
        model.addAttribute("task", optionalTask.get());
        return "task/update";
    }

    @PostMapping("/update")
    public String updateTask(Model model, @ModelAttribute Task task, HttpSession session, @RequestParam(value = "category.id") List<Integer> ids) {
        var user = (User) session.getAttribute("user");
        task.setUser(user);
        List<Category> categories = (List<Category>) categoryService.findAllByIds(ids);
        task.setCategories(categories);
        boolean flag = taskService.update(task);
        if (!flag) {
            model.addAttribute("message", "Unable to update the task. Please try again");
            return "error/message";
        }
        model.addAttribute("tasks", taskService.findAll());
        return "task/tasks";
    }

    @GetMapping("/pending")
    public String undoneTask(Model model) {
        var tasks = taskService.findByStatusPending();
        if (tasks.isEmpty()) {
            model.addAttribute("message", "It looks like you do not have any pending tasks");
            return "error/message";
        }
        model.addAttribute("tasks", tasks);
        return "task/undone";
    }

    @GetMapping("/completed")
    public String doneTask(Model model) {
        var tasks = taskService.findByStatusDone();
        if (tasks.isEmpty()) {
            model.addAttribute("message", "It looks like you do not have any completed tasks yet");
            return "error/message";
        }
        model.addAttribute("tasks", tasks);
        return "task/done";
    }

    @GetMapping("/delete/{id}")
    public String deleteTaskById(Model model, @PathVariable("id") int id) {
        boolean flag = taskService.delete(id);
        if (!flag) {
            model.addAttribute("message", "Unable to delete the task. Please try again");
            return "error/message";
        }
        model.addAttribute("tasks", taskService.findAll());
        return "task/tasks";
    }
}
