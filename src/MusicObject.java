import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Collin on 4/25/2017.
 */
public class MusicObject {

    private String id;
    private String name;
    private String[] imgURLs;

    public static enum Size
    {LARGE, MEDIUM, SMALL};



    public MusicObject(String id, String name, String[] imgURL) {
        this.id = id;
        this.name = name;
        this.imgURLs = new String[imgURL.length];
        for(int i = 0; i < imgURL.length; i++)
        {
            this.imgURLs[i] = imgURL[i];
        }
    }

    public MusicObject(JSONObject obj)
    {
        id = obj.getString("id");
        name = obj.getString("name");
        //pull the img url's from the images array in the object
        JSONArray images = obj.getJSONArray("images");
        String[] img = new String[images.length()];
        for(int i = 0; i < img.length; i++)
        {
                img[i] = images.getJSONObject(i).getString("url");
        }
        imgURLs = img;
    }


    public MusicObject() {
        id = "";
        name = "";
        imgURLs = null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgURLs(Size size) {
        switch (size)
        {
            case LARGE:
                return imgURLs[0];
            case MEDIUM:
                return imgURLs[1];
            case SMALL:
                return imgURLs[2];
        }

        return "";
    }
}
