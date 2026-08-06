package com.massoterapia.sistemaweb.controller;

import com.massoterapia.sistemaweb.exception.AgendamentoException;
import com.massoterapia.sistemaweb.service.ServicoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @GetMapping("/servicos")
    public String listarServicos(Model model, HttpSession session) {

        if(session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        model.addAttribute("servicos", servicoService.listarTodos());

        return "servicos";
    }

    @GetMapping("/servicos/editar/{id}")
    public String abrirEdicao(@PathVariable Integer id, Model model)
    {
        model.addAttribute("servico", servicoService.buscarPorId(id));

        return "editar-servico";
    }

    @GetMapping("/servicos/novo")
    public String novoServico(HttpSession session) {

        if(session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        return "novo-servico";
    }

    @PostMapping("/servicos/salvar")
    public String salvarServico(
            @RequestParam String nomeServico,
            @RequestParam BigDecimal preco,
            RedirectAttributes attributes
    ) {

        try {

            servicoService.salvar(nomeServico, preco);

            attributes.addFlashAttribute(
                    "sucesso",
                    "Serviço cadastrado com sucesso."
            );

            return "redirect:/servicos";

        } catch (AgendamentoException e) {

            attributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );

            return "redirect:/servicos/novo";
        }
    }

    @PostMapping("/servicos/editar")
    public String editarServico(
            @RequestParam Integer id,
            @RequestParam String nomeServico,
            @RequestParam BigDecimal preco,
            RedirectAttributes attributes
    ){

        try{
            servicoService.editarServico(id, nomeServico, preco);

            attributes.addFlashAttribute("sucesso", "Serviço atualizado com sucesso.");

            return "redirect:/servicos";

        }catch(AgendamentoException e){

            attributes.addFlashAttribute("erro",e.getMessage());

            return "redirect:/servicos/editar/" + id;
        }
    }

    @PostMapping("/servicos/excluir/{id}")
    public String excluirServico(
            @PathVariable Integer id,
            RedirectAttributes attributes
    ) {

        try {

            servicoService.excluirServico(id);

            attributes.addFlashAttribute(
                    "sucesso",
                    "Serviço excluído com sucesso."
            );

        } catch (AgendamentoException e) {

            attributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/servicos";
    }
}
