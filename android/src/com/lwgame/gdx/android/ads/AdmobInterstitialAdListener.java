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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdmobInterstitialAdListener extends AdListener {

    private InterstitialAd interstitialAd;

    public AdmobInterstitialAdListener(InterstitialAd interstitialAd) {
        this.interstitialAd = interstitialAd;
        doLoad();
    }

    @Override
    public void onAdLoaded() {
    }

    @Override
    public void onAdClosed() {
        doLoad();
    }

    @Override
    public void onAdOpened() {
    }

    private void doLoad() {
        AdRequest req = new AdRequest.Builder().build();
        interstitialAd.loadAd(req);
    }
}