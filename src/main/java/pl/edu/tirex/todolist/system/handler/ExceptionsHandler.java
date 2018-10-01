package pl.edu.tirex.todolist.system.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.tirex.todolist.system.exception.ForbiddenException;
import pl.edu.tirex.todolist.system.exception.NotFoundException;

@ControllerAdvice
public class ExceptionsHandler
{
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException()
    {
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleForbiddenException()
    {
    }
}
