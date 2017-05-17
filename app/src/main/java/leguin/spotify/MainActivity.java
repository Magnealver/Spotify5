package leguin.spotify;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
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
import com.wrapper.spotify.Api;
import com.wrapper.spotify.models.AuthorizationCodeCredentials;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MainActivity extends Activity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback
{

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
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
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
    }

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

    /*public void apiTest2(View view) {
        SpotifyApi api = new SpotifyApi();

        /*AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

// Most (but not all) of the Spotify Web API endpoints require authorisation.
// If you know you'll only use the ones that don't require authorisation you can skip this step
        api.setAccessToken("test");

        SpotifyService spotify = api.getService();

        spotify.getAlbum("2dIGnmEIy1WZIcZCFSj6i8", new Callback<Album>() {
            @Override
            public void success(Album album, Response response) {
                Log.d("Album success", album.name);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Album failure", error.toString());
            }
        });
    } */

    public void apiTest(View view) {

        final Api api = Api.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectURI(REDIRECT_URI)
                .build();

/* Set the necessary scopes that the application will need from the user */
        final List<String> scopes = Arrays.asList("user-read-private", "user-read-email", "playlist-read-private", "playlist-modify-private", "user-library-read");

/* Set a state. This is used to prevent cross site request forgeries. */
        final String state = "leguinhype";

        String authorizeURL = api.createAuthorizeURL(scopes, state);

/* Continue by sending the user to the authorizeURL, which will look something like
   https://accounts.spotify.com:443/authorize?client_id=5fe01282e44241328a84e7c5cc169165&response_type=code&redirect_uri=https://example.com/callback&scope=user-read-private%20user-read-email&state=some-state-of-my-choice
 */
        goToUrl(authorizeURL);


    /* Application details necessary to get an access token */
        Pattern pattern = Pattern.compile("(?<=code=)(.*)(?=&)");
        Matcher matcher = pattern.matcher(authorizeURL);

        final String code = "test";//matcher.group(1);

    /* Make a token request. Asynchronous requests are made with the .getAsync method and synchronous requests
     * are made with the .get method. This holds for all type of requests. */
        final SettableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = api.authorizationCodeGrant(code).build().getAsync();

/* Add callbacks to handle success and failure */
        Futures.addCallback(authorizationCodeCredentialsFuture, new FutureCallback<AuthorizationCodeCredentials>() {
            @Override
            public void onSuccess(AuthorizationCodeCredentials authorizationCodeCredentials) {
    /* The tokens were retrieved successfully! */
                System.out.println("Successfully retrieved an access token! " + authorizationCodeCredentials.getAccessToken());
                System.out.println("The access token expires in " + authorizationCodeCredentials.getExpiresIn() + " seconds");
                System.out.println("Luckily, I can refresh it using this refresh token! " + authorizationCodeCredentials.getRefreshToken());

    /* Set the access token and refresh token so that they are used whenever needed */
                api.setAccessToken(authorizationCodeCredentials.getAccessToken());
                api.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            }

            @Override
            public void onFailure(Throwable throwable) {
    /* Let's say that the client id is invalid, or the code has been used more than once,
     * the request will fail. Why it fails is written in the throwable's message. */
                System.out.println("idk what the fuk went wrong ok" + throwable.getMessage());
            }
        });
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
