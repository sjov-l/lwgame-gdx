package com.lwgame.gdx.ios.bindings.googlemobileads.protocol;


import com.lwgame.gdx.ios.bindings.googlemobileads.GADUnifiedNativeAd;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

@Generated
@Library("GoogleMobileAds")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("GADUnifiedNativeAdUnconfirmedClickDelegate")
public interface GADUnifiedNativeAdUnconfirmedClickDelegate {
	@Generated
	@Selector("nativeAd:didReceiveUnconfirmedClickOnAssetID:")
	void nativeAdDidReceiveUnconfirmedClickOnAssetID(
			GADUnifiedNativeAd nativeAd, String assetID);

	@Generated
	@Selector("nativeAdDidCancelUnconfirmedClick:")
	void nativeAdDidCancelUnconfirmedClick(GADUnifiedNativeAd nativeAd);
}