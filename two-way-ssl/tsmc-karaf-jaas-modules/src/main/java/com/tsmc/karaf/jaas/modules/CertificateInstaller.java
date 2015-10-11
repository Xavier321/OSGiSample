package com.tsmc.karaf.jaas.modules;

import java.io.File;

import org.apache.felix.fileinstall.ArtifactInstaller;
import org.apache.felix.utils.properties.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertificateInstaller  implements ArtifactInstaller {

	private final Logger LOGGER = LoggerFactory.getLogger(CertificateInstaller.class);
	
    private String usersFileName;
    
    private File usersFile;

    CertificateLoginModule certificateLoginModule;

    public CertificateInstaller(CertificateLoginModule certificateLoginModule, String usersFile) {
        this.certificateLoginModule = certificateLoginModule;
        this.usersFileName = usersFile;
    }

    public boolean canHandle(File artifact) {
        if (usersFile == null) {
            usersFile = new File(usersFileName);
        }
        return artifact.getName().endsWith(usersFile.getName());
    }

    public void install(File artifact) throws Exception {
        if (usersFile == null) {
            usersFile = new File(usersFileName);
        }
        Properties userProperties = new Properties(usersFile);
        //this.certificateLoginModule.encryptedPassword(userProperties);
    }

    public void update(File artifact) throws Exception {
        if (usersFile == null) {
            usersFile = new File(usersFileName);
        }
        Properties userProperties = new Properties(usersFile);
        //this.certificateLoginModule.encryptedPassword(userProperties);
    }

    public void uninstall(File artifact) throws Exception {
        LOGGER.warn("the certRole.properties was removed");
    }

}
