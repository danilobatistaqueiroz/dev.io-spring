package nerdstore.core.messages.commonmessages.notifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nerdstore.core.communication.NotificationSubscriber;
import nerdstore.core.communication.SimpleEventBus;

@Service
public class DomainNotificationHandler {

    @Autowired
    private SimpleEventBus eventBus;

    private List<DomainNotification> notifications;

    public DomainNotificationHandler() {
        notifications = new ArrayList<DomainNotification>();
    }

    public void handle(DomainNotification message) {
        notifications.add(message);
    }

    public void handler() {
      eventBus.subscribeToNotification("AdicionarItemPedidoCommand", new NotificationSubscriber() {
        @Override
        public <N extends DomainNotification> void onNotification(N notification) {
          notifications.add(notification);
        }
      });
    }

    public List<DomainNotification> obterNotificacoes() {
        return notifications;
    }

    public void limparNotificacoes() {
      notifications = new ArrayList<DomainNotification>();
    }

    public Boolean temNotificacao() {
        return obterNotificacoes().size()>0;
    }

    public void dispose(){
        notifications = new ArrayList<DomainNotification>();
    }
}