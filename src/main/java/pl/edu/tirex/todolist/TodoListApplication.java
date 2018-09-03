package pl.edu.tirex.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client
@SpringBootApplication
public class TodoListApplication
{
    private ObjectMapper objectMapper;

    @Bean
    public ObjectMapper objectMapper()
    {
        if (this.objectMapper != null)
        {
            return this.objectMapper;
        }
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return this.objectMapper;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(TodoListApplication.class, args);
    }
}
