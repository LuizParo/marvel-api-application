package br.com.treinamento.dojo.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.treinamento.model.Character;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CharacterController.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CharacterControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void shouldReturnCharacterById() {
        long id = 1009148L;
        ResponseEntity<Character> response = this.template.getForEntity("/{id}", Character.class, id);
        
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}