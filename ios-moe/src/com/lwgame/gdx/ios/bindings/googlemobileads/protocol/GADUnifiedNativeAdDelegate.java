package com.lwgame.gdx.ios.bindings.googlemobileads.protocol;


import com.lwgame.gdx.ios.bindings.googlemobileads.GADUnifiedNativeAd;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IsOptional;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

@Generated
@Library("GoogleMobileAds")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("GADUnifiedNativeAdDelegate")
public interface GADUnifiedNativeAdDelegate {
	@Generated
	@IsOptional
	@Selector("nativeAdDidDismissScreen:")
	default void nativeAdDidDismissScreen(GADUnifiedNativeAd nativeAd) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("nativeAdDidRecordClick:")
	default void nativeAdDidRecordClick(GADUnifiedNativeAd nativeAd) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("nativeAdDidRecordImpression:")
	default void nativeAdDidRecordImpression(GADUnifiedNativeAd nativeAd) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("nativeAdWillDismissScreen:")
	default void nativeAdWillDismissScreen(GADUnifiedNativeAd nativeAd) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("nativeAdWillLeaveApplication:")
	default void nativeAdWillLeaveApplication(GADUnifiedNativeAd nativeAd) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("nativeAdWillPresentScreen:")
	default void nativeAdWillPresentScreen(GADUnifiedNativeAd nativeAd) {
		throw new java.lang.UnsupportedOperationException();
	}
}