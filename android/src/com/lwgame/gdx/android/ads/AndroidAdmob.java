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

package com.lwgame.gdx.android.ads;

import android.graphics.Color;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.lwgame.gdx.Lw;
import com.lwgame.gdx.ads.Ads;
import com.lwgame.gdx.android.ads.AdmobInterstitialAdListener;
import com.lwgame.gdx.android.ads.AdmobRewardedVideoAdListener;

public class AndroidAdmob implements Ads {

    private AndroidApplication context;
    private AdView bannerView;
    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;

    public AndroidAdmob(AndroidApplication context) {
        this.context = context;
        initialize();
        Lw.ads = this;
    }

    private void initialize() {
        String appId = Lw.configuration.getString("admob.appId");
        if (appId == null) return;

        MobileAds.initialize(context, appId);
        String bannerId = Lw.configuration.getString("admob.bannerId");
        if (bannerId != null) {
            bannerView = createBannerView(bannerId);
        }
        String interstitialId = Lw.configuration.getString("admob.interstitialId");
        if (interstitialId != null) {
            interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdUnitId(interstitialId);
            interstitialAd.setAdListener(new AdmobInterstitialAdListener(interstitialAd));
        }
        String rewardedVideoId = Lw.configuration.getString("admob.rewardedVideoId");
        if (rewardedVideoId != null) {
            rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
            rewardedVideoAd.setRewardedVideoAdListener(new AdmobRewardedVideoAdListener(rewardedVideoAd, rewardedVideoId));
        }
    }

    private AdView createBannerView(String bannerId) {
        AdView bannerView = new AdView(context);
        bannerView.setAdSize(AdSize.SMART_BANNER);
        bannerView.setAdUnitId(bannerId);
        bannerView.setBackgroundColor(Color.BLACK);

        bannerView.setVisibility(View.GONE);
        bannerView.loadAd(new AdRequest.Builder().build());

        return bannerView;
    }

    @Override
    public void show(int type) {
        switch (type) {
            case BANNER:
                if (bannerView != null) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bannerView.setVisibility(View.VISIBLE);
                        }
                    });
                }
                break;
            case INTERSTITIAL:
                if (interstitialAd != null) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (interstitialAd.isLoaded())
                                interstitialAd.show();
                        }
                    });
                }
                break;
            case REWARDED_VIDEO:
                if (rewardedVideoAd != null) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (rewardedVideoAd.isLoaded())
                                rewardedVideoAd.show();
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void hide(int type) {
        if (type == BANNER && bannerView != null) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bannerView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public boolean isLoaded(int type) {
        if (type == BANNER)
            return bannerView != null;
        if (type == INTERSTITIAL)
            return interstitialAd != null && interstitialAd.isLoaded();
        if (type == REWARDED_VIDEO)
            return rewardedVideoAd != null && rewardedVideoAd.isLoaded();
        return false;
    }

    @Override
    public void resume() {
        if (bannerView != null) bannerView.resume();
        if (rewardedVideoAd != null) rewardedVideoAd.resume(context);
    }

    @Override
    public void pause() {
        if (bannerView != null) bannerView.pause();
        if (rewardedVideoAd != null) rewardedVideoAd.pause(context);
    }

    @Override
    public void destroy() {
        if (bannerView != null) bannerView.destroy();
        if (rewardedVideoAd != null) rewardedVideoAd.destroy(context);
    }

    public View getBannerView() {
        return bannerView;
    }

}
