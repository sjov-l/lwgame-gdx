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

package com.lwgame.gdx.database;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.StreamUtils;

import java.io.BufferedReader;
import java.util.Map;

public abstract class AbstractLocalDatabase implements Database {

    private FileHandle file;
    private Preferences preferences;
    private ObjectMap<String, String> data;

    protected abstract FileHandle getLocalFile();
    protected abstract Preferences getPreferences();

    public AbstractLocalDatabase() {
        file = getLocalFile();
        preferences = getPreferences();
        data = new ObjectMap<String, String>(16);
        doLoad();
    }

    private void doLoad() {
        // load data
        if (file != null) {
            BufferedReader reader = null;
            try {
                reader = file.reader(256, "utf-8");
                String line;
                while ((line = reader.readLine()) != null) {
                    int n = line.indexOf('=');
                    if (n >= 0) {
                        String key = line.substring(0, n);
                        String value = line.substring(n + 1);
                        data.put(key, value);
                    }
                }
            } catch (Exception e) {
            } finally {
                StreamUtils.closeQuietly(reader);
            }
        }

        if (preferences != null) {
            Map<String, ?> map = preferences.get();
            if (map == null || map.isEmpty()) return;
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                if (entry.getValue() != null)
                    data.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
    }

    @Override
    public void flush() {
        if (data.size == 0) return;
        if (file != null) {
            try {
                StringBuilder buf = new StringBuilder(256);
                ObjectMap.Entries<String, String> entries = data.entries();
                for (ObjectMap.Entry<String, String> entry : entries) {
                    buf.append(entry.key).append('=').append(entry.value).append('\n');
                }
                buf.setLength(buf.length() - 1); // delete the last '\n'
                file.writeString(buf.toString(), false, "utf-8");

                // 本地文件存储成功后必须将preferences清空，避免下次读取时覆盖新数据
                if (preferences != null) {
                    preferences.clear();
                    preferences.flush();
                }
                return;
            } catch (Exception e) {}
        }

        if (preferences != null) {
            ObjectMap.Entries<String, String> entries = data.entries();
            for (ObjectMap.Entry<String, String> entry : entries) {
                preferences.putString(entry.key, entry.value);
            }
            preferences.flush();
            return;
        }

        throw new GdxRuntimeException("local database flush failed: didn't find the file or preferences.");
    }

    @Override
    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    @Override
    public byte getByte(String key, byte defaultValue) {
        String v = data.get(key);
        return v != null ? Byte.parseByte(v) : defaultValue;
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        String v = data.get(key);
        return v != null ? "1".equals(v) : defaultValue;
    }

    @Override
    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    @Override
    public short getShort(String key, short defaultValue) {
        String v = data.get(key);
        return v != null ? Short.parseShort(v) : defaultValue;
    }

    @Override
    public int getInt(String key) {
        return getInt(key, 0);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        String v = data.get(key);
        return v != null ? Integer.parseInt(v) : defaultValue;
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        String v = data.get(key);
        return v != null ? Float.parseFloat(v) : defaultValue;
    }

    @Override
    public long getLong(String key) {
        return getLong(key, 0);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        String v = data.get(key);
        return v != null ? Long.parseLong(v) : defaultValue;
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        String v = data.get(key);
        return v != null ? Double.parseDouble(v) : defaultValue;
    }

    @Override
    public String getString(String key) {
        return data.get(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        String v = data.get(key);
        return v != null ? v : defaultValue;
    }

    @Override
    public void putByte(String key, byte value) {
        data.put(key, String.valueOf(value));
    }

    @Override
    public void putBoolean(String key, boolean value) {
        data.put(key, value ? "1" : "0");
    }

    @Override
    public void putShort(String key, short value) {
        data.put(key, String.valueOf(value));
    }

    @Override
    public void putInt(String key, int value) {
        data.put(key, String.valueOf(value));
    }

    @Override
    public void putFloat(String key, float value) {
        data.put(key, String.valueOf(value));
    }

    @Override
    public void putDouble(String key, double value) {
        data.put(key, String.valueOf(value));
    }

    @Override
    public void putString(String key, String value) {
        if (value == null) data.remove(key);
        else               data.put(key, value);
    }

    @Override
    public void remove(String key) {
        data.remove(key);
    }
}
