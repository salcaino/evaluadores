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
import java.util.ArrayList;
import java.util.List;

import naranjalab.evaluaciones.descriptors.UserDescriptor;
import naranjalab.evaluaciones.descriptors.UserDescriptorStatus;
import play.db.Database;
import play.db.Databases;

@Singleton
public class DBQueryExecutor {

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

    public boolean hayResultados(String query) {
        if (db == null) {
            return false;
        }
        if (query == null || query.isEmpty()) {
            return false;
        }
        ResultSet rs;
        String sql = query;
        try (Connection con = db.getConnection(); Statement st = con.createStatement()) {
            rs = st.executeQuery(sql);
            if (!rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Failed to get db results", e);
        }
        return false;
    }
    
    public UserDescriptor getUserDescriptor(String username) {
        if (db == null) {
            return new UserDescriptor(UserDescriptorStatus.ERROR, "Invalid DB instance");
        }
        if (username == null || username.isEmpty()) {
            return new UserDescriptor(UserDescriptorStatus.INVALID_USERNAME, "Usuario Invalido");
        }
        Statement st = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        UserDescriptor ret = null;
        try (Connection con = db.getConnection()) {
            if (con == null) {
                return new UserDescriptor(UserDescriptorStatus.ERROR, "No se puede obtener una conexion a la BD");
            }
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
            if (ret == null) {
                ret = new UserDescriptor(UserDescriptorStatus.NOT_FOUND, "Usuario no encontrado");
            }
        } catch (SQLException e) {
            logger.error("Failed to get db results", e);
        }
        if (ret.getPwd() != null && ret.getPwd().compareTo("newpassword") == 0) {
            ret.setStatus(UserDescriptorStatus.RESET_PASSWORD);
        }
        return ret;
    }

    public DBResultDescriptor ejecutarDBQuery(String query, String... columns){
        DBResultDescriptor ret = null;
        if(db == null) return new DBResultDescriptor("No se pudo acceder a la BD");
        if(query == null || columns == null) return new DBResultDescriptor("Parametros invalidos");
        Statement st = null;
        ResultSet rs = null;
        try (Connection con = db.getConnection()) {
            if(con == null){
                return new DBResultDescriptor("No se pudo obtener una conexion");
            }
            st = con.createStatement();
            rs = st.executeQuery(query);
            List<Object[]> resultado = new ArrayList<>();
            List<Object> columnas;
            while(rs.next()){
                columnas = new ArrayList<>(columns.length);
                for (String columna : columns) {
                    columnas.add(rs.getObject(columna));
                }
                resultado.add(columnas.toArray());
            }
            ret = new DBResultDescriptor(resultado);
        } catch (SQLException e) {
            logger.error("Failed to get db results", e);
            return new DBResultDescriptor("Error en la ejecucion. " + e.getMessage());
        }
        return ret;
    }
    
    public String ejecutarDBUpdate(String query, String... values){
        PreparedStatement stmt;
        try (Connection con = db.getConnection()) {
            if (con == null) {
                return "No se puede obtener una conexion a la BD";
            }
            stmt = con.prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                stmt.setString(i+1, values[i]);    
            }
            if (stmt.executeUpdate() > 0)
                logger.info("Update query ejecutada exitosamente");
            else
                logger.warn("No hubo cambios para la query: {}", query);
        } catch (SQLException ex) {
            logger.error("No se puede guardar en la BD la query: {}. got: {}", query, ex);
            return "No se puede guardar en la BD";
        }
        return null;
    }
}
