package pl.edu.tirex.todolist.user;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import pl.edu.tirex.todolist.board.Board;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIgnoreType
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 32, unique = true, nullable = false)
    private String hash;

    private ZonedDateTime lastLogin = ZonedDateTime.now();

    private ZonedDateTime created = ZonedDateTime.now();

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role")
    @Column(name = "role", nullable = false, length = 32)
    private Set<UserRole> roles = new HashSet<>();

    @OneToOne
    private Board board;

    public User()
    {
    }

    public User(String hash)
    {
        this.hash = hash;
    }

    public int getId()
    {
        return id;
    }

    public ZonedDateTime getLastLogin()
    {
        return lastLogin;
    }

    public void setLastLogin(ZonedDateTime lastLogin)
    {
        this.lastLogin = lastLogin;
    }

    public ZonedDateTime getCreated()
    {
        return created;
    }

    public Set<UserRole> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<UserRole> roles)
    {
        this.roles = roles;
    }

    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(hash);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof User))
        {
            return false;
        }
        User user = (User) o;
        return Objects.equals(hash, user.hash);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", hash='").append(hash).append('\'');
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", created=").append(created);
        sb.append(", roles=").append(roles);
        sb.append(", board=").append(board);
        sb.append('}');
        return sb.toString();
    }
}
