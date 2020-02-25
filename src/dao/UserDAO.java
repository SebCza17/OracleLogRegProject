package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.UserEntity;
import model.ConDB;

public class UserDAO {
	
	public UserDAO() {
		
	}
	
	public UserEntity getUser(String token) throws SQLException{
        ConDB conDB = new ConDB();
        
        PreparedStatement preparedStatement = conDB.getPreparedStatement("SELECT username, birth_day, token FROM user_basic_information WHERE token = ?");
        
        preparedStatement.setString(1, token);
        
        ResultSet resultSet = conDB.getResultFromPreparedFunction(preparedStatement);
        
        resultSet.next();
        
        UserEntity userEntity = new UserEntity(resultSet);
        
        conDB.close(resultSet);
        
        return userEntity;
    }
	
	public String isUser(String email) throws SQLException {
		ConDB conDB = new ConDB();
		
		PreparedStatement preparedStatement = conDB.getPreparedStatement("SELECT isUser(?) AS result FROM dual");
        
        preparedStatement.setString(1, email);
        
        ResultSet resultSet = conDB.getResultFromPreparedFunction(preparedStatement);
        
        resultSet.next();
        
        String result = resultSet.getString("result");
        
        conDB.close(resultSet);
        
        return result;
	}
	
	public boolean registration(String email, String password, String username, String birthDay, String ip) throws SQLException {
		ConDB conDB = new ConDB();
		
		PreparedStatement preparedStatement = conDB.getPreparedStatement("SELECT registration(?, ?, ?, ?, ?) AS result FROM Dual");
        
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, username);
        preparedStatement.setString(4, birthDay);
        preparedStatement.setString(5, ip);
        
        ResultSet resultSet = conDB.getResultFromPreparedFunction(preparedStatement);
        
        resultSet.next();
        
        boolean result = resultSet.getBoolean("result");
        
        conDB.close(resultSet);
        
        return result;
	}
	
	public String login(String email, String password, String ip) throws SQLException {
		ConDB conDB = new ConDB();
		
		PreparedStatement preparedStatement = conDB.getPreparedStatement("SELECT login(?, ?, ?) AS Token FROM dual");
        
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, ip);
        
        ResultSet resultSet = conDB.getResultFromPreparedFunction(preparedStatement);
        
        resultSet.next();
        
        String token = resultSet.getString("Token");
        
        conDB.close(resultSet);
        
        return token;
	}
	
	public void logout(String token, String ip) throws SQLException {
		ConDB conDB = new ConDB();
		
		PreparedStatement preparedStatement = conDB.getPreparedStatement("CALL logout(?, ?)");
        
        preparedStatement.setString(1, token);
        preparedStatement.setString(2, ip);
        
        conDB.makeUpdate(preparedStatement);
	}
	
	public String chagnePassword(String token, String oldPassword, String newPassword) throws SQLException {
		ConDB conDB = new ConDB();
		
		PreparedStatement preparedStatement = conDB.getPreparedStatement("SELECT change_password(?, ?, ?) AS result FROM dual");
        
        preparedStatement.setString(1, token);
        preparedStatement.setString(2, oldPassword);
        preparedStatement.setString(3, newPassword);
        
        ResultSet resultSet = conDB.getResultFromPreparedFunction(preparedStatement);
        
        resultSet.next();
        
        String result = resultSet.getString("result");
        
        conDB.close(resultSet);
        
        return result;
	}

}
