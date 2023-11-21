package nerdstore.core.messages.commonmessages.integrationevents;

import lombok.Getter;
import java.math.BigDecimal;
import nerdstore.core.domainobjects.dto.ListaProdutosPedido;

@Getter
public class PedidoIniciadoEvent extends IntegrationEvent {
    private String pedidoId;
    private String clienteId;
    private BigDecimal total;
    private ListaProdutosPedido produtosPedido;
    private String nomeCartao;
    private String numeroCartao;
    private String expiracaoCartao;
    private String cvvCartao;

    public PedidoIniciadoEvent(String pedidoId, String clienteId, ListaProdutosPedido itens, BigDecimal total, String nomeCartao, String numeroCartao, String expiracaoCartao, String cvvCartao) {
        this.setAggregateId(pedidoId);
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.produtosPedido = itens;
        this.total = total;
        this.nomeCartao = nomeCartao;
        this.numeroCartao = numeroCartao;
        this.expiracaoCartao = expiracaoCartao;
        this.cvvCartao = cvvCartao;
    }
}