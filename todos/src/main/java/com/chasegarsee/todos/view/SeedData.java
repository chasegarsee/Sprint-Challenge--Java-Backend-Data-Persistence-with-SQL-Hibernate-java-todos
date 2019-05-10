package com.chasegarsee.todos.view;

import com.chasegarsee.todos.model.Role;
import com.chasegarsee.todos.model.Todo;
import com.chasegarsee.todos.model.User;
import com.chasegarsee.todos.model.UserRoles;
import com.chasegarsee.todos.repository.RoleRepository;
import com.chasegarsee.todos.repository.ToDoRepository;
import com.chasegarsee.todos.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    RoleRepository rolerepos;
    UserRepository userrepos;
    ToDoRepository todorepos;

    public SeedData(RoleRepository rolerepos, UserRepository userrepos, ToDoRepository todorepos)
    {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;
        this.todorepos = todorepos;
    }

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));

        rolerepos.save(r1);
        rolerepos.save(r2);

        User u1 = new User("chase", "garsee", users);
        User u2 = new User("admin", "password", admins);


        // the date and time string should get converted to a datetime Java data type. This is done in the constructor!
        ArrayList<Todo> todos = new ArrayList<>();
        Todo t1 = new Todo("Go Hard", u1);
        Todo t2 = new Todo("Get Turnt", u1);
        todos.addAll(Arrays.asList(t1, t2));

        u1.setTodos(todos);



        userrepos.save(u1);
        userrepos.save(u2);

    }
}
