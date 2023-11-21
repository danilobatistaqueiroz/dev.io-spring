package nerdstore.core.messages;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import nerdstore.core.communication.ValidationResult;

public abstract class Command extends Message {
    @Getter
    private LocalDateTime timestamp;
    @Getter @Setter
    private ValidationResult validationResult;

    protected Command() {
        timestamp = LocalDateTime.now();
    }

    public Boolean ehValido() {
        throw new RuntimeException("not implemented");
    }
}
