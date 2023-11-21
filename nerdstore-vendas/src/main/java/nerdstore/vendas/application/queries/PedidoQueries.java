package nerdstore.vendas.application.queries;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nerdstore.vendas.application.queries.viewmodels.CarrinhoViewModel;
import nerdstore.vendas.application.queries.viewmodels.PedidoViewModel;
import nerdstore.vendas.domain.Pedido;
import nerdstore.vendas.domain.PedidoItem;
import nerdstore.vendas.domain.PedidoStatus;
import nerdstore.vendas.application.queries.viewmodels.CarrinhoPagamentoViewModel;
import nerdstore.vendas.application.queries.viewmodels.CarrinhoItemViewModel;
import nerdstore.vendas.data.repository.PedidoRepository;
import org.springframework.stereotype.Component;

@Component
public class PedidoQueries {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoQueries() {
    }

    public CarrinhoViewModel obterCarrinhoCliente(String clienteId) {
        Pedido pedido = pedidoRepository.obterPedidoRascunhoPorClienteId(clienteId);
        if (pedido == null) return null;

        var carrinho = new CarrinhoViewModel(clienteId,pedido.getValorTotal(),pedido.getId(),pedido.getDesconto());

        if (pedido.getVoucher()!=null && pedido.getVoucher().getCodigo()!=null) {
          carrinho.setVoucherCodigo(pedido.getVoucher().getCodigo());
        }

        for (PedidoItem item : pedido.getPedidoItems()) {
            BigDecimal valorTotal = item.getValorUnitario().multiply(new BigDecimal(item.getQuantidade()));
            carrinho.getItems().add(new CarrinhoItemViewModel(item.getProdutoId(),item.getProdutoNome(),item.getQuantidade(),valorTotal));
        }

        return carrinho;
    }

    public List<PedidoViewModel> obterPedidosCliente(String clienteId) {
        List<Pedido> pedidos = pedidoRepository.obterListaPorClienteIdStatusPagoOuCancelado(clienteId);

        var pedidosView = new ArrayList<PedidoViewModel>();

        for (Pedido pedido : pedidos) {
            pedidosView.add(new PedidoViewModel(pedido.getId(),pedido.getValorTotal(),pedido.getPedidoStatus().ordinal(),pedido.getCodigo(),pedido.getDataCadastro()));
        }

        return pedidosView;
    }
}
