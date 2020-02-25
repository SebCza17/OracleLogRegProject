package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConDB {
	public ConDB() {

	}

	private InitialContext getContext() throws NamingException {
		return new InitialContext();
	}

	private DataSource getDataSource() throws NamingException {
		return (DataSource) getContext().lookup("java:/comp/env/jdbc/myoracldb");
	}

	private Connection getConnection() throws SQLException, NamingException {

		return getDataSource().getConnection();
	}

	public PreparedStatement getPreparedStatement(String query) {
		try {
			return getConnection().prepareStatement(query);
		} catch (SQLException ex) {
			Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null, "getPreparedStatement->SQLException");
			return null;
		} catch (NamingException ex) {
			Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null, "getPreparedStatement->NamingException");
			return null;
		}
	}

	public ResultSet getResultFromPreparedFunction(PreparedStatement preparedStatement) {
		try {
			return preparedStatement.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null,
					"getResultFromPreparedFunction->SQLException");
			return null;
		}
	}

	public boolean makePrepareFunction(PreparedStatement preparedStatement) {
		ResultSet resultSet = null;
		try {
			resultSet = preparedStatement.executeQuery();
			resultSet.next();

			return resultSet.getBoolean(1);
		} catch (SQLException ex) {
			Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null, "makePrepareFunction->SQLException");
			return false;
		} finally {
			this.close(resultSet);
		}
	}
	
	public boolean makeUpdate(PreparedStatement preparedStatement){
        try {
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null, "makeInsert->SQLException");
            return false;
        } finally{
            this.close(preparedStatement);
        }
    }
	
	public boolean isConnection() {
		Connection connection = null;
		try {
			connection = getConnection();
			boolean result = !connection.isClosed();

			return result;
		} catch (SQLException ex) {
			Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null, "isConnection->SQLException");
			return false;
		} catch (NamingException ex) {
			Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null, "isConnection->NamingException");
			return false;
		} finally {
			this.close(connection);
		}
	}

	public void close(ResultSet resultSet) {
		try {
			resultSet.getStatement().getConnection().close();
			resultSet.getStatement().close();
			resultSet.close();
		} catch (SQLException ex) {
			Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null, "close->SQLException");
		} catch (NullPointerException ex) {
		}
	}

	public void close(PreparedStatement preparedStatement) {
		try {
			preparedStatement.getConnection().close();
			preparedStatement.close();
		} catch (SQLException ex) {
			Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null, "close->SQLException");
		} catch (NullPointerException ex) {
		}
	}

	public void close(Statement statement) {
		try {
			statement.getConnection().close();
			statement.close();
		} catch (SQLException ex) {
			Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null, "close->SQLException");
		} catch (NullPointerException ex) {
		}
	}

	public void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException ex) {
			Logger.getLogger(ConDB.class.getName()).log(Level.SEVERE, null, "close->SQLException");
		} catch (NullPointerException ex) {
		}
	}
}
