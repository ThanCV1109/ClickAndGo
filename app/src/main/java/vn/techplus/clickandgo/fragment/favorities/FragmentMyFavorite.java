package vn.techplus.clickandgo.fragment.favorities;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
 * Created by ThanCV on 10/9/2015.
 */
public class FragmentMyFavorite extends Fragment {
    private Bundle bundle;
    private int flag;
    private int id;
    private ListViewFavoritesApdapter apdapter;
    private ListView listView;
    private SQLiteDatabase database;
    private List<Favorite> favoriteList;
    private DatabaseHandle databaseHandle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get bundle from MyFavoritesActivity;
        bundle = getArguments();
        flag = bundle.getInt("FLAG");

        if (flag == 0) {
            id = R.layout.fragment_addresses_layout;
        } else if (flag == 1) {
            id = R.layout.fragment_passenger_layout;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addresses_layout, container, false);

        databaseHandle = new DatabaseHandle(getActivity());
        database = databaseHandle.getReadableDatabase();
        favoriteList = new ArrayList<Favorite>();
        favoriteList = databaseHandle.readDataFavorite(database);

        apdapter = new ListViewFavoritesApdapter(getActivity(), R.layout.item_favorites_layout, (ArrayList<Favorite>) favoriteList);
        listView = (ListView) view.findViewById(R.id.lvMyFavorite);
        listView.setAdapter(apdapter);

        return view;
    }

}
