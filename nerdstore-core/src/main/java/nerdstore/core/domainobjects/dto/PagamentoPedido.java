package nerdstore.core.domainobjects.dto;

import java.math.BigDecimal;

public class PagamentoPedido {
  public String pedidoId;
  public String clienteId;
  public BigDecimal total;
  public String nomeCartao;
  public String numeroCartao;
  public String expiracaoCartao;
  public String cvvCartao;
}