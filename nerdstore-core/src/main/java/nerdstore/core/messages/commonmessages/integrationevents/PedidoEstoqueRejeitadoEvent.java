package nerdstore.core.messages.commonmessages.integrationevents;

import lombok.Getter;

@Getter
public class PedidoEstoqueRejeitadoEvent extends IntegrationEvent {
    private String pedidoId;
    private String clienteId;

    public PedidoEstoqueRejeitadoEvent(String pedidoId, String clienteId) {
        this.setAggregateId(pedidoId);
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
    }
}