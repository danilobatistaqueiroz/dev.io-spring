package nerdstore.catalogo.application.automapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import nerdstore.catalogo.application.viewmodels.*;
import nerdstore.catalogo.domain.Categoria;
import nerdstore.catalogo.domain.Dimensoes;
import nerdstore.catalogo.domain.Produto;
import nerdstore.core.domainobjects.DomainException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
  private CategoriaViewModel map(Categoria categoria) {
    CategoriaViewModel viewModel = new CategoriaViewModel();
    viewModel.codigo = categoria.getCodigo();
    viewModel.nome = categoria.getNome();
    return viewModel;
  }
  public ProdutoViewModel map(Produto produto) {
    List<CategoriaViewModel> categorias = produto.getCategorias().stream().map(c -> map(c)).collect(Collectors.toList());
    ProdutoViewModel viewModel = new ProdutoViewModel();
    viewModel.produtoId = produto.getId();
    viewModel.categorias = categorias;
    viewModel.nome = produto.getNome();
    viewModel.descricao = produto.getDescricao();
    viewModel.ativo = produto.getAtivo();
    viewModel.valor = produto.getValor();
    viewModel.dataCadastro = produto.getDataCadastro();
    viewModel.imagem = produto.getImagem();
    viewModel.quantidadeEstoque = produto.getQuantidadeEstoque();
    viewModel.altura = produto.getDimensoes().getAltura();
    viewModel.largura = produto.getDimensoes().getLargura();
    viewModel.profundidade = produto.getDimensoes().getProfundidade();
    return viewModel;
  }
  private Categoria map(CategoriaViewModel viewModel) {
    Categoria categoria = new Categoria();
    categoria.setCodigo(viewModel.codigo);
    categoria.setNome(viewModel.nome);
    return categoria;
  }
  public Produto map(ProdutoViewModel viewModel) throws DomainException {
    List<Categoria> categorias = viewModel.categorias.stream().map(c -> map(c)).collect(Collectors.toList());
    String nome = viewModel.nome;
    String descricao = viewModel.descricao;
    Boolean ativo = viewModel.ativo;
    BigDecimal valor = viewModel.valor;
    LocalDateTime dataCadastro = viewModel.dataCadastro;
    Integer quantidadeEstoque = viewModel.quantidadeEstoque;
    String imagem = viewModel.imagem;
    BigDecimal altura = viewModel.altura;
    BigDecimal largura = viewModel.largura;
    BigDecimal profundidade = viewModel.profundidade;
    Dimensoes dimensoes = new Dimensoes(altura,largura,profundidade);
    Produto produto = new Produto(nome, descricao, ativo, valor, quantidadeEstoque, categorias, dataCadastro, imagem, dimensoes);
    return produto;
  }
  public List<CategoriaViewModel> map(List<Categoria> categorias) {
    List<CategoriaViewModel> viewModels = categorias.stream().map(c -> map(c)).collect(Collectors.toList());
    return viewModels;
  }
}
