import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Collin on 4/3/2017.
 */
public class Main {

    public static void main(String args[]) {
        String client_id = "5ecc4878bafb4828a14dcac669e33ea4";
        String client_secret = "16f2d4cf0f03479baf9e792fdc7c545f";

        SpotifyWrapper api = new SpotifyWrapper(client_id, client_secret);
        Scanner input = new Scanner(System.in);
        System.out.println("Please name an Artist: ");
        String query = input.nextLine();

        //Search the artist
        ArrayList<MusicObject>  artists =  api.search(query, SpotifyWrapper.SearchType.ARTIST);
        //selection loop
        Artist one = null;
        boolean isCorrect = false;
        for(int i = 0; i < artists.size(); i++)
        {
            System.out.println("Is this the artist you were talking about?");
            System.out.println(artists.get(i).getName() + "\t" + artists.get(i).getImgURLs(MusicObject.Size.LARGE) + "\t"
                    + artists.get(i).getId());

            String response = input.next();
            if(response.equals("yes"))
            {
                one = api.makeArtist(artists.get(i));
                break;
            }
            else if(response.equals("no"))
            {
                continue;
            }
            else
            {
                System.out.println("That is not a valid response. Enter y or n");
            }
        }

        one.print();
    }
}
