package com.bupt.wifimap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

   private RadioGroup tab_group;
    private RadioButton tab_telephone,tab_location,tab_signal;
    private ViewPager viewPager;

    // TODO: Rename and change types of parameters
    private String mParam1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(int param1) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        tab_group = (RadioGroup) rootView.findViewById(R.id.tab_group);
        tab_telephone = (RadioButton)rootView.findViewById(R.id.tab_telephone);
        tab_location = (RadioButton)rootView.findViewById(R.id.tab_location);
        tab_signal = (RadioButton)rootView.findViewById(R.id.tab_signal);

        tab_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tab_telephone:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_location:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_signal:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });

        FragmentAdapter adapter = new FragmentAdapter(
                getChildFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int id) {
                switch (id) {
                    case 0:
                        tab_telephone.setChecked(true);
                        break;
                    case 1:
                        tab_location.setChecked(true);
                        break;
                    case 2:
                        tab_signal.setChecked(true);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    return rootView;
    }

    public class FragmentAdapter extends FragmentPagerAdapter{
        public final static int TAB_COUNT = 3;
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int id) {
            switch (id) {
                case 0:
                    TelephoneFragment telephoneFragment = new TelephoneFragment();
                    return telephoneFragment;
                case 1:
                    LocationFragment locationFragment = new LocationFragment();
                    return locationFragment;
                case 2:
                    SignalFragment signalFragment = new SignalFragment();
                    return signalFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_PARAM1));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
