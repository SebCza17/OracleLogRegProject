package entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllowedIPEnitity {
	private int id;
	private String ip;
	private String time_stamp;
	
	public AllowedIPEnitity(ResultSet resultSet) {
		try {
			this.id = resultSet.getInt("id");
			this.ip = resultSet.getString("ip");
			this.time_stamp = resultSet.getString("time_stamp");
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
	
	
}
