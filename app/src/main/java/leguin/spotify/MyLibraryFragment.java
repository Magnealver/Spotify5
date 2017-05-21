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
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyLibraryFragment extends Fragment {

    private RelativeLayout layout;
    private Button Kappa;//TODO: change name, Kappa
    private Button Kappa2;
    private Button Kappa3;
    private Button Kappa5;
    private Button Kappa6;
    private Button MyPlaylists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.mylibrary,container,false);

        layout = (RelativeLayout) view.findViewById(R.id.item_mylibrary);

        Kappa = (Button) view.findViewById(R.id.Kappa);
        Kappa2 = (Button) view.findViewById(R.id.Kappa2);
        Kappa3 = (Button) view.findViewById(R.id.Kappa3);
        Kappa5 = (Button) view.findViewById(R.id.Kappa5);
        Kappa6 = (Button) view.findViewById(R.id.Kappa6);
        MyPlaylists = (Button) view.findViewById(R.id.button6);

        Kappa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "My Daily Mixes are currently unavailable!", Toast.LENGTH_SHORT).show();
            }
        });



        Kappa2.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {Toast.makeText(getContext(), "My Stations are currently unavailable!", Toast.LENGTH_SHORT).show();}
        });
        Kappa3.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {Toast.makeText(getContext(), "My Artists are currently unavailable!", Toast.LENGTH_SHORT).show();}
        });
        Kappa5.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {Toast.makeText(getContext(), "My Songs are currently unavailable!", Toast.LENGTH_SHORT).show();}
        });
        Kappa6.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {Toast.makeText(getContext(), "My Podcasts & Videos are currently unavailable!", Toast.LENGTH_SHORT).show();}
        });

        MyPlaylists.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            MyPlaylistsFragment f = new MyPlaylistsFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
        }
        });

        return view;
    }


    public void underConstruction (View view) {
        Toast.makeText(getActivity(),"UNDER CONSTRUCTION!",Toast.LENGTH_SHORT).show();
    }
}
