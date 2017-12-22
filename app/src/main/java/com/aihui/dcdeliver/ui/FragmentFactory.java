package com.aihui.dcdeliver.ui;


import com.aihui.dcdeliver.base.BaseFragment;
import com.aihui.dcdeliver.ui.fragment.HomeFragment;
import com.aihui.dcdeliver.ui.fragment.WorkSummaryFragment;


public class FragmentFactory {

    static HomeFragment        mDownFragment;
    static WorkSummaryFragment sMHomeFragment;
    static HomeFragment        sMMyFragment;


    public static BaseFragment getFragment(int position) {
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                if (mDownFragment == null) {
                    mDownFragment = new HomeFragment();
                }
                baseFragment = mDownFragment;
                break;
            case 1:
                if (sMHomeFragment == null) {
                    sMHomeFragment = new WorkSummaryFragment();
                }
                baseFragment = sMHomeFragment;
                break;
            case 2:
                if (sMMyFragment == null) {
                    sMMyFragment = new HomeFragment();
                }
                baseFragment = sMMyFragment;
                break;
            default:
                return null;
        }
        return baseFragment;

    }

    public static void clearAllFragment() {


        mDownFragment = null;
        sMHomeFragment = null;
        sMMyFragment = null;
    }


}
