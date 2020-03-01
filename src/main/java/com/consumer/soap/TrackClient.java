package com.consumer.soap;

import com.consumer.soap.wsdl.ObjectFactory;
import com.consumer.soap.wsdl.Witaj;
import com.consumer.soap.wsdl.WitajResponse;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.util.HashMap;
import java.util.Map;

public class TrackClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(TrackClient.class);
    @Autowired
    private Environment environment;

    public WitajResponse getWitaj(String name) {
        ObjectFactory factory = new ObjectFactory();
        Witaj request = new Witaj();
        request.setImie(factory.createWitajImie(name));

        log.info("Requesting test for " + name);

        return (WitajResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://tt.poczta-polska.pl/Sledzenie/services/Sledzenie/xsd/witaj", request, message -> {
                    try {
                        SoapHeader soapHeader = ((SoapMessage) message).getSoapHeader();
                        Map<String, String> mapRequest = new HashMap<>();

                        mapRequest.put("username", environment.getProperty("soap.auth.username"));
                        mapRequest.put("password", environment.getProperty("soap.auth.password"));
                        StringSubstitutor substitutor = new StringSubstitutor(mapRequest, "%(", ")");
                        String finalXMLRequest = substitutor.replace(environment.getProperty("soap.auth.header"));
                        StringSource headerSource = new StringSource(finalXMLRequest);
                        Transformer transformer = TransformerFactory.newInstance().newTransformer();
                        transformer.transform(headerSource, soapHeader.getResult());
                        logger.info("Marshalling of SOAP header success.");
                    } catch (Exception e) {
                        logger.error("error during marshalling of the SOAP headers", e);
                    }
                });
    }

}