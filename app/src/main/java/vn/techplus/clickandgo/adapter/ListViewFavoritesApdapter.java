package vn.techplus.clickandgo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.model.Favorite;
import vn.techplus.clickandgo.rest.InvokeWS;

/**
 * Created by ThanCV on 10/8/2015.
 */
public class ListViewFavoritesApdapter extends ArrayAdapter<Favorite> {

    private Context context;
    private int resource;
    private ArrayList<Favorite> listFavorite;
    TextView mOrigin, mDestination;

    public ListViewFavoritesApdapter(Context context, int resource, ArrayList<Favorite> list) {
        super(context, resource, list);

        this.context = context;
        this.resource = resource;
        this.listFavorite = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(resource, parent, false);

        mOrigin = (TextView) view.findViewById(R.id.tvOrigin);
        mDestination = (TextView) view.findViewById(R.id.tvDestination);

        mOrigin.setText(listFavorite.get(position).getOrigin());
        mDestination.setText(listFavorite.get(position).getDestination());

        return view;
    }
}
