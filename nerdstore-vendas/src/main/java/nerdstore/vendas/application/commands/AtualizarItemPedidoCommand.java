package nerdstore.vendas.application.commands;

import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import nerdstore.core.communication.ApplicationCommand;
import nerdstore.core.communication.ValidationResult;

@ToString
public class AtualizarItemPedidoCommand extends ApplicationCommand {
    @Getter private String clienteId;
    @Getter private String produtoId;
    @Getter private Integer quantidade;
    
    public AtualizarItemPedidoCommand(String clienteId, String produtoId, Integer quantidade) {
    	this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Boolean ehValido() {
        setValidationResult(atualizarItemPedidoValidation());
        return getValidationResult().isValid();
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

    private ValidationResult atualizarItemPedidoValidation() {
        ValidationResult validationResult = new ValidationResult();
        if(this.clienteId!=null && this.produtoId!="")
            validationResult.addError("Id do cliente inválido");

        if(this.produtoId!=null && this.produtoId!="")
            validationResult.addError("Id do produto inválido");

        if(this.quantidade!=null && this.quantidade!=0)
            validationResult.addError("A quantidade miníma de um item é 1");

        if(this.quantidade<15)
            validationResult.addError("A quantidade máxima de um item é 15");
        
        return validationResult;
    }

}
