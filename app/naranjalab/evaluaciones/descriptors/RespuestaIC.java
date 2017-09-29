package naranjalab.evaluaciones.descriptors;

import java.util.Arrays;

import org.slf4j.Logger;

public class RespuestaIC {

    private int nroRespuesta;
    private OpcionRespuesta contestado[];
    private boolean tieneBorron = false;
    private Logger logger;

    public RespuestaIC(int nroRespuesta, String recibido, Logger logger) {
        this.logger = logger;
        this.nroRespuesta = nroRespuesta;
        contestado = new OpcionRespuesta[3];
        parseRespuesta(recibido);
    }

    public RespuestaIC(int nroRespuesta, OpcionRespuesta[] contestado) {
        this.nroRespuesta = nroRespuesta;
        this.contestado = contestado;
    }

    private void parseRespuesta(String recibido) {
        logger.info("Parseando linea {}", recibido);
        if (recibido == null || recibido.isEmpty()) {
            anulaRespuesta();
            return;
        }
        String partes[] = recibido.split(",");
        if (partes.length != 3) {
            logger.warn("String recibido no es valido, got: {}", recibido);
            anulaRespuesta();
        }//check here for ,marca,marca
        contestado[0] = getOpcion(partes[0]);
        contestado[1] = getOpcion(partes[1]);
        contestado[2] = getOpcion(partes[2]);
        logger.info("Termino de procesar respuesta. resultado: {}", Arrays.asList(contestado));
    }

    private void anulaRespuesta() {
        contestado[0] = OpcionRespuesta.SIN_CONTESTAR;
        contestado[1] = OpcionRespuesta.SIN_CONTESTAR;
        contestado[2] = OpcionRespuesta.SIN_CONTESTAR;
    }

    private OpcionRespuesta getOpcion(String parte) {
        switch (parte) {
            case "marca":
                return OpcionRespuesta.A;
            case "borron":
                return OpcionRespuesta.B;
            default:
                return OpcionRespuesta.SIN_CONTESTAR;
        }
    }

    public int getNroRespuesta() {
        return nroRespuesta;
    }

    public void setNroRespuesta(int nroRespuesta) {
        this.nroRespuesta = nroRespuesta;
    }

    public OpcionRespuesta[] getContestado() {
        return contestado;
    }

    public void setContestado(OpcionRespuesta contestado[]) {
        this.contestado = contestado;
    }

    public boolean isTieneBorron() {
        return tieneBorron;
    }

    public void setTieneBorron(boolean tieneBorron) {
        this.tieneBorron = tieneBorron;
    }

    @Override
    public String toString() {
        return "[nroRespuesta=" + nroRespuesta + ", contestado=" + (contestado != null ? Arrays.toString(contestado) : "null")
                + ", tieneBorron=" + tieneBorron + "]";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.nroRespuesta;
        hash = 17 * hash + Arrays.deepHashCode(this.contestado);
        hash = 17 * hash + (this.tieneBorron ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RespuestaIC other = (RespuestaIC) obj;
        if (this.tieneBorron != other.tieneBorron) {
            return false;
        }
        return Arrays.deepEquals(this.contestado, other.contestado);
    }

}
