package nerdstore.core.messages.commonmessages.notifications;

import java.time.LocalDateTime;

import lombok.Getter;
import nerdstore.core.messages.Message;

@Getter
public class DomainNotification extends Message {
    private LocalDateTime timestamp;
    private String domainNotificationId;
    private String key;
    private String value;
    private Integer version;

    public DomainNotification(String key, String value) {
        this.timestamp = LocalDateTime.now();
        //domainNotificationId = Guid.NewGuid();
        this.version = 1;
        this.key = key;
        this.value = value;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }
}