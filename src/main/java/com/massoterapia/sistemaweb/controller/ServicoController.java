package com.massoterapia.sistemaweb.controller;

import com.massoterapia.sistemaweb.exception.AgendamentoException;
import com.massoterapia.sistemaweb.model.Servico;
import com.massoterapia.sistemaweb.service.ServicoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            RedirectAttributes attributes
    ) {

        try {

            servicoService.salvar(nomeServico);

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
}
