package nerdstore.core.messages.commonmessages.integrationevents;

import java.math.BigDecimal;
import lombok.Getter;
import nerdstore.core.domainobjects.dto.ListaProdutosPedido;

@Getter
public class PedidoEstoqueConfirmadoEvent extends IntegrationEvent {
    private String pedidoId;
    private String clienteId;
    private BigDecimal total;
    private ListaProdutosPedido produtosPedido;
    private String nomeCartao;
    private String numeroCartao;
    private String expiracaoCartao;
    private String cvvCartao;

    public PedidoEstoqueConfirmadoEvent(String pedidoId, String clienteId, BigDecimal total, ListaProdutosPedido produtosPedido, String nomeCartao, String numeroCartao, String expiracaoCartao, String cvvCartao) {
        this.setAggregateId(pedidoId);
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.total = total;
        this.produtosPedido = produtosPedido;
        this.nomeCartao = nomeCartao;
        this.numeroCartao = numeroCartao;
        this.expiracaoCartao = expiracaoCartao;
        this.cvvCartao = cvvCartao;
    }
}