package nerdstore.webapp.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import nerdstore.catalogo.application.automapper.Mapper;
import nerdstore.catalogo.application.services.ProdutoAppService;
import nerdstore.catalogo.application.viewmodels.ProdutoViewModel;
import nerdstore.catalogo.data.repository.ProdutoRepository;
import nerdstore.catalogo.domain.Produto;
import nerdstore.core.domainobjects.DomainException;
import nerdstore.catalogo.domain.Categoria;
import nerdstore.catalogo.domain.Dimensoes;

import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"isSignedIn","username","title"})
public class VitrineController extends ControllerBase {

    @Autowired
    private ProdutoAppService produtoAppService;

    @Autowired
    private Mapper mapper;

    @Autowired
    ProdutoRepository produtoRepository;

    public VitrineController() {
    }

    @GetMapping("/vitrine/index")
    public String index(Model model) {
        List<ProdutoViewModel> produtosViewModel = produtoAppService.obterTodos(); //OrderBy CategoriaId
        model.addAttribute("produtos", produtosViewModel);
        model.addAttribute("title", "Vitrine");
        model.addAttribute("isSignedIn",true);
        model.addAttribute("username","Danilo");
        return "vitrine/index";
    }

    @GetMapping("/vitrine/produto-detalhe/{id}")
    public String produtoDetalhe(@PathVariable("id") String id, Model model) {
        ProdutoViewModel produtoViewModel = produtoAppService.obterPorId(id);
        model.addAttribute("produto", produtoViewModel);
        model.addAttribute("title", "Produto Detalhe");
        model.addAttribute("isSignedIn",true);
        model.addAttribute("username","Danilo");
        var carrinhoItem = new CarrinhoItem();
        model.addAttribute("carrinhoItem",carrinhoItem);
        if (operacaoValida()==false) {
          model.addAttribute("title", "Adicionar Item Pedido");
          model.addAttribute("erros", obterMensagensErro());
          limparNotificacoes();
        }
        return "vitrine/produto-detalhe";
    }

}