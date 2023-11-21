package nerdstore.webapp.mvc.controllers.admin;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

@Getter @Setter @ToString
public class ProdutoEstoque {
  private String id;
  private Integer quantidade;
}
