package vn.techplus.clickandgo.fragment.gonow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.adapter.ListViewLocationApdapter;

/**
 * Created by ThanCV on 10/8/2015.
 */
public class FragmentTrain extends Fragment {

    private View view;
    private ListView listView;
    private ArrayList<String> listLocation;
    private ListViewLocationApdapter apdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Clear error duplicate id
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_favorites_layout, container, false);
        } catch (InflateException e) {
        }
        return view;
    }
}
