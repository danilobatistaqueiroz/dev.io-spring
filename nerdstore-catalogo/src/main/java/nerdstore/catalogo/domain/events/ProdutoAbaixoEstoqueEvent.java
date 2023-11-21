package nerdstore.catalogo.domain.events;

import lombok.Getter;
import nerdstore.core.messages.commonmessages.domainevents.DomainEvent;

public class ProdutoAbaixoEstoqueEvent extends DomainEvent {
    @Getter
    public Integer quantidadeRestante;

    public ProdutoAbaixoEstoqueEvent(String aggregateId, Integer quantidadeRestante) {
        super(aggregateId);
        this.quantidadeRestante = quantidadeRestante;
    }
}
