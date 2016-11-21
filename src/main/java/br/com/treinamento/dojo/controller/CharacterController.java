package br.com.treinamento.dojo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.treinamento.dojo.service.MarvelRequestSender;
import br.com.treinamento.model.Character;
import br.com.treinamento.model.Characters;

@RestController
@RequestMapping("characters/")
public class CharacterController {
    
    @Autowired
    private MarvelRequestSender marvelService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Characters> getCharacters() {
        Characters characters = this.marvelService.fetchCharacters();
        return ResponseEntity.ok(characters);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Character> getCharacter(@PathVariable("id") Long characterId) {
        Character character = this.marvelService.fetchCharacter(characterId);
        return ResponseEntity.ok(character);
    }
}