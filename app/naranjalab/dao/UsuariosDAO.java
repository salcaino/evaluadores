package naranjalab.dao;

import java.util.List;
import naranjalab.evaluaciones.descriptors.UserDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author salcaino
 */
public class UsuariosDAO {
    private static final Logger logger = LoggerFactory.getLogger(UsuariosDAO.class);
    public static boolean usuarioDisponible(String username){
        if(username == null || username.isEmpty()) return false;
        String sql = "SELECT * FROM users WHERE username = '" + username + "' ";
        DBQueryExecutor exec = DBAccessFactory.getExecutor();
        return exec.hayResultados(sql);
    }
    public static String guardarUsuario(UserDescriptor user) {
        if(user == null) return "Usuario invalido";
        String sql = "INSERT INTO users(username, password, valid, associatedUser, nombre, apellidoPat, apellidoMat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String valores[] = new String[7];
        valores[0] = user.getUsername();
        valores[1] = user.getPwd();
        valores[2] = user.getValid();
        valores[3] = user.getAssociatedUser();
        valores[4] = user.getNombre();
        valores[5] = user.getPaterno();
        valores[6] = user.getMaterno();
        DBQueryExecutor exec = DBAccessFactory.getExecutor();
        return exec.ejecutarDBUpdate(sql, valores);
    }
    public static String cambiarPwd(String user, String pwd) {
        if(usuarioDisponible(user)){
            return "Usuario no existe";
        }
        String sql = "UPDATE users SET password = ? WHERE username=?";
        String valores[] = new String[]{user, pwd};
        
        DBQueryExecutor exec = DBAccessFactory.getExecutor();
        return exec.ejecutarDBUpdate(sql, valores);
    }
    public static UserDescriptor getUserDescriptor(String username) {
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        DBQueryExecutor exec = DBAccessFactory.getExecutor();
        DBResultDescriptor queryresult = exec.ejecutarDBQuery(sql, "username", "password", "role", "associatedUser", "valid", "id");
        if(queryresult.getError() != null){
            logger.warn("Error al ejecutar query: {}", queryresult.getError());
            return null;
        }
        List<Object[]> resultados = queryresult.getResultados();
        if(resultados.isEmpty()){
            logger.info("No se encontraron usuarios buscando por: {}", username);
        }
        Object[] registro = resultados.get(0);
        UserDescriptor ret = new UserDescriptor();
        ret.setUsername(registro[0].toString());
        ret.setPwd(registro[1].toString());
        ret.setRole(registro[2].toString());
        ret.setAssociatedUser(registro[3].toString());
        ret.setValid(registro[4].toString());
        ret.setId(Integer.parseInt(registro[5].toString()));
        return ret;
    }
}
