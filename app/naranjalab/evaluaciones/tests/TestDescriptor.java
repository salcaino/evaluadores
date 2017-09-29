package naranjalab.evaluaciones.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDescriptor {
	protected Logger logger = LoggerFactory.getLogger("ProcesoTest");
	protected String nombreTest;
	protected String descripcionTest;
	protected String errorMsg;
	protected TestDescriptor(String nombre, String descripcion) {
		this.nombreTest = nombre;
		this.descripcionTest = descripcion;
	}
	public String getNombreTest() {
		return nombreTest;
	}
	public void setNombreTest(String nombreTest) {
		this.nombreTest = nombreTest;
	}
	public String getDescripcionTest() {
		return descripcionTest;
	}
	public void setDescripcionTest(String descripcionTest) {
		this.descripcionTest = descripcionTest;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
