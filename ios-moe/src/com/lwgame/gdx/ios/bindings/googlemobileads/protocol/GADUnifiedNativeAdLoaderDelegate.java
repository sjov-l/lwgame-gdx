package com.lwgame.gdx.ios.bindings.googlemobileads.protocol;


import com.lwgame.gdx.ios.bindings.googlemobileads.GADAdLoader;
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
@ObjCProtocolName("GADUnifiedNativeAdLoaderDelegate")
public interface GADUnifiedNativeAdLoaderDelegate extends GADAdLoaderDelegate {
	@Generated
	@Selector("adLoader:didReceiveUnifiedNativeAd:")
	void adLoaderDidReceiveUnifiedNativeAd(GADAdLoader adLoader,
			GADUnifiedNativeAd nativeAd);
}