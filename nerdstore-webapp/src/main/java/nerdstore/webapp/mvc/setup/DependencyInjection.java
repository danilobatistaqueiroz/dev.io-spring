package nerdstore.webapp.mvc.setup;

import nerdstore.vendas.application.handlers.PedidoCommandHandler;
import nerdstore.core.messages.commonmessages.notifications.DomainNotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DependencyInjection {

  @Autowired
  PedidoCommandHandler pedidoCommandHandler;

  @Autowired
  DomainNotificationHandler domainNotificationHandler;

  public void registerServices() {
    pedidoCommandHandler.handler();
    domainNotificationHandler.handler();
  }
}
