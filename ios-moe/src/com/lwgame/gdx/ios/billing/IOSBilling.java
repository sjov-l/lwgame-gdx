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

import com.badlogic.gdx.utils.ObjectMap;
import com.lwgame.gdx.billing.Billing;
import com.lwgame.gdx.billing.BillingCodes;
import com.lwgame.gdx.billing.BillingListener;
import com.lwgame.gdx.billing.Transaction;

import java.util.Date;

import apple.foundation.NSMutableSet;
import apple.foundation.NSSet;
import apple.storekit.SKPayment;
import apple.storekit.SKPaymentQueue;
import apple.storekit.SKPaymentTransaction;
import apple.storekit.SKProduct;
import apple.storekit.SKProductsRequest;

public class IOSBilling implements Billing {

    static {
        try {
            // Fix NatJ runtime class initialization order for binding classes.
            Class.forName(SKProduct.class.getName());
            Class.forName(SKPaymentTransaction.class.getName());
        } catch (Exception e) {
        }
    }

    // 购买处理队列
    private SKPaymentQueue paymentQueue;
    private IOSPaymentTransactionObserver paymentTransactionObserver;
    // 商品id
    private NSSet<String> productIdentifiers;
    // 商品信息请求
    private SKProductsRequest productsRequest;
    // 商品表
    private ObjectMap<String, SKProduct> productsMap;

    public IOSBilling() {
        paymentQueue = (SKPaymentQueue) SKPaymentQueue.defaultQueue();
        paymentQueue.addTransactionObserver(paymentTransactionObserver = new IOSPaymentTransactionObserver(paymentQueue));
        productIdentifiers = (NSMutableSet<String>) NSMutableSet.alloc().init();
        productsRequest = SKProductsRequest.alloc().initWithProductIdentifiers(productIdentifiers);
        productsRequest.setDelegate(new IOSProductsRequestDelegate(productsMap = new ObjectMap<String, SKProduct>(8)));
        productsRequest.start();
    }

    @Override
    public void pay(String productId, BillingListener listener) {
        if (!SKPaymentQueue.canMakePayments()) {
            listener.payFailed(new Transaction(null, BillingCodes.BILLING_UNAVAILABLE, productId, null, null, new Date()));
            return;
        }
        SKProduct product = productsMap.get(productId);
        if (product == null) {
            listener.payFailed(new Transaction(null, BillingCodes.PRODUCT_UNAVAILABLE, productId, null, null, new Date()));
            return;
        }
        paymentTransactionObserver.addBillingListener(productId, listener);
        SKPayment payment = SKPayment.paymentWithProduct(product);
        paymentQueue.addPayment(payment);
    }

}
