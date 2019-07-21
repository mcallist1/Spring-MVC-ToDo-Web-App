package com.tomo.todo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tomo.todo.ToDoService;

@Controller
public class ToDoController {

	@Autowired
	private ToDoService service;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String listToDos(ModelMap model) {
		model.addAttribute("todos", service.retrieveTodos(retrieveLoggedInUserName()));
		return "list-todos";
	}
	
	private String retrieveLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)return ((UserDetails) principal).getUsername();
		return principal.toString();
	}
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showToDoPage(ModelMap model) {
		model.addAttribute("todo", new ToDo(0,retrieveLoggedInUserName(),"Default Description",new Date(), false));
		return "todo"; 
	}
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addToDo(ModelMap model, @Valid ToDo todo, BindingResult result) {
		if(result.hasErrors()){
			return "todo";
		}
		service.addTodo(retrieveLoggedInUserName(), todo.getDesc(), new Date(), false);
		model.clear();
		return "redirect:list-todos";
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String updateToDo(ModelMap model, @RequestParam int id) {
		ToDo todo =  service.retrieveTodo(id);
		model.addAttribute("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateToDo(ModelMap model, @Valid ToDo todo, BindingResult result) {
		if(result.hasErrors()){
			return "todo";
		}
		todo.setUser(retrieveLoggedInUserName());
		service.updateTodo(todo);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteToDo(ModelMap model, @RequestParam int id) {
		service.deleteTodo(id);
		model.clear();
		return "redirect:list-todos";
	}
	
	
	
	
}
