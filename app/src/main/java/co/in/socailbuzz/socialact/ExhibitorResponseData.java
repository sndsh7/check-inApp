package co.in.socailbuzz.socialact;

import java.util.List;

public class ExhibitorResponseData {

    public String status;
    public List<String> errors;

    public ExhibitorResponseData(String status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
