package pl.edu.tirex.todolist.board;

import pl.edu.tirex.todolist.list.TodoList;
import pl.edu.tirex.todolist.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Board
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    private List<TodoList> lists = new ArrayList<>();

    public Board()
    {
    }

    Board(User user)
    {
        this.user = user;
    }

    public int getId()
    {
        return id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public List<TodoList> getLists()
    {
        return lists;
    }

    public void setLists(List<TodoList> lists)
    {
        this.lists = lists;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Board))
        {
            return false;
        }
        Board board = (Board) o;
        return id == board.id;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Board{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
