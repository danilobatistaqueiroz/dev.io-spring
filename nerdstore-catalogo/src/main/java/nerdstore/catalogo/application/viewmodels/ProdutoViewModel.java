package nerdstore.catalogo.application.viewmodels;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ProdutoViewModel {
  public String produtoId;
  public String categoriaId;
  public String nome;
  public String descricao;
  public Boolean ativo;
  public BigDecimal valor;
  public LocalDateTime dataCadastro;
  public String imagem;
  public Integer quantidadeEstoque;
  public BigDecimal altura;
  public BigDecimal largura;
  public BigDecimal profundidade;
  public List<CategoriaViewModel> categorias = new ArrayList<CategoriaViewModel>();
}
