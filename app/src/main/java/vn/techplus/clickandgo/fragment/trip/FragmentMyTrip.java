package vn.techplus.clickandgo.fragment.trip;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.techplus.clickandgo.R;

/**
 * Created by AnhTien.
 */
public class FragmentMyTrip extends Fragment {
    private Bundle bundle;
    private int id;
    private int flag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getArguments from MyTripActivity
        bundle = getArguments();
        flag = bundle.getInt("FLAG");

        if (flag == 0) {
            id = R.layout.fragment_incoming;

        } else if (flag == 1) {
            id = R.layout.fragment_done;

        } else if (flag == 2) {
            id = R.layout.fragment_cancel;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(id, container, false);

        return view;
    }
}
