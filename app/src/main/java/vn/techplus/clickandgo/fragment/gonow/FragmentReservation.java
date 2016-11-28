package vn.techplus.clickandgo.fragment.gonow;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.adapter.ListViewLocationApdapter;
import vn.techplus.clickandgo.database.DatabaseHandle;
import vn.techplus.clickandgo.model.Favorite;

/**
 * Created by ThanCV on 10/8/2015.
 */
public class FragmentReservation extends Fragment {

    private View view;
    private ListView listView;
    private ListViewLocationApdapter apdapter;
    private SQLiteDatabase database;
    private List<Favorite> reservationList;
    private DatabaseHandle databaseHandle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Clear error duplicate id
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_reservation_layout, container, false);
            listView = (ListView) view.findViewById(R.id.lvReservation);

            databaseHandle = new DatabaseHandle(getActivity());
            database = databaseHandle.getReadableDatabase();
            reservationList = new ArrayList<Favorite>();
            reservationList = databaseHandle.readDataReservation(database);

            apdapter = new ListViewLocationApdapter(getActivity(), R.layout.item_location_layout, (ArrayList<Favorite>) reservationList);
            listView.setAdapter(apdapter);
        } catch (InflateException e) {
        }

        return view;
    }

}
