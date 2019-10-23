package todo;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    Logger logger;

    @Autowired
    private TodoDAO todoRepository;

    public TodoController() {
        logger = Logger.getLogger(TodoController.class.getName());
    }

    // List All TODOs
    @GetMapping("/")
    public List<Todo> getAllTodos(@RequestParam(value = "q", defaultValue = "") String q) {
        logger.info("getAllTodos::q=" + q);

        switch (q) {
        case "completed":
            return todoRepository.findAllCompleted();
        default:
            return todoRepository.findAll();
        }
    }

    // Add TODO
    @PutMapping("/")
    public Todo addTodo(@RequestBody Todo todo) {
        logger.info("addTodo:: title=" + todo.getTitle() + " completed=" + todo.getCompleted());

        int id = todoRepository.insert(todo);
        return todoRepository.findById(id).get();
    }

    // Get TODO with id
    @GetMapping("/todo/{id}")
    public Optional<Todo> getTodo(@PathVariable Integer id) {
        logger.info("getTodo::" + id);
        return todoRepository.findById(id);
    }

    // Update TODO
    @PatchMapping("/todo/{id}")
    public Todo updateTodo(@RequestBody Todo todo, @PathVariable Integer id) {
        logger.info("updateTodo::" + id);

        int todoId = todoRepository.update(todo, id);

        return todoRepository.findById(todoId).get();
    }

    // Delete TODO
    @DeleteMapping("/todo/{id}")
    public Integer deleteTodo(@PathVariable Integer id) {
        logger.info("deleteTodo::" + id);

        return todoRepository.deleteById(id);
    }
}
