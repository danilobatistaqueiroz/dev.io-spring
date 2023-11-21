package nerdstore.core.communication;

import nerdstore.core.messages.commonmessages.domainevents.DomainEvent;

public interface DomainEventSubscriber {
    <D extends DomainEvent> void onDomainEvent(D domainEvent);
}
