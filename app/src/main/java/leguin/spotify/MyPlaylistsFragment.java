package leguin.spotify;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    public final ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.myplaylists,container,false);

        accessToken = CredentialsHandler.getToken(view.getContext());

        /////
        layout = (LinearLayout) view.findViewById(R.id.MyPlaylists);

        populateScrollView();

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

                    String url = "";
                    int smallestWidth = 0;
                    int smallestHeight = 0;

                    //Bitmap bitmap = Utils.getBitmapFromURL(url);

                    //Drawable drawable = new BitmapDrawable(getResources(), bitmap);

                    // Add Buttons
                    image = (ImageView) view.findViewById(R.id.entity_image);
                    Button button = new Button(getContext());
                    button.setText(item.name);
                    button.getBackground().setAlpha(0);
                    button.setTextColor(Color.WHITE);
                    //button.setCompoundDrawables(null, drawable, null, null);
                    layout.addView(button);
                }
            }

            @Override
            public void failure(SpotifyError spotifyError) {

            }
        });
    }
}
