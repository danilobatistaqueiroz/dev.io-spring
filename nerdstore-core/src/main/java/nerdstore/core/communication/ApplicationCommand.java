package nerdstore.core.communication;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import nerdstore.core.exceptions.NotImplementedException;
import nerdstore.core.communication.ValidationResult;
import nerdstore.core.messages.Command;

public abstract class ApplicationCommand extends Command {

    protected ApplicationCommand() {
        super();
    }

    public Boolean ehValido() {
        throw new NotImplementedException();
    }

    public abstract String getType();

    public abstract String getFieldValue(String key) throws NoSuchFieldException, IllegalAccessException;
}

