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
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String telefone,
            Model model
    ) {

        if (nome != null && telefone != null
                && !nome.isBlank()
                && !telefone.isBlank()) {

            model.addAttribute("nome", nome);
            model.addAttribute("telefone", telefone);

            model.addAttribute(
                    "agendamentos",
                    agendamentoService.buscarPorNomeETelefone(nome, telefone)
            );
        }

        return "consultar";
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarAgendamento(@PathVariable Integer id,
                                      @RequestParam String nome,
                                      @RequestParam String telefone,
                                      RedirectAttributes attributes)
    {

        System.out.println("Nome: " + nome);
        System.out.println("Telefone: " + telefone);

        agendamentoService.cancelarAgendamento(id);

        attributes.addFlashAttribute("sucesso", "Agendamento cancelado com sucesso.");

        return "redirect:/consultar?nome=" + nome + "&telefone=" + telefone;

    }

    @PostMapping("/agenda/cancelar/{id}")
    public String cancelarAgendamentoAgenda(
            @PathVariable Integer id,
            @RequestParam String data,
            RedirectAttributes attributes
    ) {

        agendamentoService.cancelarAgendamento(id);

        attributes.addFlashAttribute(
                "sucesso",
                "Agendamento cancelado com sucesso."
        );

        return "redirect:/agenda?data=" + data;
    }


    @PostMapping("/consultar")
    public String consultar(
            @RequestParam String nome,
            @RequestParam String telefone,
            Model model
    ) {

        List<Agendamentos> agendamentos = agendamentoService.buscarPorNomeETelefone(nome, telefone);

        model.addAttribute("nome", nome);
        model.addAttribute("telefone", telefone);


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