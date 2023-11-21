package nerdstore.core.communication;

import nerdstore.core.messages.commonmessages.notifications.DomainNotification;

public interface NotificationSubscriber {
    <N extends DomainNotification> void onNotification(N notification);
}
