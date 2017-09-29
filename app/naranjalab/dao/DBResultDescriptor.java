package naranjalab.dao;

import java.util.List;

/**
 * @author salcaino
 */
public class DBResultDescriptor {
    private String error;
    private List<Object[]> resultados;

    public DBResultDescriptor() {
    }
    public DBResultDescriptor(List<Object[]> resultados) {
        this.resultados = resultados;
    }
    public DBResultDescriptor(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Object[]> getResultados() {
        return resultados;
    }

    public void setResultados(List<Object[]> resultados) {
        this.resultados = resultados;
    }
    
}
