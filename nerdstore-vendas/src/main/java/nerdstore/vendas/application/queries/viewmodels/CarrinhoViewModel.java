package nerdstore.vendas.application.queries.viewmodels;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import lombok.ToString;

@Getter @Setter @ToString
public class CarrinhoViewModel {
  private String pedidoId;
  private String clienteId;
  private BigDecimal subTotal;
  private BigDecimal valorTotal;
  private BigDecimal valorDesconto;
  private String voucherCodigo;
  private List<CarrinhoItemViewModel> items = new ArrayList<CarrinhoItemViewModel>();
  private CarrinhoPagamentoViewModel pagamento;

  public CarrinhoViewModel(String clienteId, BigDecimal valorTotal, String pedidoId, BigDecimal desconto) {
      this.clienteId = clienteId;
      this.valorTotal = valorTotal;
      this.pedidoId = pedidoId;
      this.valorDesconto = desconto;
      this.subTotal = desconto.add(valorTotal);
  }
}