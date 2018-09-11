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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UIManager implements InputProcessor, Disposable {

    protected Viewport viewport;
    protected Batch batch;
    protected boolean ownsBatch;

    protected Array<UI> uis;
    protected boolean invalid;
    protected int beginRenderUiIndex;

    public UIManager(Viewport viewport) {
        this(viewport, new SpriteBatch());
        ownsBatch = true;
    }

    public UIManager(Viewport viewport, Batch batch) {
        this.viewport = viewport;
        this.batch = batch;
        this.uis = new Array<UI>(4);
        this.invalid = true;
        this.beginRenderUiIndex = 0;
        Gdx.input.setInputProcessor(this);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void add(UI ui) {
        ui.create();
        uis.add(ui);
        invalidate();
    }

    public void remove(UI ui) {
        for (int i = uis.size - 1; i >= 0; i--) {
            if (uis.get(i) == ui) {
                uis.removeIndex(i);
                ui.dispose();
                invalidate();
                break;
            }
        }
    }

    public int size() {
        return uis.size;
    }

    public UI get(int index) {
        return uis.get(index);
    }

    public UI peek() {
        return uis.size > 0 ? uis.peek() : null;
    }

    public void render() {
        validate();
        final Batch batch = this.batch;
        for (int i = beginRenderUiIndex, n = uis.size; i < n; i++) {
            uis.get(i).render(batch);
        }
    }

    private void invalidate() {
        invalid = true;
    }

    private void validate() {
        if (invalid) {
            invalid = false;
            // 从顶层往下查找第一个不透明的界面，即为第一个绘制的界面
            int beginRenderUiIndexNew = beginRenderUiIndex;
            for (int i = uis.size - 1; i >= 0; i--) {
                if (!uis.get(i).isTransparent()) {
                    beginRenderUiIndexNew = i;
                    break;
                }
            }
            // pause
            for (int i = beginRenderUiIndex; i < beginRenderUiIndexNew; i++) {
                uis.get(i).pause();
            }
            // resume
            for (int i = beginRenderUiIndexNew, n = beginRenderUiIndexNew < beginRenderUiIndex ? beginRenderUiIndex : uis.size; i < n; i++) {
                uis.get(i).resume();
            }
            beginRenderUiIndex = beginRenderUiIndexNew;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        for (int i = uis.size - 1; i >= 0; i--) {
            if (uis.get(i).keyDown(keycode))
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for (int i = uis.size - 1; i >= 0; i--) {
            if (uis.get(i).keyUp(keycode))
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        for (int i = uis.size - 1; i >= 0; i--) {
            if (uis.get(i).keyTyped(character))
                return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = uis.size - 1; i >= 0; i--) {
            if (uis.get(i).touchDown(screenX, screenY, pointer, button))
                return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (int i = uis.size - 1; i >= 0; i--) {
            if (uis.get(i).touchUp(screenX, screenY, pointer, button))
                return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (int i = uis.size - 1; i >= 0; i--) {
            if (uis.get(i).touchDragged(screenX, screenY, pointer))
                return true;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for (int i = uis.size - 1; i >= 0; i--) {
            if (uis.get(i).mouseMoved(screenX, screenY))
                return true;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        for (int i = uis.size - 1; i >= 0; i--) {
            if (uis.get(i).scrolled(amount))
                return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        for (int i = uis.size - 1; i >= 0; i--)
            uis.get(i).dispose();
        if (ownsBatch)
            batch.dispose();
    }

}
