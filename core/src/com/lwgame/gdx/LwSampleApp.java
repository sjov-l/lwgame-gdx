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

package com.lwgame.gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lwgame.gdx.ui.StageUI;

public class LwSampleApp extends LwApp {

    @Override
    protected void onCreate() {
        new StageUI() {
            private Texture texture;
            @Override
            public void create() {
                texture = new Texture("badlogic.jpg");
                Image image = new Image(texture);
                image.setPosition((stage.getWidth() - image.getWidth()) / 2, (stage.getHeight() - image.getHeight()) / 2);
                stage.addActor(image);
            }
            @Override
            public void dispose() {
                super.dispose();
                texture.dispose();
            }
        }.show();

    }

    @Override
    protected int getResolutionWidth() {
        return 720;
    }

    @Override
    protected int getResolutionHeight() {
        return 1280;
    }
}
