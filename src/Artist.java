import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Collin on 4/24/2017.
 */
public class Artist extends MusicObject{

    private String[] genres;
    private ArrayList<Album> discography;

    public Artist(String id, String name, String[] imgURL, ArrayList<Album> disc) {
        super(id, name, imgURL);
        discography = disc;
    }

    public Artist(JSONObject object)
    {
        super(object); //construct the basic details
        JSONArray genreJSON = object.getJSONArray("genres");
        genres = new String[genreJSON.length()];
        for(int i = 0; i <  genres.length; i++){
            genres[i] = genreJSON.getString(i);
        }

    }


}
