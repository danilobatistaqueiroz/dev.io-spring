package nerdstore.vendas.application.events;

import lombok.Getter;
import lombok.Setter;
import nerdstore.core.messages.Event;
import java.math.BigDecimal;

@Getter
public class PedidoItemAdicionadoEvent extends Event {
    private String clienteId;
    private String pedidoId;
    private String produtoId;
    @Setter
    private String produtoNome;
    private BigDecimal valorUnitario;
    private Integer quantidade;

    public PedidoItemAdicionadoEvent(String clienteId, String pedidoId, String produtoId, String produtoNome, BigDecimal valorUnitario, Integer quantidade) {
        setAggregateId(pedidoId);
        this.clienteId = clienteId;
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }
}
