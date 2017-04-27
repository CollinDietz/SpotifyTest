import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Created by Collin on 4/19/2017.
 */
public class SpotifyWrapper {

    private String token;

    public static enum SearchType {
        ARTIST("artist"), ALBUM("album"), TRACK("track");

        private final String id;
        SearchType(String s) {id = s;}
        public String toString() { return  id;}

    };

    SpotifyWrapper(String client_id, String client_secret)
    {
        token = getAccessToken(client_id, client_secret);
    }

    private JSONObject executeGet(URI uri)
    {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        JSONObject result = null;
        try
        {
            client = HttpClients.createDefault();
            HttpGet get = new HttpGet(uri);
            get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            response = client.execute(get);
            result = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));

        }catch (Exception e)
        {
            //TODO catch errors
            e.printStackTrace();
        }
        finally {
            try
            {
                response.close();
                client.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return result;
    }

    private String getAccessToken(String client_id, String client_secret)
    {
        //make base 64 encoded string for client details
        Base64.Encoder en = Base64.getEncoder();
        String encoded = en.encodeToString((client_id + ":" + client_secret).getBytes());
        String token = "";

        CloseableHttpResponse response = null;
        try
        {
            CloseableHttpClient client = HttpClients.createDefault();

            //build URI for post request
            URI auth = new URIBuilder()
                    .setScheme("https")
                    .setHost("accounts.spotify.com")
                    .setPath("/api/token")
                    .setParameter("grant_type", "client_credentials")
                    .build();

            //make post and set headers
            HttpPost post = new HttpPost(auth);
            post.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            post.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoded);

            //execute response
            response = client.execute(post);

            //convert response to JSON
            JSONObject responseJSON = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));

            token = responseJSON.getString("access_token");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                response.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return token;

    }

    public ArrayList<MusicObject> search(String query, SearchType type)
    {
        ArrayList<MusicObject> stringResults = new ArrayList<>();

        //encode spaces!!
        query = query.replace(" ", "+");

        //make URI
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.spotify.com")
                    .setPath("/v1/search")
                    .setParameter("q", query)
                    .setParameter("market", "US")
                    .setParameter("type", type.toString())
                    .build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //execute search
        JSONObject searchResults = executeGet(uri);
        //pull list of results and return
        searchResults =  searchResults.getJSONObject(type.toString() + "s");
        JSONArray items = searchResults.getJSONArray("items");
        for(int i = 0; i < items.length(); i++)
        {
            JSONObject currObject = items.getJSONObject(i);
            MusicObject item = new MusicObject(currObject);
            stringResults.add(item);
        }

        return stringResults;
    }

    public Artist makeArtist(MusicObject m)
    {
        return makeArtist(m.getId());
    }

    public Artist makeArtist(String id)
    {
    }

    public Album makeAlbum(String id)
    {

    }

    public Track makeTrack(String id)
    {

    }



}
