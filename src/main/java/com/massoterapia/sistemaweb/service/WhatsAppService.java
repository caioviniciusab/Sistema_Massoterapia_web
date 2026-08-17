package com.massoterapia.sistemaweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WhatsAppService {

    @Value("${callmebot.apikey}")
    private String apiKey;

    @Value("${callmebot.numero}")
    private String numero;

    private final RestTemplate restTemplate = new RestTemplate();

    public void enviarMensagem(String mensagem) {

        System.out.println("Tentando enviar mensagem pelo WhatsApp...");

        try {

            String mensagemCodificada =
                    URLEncoder.encode(
                            mensagem,
                            StandardCharsets.UTF_8
                    );

            String url =
                    "https://api.callmebot.com/whatsapp.php"
                            + "?phone=" + numero
                            + "&text=" + mensagemCodificada
                            + "&apikey=" + apiKey;

            System.out.println("Número utilizado: " + numero);
            System.out.println("API Key carregada: " + (apiKey != null && !apiKey.isBlank()));
            System.out.println("Chamando API da CallMeBot...");

            String resposta =
                    restTemplate.getForObject(
                            url,
                            String.class
                    );

            System.out.println(
                    "Resposta CallMeBot: " + resposta
            );

        } catch (Exception e) {

            System.out.println(
                    "Erro ao enviar mensagem para o WhatsApp: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }

}
