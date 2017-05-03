import java.util.ArrayList;

/**
 * Created by Collin on 4/24/2017.
 */
public class Album extends MusicObject {

    private ArrayList<Track> trackList;

    public Album(String id, String name, String[] imgURL, ArrayList<Track> trackList) {
        super(id, name, imgURL);
        this.trackList = trackList;
    }

    public void print()
    {
        System.out.println("Album: " + this.getName());
        System.out.println("Number of tracks: " + trackList.size());
        for(int i = 0; i < trackList.size(); i++)
        {
            System.out.print(i + ". ");
            trackList.get(i).print();
        }
    }

}
