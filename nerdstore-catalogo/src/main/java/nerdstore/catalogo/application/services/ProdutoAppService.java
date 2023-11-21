package nerdstore.catalogo.application.services;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import nerdstore.catalogo.application.automapper.Mapper;
import nerdstore.catalogo.application.viewmodels.ProdutoViewModel;
import nerdstore.catalogo.application.viewmodels.CategoriaViewModel;
import nerdstore.catalogo.data.repository.ProdutoRepository;
import nerdstore.catalogo.domain.EstoqueService;
import nerdstore.catalogo.domain.Produto;
import nerdstore.core.domainobjects.DomainException;

@Service
public class ProdutoAppService {
  
  @Autowired
  ProdutoRepository produtoRepository;
  
  @Autowired
  EstoqueService estoqueService;

  @Autowired
  Mapper mapper;

  public ProdutoViewModel obterPorId(String id) {
    var produto = produtoRepository.obterPorId(id);
    var produtoViewModel = mapper.map(produto);
    return produtoViewModel;
  }

  public Produto obterProdutoPorId(String id) {
    var produto = produtoRepository.obterPorId(id);
    return produto;
  }

  public List<ProdutoViewModel> obterTodos() {
    var produtos = produtoRepository.obterTodos();
    List<ProdutoViewModel> produtosViewModel = new ArrayList<ProdutoViewModel>();
    for(var produto : produtos){
      var produtoViewModel = mapper.map(produto);
      produtosViewModel.add(produtoViewModel);
    }
    return produtosViewModel;
  }
  
  public List<CategoriaViewModel> obterCategorias() {
      return mapper.map(produtoRepository.obterCategorias());
  }

  public void adicionarProduto(ProdutoViewModel produtoViewModel) throws DomainException {
    produtoViewModel.dataCadastro=LocalDateTime.now();
    var produto = mapper.map(produtoViewModel);
    produtoRepository.adicionar(produto);
  }
  
  public void atualizarProduto(ProdutoViewModel produtoViewModel) throws DomainException {
    var produto = mapper.map(produtoViewModel);
    produto.setId(produtoViewModel.produtoId);
    var p = produtoRepository.obterPorId(produtoViewModel.produtoId);
    produto.setQuantidadeEstoque(p.getQuantidadeEstoque());
    produtoRepository.atualizar(produto);
  }

  public ProdutoViewModel debitarEstoque(String id, Integer quantidade) throws DomainException {
      if (!estoqueService.debitarEstoque(id, quantidade)) {
          throw new DomainException("Falha ao debitar estoque");
      }
      return mapper.map(produtoRepository.obterPorId(id));
  }

  public ProdutoViewModel reporEstoque(String id, Integer quantidade) throws DomainException {
      if (!estoqueService.reporEstoque(id, quantidade)) {
          throw new DomainException("Falha ao repor estoque");
      }
      return mapper.map(produtoRepository.obterPorId(id));
  }

}