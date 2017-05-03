/**
 * Created by Collin on 4/24/2017.
 */
public class Track extends MusicObject {

    private int duration_ms;
    private String preview_URL;
    private int popularity;

    public Track(String id, String name, String[] imgURL, int duration_ms, String preview_URL, int popularity) {
        super(id, name, imgURL);
        this.duration_ms = duration_ms;
        this.preview_URL = preview_URL;
        this.popularity = popularity;
    }

    public void print()
    {
        System.out.println(getName());
    }

}
