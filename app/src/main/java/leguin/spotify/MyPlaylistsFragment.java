package leguin.spotify;

import android.graphics.Color;
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
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import retrofit.client.Response;

/**
 * Created by Magne on 5/20/2017.
 */

public class MyPlaylistsFragment extends Fragment {

    private String accessToken;
    private LinearLayout layout;
    private LinearLayout layoutHorizontal;
    private ImageButton back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.myplaylists,container,false);

        accessToken = CredentialsHandler.getToken(view.getContext());

        layout = (LinearLayout) view.findViewById(R.id.MyPlaylists);
        //layoutHorizontal = (LinearLayout) view.findViewById(R.id.MyPlaylistsHorizontal);

        populateScrollView();

        back = (ImageButton) view.findViewById(R.id.Back);

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            MyLibraryFragment f = new MyLibraryFragment();
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

        test.getMyPlaylists(new SpotifyCallback<Pager<PlaylistSimple>>() {
            @Override
            public void success(Pager<PlaylistSimple> playlistSimplePager, Response response) {
                for (PlaylistSimple item : playlistSimplePager.items) {

                    // Add Buttons
                    Button button = new Button(getContext());
                    button.setText(item.name);
                    final String PlaylistName = item.name;
                    button.getBackground().setAlpha(0);
                    button.setTextColor(Color.WHITE);
                    layout.addView(button);

                    // Add playlist imagebutton
                    ImageButton image = new ImageButton(getContext());
                    image.setBackgroundColor(121314);
                    Picasso.with(getContext()).load(item.images.get(0).url).resize(400,400).centerCrop().into(image);
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO: Call View playlist or Play playlist method
                            Toast.makeText(getContext(), "Playing playlist "+PlaylistName, Toast.LENGTH_SHORT).show();
                        }
                    });
                    layout.addView(image);
                    //layoutHorizontal.addView(image);

                    //layout.addView(layoutHorizontal);
                }
            }

            @Override
            public void failure(SpotifyError spotifyError) {

            }
        });
    }
}
