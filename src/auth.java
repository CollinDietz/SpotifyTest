import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.net.URI;
import java.util.Base64;

/**
 * Created by Collin on 4/18/2017.
 */
public class auth {
    public static void main(String args[]) {
        String client_id = "5ecc4878bafb4828a14dcac669e33ea4";
        String client_secret = "16f2d4cf0f03479baf9e792fdc7c545f";
        Base64.Encoder en = Base64.getEncoder();
        String added = client_id + ":" + client_secret;

        String encoded = "NWVjYzQ4NzhiYWZiNDgyOGExNGRjYWM2NjllMzNlYTQ6MTZmMmQ0Y2YwZjAzNDc5YmFmOWU3OTJmZGM3YzU0NWY=";
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            URI auth = new URIBuilder()
                    .setScheme("https")
                    .setHost("accounts.spotify.com")
                    .setPath("/api/token")
                    .setParameter("grant_type", "client_credentials")
                    .build();

            HttpPost post = new HttpPost(auth);
            System.out.println(post.getURI());
            post.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            post.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoded);
            Header[] h = post.getAllHeaders();
            for(int i = 0; i < h.length; i++)
            {
                System.out.println(h[i]);
            }
            System.out.println(post.toString());
            try {
                response = client.execute(post);
                response.getEntity().writeTo(System.out);
            }
            finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
