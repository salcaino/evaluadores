package naranjalab.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Singleton;

import org.slf4j.Logger;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import naranjalab.descriptors.UserDescriptor;
import naranjalab.descriptors.UserDescriptorStatus;
import play.db.Database;
import play.db.Databases;

@Singleton
public class DBQueryExecutor  {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger("DAOLog");
	private static Database db;
	private static final String dbName = System.getProperty("app.dbname", "evaluaciones");
	protected DBQueryExecutor() {
		String driver = "db.evaluaciones.driver";
		String url = "db.evaluaciones.url";
		Config conf = ConfigFactory.load();
		db = Databases.createFrom(
				dbName,
				conf.getString(driver),
				conf.getString(url)
		);
	}

	public UserDescriptor getUserDescriptor(String username){
		if(db == null) return new UserDescriptor(UserDescriptorStatus.ERROR, "Invalid DB instance");
		if(username == null || username.isEmpty()) return new UserDescriptor(UserDescriptorStatus.INVALID_USERNAME, "Usuario Invalido");
		Statement st = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        UserDescriptor ret = null;
        try (Connection con = db.getConnection()){
    		if(con == null) return new UserDescriptor(UserDescriptorStatus.ERROR, "No se puede obtener una conexion a la BD");
            st = con.createStatement();
            
        	rs = st.executeQuery(sql);
        	while (rs.next()) {
        		ret = new UserDescriptor();
        		ret.setUsername(rs.getString("username"));
        		ret.setPwd(rs.getString("password"));
        		ret.setRole(rs.getString("role"));
        		ret.setAssociatedUser(rs.getInt("associatedUser"));
        		ret.setValid(rs.getString("valid"));
        		ret.setId(rs.getInt("id"));
        	}
        	if (ret == null){
        		ret = new UserDescriptor(UserDescriptorStatus.NOT_FOUND, "Usuario no encontrado");
        	}
		} catch (SQLException e) {
			logger.error("Failed to get db results", e);
		}
		return ret;
	}

}
