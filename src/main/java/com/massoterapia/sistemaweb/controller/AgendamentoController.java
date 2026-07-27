package com.massoterapia.sistemaweb.controller;

import com.massoterapia.sistemaweb.exception.AgendamentoException;
import com.massoterapia.sistemaweb.model.Agendamentos;
import com.massoterapia.sistemaweb.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping("/agendar")
    public String agendar() {
        return "agendar";
    }

    @GetMapping("/consultar")
    public String abrirConsulta(
            @RequestParam(required = false) String telefone,
            Model model
    ) {

        if(telefone != null && !telefone.isBlank()) {

            model.addAttribute("telefone", telefone);

            model.addAttribute("agendamentos", agendamentoService.buscarPorTelefone(telefone));
        }

        return "consultar";
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarAgendamento(@PathVariable Integer id,
                                      @RequestParam String telefone,
                                      RedirectAttributes attributes)
    {
        agendamentoService.cancelarAgendamento(id);

        attributes.addFlashAttribute("sucesso", "Agendamento cancelado com sucesso.");

        return "redirect:/consultar?telefone=" + telefone;

    }


    @PostMapping("/consultar")
    public String consultar(
            @RequestParam String telefone, Model model
    ) {

        List<Agendamentos> agendamentos = agendamentoService.buscarPorTelefone(telefone);

        if(agendamentos.isEmpty()) {
            model.addAttribute("mensagem", "Nenhum agendamento encontrado para esse telefone.");
        }

        model.addAttribute("agendamentos", agendamentos);

        return "consultar";
    }

    @GetMapping("/horarios")
    @ResponseBody
    public List<String> buscarHorarios(@RequestParam String data) {

        return agendamentoService.buscarHorariosDisponiveis(data);

    }

    @PostMapping("/salvar")
    public String salvar(
            @RequestParam String nome,
            @RequestParam String telefone,
            @RequestParam String servico,
            @RequestParam String data,
            @RequestParam String horario,
            RedirectAttributes attributes
    ) {
        try {

            agendamentoService.salvarCliente(
                    nome,
                    telefone,
                    servico,
                    data,
                    horario
            );

            attributes.addFlashAttribute(
                    "sucesso",
                    "Agendamento realizado com sucesso!"
            );


        }catch (AgendamentoException e) {
            attributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/agendar";
    }

}