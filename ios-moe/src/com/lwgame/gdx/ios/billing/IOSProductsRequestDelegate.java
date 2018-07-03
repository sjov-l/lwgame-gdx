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

package com.lwgame.gdx.ios.billing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;

import apple.foundation.NSArray;
import apple.foundation.NSError;
import apple.storekit.SKProduct;
import apple.storekit.SKProductsRequest;
import apple.storekit.SKProductsResponse;
import apple.storekit.SKRequest;
import apple.storekit.protocol.SKProductsRequestDelegate;

/**
 * Created by WY on 2018/4/18.
 */

public class IOSProductsRequestDelegate implements SKProductsRequestDelegate {

    private ObjectMap<String, SKProduct> productsMap;

    public IOSProductsRequestDelegate(ObjectMap<String, SKProduct> productsMap) {
        this.productsMap = productsMap;
    }

    @Override
    public void productsRequestDidReceiveResponse(SKProductsRequest request, SKProductsResponse response) {
        if (productsMap.size > 0) productsMap.clear();
        NSArray<? extends SKProduct> products = response.products();
        for (int i = 0, n = products.size(); i < n; i++) {
            SKProduct product = products.get(i);
            productsMap.put(product.productIdentifier(), product);
        }
    }

    @Override
    public void requestDidFailWithError(SKRequest request, NSError error) {
        Gdx.app.log("ios products request failed", "code: " + error.code()
                                                        + ", reason: " + error.localizedFailureReason()
                                                        + ", desc: " + error.localizedDescription()
                                                        + ", suggestion: " + error.localizedRecoverySuggestion());
    }

    @Override
    public void requestDidFinish(SKRequest request) {
    }
}
