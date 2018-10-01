package pl.edu.tirex.todolist.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.tirex.todolist.list.TodoList;
import pl.edu.tirex.todolist.list.TodoListRepository;

import java.util.List;

@RestController
@RequestMapping("/board/{boardId}/lists")
public class BoardListsController
{
    private final TodoListRepository todoListRepository;

    @Autowired
    public BoardListsController(TodoListRepository todoListRepository)
    {
        this.todoListRepository = todoListRepository;
    }

    @GetMapping
    public List<TodoList> getLists(Board board)
    {
        return board.getLists();
    }

    @PostMapping("/create")
    public TodoList createList(@RequestParam String name)
    {
        TodoList todoList = new TodoList(name);
        this.todoListRepository.save(todoList);
        return todoList;
    }
}
