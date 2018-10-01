package pl.edu.tirex.todolist.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.tirex.todolist.system.exception.ForbiddenException;
import pl.edu.tirex.todolist.system.exception.NotFoundException;
import pl.edu.tirex.todolist.user.User;

@RestControllerAdvice(assignableTypes = {BoardController.class, BoardListsController.class})
public class BoardControllerAdvice
{
    private final BoardRepository boardRepository;

    @Autowired
    public BoardControllerAdvice(BoardRepository boardRepository)
    {
        this.boardRepository = boardRepository;
    }

    @ModelAttribute("board")
    public Board getBoard(@PathVariable Integer boardId, @AuthenticationPrincipal User user)
    {
        System.out.println("xxx");
        if (boardId == null)
        {
            throw new RuntimeException();
        }
        Board board = this.boardRepository.getBoardById(boardId);
        if (board == null)
        {
            throw new NotFoundException();
        }
        if (!user.equals(board.getUser()))
        {
            throw new ForbiddenException();
        }
        return board;
    }
}
