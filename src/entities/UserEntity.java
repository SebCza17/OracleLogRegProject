package entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEntity {
	private int id;
	private String email;
	private String password;
	private String token;
	private String username;
	private Date birthDay;
	
	public UserEntity(ResultSet resultSet) {
		try {
			this.username = resultSet.getString("username");
			this.birthDay = resultSet.getDate("birth_day");
			this.token = resultSet.getString("token");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public Date getBirthDay() {
		return birthDay;
	}



	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}



	@Override
	public String toString() {
		return "Hello: " + username + " BirthDay: " + birthDay;
	}
	
	
}
