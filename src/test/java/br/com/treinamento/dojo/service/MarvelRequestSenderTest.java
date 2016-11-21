package br.com.treinamento.dojo.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.treinamento.model.Character;
import br.com.treinamento.model.Characters;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarvelRequestSender.class)
public class MarvelRequestSenderTest {
    
    @Autowired
    private MarvelRequestSender marvelRequestSender;

    @Test
    public void shouldReturnCharactersFromMarvelApi() {
        Characters characters = this.marvelRequestSender.fetchCharacters();
        
        Assert.assertNotNull(characters);
        Assert.assertNotNull(characters.getLinks());
        Assert.assertFalse(characters.getLinks().isEmpty());
        
        characters.getLinks().forEach(link -> {
            Assert.assertNotNull(link);
            Assert.assertNotNull(link.getTitle());
            Assert.assertEquals("character", link.getRel());
        });
    }
    
    @Test
    public void shouldReturnSingleCharacterFromMarvelApi() {
        Character character = this.marvelRequestSender.fetchCharacter(1009148L);
        
        Assert.assertNotNull(character);
        Assert.assertNotNull(character.getId());
        Assert.assertNotNull(character.getName());
        Assert.assertNotNull(character.getDescription());
        Assert.assertEquals(new Long(1009148L), character.getId());
    }
}