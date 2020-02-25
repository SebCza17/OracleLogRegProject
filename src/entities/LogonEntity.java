package entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogonEntity {
	String ip;
	String time_stamp;
	String status;
	
	public LogonEntity(ResultSet resultSet) {
		try {
			this.ip = resultSet.getString("ip");
			this.time_stamp = resultSet.getString("time_stamp");
			this.status = resultSet.getString("status");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
