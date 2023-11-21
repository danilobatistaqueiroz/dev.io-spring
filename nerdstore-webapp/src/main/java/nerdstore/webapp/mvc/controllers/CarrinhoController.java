package nerdstore.webapp.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.List;

import nerdstore.core.communication.SimpleEventBus;
import nerdstore.catalogo.application.services.ProdutoAppService;
import nerdstore.catalogo.application.viewmodels.ProdutoViewModel;
import nerdstore.vendas.application.commands.AdicionarItemPedidoCommand;
import nerdstore.vendas.application.commands.AtualizarItemPedidoCommand;
import nerdstore.vendas.application.queries.PedidoQueries;
import nerdstore.vendas.application.queries.viewmodels.CarrinhoViewModel;
import nerdstore.catalogo.carrinho.Carrinho;
import nerdstore.catalogo.domain.Produto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class CarrinhoController extends ControllerBase {

    @Autowired
    SimpleEventBus eventBus;
    @Autowired
    ProdutoAppService produtoAppService;
    @Autowired
    PedidoQueries pedidoQueries;

    @GetMapping(value = {"/carrinho"})
    public String carrinho(Model model) {
        model.addAttribute("title", "Carrinho");
        model.addAttribute("voucherCodigo", 3842);
        model.addAttribute("valorDesconto", 111);
        model.addAttribute("carrinho", new Carrinho("NerdUser","nerduser@gmail.com"));
        return "carrinho/index.html";
    }

    @PostMapping(value = {"/iniciar-pedido"})
    public String carrinho(@Valid Carrinho carrinho, Model model, BindingResult bindingResult) {
        //userValidator.validate(carrinhoForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Iniciar Pedido");
            return "carrinho/index";
        }

        return "carrinho/resumo-da-compra";
    }

    @PostMapping(value = {"/carrinho/adicionar-item"})
    public ResponseEntity<String> adicionarItem(@Valid CarrinhoItem carrinhoItem, Model model, BindingResult bindingResult, HttpServletResponse response) throws IOException {
        ProdutoViewModel produto = produtoAppService.obterPorId(carrinhoItem.getId());
        if (produto == null)
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

        if (produto.quantidadeEstoque < carrinhoItem.getQuantidade()) {
            model.addAttribute("erro","Produto com estoque insuficiente");
            ResponseEntity e = new ResponseEntity<String>(HttpStatus.OK);
            return e.ok("vitrine/produto-detalhe"+carrinhoItem.getId());
        }
        var command = new AdicionarItemPedidoCommand(clienteId, produto.produtoId, produto.nome, carrinhoItem.getQuantidade(), produto.valor);
        eventBus.enviarComando(command);

        if (operacaoValida()) {
            ResponseEntity e = new ResponseEntity<String>(HttpStatus.OK);
            return e.ok("carrinho/index");
        }

        model.addAttribute("carrinhoItem",carrinhoItem);
        response.sendRedirect("/vitrine/produto-detalhe/"+carrinhoItem.getId());
        return null;
    }

    @PostMapping(value = {"/carrinho/atualizar-item"})
    public ResponseEntity<String> atualizarItem(String id, Integer quantidade, Model model) {
        var produto = produtoAppService.obterPorId(id);
        if (produto == null) return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        var command = new AtualizarItemPedidoCommand(clienteId, id, quantidade);
        eventBus.enviarComando(command);
        if (operacaoValida()) {
            ResponseEntity e = new ResponseEntity<String>(HttpStatus.OK);
            return e.ok("index");
        }
        CarrinhoViewModel carrinho = pedidoQueries.obterCarrinhoCliente(clienteId);
        model.addAttribute("carrinho", carrinho);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity e = new ResponseEntity<String>(headers,HttpStatus.OK);
        return e.ok("index");
    }

}