package naranjalab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

	public boolean isAvailable(String username) {
		if(db == null) return false;
		if(username == null || username.isEmpty()) return false;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE username = '" + username + "' ";
        try (Connection con = db.getConnection(); Statement st = con.createStatement()){
        	rs = st.executeQuery(sql);
        	if(!rs.next())
        		return true;
		} catch (SQLException e) {
			logger.error("Failed to get db results", e);
		}
		return false;
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
        		ret.setAssociatedUser(rs.getString("associatedUser"));
        		ret.setValid(rs.getString("valid"));
        		ret.setId(rs.getInt("id"));
        	}
        	if (ret == null){
        		ret = new UserDescriptor(UserDescriptorStatus.NOT_FOUND, "Usuario no encontrado");
        	}
		} catch (SQLException e) {
			logger.error("Failed to get db results", e);
		}
		if(ret.getPwd() != null && ret.getPwd().compareTo("newpassword") == 0)
			ret.setStatus(UserDescriptorStatus.RESET_PASSWORD);
		return ret;
	}
	public String saveNewUser(UserDescriptor user){
		PreparedStatement stmt;
		try (Connection con = db.getConnection()){
			if(con == null) return "No se puede obtener una conexion a la BD";
			stmt = con.prepareStatement("INSERT INTO users(username, password, valid, associatedUser, nombre, apellidoPat, apellidoMat) VALUES (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPwd());
			stmt.setString(3, user.getValid());
			stmt.setString(4, user.getAssociatedUser());
			stmt.setString(5, user.getNombre());
			stmt.setString(6, user.getPaterno());
			stmt.setString(7, user.getMaterno());
			if(stmt.executeUpdate() > 0){
				logger.info("Usuario guardado exitosamente con username: {}", user.getUsername());
			}
		} catch (SQLException ex){
			logger.error("No se puede guardar en la BD el nuevo usuario{}", ex, user.getUsername());
			return "No se puede guardar en la BD el nuevo usuario";
		}
		return null;
	}
	public String changePwd(String user, String pwd) {
		PreparedStatement stmt;
		try (Connection con = db.getConnection()){
			if(con == null) return "No se puede obtener una conexion a la BD";
			stmt = con.prepareStatement("UPDATE users SET password = ? WHERE username=?");
			stmt.setString(1, pwd);
			stmt.setString(2, user);
			if(stmt.executeUpdate() > 0){
				logger.info("Se cambio la contraseña para: {}", user);
			}
		} catch (SQLException ex){
			logger.error("No se puede guardar en la BD cambio de pwd para {}", ex, user);
			return "No se puede guardar la nueva contraseña";
		}
		return null;
	}
	public String updateUser(UserDescriptor user){
		PreparedStatement stmt;
		try (Connection con = db.getConnection()){
			if(con == null) return "No se puede obtener una conexion a la BD";
			stmt = con.prepareStatement("UPDATE users SET username = ?, password = ?, valid = ?, associatedUser = ?, nombre = ?, "
					+ "apellidoPat = ?, apellidoMat = ? WHERE username=?");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPwd());
			stmt.setString(3, user.getValid());
			stmt.setString(4, user.getAssociatedUser());
			stmt.setString(5, user.getNombre());
			stmt.setString(6, user.getPaterno());
			stmt.setString(7, user.getMaterno());
			stmt.setString(8, user.getUsername());
			if(stmt.executeUpdate() > 0){
				logger.info("Usuario guardado exitosamente con username: {}", user.getUsername());
			}
		} catch (SQLException ex){
			logger.error("No se puede guardar en la BD el nuevo usuario{}", ex, user.getUsername());
			return "No se puede guardar en la BD el nuevo usuario";
		}
		return null;
	}
}
