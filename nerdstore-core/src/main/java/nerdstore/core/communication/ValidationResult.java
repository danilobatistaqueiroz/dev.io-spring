package nerdstore.core.communication;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    
    private Boolean valid = true;
    private final List<String> errors = new ArrayList<String>();

    public ValidationResult() {

    }

    public Boolean isValid() {
        return valid;
    }

    public List<String> getErrors() {
        return new ArrayList<String>(errors);
    }

    public void addError(String message) {
        valid = false;
        errors.add(message);
    }
}