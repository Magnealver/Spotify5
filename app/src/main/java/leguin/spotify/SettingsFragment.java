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

/**
 * Created by Magne on 5/20/2017.
 */

public class SettingsFragment extends Fragment {

    private String accessToken;
    private LinearLayout layout;
    private ImageButton back;
    private Button changeTheme;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.settings,container,false);

        back = (ImageButton) view.findViewById(R.id.Back);

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            MyLibraryFragment f = new MyLibraryFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
        }
        });

        changeTheme = (Button) view.findViewById(R.id.settingsbutton);

        changeTheme.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {

            Toast.makeText(getContext(),"Additional themes are currently unavailable", Toast.LENGTH_SHORT).show();
            /* What we want to happen

            if (current style = default) {
                style.loadWhite
                Toast.makeText("Theme changed to White")
            } else {
                style.loadDefault
                Toast.makeText("Theme changed to Default"}
                */
        }
        });

        return view;
    }
}



