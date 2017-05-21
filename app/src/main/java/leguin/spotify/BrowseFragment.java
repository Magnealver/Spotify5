package leguin.spotify;

/**
 * Created by Alexanders on 2017-05-14.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.FeaturedPlaylists;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import retrofit.client.Response;


/**
 * Created by Alexanders on 2017-05-14.
 */

public class BrowseFragment extends Fragment {

    private String accessToken;
    //private RelativeLayout layout;
    private LinearLayout layout;
    private Button Kappa;//TODO: change name, Kappa
    private Button Kappa2;
    private Button Kappa3;
    private Button Kappa4;
    private Button Kappa5;
    private Button Kappa6;
    private Button NewReleases;
    private Button Discover;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.browse2,container,false);

        accessToken = CredentialsHandler.getToken(view.getContext());

        layout = (LinearLayout) view.findViewById(R.id.featured_content);

        populateScrollView();

        //layout = (RelativeLayout) view.findViewById(R.id.item_browse);


        Kappa2 = (Button) view.findViewById(R.id.Kappa2);
        Kappa3 = (Button) view.findViewById(R.id.Kappa3);
        Kappa5 = (Button) view.findViewById(R.id.Kappa5);
        Kappa6 = (Button) view.findViewById(R.id.Kappa6);
        NewReleases = (Button) view.findViewById(R.id.button6);
        Discover = (Button) view.findViewById(R.id.discoverbutton);

        NewReleases.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            NewReleasesFragment f = new NewReleasesFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
        }
        });


        Kappa2.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {Toast.makeText(getContext(), "Charts are currently unavailable!", Toast.LENGTH_SHORT).show();}
        });
        Kappa3.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {Toast.makeText(getContext(), "Genres & Moods are currently unavailable!", Toast.LENGTH_SHORT).show();}
        });
        Kappa5.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {Toast.makeText(getContext(), "Concerts are currently unavailable!", Toast.LENGTH_SHORT).show();}
        });
        Discover.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {Toast.makeText(getContext(), "Discover is currently unavailable!", Toast.LENGTH_SHORT).show();}
        });

        return view;
    }

    private void populateScrollView() {

        final SpotifyService test;
        SpotifyApi test2 = new SpotifyApi();

        test2.setAccessToken(accessToken);

        test = test2.getService();

        test.getFeaturedPlaylists(new SpotifyCallback<FeaturedPlaylists>() {
            @Override
            public void failure(SpotifyError spotifyError) {

            }

            @Override
            public void success(FeaturedPlaylists featuredPlaylists, Response response) {
                for (PlaylistSimple playlists : featuredPlaylists.playlists.items) {

                    // Add text Buttons
                    ImageButton button = new ImageButton(getContext());
                    button.setBackgroundColor(121314);

                    final String playlistName = playlists.name;
                    layout.addView(button);

                    //Put album image into button
                    Picasso.with(getContext()).load(playlists.images.get(0).url).resize(400, 400).centerCrop().into(button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO: Call View playlist or Play playlist method
                            Toast.makeText(getContext(), playlistName + " is currently unavailable", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //Adds space between each album
                    View space = new View(getContext());
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(50, 0);
                    space.setLayoutParams(p);
                    layout.addView(space);
                }
            }
        });


    }
}
