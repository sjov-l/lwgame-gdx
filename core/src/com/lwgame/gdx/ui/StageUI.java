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

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lwgame.gdx.Lw;

public abstract class StageUI extends AbstractUI {

    protected Stage stage;
    protected Skin skin;
    protected AssetManager assetManager;

    public StageUI() {
        this.stage = new Stage(Lw.uiManager.viewport, Lw.uiManager.batch);
        this.skin = Lw.skin;
        this.assetManager = Lw.assetManager;
    }

    @Override
    public void render(Batch batch) {
        stage.act();
        stage.draw();
    }

    @Override
    public boolean keyDown(int keycode) {
        return stage.keyDown(keycode) || !isTransparent();
    }

    @Override
    public boolean keyUp(int keycode) {
//        if (keycode == Input.Keys.BACK) {
//            close();
//            return true;
//        }
        return stage.keyUp(keycode) || !isTransparent();
    }

    @Override
    public boolean keyTyped(char character) {
        return stage.keyTyped(character) || !isTransparent();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return stage.touchDown(screenX, screenY, pointer, button) || !isTransparent();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button) || !isTransparent();
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return stage.touchDragged(screenX, screenY, pointer) || !isTransparent();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return stage.mouseMoved(screenX, screenY) || !isTransparent();
    }

    @Override
    public boolean scrolled(int amount) {
        return stage.scrolled(amount) || !isTransparent();
    }

    @Override
    public void create() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
