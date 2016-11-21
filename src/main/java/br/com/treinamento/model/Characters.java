package br.com.treinamento.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Link;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@JsonDeserialize(using = Characters.CharactersDeserializer.class)
public class Characters {
    private final List<CustomLink> links;

    public Characters(List<CustomLink> links) {
        if(links == null) {
            this.links = new ArrayList<>();
        } else {
            this.links = links;
        }
    }

    public List<Link> getLinks() {
        List<Link> links = new ArrayList<>();
        
        this.links.forEach(customLink -> {
            Link link = Link.fromUri(customLink.getResourceURI())
                .rel("character")
                .title(customLink.getName())
                .build();
            
            links.add(link);
        });
        
        return Collections.unmodifiableList(links);
    }
    
    public void addLink(CustomLink link) {
        this.links.add(link);
    }

    @Override
    public String toString() {
        return "Characters [links=" + links + "]";
    }

    public static class CustomLink {
        private final String resourceURI;
        private String name;

        public CustomLink(String resourceURI, String name) {
            this.resourceURI = resourceURI;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getResourceURI() {
            return resourceURI;
        }

        @Override
        public String toString() {
            return "Link [resourceURI=" + resourceURI + "]";
        }
    }
    
    public static class CharactersDeserializer extends StdDeserializer<Characters> {
        private static final long serialVersionUID = 1L;
        
        public CharactersDeserializer() {
            this(null);
        }
        
        protected CharactersDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Characters deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = jp.getCodec().readTree(jp);
            return this.fillDataNode(node.get("data"));
        }

        private Characters fillDataNode(JsonNode data) {
            if(data != null) {
                JsonNode results = data.get("results");
                return this.fillResultsNode(results);
            }
            
            return new Characters(null);
        }

        private Characters fillResultsNode(JsonNode results) {
            List<CustomLink> links = new ArrayList<>();
            
            if(results != null) {
                Iterator<JsonNode> characters = results.elements();
                while(characters.hasNext()) {
                    JsonNode character = characters.next();
                    
                    String resourceURI = character.get("resourceURI").asText();
                    String name = character.get("name").asText();
                    
                    links.add(new CustomLink(resourceURI, name));
                }
            }
            
            return new Characters(links);
        }
    }
}