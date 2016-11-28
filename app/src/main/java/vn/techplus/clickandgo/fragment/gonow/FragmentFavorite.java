package vn.techplus.clickandgo.fragment.gonow;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.adapter.ListViewFavoritesApdapter;
import vn.techplus.clickandgo.database.DatabaseHandle;
import vn.techplus.clickandgo.model.Favorite;

/**
 * Created by ThanCV on 10/8/2015.
 */
public class FragmentFavorite extends Fragment {

    private ListViewFavoritesApdapter apdapter;
    private SQLiteDatabase database;
    private List<Favorite> favoriteList;
    private DatabaseHandle databaseHandle;
    private View view;
    private ListView listView;

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

            databaseHandle = new DatabaseHandle(getActivity());
            database = databaseHandle.getReadableDatabase();
            favoriteList = new ArrayList<Favorite>();
            favoriteList = databaseHandle.readDataFavorite(database);

            apdapter = new ListViewFavoritesApdapter(getActivity(), R.layout.item_favorites_layout, (ArrayList<Favorite>) favoriteList);
            listView = (ListView) view.findViewById(R.id.lvFavorite);
            listView.setAdapter(apdapter);
        } catch (InflateException e) {
        }

        return view;
    }

}
