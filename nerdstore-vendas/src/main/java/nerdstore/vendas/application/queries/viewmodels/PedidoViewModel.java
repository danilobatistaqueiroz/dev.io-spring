package nerdstore.vendas.application.queries.viewmodels;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
public class PedidoViewModel {
    public String id;
    public  Integer codigo;
    public  BigDecimal valorTotal;
    public  LocalDateTime dataCadastro;
    public  Integer pedidoStatus;

    public PedidoViewModel(String pedidoId, BigDecimal valorTotal, Integer pedidoStatus, Integer pedidoCodigo, LocalDateTime dataCadastro) {
        this.id = pedidoId;
        this.valorTotal = valorTotal;
        this.pedidoStatus = pedidoStatus;
        this.codigo = pedidoCodigo;
        this.dataCadastro = dataCadastro;
    }
}
