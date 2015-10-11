package com.tsmc.security.callback;

import java.security.cert.Certificate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.security.auth.callback.Callback;

public class CertificateCallback implements Callback {

    private Set<Certificate> _certificates = new LinkedHashSet<Certificate>();

    /**
     * Constructs a new CertificateCallback.
     */
    public CertificateCallback() {}

    /**
     * Gets the Certificates.
     * @return the Certificates
     */
    public Set<Certificate> getCertificates() {
        Set<Certificate> copy = new LinkedHashSet<Certificate>();
        copy.addAll(_certificates);
        return copy;
    }

    /**
     * Adds a Certificate.
     * @param certificate the Certificate to add
     */
    public void addCertificate(Certificate certificate) {
        if (certificate != null) {
            _certificates.add(certificate);
        }
    }

}

