package pl.edu.tirex.todolist.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/{boardId}")
public class BoardController
{
    @GetMapping
    public Board getBoard(Board board)
    {
        return board;
    }
}
