package businessService.dbConnection.persistencia;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import businessService.dbConnection.conexao.IConectar;

public class Persistir implements IPersistir, IConectar {

	public int ExecuteUpdate(String query) {
		final Connection con = c.conectar();

		try {

			final Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			return st.executeUpdate(query);

		} catch (SQLException e) {

			System.out.printf("erro update %s\n", e);
			e.printStackTrace();
			return -1;
		} finally {
			desconectar(con);
		}
	}

	public ResultSet ExecuteQuery(String query) {
		final Connection con = c.conectar();
		try {

			final Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			return st.executeQuery(query);

		} catch (SQLException e) {
			System.out.printf("erro de execute %s\n", e);
			e.printStackTrace();
		} finally {
			desconectar(con);
		}
		return null;
	}

	public Connection desconectar(Connection con) {

		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

}
