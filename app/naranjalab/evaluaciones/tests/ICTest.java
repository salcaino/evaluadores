package naranjalab.evaluaciones.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import naranjalab.common.util.Utility;
import naranjalab.evaluaciones.descriptors.OpcionRespuesta;
import naranjalab.evaluaciones.descriptors.RespuestaIC;

public class ICTest extends TestDescriptor {

    private List<RespuestaIC> listaRespuestas;
    private static List<RespuestaIC> respuestasCorrectas;
    private int buenas, malas;
    private String[] icRespuestas;
    public ICTest() {
        super("IC Test", "Instrucciones Complejas");
        loadRespuestasCorrectas();
    }

    private void loadRespuestasCorrectas() {
        if (respuestasCorrectas != null) {
            return;
        }
        respuestasCorrectas = new ArrayList<>(25);
        try {
            List<String> respuestas = FileUtils.readLines(Utility.pathRespuestasIC.toFile());
            if (respuestas == null || respuestas.isEmpty()) {
                logger.warn("No se pudo leer el archivo de respuestas correctas");
                respuestasCorrectas = null;
                return;
            }
            int i = 0;
            for (String leido : respuestas) {
                if (leido == null || leido.isEmpty()) {
                    logger.warn("No se puede reconocer esta linea, {}", leido);
                } else {
                    leido = leido.substring(1, leido.length() - 1);
                    String[] partes = leido.split(",");
                    OpcionRespuesta[] respuesta = new OpcionRespuesta[3];
                    respuesta[0] = partes.length > 0 && partes[0].equals("x") ? OpcionRespuesta.A : OpcionRespuesta.SIN_CONTESTAR;
                    respuesta[1] = partes.length > 1 && partes[1].equals("x") ? OpcionRespuesta.A : OpcionRespuesta.SIN_CONTESTAR;
                    respuesta[2] = partes.length > 2 && partes[2].equals("x") ? OpcionRespuesta.A : OpcionRespuesta.SIN_CONTESTAR;
                    respuestasCorrectas.add(i, new RespuestaIC(i, respuesta));
                }
                i++;
            }
            logger.info("Termino de cargar respuestas correctas. Se cargaron {} respuestas", respuestasCorrectas.size());
            logger.debug("Respuestas cargadas:{}", respuestasCorrectas);
        } catch (IOException e) {
            logger.warn("No se puede leer archivo en {}", Utility.pathRespuestasIC, e);
        }
    }

    public String[] getIcRespuestas() {
        return icRespuestas;
    }

    public void setIcRespuestas(String[] icRespuestas) {
        this.icRespuestas = icRespuestas;
    }

    public void runTest() {
        logger.info("Inicio test IC");
        if (icRespuestas == null || icRespuestas.length == 0) {
            errorMsg = "Respuestas invalidas";
            return;
        }
        if (respuestasCorrectas == null || respuestasCorrectas.isEmpty()) {
            errorMsg = "Respuestas Correctas no se pudieron cargar";
            return;
        }
        RespuestaIC respuestaIC, correcta;
        listaRespuestas = new ArrayList<>(icRespuestas.length);
        for (int i = 0; i < icRespuestas.length; i++) {
            respuestaIC = new RespuestaIC(i, icRespuestas[i], logger);
            listaRespuestas.add(respuestaIC);
            logger.debug(respuestaIC.toString());
            correcta = respuestasCorrectas.get(i);
            if (correcta.equals(respuestaIC)) {
                logger.info("Respuesta correcta");
                buenas++;
            } else {
                logger.warn("Respuesta equivocada, deberia ser {}", correcta);
                malas++;
            }
        }
        logger.info("Resultado es: {} correctas y {} malas", buenas, malas);
    }

    public List<RespuestaIC> getListaRespuestas() {
        return listaRespuestas;
    }

    public void setListaRespuestas(List<RespuestaIC> listaRespuestas) {
        this.listaRespuestas = listaRespuestas;
    }

    public static List<RespuestaIC> getRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    public static void setRespuestasCorrectas(List<RespuestaIC> respuestasCorrectas) {
        ICTest.respuestasCorrectas = respuestasCorrectas;
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
    
}
