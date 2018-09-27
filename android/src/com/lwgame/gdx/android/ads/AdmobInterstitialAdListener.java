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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.lwgame.gdx.Lw;

public class AdmobInterstitialAdListener extends AdListener implements Runnable {

    private InterstitialAd interstitialAd;
    private int retryDelayMillis, retryMaxTimes, retryTimes;

    public AdmobInterstitialAdListener(InterstitialAd interstitialAd) {
        this.interstitialAd = interstitialAd;
        this.retryDelayMillis = Lw.configuration.getInt("admob.retryDelayMillis", 10000);
        this.retryMaxTimes = Lw.configuration.getInt("admob.retryMaxTimes", 5);
        this.retryTimes = 0;
        doLoad();
    }

    @Override
    public void onAdLoaded() {
        retryTimes = 0;
        Gdx.app.log("AdmobInterstitial", "loaded");
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        if (retryTimes < retryMaxTimes) {
            Gdx.app.log("AdmobInterstitial", "load failed, retry " + retryDelayMillis + " millis later. errorCode=" + errorCode);
            ((AndroidApplicationBase) Gdx.app).getHandler().postDelayed(this, retryDelayMillis);
            ++retryTimes;
        }
    }

    @Override
    public void onAdClosed() {
        doLoad();
    }

    @Override
    public void onAdOpened() {
    }

    private void doLoad() {
        interstitialAd.loadAd(AndroidAdmob.newAdRequest());
    }

    @Override
    public void run() {
        doLoad();
    }
}
