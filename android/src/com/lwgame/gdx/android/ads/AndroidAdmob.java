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

import com.badlogic.gdx.Gdx;
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

    protected AndroidApplication context;
    protected AdView bannerView;
    protected InterstitialAd interstitialAd;
    protected RewardedVideoAd rewardedVideoAd;

    public AndroidAdmob(AndroidApplication context) {
        this.context = context;
        initialize();
    }

    protected void initialize() {
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
        bannerView.loadAd(newAdRequest());

        return bannerView;
    }

    @Override
    public void show(int type) {
        show(type, null);
    }

    @Override
    public void show(int type, final Listener listener) {
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
                            if (rewardedVideoAd.isLoaded()) {
                                ((AdmobRewardedVideoAdListener) rewardedVideoAd.getRewardedVideoAdListener()).setListener((RewardedVideoListener) listener);
                                rewardedVideoAd.show();
                            }
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

    abstract class BooleanRunnable implements Runnable {
        byte state; // (result << 1) | finish
        void finish() {
            state |= 1;
        }
        boolean isFinish() {
            return (state & 1) != 0;
        }
        void setResult(boolean b) {
            state |= b ? 2 : 0;
        }
        boolean getResult() {
            return ((state >> 1) & 1) != 0;
        }
    }

    @Override
    public boolean isLoaded(int type) {
        if (type == BANNER) {
            return bannerView != null;
        }
        if (type == INTERSTITIAL) {
            if (interstitialAd == null) {
                return false;
            }
            BooleanRunnable br = new BooleanRunnable() {
                @Override
                public void run() {
                    setResult(interstitialAd.isLoaded());
                    synchronized (this) {
                        notify();
                        finish();
                    }
                }
            };
            context.runOnUiThread(br);
            synchronized (br) {
                if (!br.isFinish()) {
                    try {
                        br.wait();
                    } catch (InterruptedException e) {
                        Gdx.app.error("AndroidAdmob", e.getMessage(), e);
                    }
                }
            }
            return br.getResult();
        }
        if (type == REWARDED_VIDEO) {
            if (rewardedVideoAd == null) {
                return false;
            }
            BooleanRunnable br = new BooleanRunnable() {
                @Override
                public void run() {
                    setResult(rewardedVideoAd.isLoaded());
                    synchronized (this) {
                        notify();
                        finish();
                    }
                }
            };
            context.runOnUiThread(br);
            synchronized (br) {
                if (!br.isFinish()) {
                    try {
                        br.wait();
                    } catch (InterruptedException e) {
                        Gdx.app.error("AndroidAdmob", e.getMessage(), e);
                    }
                }
            }
            return br.getResult();
        }
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

    protected static AdRequest newAdRequest() {
        AdRequest.Builder builder = new AdRequest.Builder();
        String[] testDevices = Lw.configuration.getArray("admob.testDevices");
        if (testDevices != null) {
            for (String testDevice : testDevices) {
                builder.addTestDevice(testDevice);
            }
        }
        return builder.build();
    }

}
