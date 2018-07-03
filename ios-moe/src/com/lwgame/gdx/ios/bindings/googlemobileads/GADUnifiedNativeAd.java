package com.lwgame.gdx.ios.bindings.googlemobileads;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSDecimalNumber;
import apple.foundation.NSDictionary;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import apple.uikit.UIView;
import apple.uikit.UIViewController;
import com.lwgame.gdx.ios.bindings.googlemobileads.protocol.GADUnifiedNativeAdDelegate;
import com.lwgame.gdx.ios.bindings.googlemobileads.protocol.GADUnifiedNativeAdUnconfirmedClickDelegate;
import org.moe.natj.c.ann.FunctionPtr;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.NInt;
import org.moe.natj.general.ann.NUInt;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.VoidPtr;
import org.moe.natj.objc.Class;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.SEL;
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

@Generated
@Library("GoogleMobileAds")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class GADUnifiedNativeAd extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected GADUnifiedNativeAd(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Selector("adNetworkClassName")
	public native String adNetworkClassName();

	@Generated
	@Selector("advertiser")
	public native String advertiser();

	@Generated
	@Owned
	@Selector("alloc")
	public static native GADUnifiedNativeAd alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("body")
	public native String body();

	@Generated
	@Selector("callToAction")
	public native String callToAction();

	@Generated
	@Selector("cancelPreviousPerformRequestsWithTarget:")
	public static native void cancelPreviousPerformRequestsWithTarget(
			@Mapped(ObjCObjectMapper.class) Object aTarget);

	@Generated
	@Selector("cancelPreviousPerformRequestsWithTarget:selector:object:")
	public static native void cancelPreviousPerformRequestsWithTargetSelectorObject(
			@Mapped(ObjCObjectMapper.class) Object aTarget, SEL aSelector,
			@Mapped(ObjCObjectMapper.class) Object anArgument);

	@Generated
	@Selector("cancelUnconfirmedClick")
	public native void cancelUnconfirmedClick();

	@Generated
	@Selector("classFallbacksForKeyedArchiver")
	public static native NSArray<String> classFallbacksForKeyedArchiver();

	@Generated
	@Selector("classForKeyedUnarchiver")
	public static native Class classForKeyedUnarchiver();

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("delegate")
	@MappedReturn(ObjCObjectMapper.class)
	public native GADUnifiedNativeAdDelegate delegate();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("extraAssets")
	public native NSDictionary<String, ?> extraAssets();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("headline")
	public native String headline();

	@Generated
	@Selector("icon")
	public native GADNativeAdImage icon();

	@Generated
	@Selector("images")
	public native NSArray<? extends GADNativeAdImage> images();

	@Generated
	@Selector("init")
	public native GADUnifiedNativeAd init();

	@Generated
	@Selector("instanceMethodForSelector:")
	@FunctionPtr(name = "call_instanceMethodForSelector_ret")
	public static native NSObject.Function_instanceMethodForSelector_ret instanceMethodForSelector(
			SEL aSelector);

	@Generated
	@Selector("instanceMethodSignatureForSelector:")
	public static native NSMethodSignature instanceMethodSignatureForSelector(
			SEL aSelector);

	@Generated
	@Selector("instancesRespondToSelector:")
	public static native boolean instancesRespondToSelector(SEL aSelector);

	@Generated
	@Selector("isSubclassOfClass:")
	public static native boolean isSubclassOfClass(Class aClass);

	@Generated
	@Selector("keyPathsForValuesAffectingValueForKey:")
	public static native NSSet<String> keyPathsForValuesAffectingValueForKey(
			String key);

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("price")
	public native String price();

	@Generated
	@Selector("registerAdView:clickableAssetViews:nonclickableAssetViews:")
	public native void registerAdViewClickableAssetViewsNonclickableAssetViews(
			UIView adView,
			NSDictionary<String, ? extends UIView> clickableAssetViews,
			NSDictionary<String, ? extends UIView> nonclickableAssetViews);

	@Generated
	@Selector("registerClickConfirmingView:")
	public native void registerClickConfirmingView(UIView view);

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("rootViewController")
	public native UIViewController rootViewController();

	@Generated
	@Selector("setDelegate:")
	public native void setDelegate_unsafe(
			@Mapped(ObjCObjectMapper.class) GADUnifiedNativeAdDelegate value);

	@Generated
	public void setDelegate(
			@Mapped(ObjCObjectMapper.class) GADUnifiedNativeAdDelegate value) {
		Object __old = delegate();
		if (value != null) {
			org.moe.natj.objc.ObjCRuntime.associateObjCObject(this, value);
		}
		setDelegate_unsafe(value);
		if (__old != null) {
			org.moe.natj.objc.ObjCRuntime.dissociateObjCObject(this, __old);
		}
	}

	@Generated
	@Selector("setRootViewController:")
	public native void setRootViewController_unsafe(UIViewController value);

	@Generated
	public void setRootViewController(UIViewController value) {
		Object __old = rootViewController();
		if (value != null) {
			org.moe.natj.objc.ObjCRuntime.associateObjCObject(this, value);
		}
		setRootViewController_unsafe(value);
		if (__old != null) {
			org.moe.natj.objc.ObjCRuntime.dissociateObjCObject(this, __old);
		}
	}

	@Generated
	@Selector("setUnconfirmedClickDelegate:")
	public native void setUnconfirmedClickDelegate_unsafe(
			@Mapped(ObjCObjectMapper.class) GADUnifiedNativeAdUnconfirmedClickDelegate value);

	@Generated
	public void setUnconfirmedClickDelegate(
			@Mapped(ObjCObjectMapper.class) GADUnifiedNativeAdUnconfirmedClickDelegate value) {
		Object __old = unconfirmedClickDelegate();
		if (value != null) {
			org.moe.natj.objc.ObjCRuntime.associateObjCObject(this, value);
		}
		setUnconfirmedClickDelegate_unsafe(value);
		if (__old != null) {
			org.moe.natj.objc.ObjCRuntime.dissociateObjCObject(this, __old);
		}
	}

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("starRating")
	public native NSDecimalNumber starRating();

	@Generated
	@Selector("store")
	public native String store();

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("unconfirmedClickDelegate")
	@MappedReturn(ObjCObjectMapper.class)
	public native GADUnifiedNativeAdUnconfirmedClickDelegate unconfirmedClickDelegate();

	@Generated
	@Selector("unregisterAdView")
	public native void unregisterAdView();

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();

	@Generated
	@Selector("videoController")
	public native GADVideoController videoController();
}