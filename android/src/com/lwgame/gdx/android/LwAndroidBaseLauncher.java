/******************************************************************************
 * Copyright 2018 Li Yang<ocoao.li@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************
 */

package com.lwgame.gdx.android;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.lwgame.gdx.Lw;
import com.lwgame.gdx.android.ads.AndroidAdmob;
import com.lwgame.gdx.android.billing.GooglePlayBilling;
import com.lwgame.gdx.android.platform.AndroidPlatform;
import com.lwgame.gdx.android.platform.GooglePlayGameCenter;
import com.lwgame.gdx.conf.Configuration;

public abstract class LwAndroidBaseLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		} catch (Exception ex) {
			log("AndroidApplication", "Content already displayed, cannot request FEATURE_NO_TITLE", ex);
		}
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		RelativeLayout root = new RelativeLayout(this);
		RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		root.setLayoutParams(rootLayoutParams);

		// init game view
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.numSamples = 2;
		View gameView = initializeForView(createApplicationListener(), config);
		RelativeLayout.LayoutParams gameViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		gameViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		gameViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//		gameViewLayoutParams.addRule(RelativeLayout.BELOW, adView.getId());
		gameView.setLayoutParams(gameViewLayoutParams);
		root.addView(gameView);

		Lw.configuration = new Configuration();
		Lw.billing = new GooglePlayBilling(this);
		Lw.gameCenter = new GooglePlayGameCenter(this);
		Lw.platform = new AndroidPlatform(this);

		// init admob
		AndroidAdmob admob = new AndroidAdmob(this);
		View bannerView = admob.getBannerView();
		if (bannerView != null) {
			RelativeLayout.LayoutParams bannerLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			bannerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			bannerLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
			bannerView.setLayoutParams(bannerLayoutParams);
			root.addView(bannerView);
		}
		Lw.ads = admob;

		setContentView(root);
	}

	@Override
	protected void onPause() {
		Lw.ads.pause();
		Lw.gameCenter.pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		Lw.ads.resume();
		Lw.gameCenter.resume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		Lw.ads.destroy();
		Lw.gameCenter.resume();
		super.onDestroy();
	}

	protected abstract ApplicationListener createApplicationListener();

}
