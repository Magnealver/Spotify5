package leguin.spotify;

/**
 * Created by Magne on 5/19/2017.
 */

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

public class Search {

    public interface View {
        void reset();

        void addData(List<Track> items);

        void addDataA(List<Artist> items);
    }

    public interface ActionListener {

        void init(String token);

        String getCurrentQuery();

        void search(String searchQuery);

        void loadMoreResults();

        void selectTrack(Track item);

        void selectArtist(Artist item);

        void resume();

        void pause();

        void destroy();

    }
}

