package nerdstore.catalogo.domain;

import java.math.BigDecimal;
import java.text.MessageFormat;

import nerdstore.core.domainobjects.DomainException;
import nerdstore.core.domainobjects.Validacoes;
import lombok.Getter;

@Getter
public class Dimensoes {

  private BigDecimal altura;
  private BigDecimal largura;
  private BigDecimal profundidade;

  public Dimensoes(BigDecimal altura, BigDecimal largura, BigDecimal profundidade) throws DomainException {
      Validacoes.validarSeMenorQue(altura, new BigDecimal(1), "O campo Altura não pode ser menor ou igual a 0");
      Validacoes.validarSeMenorQue(largura, new BigDecimal(1), "O campo Largura não pode ser menor ou igual a 0");
      Validacoes.validarSeMenorQue(profundidade, new BigDecimal(1), "O campo Profundidade não pode ser menor ou igual a 0");

      this.altura = altura;
      this.largura = largura;
      this.profundidade = profundidade;
  }

  String descricaoFormatada() {
    return MessageFormat.format("LxAxP: {0} x {1} x {2}",largura,altura,profundidade);
  }

  public String toString() {
    return descricaoFormatada();
  }
}