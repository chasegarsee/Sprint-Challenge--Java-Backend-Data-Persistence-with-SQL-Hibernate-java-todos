package com.chasegarsee.todos.controller;

import com.chasegarsee.todos.exceptions.ResourceNotFoundException;
import com.chasegarsee.todos.model.Todo;
import com.chasegarsee.todos.model.User;
import com.chasegarsee.todos.service.TodoService;
import com.chasegarsee.todos.service.UserService;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class controller
{
    @Autowired
    UserService userService;
    @Autowired
    TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<?> doTest()
    {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //  GET /users/mine - return the user and todo based off of the authenticated user. You can only look up your own.
    @GetMapping(value="/users/mine", produces = "application/json")
    public ResponseEntity<?> getUserAndTodos()
    {
        // get authenticated users name
        String uname;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
        {
            return new ResponseEntity<>("Could not authenticate", HttpStatus.I_AM_A_TEAPOT);
        }
        uname = authentication.getName();

        // use name to get user object
        User user = userService.findUserByUsername(uname);
        if (user == null) {
            throw new ResourceNotFoundException("Could not find user");
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    //  POST /users - adds a user. Can only be done by an admin.
    @PostMapping(value="/users", consumes="application/json", produces="application/json")
    public ResponseEntity<?> addUser(@Valid
                                     @RequestBody User user) {
        User rtn = userService.save(user);
        return new ResponseEntity<>(rtn, HttpStatus.OK);
    }
    //  DELETE /users/userid/{userid} - Deletes a user based off of their userid and deletes all their associated todos. Can only be done by an admin.
    @DeleteMapping(value="/users/userid/{userid}", consumes="application/json")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userid)
    {
        userService.delete(userid);
        return new ResponseEntity<>("Successfully deleted user with id: " + userid, HttpStatus.OK);
    }


    //  PUT /todos/todoid/{todoid} - updates a todo based on todoid. Can be done by any user.
    @PutMapping(value="/todos/todoid/{todoid}", consumes="application/json", produces="application/json")
    public ResponseEntity<?> updateTodo(@PathVariable long todoid, @Valid @RequestBody Todo todo)
    {
        Todo toUpdate = todoService.findById(todoid);
        if (todo.getDesc() != null) {
            toUpdate.setDesc(todo.getDesc());
        }
        return new ResponseEntity<>(todoService.update(toUpdate), HttpStatus.OK);
    }


    //  POST /users/todo/{userid} - adds a todo to the assigned user. Can be done by any user.
    @PostMapping(value="/users/todo/{userid}", consumes="application/json", produces="application/json")
    public ResponseEntity<?> addTodoToUser(@PathVariable long userid, @Valid @RequestBody Todo todo)
    {
        return new ResponseEntity<>(userService.updateTodos(todo, userid), HttpStatus.OK);
    }





}
