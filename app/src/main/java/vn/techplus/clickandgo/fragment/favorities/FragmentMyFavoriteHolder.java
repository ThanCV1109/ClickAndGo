package vn.techplus.clickandgo.fragment.favorities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.techplus.clickandgo.R;

/**
 * Created by Administrator on 13/10/2015.
 */
public class FragmentMyFavoriteHolder extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_holder,container,false);
    }
}
