package vn.techplus.clickandgo.fragment.introduce;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.techplus.clickandgo.R;

/**
 * Created by ThanCV on 10/5/2015.
 */
public class FragmentAvailability extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        TextView textView = (TextView) view.findViewById(R.id.tvTitle);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgApp);

        textView.setText("AVALIABILITY");
        imageView.setImageResource(R.drawable.choice);

        return view;
    }
}
