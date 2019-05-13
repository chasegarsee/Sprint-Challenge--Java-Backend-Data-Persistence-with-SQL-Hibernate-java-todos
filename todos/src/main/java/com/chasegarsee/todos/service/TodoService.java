package com.chasegarsee.todos.service;

import com.chasegarsee.todos.model.Todo;

import java.util.ArrayList;

public interface TodoService
{
    Todo findById(Long id);
    Todo save(Todo todo);
    Todo update(Todo todo);
    void delete(Long id);
}
