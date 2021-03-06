package no.difi.meldingsutveksling.serviceregistry.servicerecord;

import no.difi.meldingsutveksling.serviceregistry.config.ServiceregistryProperties;
import org.springframework.beans.factory.annotation.Autowired;

import static no.difi.meldingsutveksling.serviceregistry.model.ServiceIdentifier.DPV;

public class PostVirksomhetServiceRecord extends ServiceRecord {
    private final ServiceregistryProperties properties;

    @Autowired
    public PostVirksomhetServiceRecord(ServiceregistryProperties properties, String orgnr) {
        super(null, DPV, orgnr);
        this.properties = properties;
    }

    @Override
    public String getEndPointURL() {
        return properties.getDpv().getEndpointURL().toString();
    }
}
