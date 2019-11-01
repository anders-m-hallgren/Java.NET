package se.clouds.app.javanet.core.server;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * TlsEngine
 */
public class TlsEngine {
    private SSLContext ctx;
    private KeyStore keyStore;
    private KeyManager[] keyMgr;
    private TrustManager[] trustMgr;

    public TlsEngine() {
        super();
        CreateSSLContext();
    }
    public void CreateSSLContext() {
        try{
            //KeyStore keyStore = KeyStore.getInstance("JKS");
            //keyStore.load(new FileInputStream("test.jks"),"passphrase".toCharArray());

            keyStore = KeyStore.getInstance("PKCS12");
            char[] password= "changeit".toCharArray();

            keyStore.load(new FileInputStream("myPrivateServerCert.pfx"),password);

            // Create key manager
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keyStore, password);

            // Create trust manager
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(keyStore);

            // Initialize SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(kmf.getKeyManagers(),  tmf.getTrustManagers(), null);

            ctx = sslContext;
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public KeyStore getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }



}
