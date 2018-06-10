/*
 * Copyright (C) 2017-18 ColtOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.colt.settings.tabs;

import android.provider.Settings;
import android.preference.Preference;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import com.colt.settings.PagerSlidingTabStrip;
import com.colt.settings.fragments.buttonstabs.VolumeSettings;
import com.colt.settings.fragments.buttonstabs.PowerMenu;
import com.colt.settings.fragments.buttonstabs.HardwareButtons;

public class ButtonsSettingsTabs extends SettingsPreferenceFragment {

    ViewPager mViewPager;
    String titleString[];
    ViewGroup mContainer;
    PagerSlidingTabStrip mTabs;

    static Bundle mSavedState;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainer = container;

        View view = inflater.inflate(R.layout.preference_colt, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        ButtonsAdapter ButtonsAdapter = new ButtonsAdapter(getFragmentManager());
        mViewPager.setAdapter(ButtonsAdapter);
        mTabs.setViewPager(mViewPager);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.COLT;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    class ButtonsAdapter extends FragmentPagerAdapter {
        String titles[] = getTitles();
        private Fragment frags[] = new Fragment[titles.length];

        public ButtonsAdapter(FragmentManager fm) {
	      super(fm);
              frags[1] = new VolumeSettings();
              frags[2] = new PowerMenu();
		try {
		        frags[0] = new HardwareButtons();
		} catch (IndexOutOfBoundsException e) {
			// Do nothing
		}
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return frags[position];
        }

        @Override
        public int getCount() {
            return frags.length;
        }
    }

    private String[] getTitles() {
	if (getResources().getInteger(
                com.android.internal.R.integer.config_deviceHardwareKeys) > 64) {
	        return new String[] { getString(R.string.hardware_button_title),
			    getString(R.string.volume_tab_title),
                            getString(R.string.power_menu_title)};
	} else {
		return new String[] { getString(R.string.volume_tab_title),
                            getString(R.string.power_menu_title)};
	}

    }
}

