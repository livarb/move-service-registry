package no.difi.meldingsutveksling.serviceregistry.servicerecord;


import static no.difi.meldingsutveksling.serviceregistry.model.ServiceIdentifier.DPO;

/**
 * Represents a Service Record for EDU messages
 *
 */
public class EDUServiceRecord extends ServiceRecord {

    private String serviceCode;
    private String serviceEditionCode;

    public EDUServiceRecord(String pemCertificate, String endpoint, String orgnr) {
        super(pemCertificate, DPO, orgnr);
        this.endpointUrl = endpoint;
    }

    public EDUServiceRecord(String pemCertificate, String endpoint, String serviceCode,
                            String serviceEditionCode, String orgnr) {
        super(pemCertificate, DPO, orgnr);
        this.endpointUrl = endpoint;
        this.serviceCode = serviceCode;
        this.serviceEditionCode = serviceEditionCode;
    }

    @Override
    public String getEndPointURL() {
        return endpointUrl;
    }

    @Override
    public String getOrganisationNumber() {
        return organisationNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceEditionCode() {
        return serviceEditionCode;
    }

    public void setServiceEditionCode(String serviceEditionCode) {
        this.serviceEditionCode = serviceEditionCode;
    }
}
