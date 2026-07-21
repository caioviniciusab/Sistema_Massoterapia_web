package com.massoterapia.sistemaweb.controller;

import com.massoterapia.sistemaweb.service.AgendamentoService;
import com.massoterapia.sistemaweb.service.MassoterapeutaService;
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

import java.time.LocalDate;

@Controller
public class MassoterapeutaController {

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private MassoterapeutaService massoterapeutaService;

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping("/login")
    public String login() {

        return "login";

    }

    @GetMapping("/dashboard")
    public String dashboard(
            HttpSession session,
            Model model
    ) {
        if (session.getAttribute("usuarioLogado") ==  null) {

            return "redirect:/login";
        }

        model.addAttribute(
                "agendados",
                agendamentoService.quantidadeAgendados()
        );

        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(
            HttpSession session
    ){

        session.invalidate();

        return "redirect:/login";
    }

    @GetMapping("/editar/{id}")
    public String editarAgendamento(
            @PathVariable Integer id,
            HttpSession session,
            Model model
    ) {

        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "agendamento",
                agendamentoService.buscarPorId(id)
        );

        model.addAttribute(
                "servicos",
                servicoService.listarTodos()
        );

        return "editar-agendamento";
    }

    @GetMapping("/agenda")
    public String agenda(
            @RequestParam (required = false) LocalDate data,
            HttpSession session,
            Model model
    ) {

        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        if (data == null) {
            data = LocalDate.now();
        }

        model.addAttribute("dataSelecionada", data);

        model.addAttribute(
                "agendamentos",
                agendamentoService.buscarAgendamentosPorData(data)
        );

        return "agenda";

    }

    @PostMapping("/login")
    public String autenticar(
            @RequestParam String usuario,
            @RequestParam String senha,
            RedirectAttributes attributes,
            HttpSession session
    ) {
        boolean autenticado = massoterapeutaService.autenticar(usuario, senha);

        if (autenticado) {

            session.setAttribute("usuarioLogado", usuario);

            return "redirect:/dashboard";
        }

        attributes.addFlashAttribute(
                "erro",
                "Usuário ou senha inválido"
        );

        return "redirect:/login";

    }

    @PostMapping("/editar")
    public String salvarEdicao(
            @RequestParam Integer id,
            @RequestParam String nome,
            @RequestParam String telefone,
            @RequestParam Integer servicoId,
            @RequestParam String data,
            @RequestParam String horario,
            RedirectAttributes attributes
    ){

        agendamentoService.editarAgendamento(
                id,
                nome,
                telefone,
                servicoId,
                data,
                horario
        );

        attributes.addFlashAttribute(
                "Sucesso",
                "Agendamento atualizado com sucesso"
        );

        return "redirect:/agenda";
    }

    @PostMapping("/concluir/{id}")
    public String concluir(
            @PathVariable Integer id,
            RedirectAttributes attributes
    ) {

        agendamentoService.concluirAgendamento(id);

        attributes.addFlashAttribute(
                "sucesso",
                "Atendimento concluído com sucesso!"
        );

        return "redirect:/agenda";

    }
}

