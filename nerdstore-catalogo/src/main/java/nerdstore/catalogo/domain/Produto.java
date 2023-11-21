package nerdstore.catalogo.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import nerdstore.core.domainobjects.DomainException;
import nerdstore.core.domainobjects.Entity;
import nerdstore.core.domainobjects.IAggregateRoot;
import nerdstore.core.domainobjects.Validacoes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;

@Getter
@Setter
@ToString
public class Produto extends Entity implements IAggregateRoot {
  @Id
  String id;
  
  String nome;
  String descricao;
  Boolean ativo;
  BigDecimal valor;
  LocalDateTime dataCadastro;
  String imagem;
  Integer quantidadeEstoque;
  Dimensoes dimensoes;
  
  @DocumentReference
  List<Categoria> categorias;

  private Produto(){}

  public Produto(String nome, String descricao, Boolean ativo, BigDecimal valor, Integer quantidadeEstoque, List<Categoria> categorias, LocalDateTime dataCadastro, String imagem, Dimensoes dimensoes) throws DomainException {
      this.categorias = categorias;
      this.nome = nome;
      this.descricao = descricao;
      this.ativo = ativo;
      this.quantidadeEstoque = quantidadeEstoque;
      this.valor = valor;
      this.dataCadastro = dataCadastro;
      this.imagem = imagem;
      this.dimensoes = dimensoes;

      this.validar();
  }
  
  public void ativar() {
	  ativo = true;
  }

  public void desativar() {
	  ativo = false;
  }

  public void alterarDescricao(String descricao) throws DomainException {
      Validacoes.validarSeVazio(descricao, "O campo Descricao do produto não pode estar vazio");
      this.descricao = descricao;
  }
  
  public void reporEstoque(Integer quantidade) {
      quantidadeEstoque += quantidade;
  }
  
  public void debitarEstoque(Integer quantidade) throws DomainException {
      if (quantidade < 0) quantidade *= -1;
      if (!possuiEstoque(quantidade)) throw new DomainException("Estoque insuficiente");
      quantidadeEstoque -= quantidade;
  }
  
  public Boolean possuiEstoque(Integer quantidade) {
      return quantidadeEstoque >= quantidade;
  }

  void validar() throws DomainException {
      Validacoes.validarSeVazio(nome, "O campo Nome do produto não pode estar vazio");
      Validacoes.validarSeVazio(descricao, "O campo Descricao do produto não pode estar vazio");
      //Validacoes.validarSeIgual(categoriaId, null, "O campo CategoriaId do produto não pode estar vazio");
      Validacoes.validarSeMenorQue(valor, new BigDecimal(1), "O campo Valor do produto não pode se menor igual a 0");
      Validacoes.validarSeVazio(imagem, "O campo Imagem do produto não pode estar vazio");
  }


}
