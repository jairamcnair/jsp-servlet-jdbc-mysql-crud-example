package net.javaguides.usermanagement.web;

import java.io.IOException; // need for throws IOException for methods
import java.sql.SQLException; // need for throws SQLException for methods
import java.util.ArrayList; // need for ArrayList in listUser method
import java.util.List; // need for List in listUser method

import javax.servlet.RequestDispatcher; // need for RequestDispatcher in showNewForm() method
import javax.servlet.ServletException; // need for throws ServletException for methods
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.javaguides.usermanagement.dao.*; // need for UserDAO object
import net.javaguides.usermanagement.model.*; // need for User object

// the Servlet is the controller in MVC
// video source for entire project: https://www.youtube.com/watch?v=RqiuxA_OFOk&list=PLG1cGgEaoMYlgDjA1kdQL4bXr-DmvW_il&index=3

@WebServlet("/") //@WebServlet("/UserServlet")- make sure to change this or it will result in a HTTP Status 404 Error
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO; // 1. add DAO object
   
    public UserServlet() {
    	this.userDAO = new UserDAO(); // 1. add DAO object
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response); // 2. post the requests
	}
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
	// handle all requests in the doGet method - use a switch statement to handle all the actions
		
		String action = request.getServletPath(); // 3. define the action and handle actions with switch statement in the doGet method
		
		switch(action) { // 4. each case is a jsp file
		
		case "/new":
			showNewForm(request, response); // 6. put showNewForm method in switch statements
			break;
			
		case "/insert":
			try { // 8. handle insert case with insertUser method
				insertUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		case "/delete":
			try { // 10. handle delete case with deleteUser method
				deleteUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/edit":
			try { // 12. handle edit case with showEditForm method
				showEditForm(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/update":
			try { // 14. handle update case with updateUser method
				updateUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		default:
			try { // 16. handle default case wtih listUser method
				listUsers(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
	}
	
	
	// 5. create method to handle "/new" case
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// the requested action from the doGet method switch statements is forwarded to the user-form.jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp"); 
		dispatcher.forward(request, response);
	}
	
	// 7. create method to handle "/insert" case
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// the user selects insert > user is take to insert-form.jsp > the form values are passed here > these values are passed to to UserBean to create object
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		User newUser = new User(name, email, country); // new user object created
		userDAO.insertUser(newUser); // newUser object is passed to UserDAO to be inserted into database
		response.sendRedirect("list");
	}
	
	// 9. create method to handle "/insert" case
		private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			userDAO.deleteUser(id);
			response.sendRedirect("list");
		}
		
	// 11. create method to handle "/edit" cast
		private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			User existingUser = userDAO.selectUser(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			request.setAttribute("user", existingUser);
			dispatcher.forward(request, response);
		}
	// 13. create method to handle "/update" case
		private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String country = request.getParameter("country");
			
			User book = new User(id, name, email, country);
			userDAO.updateUser(book);
			response.sendRedirect("list");
		}
		
	// 15. create method to handle default case
		private void listUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			List<User> listUser = userDAO.selectAllUsers();
			request.setAttribute("listUser", listUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
			dispatcher.forward(request, response);
		}
}
