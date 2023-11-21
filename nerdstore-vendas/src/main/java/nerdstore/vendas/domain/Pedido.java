package nerdstore.vendas.domain;

import nerdstore.core.communication.ValidationResult;
import nerdstore.core.domainobjects.DomainException;
import nerdstore.core.domainobjects.Entity;
import nerdstore.core.domainobjects.IAggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@AllArgsConstructor
@ToString
@Getter
public class Pedido extends Entity implements IAggregateRoot {
    @Id
    private String id;
    private Integer codigo;
    private String clienteId;
    private Boolean voucherUtilizado;
    private BigDecimal desconto;
    private BigDecimal valorTotal;
    private LocalDateTime dataCadastro;
    private PedidoStatus pedidoStatus;
    
    @DocumentReference
    private Voucher voucher;
    
    protected Pedido() {
      pedidoItems = new ArrayList<PedidoItem>();
    }

    protected Pedido(String clienteId) {
      this.clienteId = clienteId;
    }
    
    public BigDecimal getValorTotal() {
      return valorTotal;
    }
    
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<PedidoItem> pedidoItems;

    public List<PedidoItem> getPedidoItems() {
      return new ArrayList<PedidoItem>(pedidoItems);
    }

    public ValidationResult aplicarVoucher(Voucher voucher) {
      var validationResult = voucher.validarSeAplicavel();
      if (!validationResult.isValid()) return validationResult;

      this.voucher = voucher;
      voucherUtilizado = true;
      calcularValorPedido();

      return validationResult;
    }

    public void calcularValorPedido() {
      valorTotal = pedidoItems.stream().map(p -> p.calcularValor()).reduce(new BigDecimal(0), (subtotal, valor) -> subtotal.add(valor));
      calcularValorTotalDesconto();
    }

    public void calcularValorTotalDesconto() {
      if (!voucherUtilizado) return;

      BigDecimal desconto = new BigDecimal(0);
      var valor = valorTotal;

      if (voucher.getTipoDescontoVoucher() == TipoDescontoVoucher.Porcentagem) {
        if (voucher.getPercentual().intValue()>0) {
          desconto = (valor.multiply(voucher.getPercentual())).divide(new BigDecimal(100));
          valor=valor.subtract(desconto);
        }
      } else {
        if (voucher.getValorDesconto().intValue()>0) {
          desconto = voucher.getValorDesconto();
          valor=valor.subtract(desconto);
        }
      }
      valorTotal = valor.intValue() < 0 ? new BigDecimal(0) : valor;
      this.desconto = desconto;
    }

    public Boolean pedidoItemExistente(PedidoItem item) {
      return !pedidoItems.stream().filter(p -> p.getProdutoId() == item.getProdutoId()).collect(Collectors.toList()).isEmpty();
    }

    public void adicionarOuAtualizarItem(PedidoItem item) {
      if (!item.ehValido()) return;

      if (pedidoItemExistente(item)) {
        final PedidoItem pedidoItem = item;
        var itemExistente = pedidoItems.stream().filter(p -> p.getProdutoId() == pedidoItem.getProdutoId()).findFirst().get();
        itemExistente.adicionarUnidades(item.getQuantidade());
        item = itemExistente;

        pedidoItems.remove(itemExistente);
      }

      item.calcularValor();
      pedidoItems.add(item);

      calcularValorPedido();
    }

    public void removerItem(PedidoItem item) throws DomainException {
      if (!item.ehValido()) return;

      var itemExistente = pedidoItems.stream().filter(p -> p.getProdutoId() == item.getProdutoId()).findFirst().get();

      if (itemExistente == null) throw new DomainException("O item não pertence ao pedido");
      pedidoItems.remove(itemExistente);

      calcularValorPedido();
    }

    public void atualizarItem(PedidoItem item) throws DomainException {
      if (!item.ehValido()) return;

      var itemExistente = pedidoItems.stream().filter(p -> p.getProdutoId() == item.getProdutoId()).findFirst().get();

      if (itemExistente == null) throw new DomainException("O item não pertence ao pedido");

      pedidoItems.remove(itemExistente);
      pedidoItems.add(item);

      calcularValorPedido();
    }

    public void atualizarUnidades(PedidoItem item, Integer unidades) throws DomainException {
      item.atualizarUnidades(unidades);
      atualizarItem(item);
    }

    public void tornarRascunho() {
      pedidoStatus = PedidoStatus.Rascunho;
    }

    public void iniciarPedido() {
      pedidoStatus = PedidoStatus.Iniciado;
    }

    public void finalizarPedido() {
      pedidoStatus = PedidoStatus.Pago;
    }

    public void cancelarPedido() {
      pedidoStatus = PedidoStatus.Cancelado;
    }

    public static Pedido novoPedidoRascunho(String clienteId) {
      var pedido = new Pedido(clienteId);
      pedido.tornarRascunho();
      return pedido;
    }

}