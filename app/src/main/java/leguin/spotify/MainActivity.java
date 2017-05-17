package leguin.spotify;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.concurrent.TimeUnit;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.client.Response;

public class MainActivity extends Activity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback
{
    private static final String TAG = MainActivity.class.getSimpleName();


    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "7ec97bdbb2434a779d97545c67cacbba";
    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "leguinapp://callback";
    private static final String CLIENT_SECRET = "fe8533750fef4296bbd0d3e3f2cf1516";

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private Player mPlayer;

    RadioGroup radioGroup1;
    //RadioButton selected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);
       // selected = (RadioButton)findViewById(R.id.radioButton_home);
        //radioGroup1.check(R.id.radioButton_home);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()

        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Intent in;
                Log.i("matching", "matching inside1 bro" + checkedId);
                switch (checkedId)
                {
                    case R.id.radioButton_home:
                        Log.i("matching", "matching inside1 matching" +  checkedId);
                        in=new Intent(getBaseContext(),HomeActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.radioButton_browse:
                        Log.i("matching", "matching inside1 watchlistAdapter" + checkedId);

                        in = new Intent(getBaseContext(),BrowseActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);

                        break;
                    case R.id.radioButton_search:
                        Log.i("matching", "matching inside1 rate" + checkedId);

                        in = new Intent(getBaseContext(),SearchActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.radioButton_radio:
                        Log.i("matching", "matching inside1 listing" + checkedId);
                        in = new Intent(getBaseContext(), RadioActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.radioButton_mylibrary:
                        Log.i("matching", "matching inside1 deals" + checkedId);
                        in = new Intent(getBaseContext(), MyLibraryActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    default:
                        break;
                }
            }
        });
        //kod för inloggning via Spotifys api
        /*AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);*/
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addNotificationCallback(MainActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }*/

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

    public void apiTest(View view) {
        final AuthenticationRequest request = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
                .setScopes(new String[]{"playlist-read", "user-read-private", "user-read-email", "playlist-read-private", "playlist-modify-private", "user-library-read" })
                .build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);


    }

    public void apiTest2(View view) {
        SpotifyService test;
        SpotifyApi test2 = new SpotifyApi();

        test2.setAccessToken(CredentialsHandler.getToken(this));

        test = test2.getService();

        test.getMe(new SpotifyCallback<UserPrivate>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                logError("Feelsbadman");
            }

            @Override
            public void success(UserPrivate userPrivate, Response response) {
                logMessage("Feelsgoodman");
                logMessage(userPrivate.id);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    logMessage("Got token: " + response.getAccessToken());
                    CredentialsHandler.setToken(this, response.getAccessToken(), response.getExpiresIn(), TimeUnit.SECONDS);
                    logMessage("");
                    //startMainActivity(response.getAccessToken());
                    break;

                // Auth flow returned an error
                case ERROR:
                    logError("Auth error: " + response.getError());
                    break;

                // Most likely auth flow was cancelled
                default:
                    logError("Auth result: " + response.getType());
            }
        }
    }

    private void logError(String msg) {
        Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msg);
    }

    private void logMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
/*
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void playSong(View view) {
        Log.d("MainActivity","Playing track");
        mPlayer.playUri(null, "spotify:track:4JehYebiI9JE8sR8MisGVb", 0, 0);
    }

    public void pauseSong(View view) {
        Log.d("MainActivity","Pausing track");
        mPlayer.pause(null);
    }

    public void resumeSong(View view) {
        Log.d("MainActivity","Resuming track");
        mPlayer.resume(null);
       // AuthenticationClient#clearCookies();
    }
*/
}
