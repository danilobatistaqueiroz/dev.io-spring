package nerdstore.vendas.application.events;

import nerdstore.core.messages.Event;
import lombok.Getter;

@Getter
public class PedidoRascunhoIniciadoEvent extends Event {
    private String clienteId;
    private String pedidoId;

    public PedidoRascunhoIniciadoEvent(String clienteId, String pedidoId) {
        this.setAggregateId(pedidoId);
        this.clienteId = clienteId;
        this.pedidoId = pedidoId;
    }
}
