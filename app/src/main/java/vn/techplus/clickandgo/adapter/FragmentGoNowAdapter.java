package vn.techplus.clickandgo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.fragment.gonow.FragmentTrain;
import vn.techplus.clickandgo.fragment.gonow.FragmentReservation;
import vn.techplus.clickandgo.fragment.gonow.FragmentMap;
import vn.techplus.clickandgo.fragment.gonow.FragmentPlane;
import vn.techplus.clickandgo.fragment.gonow.FragmentFavorite;

/**
 * Created by ThanCV on 10/5/2015.
 */
public class FragmentGoNowAdapter extends FragmentPagerAdapter {

    private int[] iconTab = {R.drawable.global, R.drawable.favorite, R.drawable.map, R.drawable.airport, R.drawable.transport};
    private Context context;

    public FragmentGoNowAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentMap();
            case 1:
                return new FragmentFavorite();
            case 2:
                return new FragmentReservation();
            case 3:
                return new FragmentPlane();
            case 4:
                return new FragmentTrain();
            default:
                return new FragmentMap();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = context.getResources().getDrawable(iconTab[position]);
        //image.setBounds(0, 0, 10, 10);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;

    }


}
