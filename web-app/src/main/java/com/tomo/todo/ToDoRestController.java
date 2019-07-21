package com.tomo.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToDoRestController {
	@Autowired
	private ToDoService service;

	@RequestMapping(value = "/todo/", method = RequestMethod.GET)
	public List<ToDo> listAllTodos() {
		List<ToDo> users = service.retrieveTodos("tomo");
		return users;
	}

}
