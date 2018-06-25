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

package com.lwgame.gdx.billing;

import java.util.Date;

public class Transaction {

    /** the order id */
    public final String id;
    public final int code;
    public final String productId;
    public final String signature;
    public final Object payload;
    public final Date time;

    public Transaction(String id, int code, String productId, String signature, Object payload, Date time) {
        this.id = id;
        this.code = code;
        this.productId = productId;
        this.signature = signature;
        this.payload = payload;
        this.time = time;
    }

}
