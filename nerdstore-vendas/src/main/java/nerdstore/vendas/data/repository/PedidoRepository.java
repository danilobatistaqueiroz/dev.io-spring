package nerdstore.vendas.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import nerdstore.vendas.domain.Pedido;
import nerdstore.vendas.domain.PedidoItem;
import nerdstore.vendas.domain.PedidoStatus;
import nerdstore.vendas.domain.Voucher;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, String> {

    public Pedido findByClienteIdAndPedidoStatus(String clienteId, PedidoStatus pedidoStatus);

    public List<Pedido> findByClienteId(String clienteId);

    public List<Pedido> findByVoucherId(String voucherId);

    public List<Pedido> findByClienteIdOrderByCodigoDesc(String clienteId);

    public default List<Pedido> obterListaPorClienteIdStatusPagoOuCancelado(String clienteId) {
      var ps = this.findByClienteIdOrderByCodigoDesc(clienteId);
      List<Pedido> pedidos = new ArrayList<Pedido>();
      for(Pedido p : ps){
        if(p.getPedidoStatus() == PedidoStatus.Pago || p.getPedidoStatus() == PedidoStatus.Cancelado){
          pedidos.add(p);
        }
      }
      return pedidos;
    }

    public default Pedido obterPedidoRascunhoPorClienteId(String clienteId){
      return findByClienteIdAndPedidoStatus(clienteId, PedidoStatus.Rascunho);
    }

    public default Optional<Pedido> obterPorId(String id) {
      return findById(id);
    }

    public default List<Pedido> obterListaPorClienteId(String clienteId) {
      return findByClienteId(clienteId);
    }

    public default void adicionar(Pedido pedido) {
      insert(pedido);
    }

    public default void atualizar(Pedido pedido) {
      save(pedido);
    }

    public default List<PedidoItem> obterItemPorId(String id) {
      return findById(id).get().getPedidoItems();
    }

    public default PedidoItem obterItemPorPedido(String pedidoId, String produtoId) {
      PedidoItem pedidoItem = findById(pedidoId).get().getPedidoItems().stream().filter(pi -> pi.getProdutoId() == produtoId).findFirst().get();
      return pedidoItem;
    }

}