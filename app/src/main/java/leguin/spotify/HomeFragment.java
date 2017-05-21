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
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeFragment extends Fragment {


    private String accessToken;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home,container,false);

        accessToken = CredentialsHandler.getToken(view.getContext());

        ImageButton addPin = (ImageButton) view.findViewById(R.id.addPin);

        addPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Pins are currently unavailable", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
