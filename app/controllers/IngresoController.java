package controllers;

import java.util.Arrays;
import naranjalab.evaluaciones.tests.ICTest;
import naranjalab.evaluaciones.Evaluacion;
import naranjalab.evaluaciones.descriptors.ResultadoEvalDescriptor;
import naranjalab.evaluaciones.descriptors.SujetoDescriptor;
import naranjalab.form.IngresoForm;
import play.data.Form;
import play.mvc.Result;
import views.html.ingreso;
import views.html.maincontainer;

public class IngresoController extends MainContainerController {

    public Result displayForm() {
        return ok(maincontainer.render(ingreso.render(ff.form(IngresoForm.class)), flash(), session()));
    }

    public Result doPost() {
        if(session("id") == null){
            return sendToLogin();
        }
        
        Form<IngresoForm> userForm = ff.form(IngresoForm.class).bindFromRequest();
        if (userForm.hasErrors()) {
            return sendErrorMsg("Datos Incompletos", ingreso.render(ff.form(IngresoForm.class)));
        }
        IngresoForm testform = userForm.get();
        String user = session("user");
        logger.info("User {} sent a form: {}", user, testform);
        
        Evaluacion proceso = Evaluacion.creaEvaluacion(user);
        SujetoDescriptor sujeto = new SujetoDescriptor();
        sujeto.setRut(testform.getInputrut());
        sujeto.setNombre(testform.getNombreinput());
        sujeto.setApellidoPaterno(testform.getPaternoinput());
        sujeto.setApellidoMaterno(testform.getMaternoinput());
        sujeto.setProfesion(testform.getProfesion());
        sujeto.setTelefono(testform.getInputtelefono());
        logger.debug("Sujeto eval: {}", sujeto);
        proceso.setSujeto(sujeto);
        //parse respuestas y devuelve resultado
        String[] icRespuestas = testform.getICRespuestas();
        logger.debug("Got respuestas: {}", Arrays.asList(icRespuestas).toString());
        proceso.agregarICTest(icRespuestas);
        logger.info("Iniciando proceso de evaluacion");
        ResultadoEvalDescriptor resultado = proceso.procesarEvaluacion();
//        logger.info("Resultado: {} buenas, {} malas, error: {}", test.getBuenas(), test.getMalas(), test.getErrorMsg());

        return ok(maincontainer.render(ingreso.render(ff.form(IngresoForm.class)), flash(), session()));
    }
}
