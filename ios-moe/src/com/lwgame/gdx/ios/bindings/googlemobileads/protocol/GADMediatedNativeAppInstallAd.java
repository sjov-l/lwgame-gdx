package com.lwgame.gdx.ios.bindings.googlemobileads.protocol;


import apple.foundation.NSArray;
import apple.foundation.NSDecimalNumber;
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
@ObjCProtocolName("GADMediatedNativeAppInstallAd")
public interface GADMediatedNativeAppInstallAd extends GADMediatedNativeAd {
	@Generated
	@IsOptional
	@Selector("adChoicesView")
	default UIView adChoicesView() {
		throw new java.lang.UnsupportedOperationException();
	}

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
	@Selector("icon")
	GADNativeAdImage icon();

	@Generated
	@Selector("images")
	NSArray<?> images();

	@Generated
	@IsOptional
	@Selector("mediaView")
	default UIView mediaView() {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@Selector("price")
	String price();

	@Generated
	@Selector("starRating")
	NSDecimalNumber starRating();

	@Generated
	@Selector("store")
	String store();
}