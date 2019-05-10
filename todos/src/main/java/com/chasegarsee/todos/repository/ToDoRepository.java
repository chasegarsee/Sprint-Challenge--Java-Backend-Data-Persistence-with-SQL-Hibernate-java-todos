package com.chasegarsee.todos.repository;

import com.chasegarsee.todos.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<Todo, Long>
{
}
