package br.com.diolabs.restful.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.diolabs.restful.enums.RacaEnum;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper getObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        // Não falha se tem propriedades desconhecidas
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Compatibilidade de array, não falha caso array tenho único valor
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);

        // Não falha caso tenha propriedades vazias
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // Trata datas
        objectMapper.registerModule(new JavaTimeModule());

        // Trata RacaEnum
        objectMapper.registerModule(getModuleRacaENum());

        return objectMapper;
    }

    private SimpleModule getModuleRacaENum() {
        SimpleModule modulo = new SimpleModule("ModuleRacaENum", PackageVersion.VERSION);
        modulo.addSerializer(RacaEnum.class, new RacaEnumSerializer());
        modulo.addDeserializer(RacaEnum.class, new RacaEnumDeserializer());
        return modulo;
    } 


    class RacaEnumSerializer extends StdSerializer<RacaEnum>{

        protected RacaEnumSerializer() {
            super(RacaEnum.class);           
        }

        @Override
        public void serialize(RacaEnum raca, JsonGenerator json, SerializerProvider provider) throws IOException {
            json.writeString(raca.getValue());
            
        }

    }

    class RacaEnumDeserializer extends StdDeserializer<RacaEnum>{

        protected RacaEnumDeserializer() {
            super(RacaEnum.class);         
        }

        @Override
        public RacaEnum deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException {
            
            return RacaEnum.getRacaEnum(p.getText().toUpperCase());
        }

    }

}


