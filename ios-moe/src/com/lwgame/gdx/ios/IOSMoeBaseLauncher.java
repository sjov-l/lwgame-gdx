package com.lwgame.gdx.ios;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.backends.iosmoe.IOSApplicationConfiguration;
import com.lwgame.gdx.Lw;
import com.lwgame.gdx.LwApp;
import com.lwgame.gdx.conf.Configuration;

import org.moe.natj.general.Pointer;

import apple.foundation.NSDictionary;
import apple.uikit.UIApplication;
import apple.uikit.c.UIKit;

public abstract class IOSMoeBaseLauncher extends IOSApplication.Delegate {

    protected IOSMoeBaseLauncher(Pointer peer) {
        super(peer);
    }

    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.useAccelerometer = false;
        return new IOSApplication(createApplicationListener(), config);
    }

    @Override
    public boolean applicationDidFinishLaunchingWithOptions(UIApplication application, NSDictionary<?, ?> launchOptions) {
        boolean r = super.applicationDidFinishLaunchingWithOptions(application, launchOptions);
        Lw.configuration = new Configuration();
        Lw.billing = null;
        Lw.gameCenter = null;
        Lw.platform = null;
        Lw.ads = null;
        return r;
    }

    protected abstract ApplicationListener createApplicationListener();

    public static void main(String[] argv) {
        UIKit.UIApplicationMain(0, null, null, IOSMoeBaseLauncher.class.getName());
    }
}
