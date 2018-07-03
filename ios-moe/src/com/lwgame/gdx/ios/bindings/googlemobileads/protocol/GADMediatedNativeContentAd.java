package com.lwgame.gdx.ios.bindings.googlemobileads.protocol;


import apple.foundation.NSArray;
import apple.uikit.UIView;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADNativeAdImage;
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
@ObjCProtocolName("GADMediatedNativeContentAd")
public interface GADMediatedNativeContentAd extends GADMediatedNativeAd {
	@Generated
	@IsOptional
	@Selector("adChoicesView")
	default UIView adChoicesView() {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@Selector("advertiser")
	String advertiser();

	@Generated
	@Selector("body")
	String body();

	@Generated
	@Selector("callToAction")
	String callToAction();

	@Generated
	@IsOptional
	@Selector("hasVideoContent")
	default boolean hasVideoContent() {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@Selector("headline")
	String headline();

	@Generated
	@Selector("images")
	NSArray<?> images();

	@Generated
	@Selector("logo")
	GADNativeAdImage logo();

	@Generated
	@IsOptional
	@Selector("mediaView")
	default UIView mediaView() {
		throw new java.lang.UnsupportedOperationException();
	}
}