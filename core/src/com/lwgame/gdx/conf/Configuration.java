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

package com.lwgame.gdx.conf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.PropertiesUtils;
import com.badlogic.gdx.utils.StreamUtils;

import java.io.Reader;

public class Configuration {

    private ObjectMap<String, String> map;

    public Configuration() {
        map = new ObjectMap<String, String>();
        FileHandle file = Gdx.files.internal("lw.properties");
        if (file.exists()) {
            Reader reader = file.reader("utf-8");
            try {
                PropertiesUtils.load(map, reader);
            } catch (Exception e) {
                throw new GdxRuntimeException(e);
            } finally {
                StreamUtils.closeQuietly(reader);
            }
        }
    }

    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    public byte getByte(String key, byte defaultValue) {
        String v = getString(key);
        return v != null ? Byte.parseByte(v) : defaultValue;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String v = getString(key);
        return v != null ? Boolean.parseBoolean(v) : defaultValue;
    }

    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    public short getShort(String key, short defaultValue) {
        String v = getString(key);
        return v != null ? Short.parseShort(v) : defaultValue;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        String v = getString(key);
        return v != null ? Integer.parseInt(key) : defaultValue;
    }

    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    public float getFloat(String key, float defaultValue) {
        String v = getString(key);
        return v != null ? Float.parseFloat(v) : defaultValue;
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        String v = getString(key);
        return v != null ? Long.parseLong(v) : defaultValue;
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double defaultValue) {
        String v = getString(key);
        return v != null ? Double.parseDouble(v) : defaultValue;
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        String v = map.get(key);
        return v != null ? v : defaultValue;
    }

    public String[] getArray(String key) {
        return getArray(key, null);
    }

    public String[] getArray(String key, String[] defaultValue) {
        String v = getString(key);
        if (v == null)
            return defaultValue;
        return v.split(",");
    }

}
