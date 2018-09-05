/******************************************************************************
 * Copyright 2018 Li Yang<ocoao.li@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************
 */

package com.lwgame.gdx.ui;

import com.lwgame.gdx.Lw;

public abstract class AbstractUI implements UI {

    protected boolean transparent;

    public AbstractUI() {
    }

    @Override
    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    @Override
    public void show() {
        Lw.uiManager.add(this);
    }

    @Override
    public void close() {
        Lw.uiManager.remove(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        return !isTransparent();
    }

    @Override
    public boolean keyUp(int keycode) {
        return !isTransparent();
    }

    @Override
    public boolean keyTyped(char character) {
        return !isTransparent();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return !isTransparent();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return !isTransparent();
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return !isTransparent();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return !isTransparent();
    }

    @Override
    public boolean scrolled(int amount) {
        return !isTransparent();
    }

}
