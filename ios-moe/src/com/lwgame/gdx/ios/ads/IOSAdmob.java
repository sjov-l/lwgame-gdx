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

import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.lwgame.gdx.Lw;
import com.lwgame.gdx.ads.Ads;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADBannerView;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADInterstitial;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADMobileAds;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADRequest;
import com.lwgame.gdx.ios.bindings.googlemobileads.c.GoogleMobileAds;

import apple.coregraphics.struct.CGPoint;
import apple.coregraphics.struct.CGRect;
import apple.coregraphics.struct.CGSize;
import apple.foundation.NSArray;
import apple.foundation.NSMutableArray;
import apple.uikit.UIScreen;

public class IOSAdmob implements Ads {

    protected IOSApplication app;

    protected GADBannerView gadBannerView;
    protected IOSAdmobInterstitialDelegate gadInterstitialDelegate;
    protected IOSAdmobRewardVideoAdDelegate gadRewardVideoAdDelegate;

    public IOSAdmob(IOSApplication app) {
        this.app = app;
        initialize();
    }

    protected void initialize() {
        String appId = Lw.configuration.getString("admob.appId");
        if (appId == null) return;
        GADMobileAds.configureWithApplicationID(appId);

        String bannerId = Lw.configuration.getString("admob.bannerId");
        if (bannerId != null) {
            GADBannerView gadBannerView = GADBannerView.alloc().initWithAdSize(GoogleMobileAds.kGADAdSizeBanner());
            gadBannerView.setAdUnitID(bannerId);
            gadBannerView.setRootViewController(app.getUIViewController());
            app.getUIViewController().view().addSubview(gadBannerView);
            gadBannerView.loadRequest(newGADRequest());
            this.gadBannerView = gadBannerView;
        }

        String interstitialId = Lw.configuration.getString("admob.interstitialId");
        if (interstitialId != null) {
            gadInterstitialDelegate = new IOSAdmobInterstitialDelegate(interstitialId);
        }

        String rewardVideoAdId = Lw.configuration.getString("admob.rewardedVideoId");
        if (rewardVideoAdId != null) {
            gadRewardVideoAdDelegate = new IOSAdmobRewardVideoAdDelegate(rewardVideoAdId);
        }
    }

    static GADRequest newGADRequest() {
        GADRequest req = GADRequest.request();
        NSArray testDevicesArr = NSMutableArray.alloc().init();
        testDevicesArr.add(GoogleMobileAds.kGADSimulatorID());
        String[] testDevices = Lw.configuration.getArray("admob.testDevices");
        if (testDevices != null) {
            for (String dev : testDevices) {
                testDevicesArr.add(dev);
            }
        }
        req.setTestDevices(testDevicesArr);
        return req;
    }

    @Override
    public void show(int type) {
        show(type, null);
    }

    @Override
    public void show(int type, Listener listener) {
        switch (type) {
            case BANNER:
                if (gadBannerView != null) {
                    CGSize size = gadBannerView.adSize().size();
                    CGSize screen = UIScreen.mainScreen().bounds().size();
                    gadBannerView.setFrame(new CGRect(new CGPoint((screen.width() - size.width()) / 2, screen.height() - size.height()), size));
                }
                break;
            case INTERSTITIAL:
                if (gadInterstitialDelegate != null && gadInterstitialDelegate.getAd().isReady() && !gadInterstitialDelegate.getAd().hasBeenUsed()) {
                    gadInterstitialDelegate.getAd().presentFromRootViewController(app.getUIViewController());
                }
                break;
            case REWARDED_VIDEO:
                if (gadRewardVideoAdDelegate != null && gadRewardVideoAdDelegate.getAd().isReady()) {
                    gadRewardVideoAdDelegate.setListener((RewardedVideoListener) listener);
                    gadRewardVideoAdDelegate.getAd().presentFromRootViewController(app.getUIViewController());
                }
                break;
        }
    }

    @Override
    public void hide(int type) {
        if (type == BANNER && gadBannerView != null) {
            CGSize size = gadBannerView.adSize().size();
            gadBannerView.setFrame(new CGRect(new CGPoint(0, -size.height()), size));
        }
    }

    @Override
    public boolean isLoaded(int type) {
        if (type == BANNER)
            return gadBannerView != null;
        if (type == INTERSTITIAL)
            return gadInterstitialDelegate != null && gadInterstitialDelegate.getAd().isReady();
        if (type == REWARDED_VIDEO)
            return gadRewardVideoAdDelegate != null && gadRewardVideoAdDelegate.getAd().isReady();
        return false;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
    }
}
