package nerdstore.core.messages;

import lombok.Getter;
import lombok.Setter;

public abstract class Message {
    @Getter @Setter
    private String messageType;
    @Getter @Setter
    private String aggregateId;

    protected Message() {
    }
}
