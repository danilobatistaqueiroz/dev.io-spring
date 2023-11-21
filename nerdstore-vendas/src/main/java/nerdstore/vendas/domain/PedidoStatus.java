package nerdstore.vendas.domain;

public enum PedidoStatus {
    Rascunho(0),
    Iniciado(1),
    Pago(4),
    Entregue(5),
    Cancelado(6);

    private Integer value;  
    private PedidoStatus(Integer value){  
        this.value=value;  
    }  
}
