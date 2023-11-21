package nerdstore.catalogo.domain.handlers;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import nerdstore.catalogo.data.repository.ProdutoRepository;
import nerdstore.catalogo.domain.EstoqueService;
import nerdstore.core.communication.SimpleEventBus;
import nerdstore.core.domainobjects.DomainException;
import nerdstore.catalogo.domain.events.ProdutoAbaixoEstoqueEvent;
import nerdstore.core.messages.commonmessages.integrationevents.PedidoEstoqueConfirmadoEvent;
import nerdstore.core.messages.commonmessages.integrationevents.PedidoEstoqueRejeitadoEvent;
import nerdstore.core.messages.commonmessages.integrationevents.PedidoIniciadoEvent;
import nerdstore.core.messages.commonmessages.integrationevents.PedidoProcessamentoCanceladoEvent;

@Service
public class ProdutoEventHandler {

  @Autowired
  private ProdutoRepository produtoRepository;

  @Autowired
  private EstoqueService estoqueService;

  @Autowired
  private SimpleEventBus eventBus;

  public ProdutoEventHandler() {
  }

  public void handle(ProdutoAbaixoEstoqueEvent mensagem) {
      var produto = produtoRepository.obterPorId(mensagem.getAggregateId());
      // Enviar um email para aquisicao de mais produtos.
  }

  public void handle(PedidoIniciadoEvent message) throws DomainException {
      var result = estoqueService.debitarListaProdutosPedido(message.getProdutosPedido());
      if (result) {
          eventBus.publicarEvento(new PedidoEstoqueConfirmadoEvent(message.getPedidoId(), message.getClienteId(), message.getTotal(), message.getProdutosPedido(), message.getNomeCartao(), message.getNumeroCartao(), message.getExpiracaoCartao(), message.getCvvCartao()));
      } else {
          eventBus.publicarEvento(new PedidoEstoqueRejeitadoEvent(message.getPedidoId(), message.getClienteId()));
      }
  }

  public void handle(PedidoProcessamentoCanceladoEvent message) {
      estoqueService.reporListaProdutosPedido(message.getProdutosPedido());
  }

}