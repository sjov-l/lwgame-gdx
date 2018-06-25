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

import com.badlogic.gdx.utils.Array;

import java.util.Locale;

public class SimpleLanguageFactory {

    private static Array<Language> languages = new Array<Language>(4);

    private static final Language EN;

    static {
        languages.add(EN = new LanguageImpl(1, "EN", Locale.ENGLISH));
        languages.add(new LanguageImpl(2, "中文", Locale.SIMPLIFIED_CHINESE));
        languages.add(new LanguageImpl(3, "中文繁体", Locale.TRADITIONAL_CHINESE));
        languages.add(new LanguageImpl(4, "日本語", Locale.JAPANESE));
    }

    public static Language forName(String name) {
        for (int i = 0; i < languages.size; i++) {
            Language l = languages.get(i);
            if (name.equals(l.getName())) {
                return l;
            }
        }
        return EN;
    }

    public static Language forLocale(Locale locale) {
        for (int i = 0; i < languages.size; i++) {
            Language l = languages.get(i);
            if (locale.equals(l.getLocale())) {
                return l;
            }
        }
        return EN;
    }

    public static Language getDefault() {
        return forLocale(Locale.getDefault());
    }

    public static void add(Language language) {
        languages.add(language);
    }

}
