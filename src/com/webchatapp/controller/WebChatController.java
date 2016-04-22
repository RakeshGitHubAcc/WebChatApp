package com.webchatapp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.webchatapp.hibernatebeans.Message;
import com.webchatapp.hibernatebeans.OnlineUsers;
import com.webchatapp.hibernatebeans.User;
import com.webchatapp.service.JavaPojoJSONCaster;
import com.webchatapp.service.MessageDatastoreService;
import com.webchatapp.service.UserDatastoreService;

@Controller
public class WebChatController {
	@Autowired
	UserDatastoreService userDatastoreService;
	@Autowired
	MessageDatastoreService messageDatastoreService;

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest req, HttpServletResponse resp) {
		User user = null;
		String username = req.getParameter("uname");
		String password = req.getParameter("pword");
		user = userDatastoreService.loginByUsernameAndPassword(username,
				password);
		if (user != null) {
			req.getSession().setAttribute("USER", user);
			userDatastoreService.changeMyOnlineStatus(user.getId(), 1);
		}
		return new ModelAndView("welcome");
	}

	@RequestMapping("/addUser")
	public ModelAndView addUser(HttpServletRequest request,
			HttpServletResponse response) {
		String username = request.getParameter("uname");
		String password = request.getParameter("pword");
		userDatastoreService.createUserByHibernate(username, password);
		return new ModelAndView("welcome");
	}

	@RequestMapping("/sendMessage")
	public ModelAndView sendMessage(HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("USER");
		String sender = user.getUserName();
		String receiver = request.getParameter("to");
		String message = request.getParameter("message");
		messageDatastoreService
				.sendMessageToThisUser(receiver, message, sender);
		return new ModelAndView("welcome");
	}

	@RequestMapping("/getMyMessages")
	public void getMyMessages(HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("USER");
		String loggedUserName = user.getUserName();
		List<Object> messages = messageDatastoreService
				.getMyLatestMessages(loggedUserName);
		response.setContentType("json");
		JavaPojoJSONCaster pojoJSONCaster = new JavaPojoJSONCaster();
		try {
			response.getWriter().write(
					pojoJSONCaster.convert_ThisPojoList_To_JSONArray(messages,
							Message.class).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(pojoJSONCaster.convert_ThisPojoList_To_JSONArray(
				messages, Message.class).toString());
	}

	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect("index.jsp");
			userDatastoreService.changeMyOnlineStatus(((User) request
					.getSession(true).getAttribute("USER")).getId(), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		request.getSession(true).invalidate();
	}

	@RequestMapping("/getAllUsers")
	public void getAllUsers(HttpServletRequest request,
			HttpServletResponse response) {
		List<Object> users = userDatastoreService.getAllUsers(((User) request
				.getSession().getAttribute("USER")).getUserName());
		response.setContentType("json");
		JavaPojoJSONCaster pojo_JSONCaster = new JavaPojoJSONCaster();
		try {
			response.getWriter().write(
					pojo_JSONCaster.convert_ThisPojoList_To_JSONArray(users,
							User.class).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(pojo_JSONCaster.convert_ThisPojoList_To_JSONArray(
				users, User.class).toString());
	}

	@RequestMapping("/getPrev")
	public void getPrev(HttpServletRequest request, HttpServletResponse response) {
		String minVal = request.getParameter("minVal");

		User user = (User) request.getSession().getAttribute("USER");

		String loggedUserName = user.getUserName();

		List<Object> messages = messageDatastoreService.getMyPrevMessages(
				loggedUserName, minVal);

		response.setContentType("json");
		JavaPojoJSONCaster pojo_JSONCaster = new JavaPojoJSONCaster();
		try {
			response.getWriter().write(
					pojo_JSONCaster.convert_ThisPojoList_To_JSONArray(messages,
							Message.class).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/getNext")
	public void getNext(HttpServletRequest request, HttpServletResponse response) {
		String maxVal = request.getParameter("maxVal");

		User user = (User) request.getSession().getAttribute("USER");

		String loggedUserName = user.getUserName();

		List<Object> messages = messageDatastoreService.getMyNextMessages(
				loggedUserName, maxVal);

		response.setContentType("json");
		JavaPojoJSONCaster pojo_JSONCaster = new JavaPojoJSONCaster();
		try {
			response.getWriter().write(
					pojo_JSONCaster.convert_ThisPojoList_To_JSONArray(messages,
							Message.class).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/getOnlineUsers")
	public void getOnlineUsers(HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("USER");
		String loggedUserName = user.getUserName();
		List<Object> onlineUsers = userDatastoreService.getOnlineUsers(loggedUserName);
		response.setContentType("json");
		JavaPojoJSONCaster pojo_JSONCaster = new JavaPojoJSONCaster();
		try {
			response.getWriter().write(
					pojo_JSONCaster.convert_ThisPojoList_To_JSONArray(
							onlineUsers, OnlineUsers.class).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
