package nerdstore.vendas.application.queries.viewmodels;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CarrinhoItemViewModel {
  private String produtoId;
  private String produtoNome;
  private Integer quantidade;
  private BigDecimal valorUnitario;
  private BigDecimal valorTotal;

  public CarrinhoItemViewModel(String produtoId, String produtoNome, Integer quantidade, BigDecimal valorUnitario) {
    this.produtoId = produtoId;
    this.produtoNome = produtoNome;
    this.quantidade = quantidade;
    this.valorUnitario = valorUnitario;
    this.valorTotal = valorUnitario.multiply(new BigDecimal(quantidade));
  }

}