package naranjalab.evaluaciones;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import naranjalab.evaluaciones.tests.ICTest;
import naranjalab.evaluaciones.tests.TestDescriptor;
import naranjalab.common.util.Utility;
import naranjalab.evaluaciones.descriptors.ICTestResultDescriptor;
import naranjalab.evaluaciones.descriptors.ResultadoEvalDescriptor;
import naranjalab.evaluaciones.descriptors.SujetoDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author salcaino
 */
public class Evaluacion {

    private static final Logger logger = LoggerFactory.getLogger(Evaluacion.class.getName());
    
    private static final Map<String, Evaluacion> evaluacionesActuales = Collections.synchronizedMap(new HashMap<String, Evaluacion>());
    private final String evaluacionId;
    private SujetoDescriptor sujeto;
    private List<TestDescriptor> evaluaciones;
    private Date fechaTest;
    private String error;
    private String userCreador;
    private ICTest icTest;
    
    private Evaluacion(String user){
        evaluacionId = Utility.generarId();
        this.userCreador = user;
    }
    public String agregarICTest(String[] icRespuestas){
        if(icRespuestas != null && icRespuestas.length > 0){
            logger.info("Agregando ICTest a la evaluacion con {} respuestas", icRespuestas.length);
            icTest = new ICTest();
            icTest.setIcRespuestas(icRespuestas);
        } else
            return "Parametros invalidos para iniciar un IC test";
        return null;
    }
    public static Evaluacion creaEvaluacion(String username){
        Evaluacion ret = new Evaluacion(username);
        String key = String.valueOf(Utility.getHashCode(username, ret.getEvaluacionId()));
        logger.info("Agregando evaluacion para usuario {}, id {}, hash {}", username, ret.getEvaluacionId(), key);
        evaluacionesActuales.put(key, ret);
        return ret;
    }
    public static Evaluacion buscarEvaluacion(String username, String evId){
        if(username == null || evId == null) return null;
        Integer hashCode = Utility.getHashCode(username, evId);
        logger.info("Buscando evaluacion id {} para usuario {} hash value {}", evId, username, hashCode);
        Evaluacion got = evaluacionesActuales.get(hashCode.toString());
        if(got == null){
            logger.debug("No se encontro evaluacion de usuario {} con id {}", username, evId);
        }
        return got;
    }
    
    public ResultadoEvalDescriptor procesarEvaluacion(){
        logger.info("Iniciando evaluacion");
        if(sujeto == null){
            return new ResultadoEvalDescriptor("Falta informacion del sujeto");
//            logger.info("Se va a guardar la informacion del sujeto a evaluar");
//            if(sujeto.getRut() == null || sujeto.getRut().isEmpty()){
//                
//            }
        }
        ResultadoEvalDescriptor ret = new ResultadoEvalDescriptor();
        if(icTest != null){
            logger.info("Se va a iniciar test IC");
            icTest.runTest();
            logger.debug("Se termino de procesar test IC, buenas {}, malas {}", icTest.getBuenas(), icTest.getMalas());
            ICTestResultDescriptor icTestResult;
            if(icTest.getErrorMsg() != null && !icTest.getErrorMsg().isEmpty()){
                logger.warn("Test IC termino con errores, {}", icTest.getErrorMsg());
                icTestResult = new ICTestResultDescriptor("Error al procesar IC test. " + icTest.getErrorMsg());
            } else {
                icTestResult = new ICTestResultDescriptor();
                icTestResult.setBuenas(icTest.getBuenas());
                icTestResult.setMalas(icTest.getMalas());
                icTestResult.setRespuestas(icTest.getListaRespuestas());
            }
            ret.setIcTestResult(icTestResult);
        }
        return ret;
    }
    
    public SujetoDescriptor getSujeto() {
        return sujeto;
    }

    public void setSujeto(SujetoDescriptor sujeto) {
        this.sujeto = sujeto;
    }

    public List<TestDescriptor> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<TestDescriptor> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public Date getFechaTest() {
        return fechaTest;
    }

    public void setFechaTest(Date fechaTest) {
        this.fechaTest = fechaTest;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getEvaluacionId() {
        return evaluacionId;
    }

    public String getUserCreador() {
        return userCreador;
    }

    public void setUserCreador(String userCreador) {
        this.userCreador = userCreador;
    }
}
