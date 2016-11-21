package br.com.treinamento.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@JsonDeserialize(using = Character.CharacterDeserializer.class)
public class Character implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;

    public Character(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Character [id=" + id + ", name=" + name + ", description=" + description + "]";
    }

    public static class CharacterDeserializer extends StdDeserializer<Character> {
        private static final long serialVersionUID = 1L;

        public CharacterDeserializer() {
            this(null);
        }

        protected CharacterDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Character deserialize(JsonParser jp, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            JsonNode node = jp.getCodec().readTree(jp);
            return this.fillDataNode(node.get("data"));
        }

        private Character fillDataNode(JsonNode data) {
            if (data != null) {
                JsonNode results = data.get("results");
                return this.fillResultsNode(results);
            }

            return new Character(null, null, null);
        }

        private Character fillResultsNode(JsonNode results) {
            if (results != null) {
                Iterator<JsonNode> characters = results.elements();
                while (characters.hasNext()) {
                    JsonNode character = characters.next();

                    long id = character.get("id").asLong();
                    String name = character.get("name").asText();
                    String description = character.get("description").asText();

                    return new Character(id, name, description);
                }
            }

            return new Character(null, null, null);
        }
    }
}