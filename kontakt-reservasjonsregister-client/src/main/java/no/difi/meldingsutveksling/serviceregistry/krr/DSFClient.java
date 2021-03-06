package no.difi.meldingsutveksling.serviceregistry.krr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.proc.BadJWSException;
import no.difi.move.common.oauth.JWTDecoder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.CertificateException;

public class DSFClient {

    private URL endpointURL;

    public DSFClient(URL endpointURL) {
        this.endpointURL= endpointURL;
    }

    public DSFResource getDSFResource(String identifier, String token) throws KRRClientException {

        URI uri;
        try {
             uri = endpointURL.toURI();
        } catch (URISyntaxException e) {
            throw new KRRClientException("Failed to create URI instance of \"" + endpointURL + "\"", e);
        }

        PersonRequest request = PersonRequest.of(identifier);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);
        headers.set("Accept", "application/jose");
        HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new KRRClientException(String.format("DSF endpoint returned %s (%s)", response.getStatusCode().value(),
                    response.getStatusCode().getReasonPhrase()));
        }

        String payload;
        try {
            JWTDecoder jwtDecoder = new JWTDecoder();
            payload = jwtDecoder.getPayload(response.getBody());
        } catch (CertificateException | BadJWSException e) {
            throw new KRRClientException("Error during decoding JWT response from KRR" ,e);
        }

        ObjectMapper om = new ObjectMapper();
        DSFResponse dsfResponse;
        try {
            dsfResponse = om.readValue(payload, DSFResponse.class);
        } catch (IOException e) {
            throw new KRRClientException("Error mapping payload to " + DSFResponse.class.getName(), e);
        }

        return dsfResponse.getPersons().get(0);
    }
}
