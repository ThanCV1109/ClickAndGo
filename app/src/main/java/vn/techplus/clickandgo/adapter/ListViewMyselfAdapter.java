package vn.techplus.clickandgo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import vn.techplus.clickandgo.model.Address;

/**
 * Created by ThanCV on 10/10/2015.
 */
public class ListViewMyselfAdapter extends ArrayAdapter<Address> {

    private Context context;
    private int resource;
    private ArrayList<Address> list;

    public ListViewMyselfAdapter(Context context, int resource, ArrayList<Address> list) {
        super(context, resource, list);

        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(resource, parent, false);

        return view;
    }
}
