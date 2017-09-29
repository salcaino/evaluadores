package naranjalab.evaluaciones.descriptors;

import java.util.List;

/**
 * @author salcaino
 */
public class ICTestResultDescriptor {
    private String error;
    private int buenas, malas;
    private List<RespuestaIC> respuestas;
    private EvalStatus status;
    public ICTestResultDescriptor() {
        status = EvalStatus.NEW;
    }

    public ICTestResultDescriptor(String error) {
        this.error = error;
        status = EvalStatus.ERROR;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getBuenas() {
        return buenas;
    }

    public void setBuenas(int buenas) {
        this.buenas = buenas;
    }

    public int getMalas() {
        return malas;
    }

    public void setMalas(int malas) {
        this.malas = malas;
    }

    public List<RespuestaIC> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaIC> respuestas) {
        this.respuestas = respuestas;
    }

    public EvalStatus getStatus() {
        return status;
    }

    public void setStatus(EvalStatus status) {
        this.status = status;
    }
    
}
