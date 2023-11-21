package nerdstore.vendas.application.handlers;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import nerdstore.core.communication.*;
import nerdstore.core.messages.Command;
import nerdstore.core.messages.commonmessages.notifications.DomainNotification;
import nerdstore.core.messages.commonmessages.notifications.DomainNotificationHandler;
import nerdstore.vendas.data.repository.PedidoRepository;
import nerdstore.vendas.domain.*;
import nerdstore.vendas.application.events.PedidoItemAdicionadoEvent;
import nerdstore.vendas.application.events.PedidoRascunhoIniciadoEvent;

@Service
public class PedidoCommandHandler {

  @Autowired
  private SimpleEventBus eventBus;

  @Autowired
  DomainNotificationHandler notificationHandler;

  @Autowired
  private PedidoRepository pedidoRepository;

  public void handler() {
    eventBus.subscribeToCommand("AdicionarItemPedidoCommand", new CommandSubscriber() {
      @Override
      public <C extends ApplicationCommand> void onCommand(C command) {
        try {
          if (!validarComando(command)) {
            return;
          }
          var produtoId = command.getFieldValue("produtoId");
          var clienteId = command.getFieldValue("clienteId");
          var nome = command.getFieldValue("nome");
          var quantidade = Integer.parseInt(command.getFieldValue("quantidade"));
          var valorUnitario = new BigDecimal(command.getFieldValue("valorUnitario"));
          adicionarItemPedido(produtoId, clienteId, nome, quantidade, valorUnitario);
        } catch (Exception ex) {
          System.err.println(ex.getMessage());
        }
      }
    });
  }

  public void adicionarItemPedido(String produtoId, String clienteId, String nome, Integer quantidade, BigDecimal valorUnitario) {
    Pedido pedido = pedidoRepository.obterPedidoRascunhoPorClienteId(clienteId);
    var pedidoItem = new PedidoItem(produtoId, nome, quantidade, valorUnitario);
    if (pedido == null) {
      pedido = Pedido.novoPedidoRascunho(clienteId);
      pedido.adicionarOuAtualizarItem(pedidoItem);
      pedidoRepository.adicionar(pedido);
      pedido.adicionarEvento(new PedidoRascunhoIniciadoEvent(clienteId, produtoId));
    } else {
      pedido.adicionarOuAtualizarItem(pedidoItem);
      pedidoRepository.atualizar(pedido);
    }
    pedido.adicionarEvento(new PedidoItemAdicionadoEvent(clienteId, pedido.getId(), produtoId, nome, valorUnitario, quantidade));
  }

  private Boolean validarComando(ApplicationCommand command) {
    if (command.ehValido())
      return true;
    for (String error : command.getValidationResult().getErrors()) {
      eventBus.publicarNotificacao(new DomainNotification(command.getType(), error));
    }
    return false;
  }
}