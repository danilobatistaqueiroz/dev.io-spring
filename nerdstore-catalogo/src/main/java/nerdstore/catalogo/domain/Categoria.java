package nerdstore.catalogo.domain;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import nerdstore.core.domainobjects.DomainException;
import nerdstore.core.domainobjects.Entity;
import nerdstore.core.domainobjects.Validacoes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Categoria extends Entity {

  @Id
  private String id;
  private String nome;
  private Integer codigo;
  
  @DocumentReference
  @Transient
  @BsonIgnore
  List<Produto> produtos;

  public Categoria(){}

  Categoria(String nome, Integer codigo) throws DomainException {
    this.nome = nome;
    this.codigo = codigo;
    validar();
  }

  public String toString() {
    return nome+" - "+codigo;
  }

  void validar() throws DomainException {
    Validacoes.validarSeVazio(nome, "O campo Nome da categoria não pode estar vazio");
    Validacoes.validarSeIgual(codigo, 0, "O campo Codigo não pode ser 0");
  }
}
