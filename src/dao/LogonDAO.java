package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.AllowedIPEnitity;
import entities.LogonEntity;
import model.ConDB;

public class LogonDAO {

	public ArrayList<LogonEntity> getLogonList(String token) throws SQLException {
		ArrayList<LogonEntity> logonEntityList = new ArrayList<LogonEntity>();

		ConDB conDB = new ConDB();

		PreparedStatement preparedStatement = conDB.getPreparedStatement("SELECT ip, time_stamp, status FROM Logon WHERE id_user = (SELECT id_user FROM Tokens WHERE token = ?) ORDER BY time_stamp DESC OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY");

		preparedStatement.setString(1, token);

		ResultSet resultSet = conDB.getResultFromPreparedFunction(preparedStatement);
		System.out.println(token);
		
		while(resultSet.next()) {
			LogonEntity logonEntity = new LogonEntity(resultSet);
			logonEntityList.add(logonEntity);
		}
		
		conDB.close(resultSet);

		return logonEntityList;
	}
	
}

