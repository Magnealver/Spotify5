package leguin.spotify;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.spotify.sdk.android.player.Spotify;

public class MainActivity extends AppCompatActivity
{

    BottomBar mBottomBar;
    ImageButton playpause;
    TextView song;

    private final Context mContext = this;
    private leguin.spotify.Player mPlayer;

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayer = ((PlayerService.PlayerBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPlayer = null;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                       // TODO: Create Previous Song method
                       // PreviousSong();
                    }

                    // Right to left swipe action
                    else
                    {
                        // TODO: Create Next Song method
                        // NextSong();
                    }

                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playpause= (ImageButton)findViewById(R.id.button12);
        playpause.setOnClickListener(imgButtonHandler);
        song = (TextView) findViewById(R.id.textView6);
        song.setText("Not playing any track currently");

        mContext.bindService(PlayerService.getIntent(mContext), mServiceConnection, Activity.BIND_AUTO_CREATE);

        mBottomBar = BottomBar.attach(this,savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.menu_main, new OnMenuTabClickListener() {
                    @Override
                    public void onMenuTabSelected(@IdRes int menuItemId) {
                        if (menuItemId == R.id.item_home){
                            HomeFragment f = new HomeFragment();
                            Drawable mDrawable = getApplicationContext().getResources().getDrawable(R.drawable.ic_home_black_24dp);
                            mDrawable.setColorFilter(1947988, PorterDuff.Mode.MULTIPLY);
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
            if (mPlayer.isPlaying()) {
                pauseIcon();
                mPlayer.pause();
                song.setText("Not playing any track currently");
            } else {
                playIcon();
                mPlayer.resume();
                song.setText("Playing track");
            }

        }
    };

    public void pauseIcon() {
        playpause.setImageResource(R.drawable.play);
    }

    public void playIcon() {
        playpause.setImageResource(R.drawable.pause);
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}


