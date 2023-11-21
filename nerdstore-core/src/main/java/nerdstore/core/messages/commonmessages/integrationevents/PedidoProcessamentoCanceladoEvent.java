package nerdstore.core.messages.commonmessages.integrationevents;

import nerdstore.core.domainobjects.dto.ListaProdutosPedido;
import lombok.Getter;

@Getter
public class PedidoProcessamentoCanceladoEvent extends IntegrationEvent {
    private String pedidoId;
    private String clienteId;
    private ListaProdutosPedido produtosPedido;

    public PedidoProcessamentoCanceladoEvent(String pedidoId, String clienteId, ListaProdutosPedido produtosPedido) {
        this.setAggregateId(pedidoId);
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.produtosPedido = produtosPedido;
    }
}