package com.massoterapia.sistemaweb.service;

import com.massoterapia.sistemaweb.exception.AgendamentoException;
import com.massoterapia.sistemaweb.model.Agendamentos;
import com.massoterapia.sistemaweb.model.Cliente;
import com.massoterapia.sistemaweb.model.Servico;
import com.massoterapia.sistemaweb.repository.AgendamentoRepository;
import com.massoterapia.sistemaweb.repository.ClienteRepository;
import com.massoterapia.sistemaweb.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<String> buscarHorariosDisponiveis(String data) {

        List<String> horariosDisponiveis = new ArrayList<>();

        horariosDisponiveis.add("08:00");
        horariosDisponiveis.add("09:00");
        horariosDisponiveis.add("10:00");
        horariosDisponiveis.add("11:00");
        horariosDisponiveis.add("13:00");
        horariosDisponiveis.add("14:00");
        horariosDisponiveis.add("15:00");
        horariosDisponiveis.add("16:00");
        horariosDisponiveis.add("17:00");
        horariosDisponiveis.add("18:00");

        LocalDate dia = LocalDate.parse(data);
        LocalDateTime inicio = dia.atStartOfDay();
        LocalDateTime fim = dia.atTime(23, 59, 59);

        List<Agendamentos> agendamentos = agendamentoRepository.findByDataBetween(inicio, fim);

        for (Agendamentos agendamento : agendamentos) {
            String horario = agendamento.getData().toLocalTime().toString();

            horariosDisponiveis.remove(horario);
        }

        return horariosDisponiveis;

    }

    public Agendamentos buscarPorId(Integer id) {

        return agendamentoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Agendamento não encontrado."));
    }


    public List<Agendamentos> buscarPorTelefone(String telefone) {

        return agendamentoRepository.findByClienteTelefone(telefone);

    }

    public List<Agendamentos> buscarAgendamentosPorData(LocalDate data) {


        LocalDateTime inicio = data.atStartOfDay();

        LocalDateTime fim = data.atTime(23, 59, 59);

        return agendamentoRepository
                .findByDataBetweenOrderByDataAsc(inicio, fim);

    }

    public long quantidadeAgendados(){
        return agendamentoRepository.countByStatus("Agendado");
    }

    public void cancelarAgendamento(Integer idAgendamento) {
        Agendamentos agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado."));

        agendamento.setStatus("CANCELADO");

        agendamentoRepository.save(agendamento);

    }

    public String salvarCliente(
            String nome,
            String telefone,
            String servico,
            String data,
            String horario
    ) {

        Optional<Cliente> clienteOptional = clienteRepository.findByTelefone(telefone);

        Cliente cliente;

        if (clienteOptional.isPresent()) {
            cliente = clienteOptional.get();

            if (!cliente.getNome().equalsIgnoreCase(nome)) {
                throw new AgendamentoException("Já existe um cliente cadastrado com este telefone. Verifique o nome informado ou utilize outro número.");
            }
        }
        else{
            cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setTelefone(telefone);

            clienteRepository.save(cliente);
        }

        Servico servicoBanco = servicoRepository.findByNomeservico(servico);

        LocalDate date =  LocalDate.parse(data);

        LocalTime hora =  LocalTime.parse(horario);

        LocalDateTime datahora = LocalDateTime.of(date, hora);

        LocalDateTime agora = LocalDateTime.now();

        if (datahora.isBefore(agora)) {
            throw new AgendamentoException("A data do agendamento não pode ser anterior a data do atual.");
        }

        if (agendamentoRepository.existsByData(datahora)) {
            throw new AgendamentoException("Este horário já está ocupado.");
        }

        Agendamentos agendamento = new Agendamentos();

        agendamento.setCliente(cliente);
        agendamento.setServico(servicoBanco);
        agendamento.setData(datahora);
        agendamento.setStatus("AGENDADO");

        agendamentoRepository.save(agendamento);


        return nome;
    }

    public void editarAgendamento(
            Integer id,
            String nome,
            String telefone,
            Integer servicoId,
            String data,
            String horario
    ) {

        Agendamentos agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Agendamento não encontrado."));

        Cliente cliente = agendamento.getCliente();

        cliente.setNome(nome);
        cliente.setTelefone(telefone);

        clienteRepository.save(cliente);

        Servico servico = servicoRepository.findById(servicoId).orElseThrow(() -> new RuntimeException("Serviço não encontrado."));

        agendamento.setServico(servico);

        LocalDate novaData = LocalDate.parse(data);
        LocalTime novaHora = LocalTime.parse(horario);

        agendamento.setData(LocalDateTime.of(novaData, novaHora));

        agendamentoRepository.save(agendamento);

    }

    public void concluirAgendamento(Integer id) {

        Agendamentos agendamento = agendamentoRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Agendamento não encontrado."));

        agendamento.setStatus("CONCLUIDO");

        agendamentoRepository.save(agendamento);

    }
}
