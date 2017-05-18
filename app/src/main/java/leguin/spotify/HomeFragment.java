package leguin.spotify;

/**
 * Created by Alexanders on 2017-05-14.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import retrofit.client.Response;


/**
 * Created by Alexanders on 2017-05-14.
 */

public class HomeFragment extends Fragment {

    private LinearLayout layout;

    private String accessToken;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    layout.addView(button);
                }
            }

            @Override
            public void failure(SpotifyError spotifyError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home,container,false);

        accessToken = CredentialsHandler.getToken(view.getContext());

        layout = (LinearLayout) view.findViewById(R.id.made_for_you);

        populateScrollView();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    /*@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/
}
