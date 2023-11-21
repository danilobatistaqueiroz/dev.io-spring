package nerdstore.webapp.mvc.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import nerdstore.catalogo.application.services.ProdutoAppService;
import nerdstore.catalogo.application.viewmodels.ProdutoViewModel;
import nerdstore.core.domainobjects.DomainException;
import nerdstore.webapp.mvc.controllers.ControllerBase;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin-produtos")
public class AdminProdutosController extends ControllerBase {
    
    @Autowired
    private ProdutoAppService produtoAppService;

    public AdminProdutosController() {
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<ProdutoViewModel> produtos = produtoAppService.obterTodos();
        model.addAttribute("produtos", produtos);
        return "admin-produtos/index.html";
    }

    @GetMapping("/novo-produto")
    public String novoProduto(Model model) {
        ProdutoViewModel produto = popularCategorias(new ProdutoViewModel());
        model.addAttribute("produto",produto);
        model.addAttribute("title", "NovoProduto");
        var categorias = produtoAppService.obterCategorias();
        model.addAttribute("categorias",categorias);
        return "/admin-produtos/novo-produto.html";
    }

    @PostMapping("/novo-produto")
    public String novoProduto(@Valid ProdutoViewModel produtoViewModel, BindingResult bindingResult, Model model) throws DomainException {
        if (bindingResult.hasErrors()) {
          model.addAttribute("produto", popularCategorias(produtoViewModel));
          model.addAttribute("title", "NovoProduto");
          return "/admin-produtos/novo-produto.html";
        }
        produtoAppService.adicionarProduto(produtoViewModel);
        return "/admin-produtos/index.html";
    }

    @GetMapping("/atualizar-produto/{id}")
    public String atualizarProduto(@PathVariable("id") String id, Model model) {
        model.addAttribute("produto", popularCategorias(produtoAppService.obterPorId(id)));
        model.addAttribute("categorias", produtoAppService.obterCategorias());
        return "/admin-produtos/atualizar-produto.html";
    }

    @PostMapping("/atualizar-produto")
    public String atualizarProduto(ProdutoViewModel produtoViewModel, BindingResult bindingResult, Model model) throws DomainException {
        if (bindingResult.hasErrors()) {
          model.addAttribute("produto", popularCategorias(produtoViewModel));
          model.addAttribute("categorias", produtoAppService.obterCategorias());
          return "/admin-produtos/atualizar-produto.html";
        }
        produtoAppService.atualizarProduto(produtoViewModel);
        return "/admin-produtos/index.html";
    }

    @GetMapping("/produtos-atualizar-estoque/{id}")
    public String atualizarEstoque(@PathVariable("id") String id, Model model) {
        model.addAttribute("produto", produtoAppService.obterPorId(id));
        model.addAttribute("title", "Editar Produto");
        return "/admin-produtos/estoque.html";
    }

    @PostMapping("/produtos-atualizar-estoque")
    public String atualizarEstoque(ProdutoEstoque produtoEstoque, Model model) throws DomainException {
        if (produtoEstoque.getQuantidade() > 0) {
            produtoAppService.reporEstoque(produtoEstoque.getId(), produtoEstoque.getQuantidade());
        }
        else {
            produtoAppService.debitarEstoque(produtoEstoque.getId(), produtoEstoque.getQuantidade());
        }
        model.addAttribute("produtos", produtoAppService.obterTodos());
        return "/admin-produtos/index.html";
    }

    private ProdutoViewModel popularCategorias(ProdutoViewModel produto) {
        produto.categorias = produtoAppService.obterCategorias();
        return produto;
    }
}