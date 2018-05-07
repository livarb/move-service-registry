package no.difi.meldingsutveksling.serviceregistry.svarut;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SvarUtService {

    private SvarUtClient svarUtClient;

    @Autowired
    SvarUtService(SvarUtClient svarUtClient) {
        this.svarUtClient = svarUtClient;
    }

    public boolean hasSvarUtAdressering(String orgnr) {
        RetrieveMottakerSystemForOrgnr request = RetrieveMottakerSystemForOrgnr.builder().withOrganisasjonsnr(orgnr).build();
        try {
            RetrieveMottakerSystemForOrgnrResponse response = svarUtClient.retrieveMottakerSystemForOrgnr(request);
            return !response.getReturn().isEmpty();
        } catch (Exception e) {
            log.error("Caught exception while looking up receiver in SvarUt", e);
            return false;
        }
    }
}
