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

        try {
            String url = "https://api.callmebot.com/whatsapp.php"
                    + "?phone=" + numero
                    + "&text=" + mensagem
                    + "&apikey=" + apiKey;

            restTemplate.getForObject(url, String.class);

        }catch (Exception e){
            System.out.println("Erro ao enviar mensagem para o WhatsApp: " + e.getMessage());
        }

    }

}
