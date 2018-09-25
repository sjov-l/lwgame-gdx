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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationBase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.lwgame.gdx.Lw;
import com.lwgame.gdx.ads.Ads;

public class AdmobRewardedVideoAdListener implements RewardedVideoAdListener, Runnable {

    private String rewardedVideoAdId;
    private RewardedVideoAd rewardedVideoAd;
    private Ads.RewardedVideoListener listener;
    private int retryDelayMillis, retryMaxTimes;

    public AdmobRewardedVideoAdListener(RewardedVideoAd rewardedVideoAd, String rewardedVideoAdId) {
        this.rewardedVideoAd = rewardedVideoAd;
        this.rewardedVideoAdId = rewardedVideoAdId;
        this.retryDelayMillis = Lw.configuration.getInt("admob.retryDelayMillis", 10000);
        this.retryMaxTimes = Lw.configuration.getInt("admob.retryMaxTimes", 5);
        doLoad();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Gdx.app.log("AdmobRewardedVideo", "loaded");
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
        doLoad();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        // do reward
        if (listener != null) {
            listener.onRewarded(rewardItem.getType(), rewardItem.getAmount());
            listener = null;
        }
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        if (/*errorCode == AdRequest.ERROR_CODE_NO_FILL && */retryMaxTimes > 0) {
            Gdx.app.log("AdmobRewardedVideo", "load failed, retry 5 seconds later. errorCode=" + errorCode);
            ((AndroidApplicationBase) Gdx.app).getHandler().postDelayed(this, retryDelayMillis);
            --retryMaxTimes;
        }
    }

    @Override
    public void onRewardedVideoCompleted() {
    }

    private void doLoad() {
        AdRequest.Builder builder = new AdRequest.Builder();
        String[] testDevices = Lw.configuration.getArray("admob.testDevices");
        if (testDevices != null) {
            for (String testDevice : testDevices) {
                builder.addTestDevice(testDevice);
            }
        }
        rewardedVideoAd.loadAd(rewardedVideoAdId, builder.build());
    }

    public void setListener(Ads.RewardedVideoListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        doLoad();
    }
}
