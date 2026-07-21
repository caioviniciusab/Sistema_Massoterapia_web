package com.massoterapia.sistemaweb.controller;

import com.massoterapia.sistemaweb.service.ServicoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
