package br.com.diolabs.restful.enums;

import java.util.stream.Stream;

public enum RacaEnum {
    HUMANO("HUMANO"),
    ELFO("ELFO"),
    ORC("ORC"),
    ANAO("ANAO");

    private String value;

    RacaEnum(String value){
        this.value =  value;
    }

    public String getValue(){
        return this.value;
    }

    public static RacaEnum getRacaEnum(String value){
        return Stream.of(RacaEnum.values()).filter(e -> e.getValue().equals(value)).findFirst().orElseThrow();
    }


}
