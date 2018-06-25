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

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lwgame.gdx.ads.Ads;
import com.lwgame.gdx.billing.Billing;
import com.lwgame.gdx.conf.Configuration;
import com.lwgame.gdx.database.Database;
import com.lwgame.gdx.i18n.Language;
import com.lwgame.gdx.platform.GameCenter;
import com.lwgame.gdx.platform.Platform;
import com.lwgame.gdx.ui.UIManager;

public class Lw {

    public static Skin skin;
    public static AssetManager assetManager;
    public static UIManager uiManager;
    public static Database database;
    public static Language language;
    public static Ads ads;
    public static Billing billing;
    public static GameCenter gameCenter;
    public static Platform platform;
    public static Configuration configuration;

}
