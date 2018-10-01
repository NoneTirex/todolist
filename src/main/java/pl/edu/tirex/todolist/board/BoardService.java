package pl.edu.tirex.todolist.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.tirex.todolist.user.User;

@Service
public class BoardService
{
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository)
    {
        this.boardRepository = boardRepository;
    }

    public Board createBoard(User user)
    {
        Board board = new Board(user);
        this.boardRepository.save(board);
        return board;
    }
}
