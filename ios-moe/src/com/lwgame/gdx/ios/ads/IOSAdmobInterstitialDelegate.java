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

import com.lwgame.gdx.ios.bindings.googlemobileads.GADInterstitial;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADRequestError;
import com.lwgame.gdx.ios.bindings.googlemobileads.protocol.GADInterstitialDelegate;

public class IOSAdmobInterstitialDelegate implements GADInterstitialDelegate {

    private GADInterstitial gadInterstitial;

    public IOSAdmobInterstitialDelegate(GADInterstitial gadInterstitial) {
        this.gadInterstitial = gadInterstitial;
        doLoad();
    }

    @Override
    public void interstitialDidFailToReceiveAdWithError(GADInterstitial ad, GADRequestError error) {
    }

    @Override
    public void interstitialDidFailToPresentScreen(GADInterstitial ad) {
    }

    @Override
    public void interstitialDidReceiveAd(GADInterstitial ad) {
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
        this.gadInterstitial = GADInterstitial.alloc().initWithAdUnitID(ad.adUnitID());
        doLoad();
    }

    private void doLoad() {
        gadInterstitial.setDelegate(this);
        gadInterstitial.loadRequest(IOSAdmob.newGADRequest());
    }

    public GADInterstitial getAd() {
        return gadInterstitial;
    }
}
