package nerdstore.catalogo.data.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nerdstore.catalogo.domain.Categoria;
import nerdstore.catalogo.domain.Produto;

@Service
public class ProdutoRepository {

  @Autowired
  IProdutoRepository iProdutoRepository;

  @Autowired
  CategoriaRepository categoriaRepository;

  public List<Produto> obterTodos() {
    return iProdutoRepository.findAll();
  }

  public Produto obterPorId(String id) {
    return iProdutoRepository.findById(id).get();
  }

  public Categoria obterPorCategoria(Integer codigo) {
    return categoriaRepository.findByCodigo(codigo);
  }

  public List<Categoria> obterCategorias() {
    var categorias = categoriaRepository.findAll();
    System.out.println(categorias.size());
    return categorias;
  }

  public void adicionar(Produto produto) {
    for(var categoria : produto.getCategorias()){
      var c = categoriaRepository.findByNome(categoria.getNome());
      if(c==null)
        categoriaRepository.insert(categoria);
    }
    iProdutoRepository.insert(produto);
  }

  public void atualizar(Produto produto) {
    iProdutoRepository.save(produto);
  }

  public void adicionar(Categoria categoria) {
  }

  public void atualizar(Categoria categoria) {
  }

}