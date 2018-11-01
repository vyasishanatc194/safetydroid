package com.mysafetynet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.fragments.ChildListFragment;
import com.mysafetynet.fragments.OrderListFragment;
import com.mysafetynet.fragments.ParentSettingScreen;
import com.mysafetynet.fragments.PlanListlFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ParentTabActivity extends AppCompatActivity {


    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.main_content)
    RelativeLayout mainContent;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_tab);
        ButterKnife.bind(this);
        hideItems();
        initTabs();
    }

    private void hideItems() {
        imbgDone.setVisibility(View.INVISIBLE);
        imbgBack.setVisibility(View.GONE);

    }

    private void initTabs() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        imbgDone.setVisibility(View.VISIBLE);
                        mViewPager.setCurrentItem(tab.getPosition());
                        txtTitle.setText("HOME");
                        break;
                    case 1:
                        imbgDone.setVisibility(View.INVISIBLE);
                        mViewPager.setCurrentItem(tab.getPosition());
                        txtTitle.setText("ORDERS");
                        break;
                    case 2:
                        imbgDone.setVisibility(View.INVISIBLE);
                        mViewPager.setCurrentItem(tab.getPosition());
                        txtTitle.setText("SUBSCRIPTIONS");
                        break;
                    case 3:
                        imbgDone.setVisibility(View.INVISIBLE);
                        mViewPager.setCurrentItem(tab.getPosition());
                        txtTitle.setText("SETTING");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        imbgDone.setVisibility(View.VISIBLE);
                        mViewPager.setCurrentItem(tab.getPosition());
                        break;
                    case 1:
                        imbgDone.setVisibility(View.INVISIBLE);
                        mViewPager.setCurrentItem(tab.getPosition());
                        break;
                    case 2:
                        imbgDone.setVisibility(View.INVISIBLE);
                        mViewPager.setCurrentItem(tab.getPosition());
                        break;
                    case 3:
                        imbgDone.setVisibility(View.INVISIBLE);
                        mViewPager.setCurrentItem(tab.getPosition());
                        break;
                }
            }
        });
    }

    @OnClick({R.id.imbgBack, R.id.imbgDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imbgBack:
                onBackPressed();
                break;
            case R.id.imbgDone:
                Intent intent = new Intent(ParentTabActivity.this, AddChildActivity.class);
                startActivity(intent);
                break;
        }
    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return ChildListFragment.newInstance();
            } else if (position == 1) {
                return OrderListFragment.newInstance();
            } else if (position == 2) {
                return PlanListlFragment.newInstance();
            } else if (position == 3) {
                return ParentSettingScreen.newInstance();
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
