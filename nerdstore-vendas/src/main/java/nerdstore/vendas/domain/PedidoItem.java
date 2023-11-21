package nerdstore.vendas.domain;

import java.math.BigDecimal;

import nerdstore.core.domainobjects.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoItem {
  private String produtoId;
  private String produtoNome;
  private Integer quantidade;
  private BigDecimal valorUnitario;

  private Pedido pedido;

  public PedidoItem(String produtoId, String produtoNome, Integer quantidade, BigDecimal valorUnitario) {
    this.produtoId = produtoId;
    this.produtoNome = produtoNome;
    this.quantidade = quantidade;
    this.valorUnitario = valorUnitario;
  }

  public BigDecimal calcularValor() {
      return this.valorUnitario.multiply(new BigDecimal(this.quantidade));
  }

  void adicionarUnidades(Integer unidades) {
      this.quantidade += unidades;
  }

  void atualizarUnidades(Integer unidades) {
      this.quantidade = unidades;
  }

  public Boolean ehValido() {
      return true;
  }
}
