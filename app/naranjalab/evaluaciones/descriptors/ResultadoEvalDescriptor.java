package naranjalab.evaluaciones.descriptors;

/**
 * @author salcaino
 */
public class ResultadoEvalDescriptor {
    private String errorMsg;
    private EvalStatus status = EvalStatus.NEW;
    private ICTestResultDescriptor icTestResult;
    
    
    public ResultadoEvalDescriptor(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ResultadoEvalDescriptor() {
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public EvalStatus getStatus() {
        return status;
    }

    public void setStatus(EvalStatus status) {
        this.status = status;
    }

    public ICTestResultDescriptor getIcTestResult() {
        return icTestResult;
    }

    public void setIcTestResult(ICTestResultDescriptor icTestResult) {
        this.icTestResult = icTestResult;
    }
    
}
