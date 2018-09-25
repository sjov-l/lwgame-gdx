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
import com.lwgame.gdx.ads.Ads;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADAdReward;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADRewardBasedVideoAd;
import com.lwgame.gdx.ios.bindings.googlemobileads.protocol.GADRewardBasedVideoAdDelegate;

import apple.foundation.NSError;

public class IOSAdmobRewardVideoAdDelegate implements GADRewardBasedVideoAdDelegate {

    private String unitId;
    private GADRewardBasedVideoAd gadRewardBasedVideoAd;
    private Ads.RewardedVideoListener listener;
    private int retryDelayMillis, retryMaxTimes, retryTimes;

    public IOSAdmobRewardVideoAdDelegate(String unitId) {
        this.unitId = unitId;
        this.gadRewardBasedVideoAd = GADRewardBasedVideoAd.sharedInstance();
        this.gadRewardBasedVideoAd.setDelegate(this);
        this.gadRewardBasedVideoAd.loadRequestWithAdUnitID(IOSAdmob.newGADRequest(), unitId);
        this.retryDelayMillis = Lw.configuration.getInt("admob.retryDelayMillis", 10000);
        this.retryMaxTimes = Lw.configuration.getInt("admob.retryMaxTimes", 5);
        this.retryTimes = 0;
    }

    @Override
    public void rewardBasedVideoAdDidFailToLoadWithError(GADRewardBasedVideoAd rewardBasedVideoAd, NSError error) {
        if (error == null) {
            Gdx.app.log("AdmobRewardedVideo", "load failed with unknown error.");
            return;
        }
        long code = error.code();
        if (/*(code == GADErrorCode.NoFill || code == GADErrorCode.Timeout) && */retryTimes < retryMaxTimes) {
            Gdx.app.log("AdmobRewardedVideo", "load failed, retry 5 seconds later. errorCode=" + code);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    gadRewardBasedVideoAd.loadRequestWithAdUnitID(IOSAdmob.newGADRequest(), unitId);
                }
            }, retryDelayMillis / 5f);
            ++retryTimes;
        }
    }

    @Override
    public void rewardBasedVideoAdDidRewardUserWithReward(GADRewardBasedVideoAd rewardBasedVideoAd, GADAdReward reward) {
        // do reward
        if (listener != null) {
            listener.onRewarded(reward.type(), reward.amount().intValue());
            listener = null;
        }
    }

    @Override
    public void rewardBasedVideoAdDidClose(GADRewardBasedVideoAd rewardBasedVideoAd) {
        gadRewardBasedVideoAd.loadRequestWithAdUnitID(IOSAdmob.newGADRequest(), unitId);
    }

    @Override
    public void rewardBasedVideoAdDidCompletePlaying(GADRewardBasedVideoAd rewardBasedVideoAd) {
    }

    @Override
    public void rewardBasedVideoAdDidOpen(GADRewardBasedVideoAd rewardBasedVideoAd) {
    }

    @Override
    public void rewardBasedVideoAdDidReceiveAd(GADRewardBasedVideoAd rewardBasedVideoAd) {
        retryTimes = 0;
        Gdx.app.log("AdmobRewardedVideo", "loaded");
    }

    @Override
    public void rewardBasedVideoAdDidStartPlaying(GADRewardBasedVideoAd rewardBasedVideoAd) {
    }

    @Override
    public void rewardBasedVideoAdWillLeaveApplication(GADRewardBasedVideoAd rewardBasedVideoAd) {
    }

    public GADRewardBasedVideoAd getAd() {
        return gadRewardBasedVideoAd;
    }

    public void setListener(Ads.RewardedVideoListener listener) {
        this.listener = listener;
    }

}
