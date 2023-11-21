package nerdstore.catalogo.domain;

import nerdstore.catalogo.data.repository.ProdutoRepository;
import nerdstore.catalogo.domain.events.ProdutoAbaixoEstoqueEvent;
import nerdstore.core.communication.SimpleEventBus;
import nerdstore.core.domainobjects.dto.ListaProdutosPedido;
import nerdstore.core.messages.commonmessages.notifications.DomainNotification;
import nerdstore.core.domainobjects.DomainException;
import nerdstore.core.domainobjects.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private SimpleEventBus eventBus;

    public EstoqueService() {
    }

    public Boolean debitarEstoque(String produtoId, Integer quantidade) throws DomainException {
        if (!debitarItemEstoque(produtoId, quantidade)) return false;
        return true;
    }

    public Boolean debitarListaProdutosPedido(ListaProdutosPedido lista) throws DomainException {
        for(Item item : lista.itens) {
            if (!debitarItemEstoque(item.id, item.quantidade)) return false;
        }
        return true;
    }

    private Boolean debitarItemEstoque(String produtoId, Integer quantidade) throws DomainException {
        var produto = produtoRepository.obterPorId(produtoId);

        if (produto == null) return false;

        if (!produto.possuiEstoque(quantidade)) {
            eventBus.publicarNotificacao(new DomainNotification("Estoque", "Produto - "+produto.nome+" sem estoque"));
            return false;
        }

        produto.debitarEstoque(quantidade);

        // TODO: 10 pode ser parametrizavel em arquivo de configuração
        if (produto.quantidadeEstoque < 10) {
            eventBus.publicarDomainEvent(new ProdutoAbaixoEstoqueEvent(produto.getId(), produto.getQuantidadeEstoque()));
        }

        produtoRepository.atualizar(produto);
        return true;
    }

    public Boolean reporListaProdutosPedido(ListaProdutosPedido lista) {
        for(Item item : lista.itens) {
            reporItemEstoque(item.id, item.quantidade);
        }
        return true;
    }

    public Boolean reporEstoque(String produtoId, Integer quantidade) {
        var sucesso = reporItemEstoque(produtoId, quantidade);
        if (!sucesso) return false;
        return true;
    }

    private Boolean reporItemEstoque(String produtoId, Integer quantidade) {
        var produto = produtoRepository.obterPorId(produtoId);
        if (produto == null) return false;
        produto.reporEstoque(quantidade);
        produtoRepository.atualizar(produto);
        return true;
    }

}