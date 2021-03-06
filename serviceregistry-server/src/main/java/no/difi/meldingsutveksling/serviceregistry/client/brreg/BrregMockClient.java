package no.difi.meldingsutveksling.serviceregistry.client.brreg;

import no.difi.meldingsutveksling.serviceregistry.config.ServiceregistryProperties;
import no.difi.meldingsutveksling.serviceregistry.model.BrregEnhet;
import no.difi.meldingsutveksling.serviceregistry.service.brreg.dev.TestEnvironmentEnheter;

import java.net.URISyntaxException;
import java.util.Optional;

public class BrregMockClient implements BrregClient {

    TestEnvironmentEnheter enheter;
    ServiceregistryProperties properties;
    BrregClient clientImpl;

    public BrregMockClient(TestEnvironmentEnheter enheter, ServiceregistryProperties properties) throws URISyntaxException {
        this.enheter = enheter;
        this.properties = properties;
        this.clientImpl = new BrregClientImpl(properties.getBrreg().getEndpointURL().toURI());
    }

    @Override
    public Optional<BrregEnhet> getBrregEnhetByOrgnr(String orgnr) {

        Optional<BrregEnhet> enhet = enheter.getBrregEnhet(orgnr);
        if (enhet.isPresent()) {
            return enhet;
        }

        return clientImpl.getBrregEnhetByOrgnr(orgnr);
    }

    @Override
    public Optional<BrregEnhet> getBrregUnderenhetByOrgnr(String orgnr) {
        Optional<BrregEnhet> enhet = enheter.getBrregEnhet(orgnr);
        if (enhet.isPresent()) {
            return enhet;
        }

        return clientImpl.getBrregUnderenhetByOrgnr(orgnr);
    }
}
