package pl.edu.tirex.todolist.list;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.tirex.todolist.board.Board;
import pl.edu.tirex.todolist.system.exception.NotFoundException;

@RestController
@RequestMapping("/board/{boardId}/list/{listId}")
public class TodoListController
{
    @GetMapping
    public TodoList getList(TodoList todoList)
    {
        return todoList;
    }

    @ModelAttribute("list")
    public TodoList getList(@PathVariable Integer listId, Board board)
    {
        if (listId == null)
        {
            throw new RuntimeException();
        }
        return board.getLists().stream().filter(todoList -> todoList.getId() == listId).findFirst()
                    .orElseThrow(NotFoundException::new);
    }
}
