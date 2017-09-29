package naranjalab.evaluaciones.descriptors;
/**
 * @author salcaino
 */
public class SujetoDescriptor {
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNcto;
    private String profesion;
    private String email;
    private String telefono;
    private String direccion;

    public SujetoDescriptor() {
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFechaNcto() {
        return fechaNcto;
    }

    public void setFechaNcto(String fechaNcto) {
        this.fechaNcto = fechaNcto;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "{" + "rut=" + rut + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno 
                + ", apellidoMaterno=" + apellidoMaterno + ", fechaNcto=" + fechaNcto + ", profesion=" + profesion 
                + ", email=" + email + ", telefono=" + telefono + ", direccion=" + direccion + '}';
    }
}
