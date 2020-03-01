package com.consumer.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class TrackConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.consumer.soap.wsdl");
        return marshaller;
    }

    @Bean
    public TrackClient trackClient(Jaxb2Marshaller marshaller) {
        TrackClient client = new TrackClient();
        client.setDefaultUri("http://ws.sledzenie.pocztapolska.pl/xsd");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
