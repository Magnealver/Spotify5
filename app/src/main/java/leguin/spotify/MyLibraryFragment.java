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
import android.widget.Toast;

public class MyLibraryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.mylibrary,container,false);
        return view;
    }

    public void underConstruction (View view) {
        Toast.makeText(getActivity(),"UNDER CONSTRUCTION!",Toast.LENGTH_SHORT).show();
    }
}
