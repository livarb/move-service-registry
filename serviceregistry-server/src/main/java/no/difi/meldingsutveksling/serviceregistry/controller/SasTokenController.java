package no.difi.meldingsutveksling.serviceregistry.controller;

import com.google.common.base.Strings;
import no.difi.meldingsutveksling.logging.Audit;
import no.difi.meldingsutveksling.serviceregistry.config.ServiceregistryProperties;
import no.difi.meldingsutveksling.serviceregistry.security.EntitySignerException;
import no.difi.meldingsutveksling.serviceregistry.security.PayloadSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SasTokenController {

    private ServiceregistryProperties props;
    private PayloadSigner payloadSigner;

    @Autowired
    SasTokenController(ServiceregistryProperties props,
                       PayloadSigner payloadSigner) {
        this.props = props;
        this.payloadSigner = payloadSigner;
    }

    @PreAuthorize("#oauth2.hasScope('move/dpe.read')")
    @RequestMapping(value = "/sastoken", method = RequestMethod.GET, produces = "application/jose")
    public ResponseEntity getToken(Authentication auth) throws EntitySignerException {

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Audit.info(String.format("SAS token request by %s", (String) auth.getPrincipal()));

        String sasToken = props.getAuth().getSasToken();
        if (Strings.isNullOrEmpty(sasToken)) {
            Audit.error("SAS token not defined");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(payloadSigner.sign(sasToken));
    }

}
