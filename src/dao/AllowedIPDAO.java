package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.AllowedIPEnitity;
import model.ConDB;

public class AllowedIPDAO {

	public ArrayList<AllowedIPEnitity> getAllowedIP(String token) throws SQLException {
		ArrayList<AllowedIPEnitity> ipList = new ArrayList<AllowedIPEnitity>();

		ConDB conDB = new ConDB();

		PreparedStatement preparedStatement = conDB.getPreparedStatement("SELECT id, ip, time_stamp FROM allowed_ip WHERE token = ?");

		preparedStatement.setString(1, token);

		ResultSet resultSet = conDB.getResultFromPreparedFunction(preparedStatement);
		System.out.println(token);
		while(resultSet.next()) {
			AllowedIPEnitity allowedIPEnitity = new AllowedIPEnitity(resultSet);
			ipList.add(allowedIPEnitity);
		}
		
		conDB.close(resultSet);

		return ipList;
	}
	
	public void delAllowedIP(int id, String token) throws SQLException {
		ConDB conDB = new ConDB();
		
		PreparedStatement preparedStatement = conDB.getPreparedStatement("CALL del_ip(?, ?)");

		preparedStatement.setInt(1, id);
		preparedStatement.setString(2, token);

		conDB.makeUpdate(preparedStatement);
	}
	
	public void addAllowedIP(String newIP, String token) throws SQLException {
		ConDB conDB = new ConDB();
		
		PreparedStatement preparedStatement = conDB.getPreparedStatement("CALL add_ip(?, ?)");

		preparedStatement.setString(1, newIP);
		preparedStatement.setString(2, token);

		conDB.makeUpdate(preparedStatement);
	}
}
