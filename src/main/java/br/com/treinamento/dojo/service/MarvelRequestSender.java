package br.com.treinamento.dojo.service;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.stereotype.Service;

import br.com.treinamento.model.Character;
import br.com.treinamento.model.Characters;
import br.com.treinamento.util.MD5;

/**
 * Created by diegob on 27/10/2016.
 */
@Service
public class MarvelRequestSender {
    public static final String HOST = "https://gateway.marvel.com:443/v1/public";
    private static final String RESOURCE = "characters";
    private static final String PUBLIC_KEY = "1acd3b921cc928af72236227fcc1fadd";
    private static final String PRIVATE_KEY = "2db991eeff8833acdecf83d663f416f66c89ffe6";
    
    public Characters fetchCharacters() {
        long ts = System.currentTimeMillis();
        
        return ClientBuilder.newClient()
            .target(HOST)
            .register(JacksonFeature.class)
            .path(RESOURCE)
            .queryParam("ts", ts)
            .queryParam("apikey", PUBLIC_KEY)
            .queryParam("hash", MD5.generateHash(ts + PRIVATE_KEY + PUBLIC_KEY))
            .request(MediaType.APPLICATION_JSON)
            .get(Characters.class);
    }
    
    public Character fetchCharacter(Long id) {
        long ts = System.currentTimeMillis();
        
        return ClientBuilder.newClient()
            .target(HOST)
            .register(JacksonFeature.class)
            .path(RESOURCE + "/" + id)
            .queryParam("ts", ts)
            .queryParam("apikey", PUBLIC_KEY)
            .queryParam("hash", MD5.generateHash(ts + PRIVATE_KEY + PUBLIC_KEY))
            .request(MediaType.APPLICATION_JSON)
            .get(Character.class);
    }
}