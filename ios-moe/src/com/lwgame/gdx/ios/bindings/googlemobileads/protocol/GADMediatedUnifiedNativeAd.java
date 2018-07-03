package com.lwgame.gdx.ios.bindings.googlemobileads.protocol;


import apple.foundation.NSArray;
import apple.foundation.NSDecimalNumber;
import apple.foundation.NSDictionary;
import apple.uikit.UIView;
import apple.uikit.UIViewController;
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
@ObjCProtocolName("GADMediatedUnifiedNativeAd")
public interface GADMediatedUnifiedNativeAd {
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
	@Selector("didRecordClickOnAssetWithName:view:viewController:")
	default void didRecordClickOnAssetWithNameViewViewController(
			String assetName, UIView view, UIViewController viewController) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("didRecordImpression")
	default void didRecordImpression() {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("didRenderInView:clickableAssetViews:nonclickableAssetViews:viewController:")
	default void didRenderInViewClickableAssetViewsNonclickableAssetViewsViewController(
			UIView view,
			NSDictionary<String, ? extends UIView> clickableAssetViews,
			NSDictionary<String, ? extends UIView> nonclickableAssetViews,
			UIViewController viewController) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("didUntrackView:")
	default void didUntrackView(UIView view) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@Selector("extraAssets")
	NSDictionary<String, ?> extraAssets();

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
	NSArray<? extends GADNativeAdImage> images();

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