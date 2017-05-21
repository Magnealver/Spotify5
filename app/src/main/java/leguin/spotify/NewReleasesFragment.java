package leguin.spotify;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.NewReleases;
import retrofit.client.Response;

/**
 * Created by Magne on 5/20/2017.
 */

public class NewReleasesFragment extends Fragment {

    private String accessToken;
    private LinearLayout layout;
    private ImageButton back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.newreleases,container,false);

        accessToken = CredentialsHandler.getToken(view.getContext());

        layout = (LinearLayout) view.findViewById(R.id.NewReleasesContent);

        populateScrollView();

        back = (ImageButton) view.findViewById(R.id.Back);

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            BrowseFragment f = new BrowseFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
        }
        });

        return view;
    }

    private void populateScrollView() {

        final SpotifyService test;
        SpotifyApi test2 = new SpotifyApi();

        test2.setAccessToken(accessToken);

        test = test2.getService();

        test.getNewReleases(new SpotifyCallback<NewReleases>() {
            @Override
            public void failure(SpotifyError spotifyError) {

            }

            @Override
            public void success(NewReleases newReleases, Response response) {
                for (AlbumSimple album : newReleases.albums.items) {

                        // Add text Buttons
                        TextImageButton button = new TextImageButton(getContext());
                        button.setText(album.name);
                        final String albumName = album.name;
                        button.setTextColor(Color.WHITE);
                        button.setTextSize(25);
                        button.setHeight(400);
                        button.setWidth(400);
                        button.setShadowLayer(1f, 2, 2, Color.BLACK);
                        layout.addView(button);

                        //Put album image into button
                        Picasso.with(getContext()).load(album.images.get(0).url).resize(400, 400).centerCrop().into(button);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), albumName + " is currently unavailable", Toast.LENGTH_SHORT).show();
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


