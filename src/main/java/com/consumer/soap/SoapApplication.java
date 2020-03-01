package com.consumer.soap;

import com.consumer.soap.wsdl.WitajResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoapApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(TrackClient quoteClient) {
        return args -> {
            String name = "Beata";
            if (args.length > 0)
                name = args[0];
            WitajResponse response = quoteClient.getWitaj(name);
            System.out.println(response.getReturn().getValue());
        };
    }
}
