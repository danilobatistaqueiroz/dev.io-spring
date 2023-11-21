package nerdstore.webapp.mvc.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import nerdstore.core.messages.commonmessages.notifications.DomainNotificationHandler;
import nerdstore.core.messages.commonmessages.notifications.DomainNotification;
import nerdstore.core.communication.SimpleEventBus;

public class ControllerBase {

  @Autowired
  private DomainNotificationHandler notifications;
  @Autowired
  private SimpleEventBus eventBus;

  protected String clienteId = "4885e451-b0e4-4490-b959-04fabc806d32";

  protected ControllerBase() {
  }

  protected Boolean operacaoValida() {
    return !notifications.temNotificacao();
  }

  protected List<String> obterMensagensErro() {
    return notifications.obterNotificacoes().stream().map(n -> n.getValue()).collect(Collectors.toList());
  }

  protected void limparNotificacoes() {
    notifications.limparNotificacoes();
  }

  protected void notificarErro(String codigo, String mensagem) {
    eventBus.publicarNotificacao(new DomainNotification(codigo, mensagem));
  }
}
