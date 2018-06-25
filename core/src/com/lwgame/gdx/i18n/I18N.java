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

package com.lwgame.gdx.i18n;

import com.lwgame.gdx.Lw;

public class I18N {

    public static String getString(String key) {
        return getString(key, Lw.language);
    }

    public static String getString(String key, Language language) {
        return language.getBundle().get(key);
    }

    public static String getString(String key, Object... args) {
        return getString(key, Lw.language, args);
    }

    public static String getString(String key, Language language, Object... args) {
        return language.getBundle().format(key, args);
    }

}
