package businessService.dbConnection.conexao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Conexao implements Iconexao {

	private Connection con;

	public Connection conectar() {
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovaces", "wildfly", "col@wildfly");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
			con = null;
		}
		return con;

	}

}
