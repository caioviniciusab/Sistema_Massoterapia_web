package com.massoterapia.sistemaweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

            String url = UriComponentsBuilder
                    .fromHttpUrl("https://api.callmebot.com/whatsapp.php")
                    .queryParam("phone", numero)
                    .queryParam("text", mensagem)
                    .queryParam("apikey", apiKey)
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();

            System.out.println("Número utilizado: " + numero);
            System.out.println("API Key carregada: " + (apiKey != null && !apiKey.isBlank()));

            String resposta =
                    restTemplate.getForObject(url, String.class);

            System.out.println("Resposta CallMeBot: " + resposta);

        } catch (Exception e) {

            System.out.println(
                    "Erro ao enviar mensagem para o WhatsApp: "
                            + e.getMessage()
            );
        }
    }

}
