package com.lwgame.gdx.ios.bindings.googlemobileads.protocol;


import apple.foundation.NSArray;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADAdLoader;
import com.lwgame.gdx.ios.bindings.googlemobileads.GADNativeCustomTemplateAd;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

@Generated
@Library("GoogleMobileAds")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("GADNativeCustomTemplateAdLoaderDelegate")
public interface GADNativeCustomTemplateAdLoaderDelegate extends
		GADAdLoaderDelegate {
	@Generated
	@Selector("adLoader:didReceiveNativeCustomTemplateAd:")
	void adLoaderDidReceiveNativeCustomTemplateAd(GADAdLoader adLoader,
			GADNativeCustomTemplateAd nativeCustomTemplateAd);

	@Generated
	@Selector("nativeCustomTemplateIDsForAdLoader:")
	NSArray<String> nativeCustomTemplateIDsForAdLoader(GADAdLoader adLoader);
}