package nerdstore.vendas.application.commands;

import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import nerdstore.core.communication.ApplicationCommand;
import nerdstore.core.communication.ValidationResult;

@ToString
public class AdicionarItemPedidoCommand extends ApplicationCommand {
    @Getter private String clienteId;
    @Getter private String produtoId;
    @Getter private String nome;
    @Getter private Integer quantidade;
    @Getter private BigDecimal valorUnitario;
    
    public AdicionarItemPedidoCommand(String clienteId, String produtoId, String nome, Integer quantidade, BigDecimal valorUnitario) {
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
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

    @Override
    public Boolean ehValido() {
        setValidationResult(adicionarItemPedidoValidation());
        return getValidationResult().isValid();
    }

    public ValidationResult adicionarItemPedidoValidation() {
        ValidationResult validationResult = new ValidationResult();
        if(this.clienteId!=null && this.produtoId!="")
            validationResult.addError("Id do cliente inválido");

        if(this.produtoId!=null && this.produtoId!="")
            validationResult.addError("Id do produto inválido");

        if(this.nome!=null && this.nome!="")
            validationResult.addError("O nome do produto não foi informado");

        if(this.quantidade!=null && this.quantidade!=0)
            validationResult.addError("A quantidade miníma de um item é 1");

        if(this.quantidade<15)
            validationResult.addError("A quantidade máxima de um item é 15");
        
        if(this.valorUnitario.compareTo(new BigDecimal(0))<=0)
            validationResult.addError("O valor do item precisa ser maior que 0");

        return validationResult;

    }

}
