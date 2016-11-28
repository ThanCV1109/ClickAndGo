package vn.techplus.clickandgo.fragment.trip;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.techplus.clickandgo.R;

/**
 * Created by Administrator on 14/10/2015.
 */
public class FragmentMyTripHolder extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_holder, container, false);
    }
}
