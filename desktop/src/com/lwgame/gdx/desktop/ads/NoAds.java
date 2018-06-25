package com.lwgame.gdx.desktop.ads;

import com.lwgame.gdx.ads.Ads;

public class NoAds implements Ads {
    @Override
    public void show(int type) {
    }

    @Override
    public void hide(int type) {
    }

    @Override
    public boolean isLoaded(int type) {
        return false;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
    }
}
