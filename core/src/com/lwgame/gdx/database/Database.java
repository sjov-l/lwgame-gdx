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

public interface Database {

    void flush();

    byte getByte(String key);
    byte getByte(String key, byte defaultValue);
    boolean getBoolean(String key);
    boolean getBoolean(String key, boolean defaultValue);
    short getShort(String key);
    short getShort(String key, short defaultValue);
    int getInt(String key);
    int getInt(String key, int defaultValue);
    float getFloat(String key);
    float getFloat(String key, float defaultValue);
    long getLong(String key);
    long getLong(String key, long defaultValue);
    double getDouble(String key);
    double getDouble(String key, double defaultValue);
    String getString(String key);
    String getString(String key, String defaultValue);

    void putByte(String key, byte value);
    void putBoolean(String key, boolean value);
    void putShort(String key, short value);
    void putInt(String key, int value);
    void putFloat(String key, float value);
    void putDouble(String key, double value);
    void putString(String key, String value);

    void remove(String key);

}
