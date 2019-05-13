package com.chasegarsee.todos.service;


import com.chasegarsee.todos.model.Todo;
import com.chasegarsee.todos.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

@Service(value = "todoService")
public class TodoServiceImpl implements TodoService
{
    @Autowired
    ToDoRepository todorepos;

    @Override
    @Transactional
    public Todo findById(Long id)
    {
        return todorepos.findById(id).orElseThrow( () -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    @Transactional
    public Todo save(Todo todo)
    {
        return todorepos.save(todo);
    }

    @Override
    public Todo update(Todo todo)
    {
        return todorepos.save(todo);
    }

    @Override
    public void delete(Long id)
    {

    }
}
