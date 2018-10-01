package pl.edu.tirex.todolist.list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository
        extends JpaRepository<TodoList, Integer>
{
}
