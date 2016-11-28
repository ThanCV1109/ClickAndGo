package vn.techplus.clickandgo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import vn.techplus.clickandgo.fragment.introduce.FragmentAddressTyping;
import vn.techplus.clickandgo.fragment.introduce.FragmentAvailability;
import vn.techplus.clickandgo.fragment.introduce.FragmentChoice;
import vn.techplus.clickandgo.fragment.introduce.FragmentFixedPrices;
import vn.techplus.clickandgo.fragment.introduce.FragmentQuality;
import vn.techplus.clickandgo.fragment.introduce.FragmentTrip;
import vn.techplus.clickandgo.fragment.introduce.FragmentWelcome;

/**
 * Created by ThanCV on 10/5/2015.
 */
public class FragmentIntroduceAdapter extends FragmentPagerAdapter {

    public FragmentIntroduceAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new FragmentWelcome();
        switch (position) {
            case 0:
                fragment = new FragmentWelcome();
                break;
            case 1:
                fragment = new FragmentTrip();
                break;
            case 2:
                fragment = new FragmentAvailability();
                break;
            case 3:
                fragment = new FragmentAddressTyping();
                break;
            case 4:
                fragment = new FragmentFixedPrices();
                break;
            case 5:
                fragment = new FragmentQuality();
                break;
            case 6:
                fragment = new FragmentChoice();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 7;
    }
}
