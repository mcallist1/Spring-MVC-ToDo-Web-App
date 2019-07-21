package com.tomo.todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tomo.todo.ToDo;

@Service
public class ToDoService {
	private static List<ToDo> todos = new ArrayList<>();
	private static int todoCount = 3;

	static {
		todos.add(new ToDo(1, "tomo", "Learn Spring MVC", new Date(),
				false));
		todos.add(new ToDo(2, "tomo", "Learn Struts", new Date(), false));
		todos.add(new ToDo(3, "tomo", "Learn Hibernate", new Date(),
				false));
	}

	public List<ToDo> retrieveTodos(String user) {
		List<ToDo> filteredTodos = new ArrayList<>();
		for (ToDo todo : todos) {
			if (todo.getUser().equals(user))
				filteredTodos.add(todo);
		}
		return filteredTodos;
	}

	public ToDo retrieveTodo(int id) {
		for (ToDo todo : todos) {
			if (todo.getId() == id)
				return todo;
		}
		return null;
	}

	public void updateTodo(ToDo todo) {
		todos.remove(todo);
		todos.add(todo);
	}
	
	
	public void addTodo(String name, String desc, Date targetDate, boolean isDone) {
		todos.add(new ToDo(++todoCount, name, desc, targetDate, isDone));
	}

	public void deleteTodo(int id) {
		Iterator<ToDo> iterator = todos.iterator();
		while (iterator.hasNext()) {
			ToDo todo = iterator.next();
			if (todo.getId() == id) {
				iterator.remove();
			}
		}
	}
}
