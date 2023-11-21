package nerdstore.core.messages.commonmessages.domainevents;

import java.time.LocalDateTime;
import lombok.Getter;
import nerdstore.core.messages.Message;

public abstract class DomainEvent extends Message {
  @Getter  
  public LocalDateTime timestamp;

    protected DomainEvent(String aggregateId) {
        this.setAggregateId(aggregateId);
        timestamp = LocalDateTime.now();
    }
}
