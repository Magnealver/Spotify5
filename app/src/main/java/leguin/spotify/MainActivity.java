package leguin.spotify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback
{
    private static final String TAG = MainActivity.class.getSimpleName();

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private Player mPlayer;

    RadioGroup radioGroup1;
    //RadioButton selected;

    BottomBar mBottomBar;
    ImageButton playpause;
    boolean isPlaying = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playpause= (ImageButton)findViewById(R.id.button12);
        playpause.setOnClickListener(imgButtonHandler);

        mBottomBar = BottomBar.attach(this,savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.menu_main, new OnMenuTabClickListener() {
                    @Override
                    public void onMenuTabSelected(@IdRes int menuItemId) {
                        if (menuItemId == R.id.item_home){
                            HomeFragment f = new HomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                        }
                        if (menuItemId == R.id.item_browse){
                            BrowseFragment f = new BrowseFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                        }
                        if (menuItemId == R.id.item_search){
                            SearchFragment f = new SearchFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                        }
                        if (menuItemId == R.id.item_radio){
                            RadioFragment f = new RadioFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                        }
                        if (menuItemId == R.id.item_mylibrary){
                            MyLibraryFragment f = new MyLibraryFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                        }
                    }

                    @Override
                    public void onMenuTabReSelected(@IdRes int menuItemId) {

                    }
                }
        );
        mBottomBar.mapColorForTab(0,"#222326");
        mBottomBar.mapColorForTab(1,"#222326");
        mBottomBar.mapColorForTab(2,"#222326");
        mBottomBar.mapColorForTab(3,"#222326");
        mBottomBar.mapColorForTab(4,"#222326");
    }

    View.OnClickListener imgButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            if (isPlaying) {
                playpause.setImageResource(R.drawable.pause);
            } else {
                playpause.setImageResource(R.drawable.play);
            }
            isPlaying = !isPlaying;
        }
    };

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    public void apiTest2(View view) {
        SpotifyService test;
        SpotifyApi test2 = new SpotifyApi();

        test2.setAccessToken(CredentialsHandler.getToken(this));

        test = test2.getService();

        test.getMe(new SpotifyCallback<UserPrivate>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d(TAG, "Feelsbadman");
            }

            @Override
            public void success(UserPrivate userPrivate, Response response) {
                Log.d(TAG, "Feelsgoodman");
                Log.d(TAG, userPrivate.id);
            }
        });
    }
}


