package nerdstore.vendas.application.commands;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import nerdstore.core.communication.ApplicationCommand;

@Getter @Setter @ToString
public class IniciarPedidoCommand extends ApplicationCommand {
    private String pedidoId;
    private String clienteId;
    private BigDecimal total;
    private String nomeCartao;
    private String numeroCartao;
    private String expiracaoCartao;
    private String cvvCartao;

    public IniciarPedidoCommand(String pedidoId, String clienteId, BigDecimal total, String nomeCartao, String numeroCartao, String expiracaoCartao, String cvvCartao) {
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.total = total;
        this.nomeCartao = nomeCartao;
        this.numeroCartao = numeroCartao;
        this.expiracaoCartao = expiracaoCartao;
        this.cvvCartao = cvvCartao;
    }

    @Override
    public Boolean ehValido() {
        var validationResult = this.iniciarPedidoValidation();
        return validationResult;
    }

    private Boolean iniciarPedidoValidation() {
        if(this.clienteId!=null)
            this.getValidationResult().addError("Id do cliente inválido");

        if(this.pedidoId!=null)
            this.getValidationResult().addError("Id do pedido inválido");

        if(this.nomeCartao.isEmpty())
            this.getValidationResult().addError("O nome no cartão não foi informado");

        //if(this.numeroCartao.creditCard())
        //    this.validationResult.addError("Número de cartão de crédito inválido");

        if(this.expiracaoCartao.isEmpty())
            this.getValidationResult().addError("Data de expiração não informada");

        //if(this.cvvCartao.length(3, 4))
        //    this.validationResult.addError("O CVV não foi preenchido corretamente");

        return true;
    }

    @Override
    public String getFieldValue(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = this.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        String value = String.valueOf(field.get(this));
        return value;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

}