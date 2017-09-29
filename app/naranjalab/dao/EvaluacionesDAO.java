package naranjalab.dao;

import naranjalab.evaluaciones.descriptors.SujetoDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author salcaino
 */
public class EvaluacionesDAO {
    private static final Logger logger = LoggerFactory.getLogger(EvaluacionesDAO.class);
    public String guardarSujetoEvaluacion(SujetoDescriptor sujeto){
        if(sujeto == null) return "Sujeto invalido";
        
        logger.info("Se va a guardar sujeto nombre {}", sujeto.getNombre());
        String sql = "INSERT INTO users(username, password, valid, associatedUser, nombre, apellidoPat, apellidoMat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String valores[] = new String[7];
        valores[0] = sujeto.getRut();
        valores[1] = sujeto.getNombre();
        valores[2] = sujeto.getApellidoPaterno();
        valores[3] = sujeto.getApellidoMaterno();
        valores[4] = sujeto.getEmail();
        valores[5] = sujeto.getDireccion();
        valores[6] = sujeto.getFechaNcto();
        valores[7] = sujeto.getProfesion();
        valores[8] = sujeto.getTelefono();
        
//        DBQueryExecutor exec = DBAccessFactory.getExecutor();
        return null;
    }

}
