package naranjalab.form;

import java.util.Arrays;

public class IngresoForm {

	private String inputrut;
	private String nombreinput;
	private String paternoinput;
	private String maternoinput;
	private String nacimiento;
	private String profesion;
	private String inputemail;
	private String inputtelefono;
	private String inputdireccion;
	private String inputcomuna;
	private String checktest1;
	private String ICRespuestas[];
	public IngresoForm() {
		ICRespuestas = new String[25];
	}
	public String getInputrut() {
		return inputrut;
	}
	public void setInputrut(String inputrut) {
		this.inputrut = inputrut;
	}
	public String getNombreinput() {
		return nombreinput;
	}
	public void setNombreinput(String nombreinput) {
		this.nombreinput = nombreinput;
	}
	public String getPaternoinput() {
		return paternoinput;
	}
	public void setPaternoinput(String paternoinput) {
		this.paternoinput = paternoinput;
	}
	public String getMaternoinput() {
		return maternoinput;
	}
	public void setMaternoinput(String maternoinput) {
		this.maternoinput = maternoinput;
	}
	public String getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(String nacimiento) {
		this.nacimiento = nacimiento;
	}
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	public String getInputemail() {
		return inputemail;
	}
	public void setInputemail(String inputemail) {
		this.inputemail = inputemail;
	}
	public String getInputtelefono() {
		return inputtelefono;
	}
	public void setInputtelefono(String inputtelefono) {
		this.inputtelefono = inputtelefono;
	}
	public String getInputdireccion() {
		return inputdireccion;
	}
	public void setInputdireccion(String inputdireccion) {
		this.inputdireccion = inputdireccion;
	}
	public String getInputcomuna() {
		return inputcomuna;
	}
	public void setInputcomuna(String inputcomuna) {
		this.inputcomuna = inputcomuna;
	}
	public String getChecktest1() {
		return checktest1;
	}
	public void setChecktest1(String checktest1) {
		this.checktest1 = checktest1;
	}
	public String[] getICRespuestas() {
		return ICRespuestas;
	}
	public void setICRespuestas(String[] iCRespuestas) {
		ICRespuestas = iCRespuestas;
	}
	@Override
	public String toString() {
		return "IngresoForm [inputrut=" + inputrut + ", nombreinput=" + nombreinput + ", paternoinput=" + paternoinput
				+ ", maternoinput=" + maternoinput + ", nacimiento=" + nacimiento + ", profesion=" + profesion
				+ ", inputemail=" + inputemail + ", inputtelefono=" + inputtelefono + ", inputdireccion="
				+ inputdireccion + ", inputcomuna=" + inputcomuna + ", checktest1=" + checktest1 + ", ICRespuestas="
				+ (ICRespuestas == null ? "null" : getRespuestas());
	}
	private String getRespuestas() {
		if(ICRespuestas == null || ICRespuestas.length == 0) return null;
		String ret = "{";
		for (int i = 0; i < ICRespuestas.length; i++) {
			ret += i + "[" + ICRespuestas[i] + "]";
		}
		ret += "}";
		return ret;
	}
	
}
