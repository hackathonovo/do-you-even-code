package eu.hackathonovo.ui.photo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mFragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<String> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return SelectedPhotoFragment.newIstance(mFragmentList.get(position));
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }



}
