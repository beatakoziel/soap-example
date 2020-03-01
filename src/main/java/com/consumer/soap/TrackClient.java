package com.consumer.soap;

import com.consumer.soap.wsdl.ObjectFactory;
import com.consumer.soap.wsdl.Witaj;
import com.consumer.soap.wsdl.WitajResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


public class TrackClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(TrackClient.class);

    public WitajResponse getWitaj(String name) {
        ObjectFactory factory = new ObjectFactory();
        Witaj request = new Witaj();
        request.setImie(factory.createWitajImie(name));

        log.info("Requesting test for " + name);

        return (WitajResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://tt.poczta-polska.pl/Sledzenie/services/Sledzenie/xsd/witaj", request, null);
    }

}