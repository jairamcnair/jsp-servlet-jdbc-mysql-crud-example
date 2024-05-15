package net.javaguides.usermanagement.dao;

import java.sql.*; // I did this because the Connection word was underlined
import java.sql.DriverManager; // need for jdbc DriverManager
import java.sql.SQLException; // need for try-catch block
import java.util.ArrayList; // need for ArrayList
import java.util.List; // need for ArrayList

import net.javaguides.usermanagement.model.*; // need for (User user) to not be underlined

// this DAO (Data Access Object) class provides CRUD operations (Create - Read - Update - Delete) for the users table in the mvcexample database

public class UserDAO {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "MySQL8IsGreat!";
	
	// establish database connection
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbh.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	// SQL QUERIES
	// C - Create
	private static final String INSERT_USER_SQL = "INSERT INTO users (name, email, country) VALUES (?, ?, ?);";
	
	//R- Read
	private static final String SELECT_USERS_BY_ID = "SELECT id, name, email, country FROM users WHERE id = ?;";
	private static final String SELECT_ALL_USERS = "SELECT * FROM users;";
	
	//U - Update
	private static final String UPDATE_USERS_SQL = "UPDATE users SET name = ?, email = ?, country = ? WHERE id = ?;";
	
	//D - Delete
	private static final String DELETE_USERS_SQL = "DELETE FROM users WHERE id = ?;";
	
	
	
	// Create User (INSERT)
	public void insertUser(User user) throws SQLException{
		try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)){
			// we don't do user.getId() because the id auto increments
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2,  user.getEmail());
			preparedStatement.setString(3,  user.getCountry());
			preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	// Read User (SELECT)- by ID and ALL
	
	// SELECT USER WITH ID
	public User selectUser(int id) {  // the method will return a User object
		User user = null;
		
		//establish connection and create statement
		try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_BY_ID)){
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(name, email, country);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}
	
	// SELECT ALL USERS
	public List<User> selectAllUsers() {  // the method will return a User object
		// array list
		List<User> users = new ArrayList<>();
		
		//establish connection and create statement
		try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)){
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add(new User(name, email, country));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return users;
	}
	
	
	
	// Update User (UPDATE)
	public boolean updateUser(User user) throws SQLException{
		boolean rowUpdated;
		try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)){
			// we don't do user.getId() because the id auto increments
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2,  user.getEmail());
			preparedStatement.setString(3,  user.getCountry());
			preparedStatement.setInt(4,  user.getId());
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	
	
	// Delete User (DELETE)
	public boolean deleteUser(int id) throws SQLException{  // pass the User user id to the function to delete
		boolean rowDeleted;
		try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)){
			preparedStatement.setInt(1, id);
			rowDeleted = preparedStatement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	
}
