package pl.edu.tirex.todolist.user;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.Objects;

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
        sb.append(", created=").append(created);
        sb.append('}');
        return sb.toString();
    }
}
