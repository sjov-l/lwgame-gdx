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

package com.lwgame.gdx.ios.ads;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.lwgame.gdx.Lw;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADInterstitial;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADRequestError;
import com.lwgame.gdx.ios.bindings.googlemobileads.protocol.GADInterstitialDelegate;

public class IOSAdmobInterstitialDelegate implements GADInterstitialDelegate {

    private GADInterstitial gadInterstitial;
    private int retryDelayMillis, retryMaxTimes, retryTimes;

    public IOSAdmobInterstitialDelegate(String unitId) {
        this.retryDelayMillis = Lw.configuration.getInt("admob.retryDelayMillis", 10000);
        this.retryMaxTimes = Lw.configuration.getInt("admob.retryMaxTimes", 5);
        this.retryTimes = 0;
        doLoad(unitId);
    }

    @Override
    public void interstitialDidFailToReceiveAdWithError(GADInterstitial ad, GADRequestError error) {
        if (retryTimes < retryMaxTimes) {
            Gdx.app.log("AdmobInterstitial", "load failed, retry " + retryDelayMillis + " millis later. errorCode=" + (error != null ? error.code() : "unknown"));
            final String unitId = ad.adUnitID();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    doLoad(unitId);
                }
            }, retryDelayMillis / 1000f);
            ++retryTimes;
        }
    }

    @Override
    public void interstitialDidFailToPresentScreen(GADInterstitial ad) {
    }

    @Override
    public void interstitialDidReceiveAd(GADInterstitial ad) {
        retryTimes = 0;
        Gdx.app.log("AdmobInterstitial", "loaded");
    }

    @Override
    public void interstitialWillDismissScreen(GADInterstitial ad) {
    }

    @Override
    public void interstitialWillLeaveApplication(GADInterstitial ad) {
    }

    @Override
    public void interstitialWillPresentScreen(GADInterstitial ad) {
    }

    @Override
    public void interstitialDidDismissScreen(GADInterstitial ad) {
        doLoad(ad.adUnitID());
    }

    private void doLoad(String unitId) {
        gadInterstitial = GADInterstitial.alloc().initWithAdUnitID(unitId);
        gadInterstitial.setDelegate(this);
        gadInterstitial.loadRequest(IOSAdmob.newGADRequest());
    }

    public GADInterstitial getAd() {
        return gadInterstitial;
    }
}
