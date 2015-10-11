package com.tsmc.karaf.jaas.modules;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import org.apache.felix.utils.properties.Properties;
import org.apache.karaf.jaas.boot.principal.GroupPrincipal;
import org.apache.karaf.jaas.boot.principal.RolePrincipal;
import org.apache.karaf.jaas.boot.principal.UserPrincipal;
import org.apache.karaf.jaas.modules.AbstractKarafLoginModule;
import org.eclipse.jetty.plus.jaas.callback.ObjectCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertificateLoginModule extends AbstractKarafLoginModule {
	
	private Map<String, ?> _options;
	
	private X509Certificate _verifiedCallerCertificate;
	
    private static final transient Logger LOGGER = LoggerFactory.getLogger(CertificateLoginModule.class);

    static final String USER_FILE = "certRole";

    private String usersFile;
    
    private CertificateInstaller certificateInstaller;

    public void initialize(Subject sub, CallbackHandler handler, Map sharedState, Map options) {
        super.initialize(sub,handler,options);
        usersFile = (String) options.get(USER_FILE);
        _options = options;
        if (debug) {
            LOGGER.debug("Initialized debug={} usersFile={}", debug, usersFile);
        }
        certificateInstaller = new CertificateInstaller(this, usersFile);
        if (this.bundleContext != null) {
            this.bundleContext.registerService("org.apache.felix.fileinstall.ArtifactInstaller", certificateInstaller, null);
        }       
    }

	public boolean login() throws LoginException {
        if (usersFile == null) {
            throw new LoginException("The property users may not be null");
        }
        File f = new File(usersFile);
        if (!f.exists()) {
            throw new LoginException("Users file not found at " + f);
        }

        Properties users;
        try {
            users = new Properties(f);
        } catch (IOException ioe) {
            throw new LoginException("Unable to load user properties file " + f);
        }

        //encrypt all password if necessary
        //encryptedPassword(users);
		
        Callback[] callbacks = new Callback[2];

        callbacks[0] = new NameCallback("alias");
        callbacks[1] = new ObjectCallback();
        try {
            callbackHandler.handle(callbacks);
        } catch (IOException ioe) {
            throw new LoginException(ioe.getMessage());
        } catch (UnsupportedCallbackException uce) {
            throw new LoginException(uce.getMessage() + " not available to obtain information from user");
        }
        X509Certificate callerCertificate = getCallerCertificate((ObjectCallback) callbacks[1]);
        KeyStore keyStore = getKeyStore();


        
		return false;
	}

	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean logout() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

    private X509Certificate getCallerCertificate(ObjectCallback objectCallback) throws LoginException {
        X509Certificate x509cert = null;
        Certificate cert = (Certificate) objectCallback.getObject();
        if (cert instanceof X509Certificate) {
            x509cert = (X509Certificate)cert;
        }
        if (x509cert == null) {
        	throw new LoginException("Client x509cert can not be null");
        }
        return x509cert;
    }

    private KeyStore getKeyStore() throws LoginException {
    	String keyStoreLocation = (String) options.get("keyStoreLocation");
    	String keyStoreType = (String) options.get("keyStoreType");
    	String keyStorePassword = (String) options.get("keyStorePassword");
        KeyStorePuller keyStorePuller = new KeyStorePuller(keyStoreType, keyStorePassword != null ? keyStorePassword.toCharArray() : null);
        return keyStorePuller.pullPath(keyStoreLocation, getClass(), PathType.values());
    }
    
//    public String getOption(String name) {
//        return getOption(name, false);
//    }

//    public String getOption(String name, boolean required) {
//        Map<String, ?> options = _options;
//        if (options == null) {
//            if (required) {
//                throw BaseSecurityMessages.MESSAGES.optionsNotSet();
//            }
//        } else {
//            Object value = options.get(name);
//            if (value != null) {
//                return Strings.replaceSystemAndTestProperties(String.valueOf(value));
//            } else if (required) {
//                throw BaseSecurityMessages.MESSAGES.optionNotSet(name);
//            }
//        }
//        return null;
//    }

}
