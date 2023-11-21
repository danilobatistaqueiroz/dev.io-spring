package nerdstore.vendas.domain;

public enum TipoDescontoVoucher {
    Porcentagem(0),
    Valor(1);

    private Integer value;  
    private TipoDescontoVoucher(Integer value){  
        this.value=value;  
    }  
}
