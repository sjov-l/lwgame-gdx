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

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lwgame.gdx.i18n.SimpleLanguageFactory;
import com.lwgame.gdx.ui.UIManager;

public abstract class LwApp extends ApplicationAdapter {

	@Override
	public void create () {
		I18NBundle.setExceptionOnMissingKey(false);
		I18NBundle.setSimpleFormatter(true);
		Gdx.app.getInput().setCatchBackKey(true);

		Lw.language = SimpleLanguageFactory.getDefault();
		Lw.skin = new Skin();
		Lw.assetManager = new AssetManager();
		Texture.setAssetManager(Lw.assetManager);
		Lw.uiManager = new UIManager(new ExtendViewport(getResolutionWidth(), getResolutionHeight()));

		onCreate();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Lw.uiManager.render();
	}
	
	@Override
	public void resize(int width, int height) {
		Lw.uiManager.resize(width, height);
	}

	@Override
	public void dispose () {
		Lw.assetManager.dispose();
		Lw.uiManager.dispose();
		if (Lw.database != null)
			Lw.database.flush();
		System.exit(0);
	}

	protected abstract void onCreate();
	protected abstract int getResolutionWidth();
	protected abstract int getResolutionHeight();

}
