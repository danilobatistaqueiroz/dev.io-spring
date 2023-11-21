package nerdstore.core.messages;

import java.time.LocalDateTime;
import lombok.Getter;

public class Event extends Message {
    @Getter
    private LocalDateTime timestamp;

    protected Event() {
      timestamp = LocalDateTime.now();
    }
}
