package businessService.dbConnection.persistencia;

import java.sql.ResultSet;

public interface IPersistir {
	
	public int ExecuteUpdate(String query);
	
	public ResultSet ExecuteQuery(String query);

}
