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

/**
 * Created by ThanCV on 10/8/2015.
 */
public class ListViewLocationApdapter extends ArrayAdapter<Favorite> {

    private Context context;
    private int resource;
    private ArrayList<Favorite> listLocation;
    private TextView mOrigin, mDestination, mDuration, mDistance, mDate, mPrice;

    public ListViewLocationApdapter(Context context, int resource, ArrayList<Favorite> list) {
        super(context, resource, list);

        this.context = context;
        this.resource = resource;
        this.listLocation = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(resource, parent, false);

        //Initialize
        mOrigin = (TextView) view.findViewById(R.id.tvOrigin);
        mDestination = (TextView) view.findViewById(R.id.tvDestination);
        mDuration = (TextView) view.findViewById(R.id.tvDuration);
        mDistance = (TextView) view.findViewById(R.id.tvDistance);
        mDate = (TextView) view.findViewById(R.id.tvDate);
        mPrice = (TextView) view.findViewById(R.id.tvPrice);

        //settext
        mOrigin.setText(listLocation.get(position).getOrigin());
        mDestination.setText(listLocation.get(position).getDestination());
        mDuration.setText(listLocation.get(position).getDuration());
        mDistance.setText(listLocation.get(position).getDistance());
        mDate.setText(listLocation.get(position).getDate());
        mPrice.setText(listLocation.get(position).getTarifFinal());

        return view;
    }
}
