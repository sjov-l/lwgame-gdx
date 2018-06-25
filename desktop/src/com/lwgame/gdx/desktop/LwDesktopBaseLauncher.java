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

package com.lwgame.gdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lwgame.gdx.Lw;
import com.lwgame.gdx.LwApp;
import com.lwgame.gdx.conf.Configuration;
import com.lwgame.gdx.desktop.ads.NoAds;
import com.lwgame.gdx.desktop.billing.NoBilling;
import com.lwgame.gdx.desktop.platform.NoGameCenter;
import com.lwgame.gdx.desktop.platform.NoPlatform;

public class LwDesktopBaseLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new LwApp() {

			@Override
			protected void onCreate() {
				Lw.configuration = new Configuration();
				Lw.billing = new NoBilling();
				Lw.gameCenter = new NoGameCenter();
				Lw.platform = new NoPlatform();
				Lw.ads = new NoAds();
			}

			@Override
			protected int getUIWidth() {
				return 0;
			}

			@Override
			protected int getUIHeight() {
				return 0;
			}
		}, config);
	}
}
