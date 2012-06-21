package demo;

import java.io.*;
import java.security.KeyStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ClientCustomSSL {

    public final static void main(String[] args) throws Exception {

        DefaultHttpClient httpclient = new DefaultHttpClient();

        try {

            KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));
            try {
            //����keyStore d:\\tomcat.keystore
                trustStore.load(instream, "123456".toCharArray());
            } finally {
                try { instream.close(); } catch (Exception ignore) {}
            }
            //����Socket����,��trustStoreע��
            SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
            //����Scheme
            Scheme sch = new Scheme("https", 8899, socketFactory);
            //ע��Scheme
            httpclient.getConnectionManager().getSchemeRegistry().register(sch);
            //����http����(get��ʽ)
            HttpGet httpget = new HttpGet("https://localhost:8899/operate/index.jsp");
            System.out.println("executing request" + httpget.getRequestLine());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
               System.out.println("Response content length: " + entity.getContentLength());
               String ss = EntityUtils.toString(entity);
               System.out.println(ss);
               EntityUtils.consume(entity);
            }
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

    }

}