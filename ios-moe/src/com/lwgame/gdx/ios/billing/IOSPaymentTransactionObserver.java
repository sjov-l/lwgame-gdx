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
import com.lwgame.gdx.billing.BillingCodes;
import com.lwgame.gdx.billing.BillingListener;
import com.lwgame.gdx.billing.Transaction;

import java.util.Date;

import apple.foundation.NSArray;
import apple.foundation.NSBundle;
import apple.foundation.NSData;
import apple.foundation.NSDate;
import apple.foundation.NSError;
import apple.foundation.NSURL;
import apple.storekit.SKDownload;
import apple.storekit.SKPayment;
import apple.storekit.SKPaymentQueue;
import apple.storekit.SKPaymentTransaction;
import apple.storekit.SKProduct;
import apple.storekit.SKReceiptRefreshRequest;
import apple.storekit.SKRequest;
import apple.storekit.enums.SKPaymentTransactionState;
import apple.storekit.protocol.SKPaymentTransactionObserver;
import apple.storekit.protocol.SKRequestDelegate;

/**
 * Created by WY on 2018/4/18.
 */

public class IOSPaymentTransactionObserver implements SKPaymentTransactionObserver {

    private ObjectMap<String, BillingListener> listenersMap = new ObjectMap<String, BillingListener>();
    private SKPaymentQueue paymentQueue;

    public IOSPaymentTransactionObserver(SKPaymentQueue paymentQueue) {
        this.paymentQueue = paymentQueue;
    }

    @Override
    public void paymentQueueRemovedTransactions(SKPaymentQueue queue, NSArray<? extends SKPaymentTransaction> transactions) {
    }

    @Override
    public void paymentQueueRestoreCompletedTransactionsFailedWithError(SKPaymentQueue queue, NSError error) {
    }

    @Override
    public void paymentQueueUpdatedDownloads(SKPaymentQueue queue, NSArray<? extends SKDownload> downloads) {
    }

    @Override
    public void paymentQueueUpdatedTransactions(SKPaymentQueue queue, NSArray<? extends SKPaymentTransaction> transactions) {
        for (int i = 0, n = transactions.size(); i < n; i++) {
            SKPaymentTransaction transaction = transactions.get(i);
            long state = transaction.transactionState();
            SKPayment payment = transaction.payment();
            final String orderId = transaction.transactionIdentifier();
            final String productId = payment != null ? payment.productIdentifier() : "";
            final NSDate time = transaction.transactionDate();
            BillingListener listener = listenersMap.remove(productId);
            if (state == SKPaymentTransactionState.Purchased || state == SKPaymentTransactionState.Restored/*恢复购买(非消耗品)*/) {
                NSURL receiptUrl = NSBundle.mainBundle().appStoreReceiptURL();
                NSData receipt = NSData.dataWithContentsOfURL(receiptUrl);
                if (receipt != null) {
                    if (listener != null)
                        listener.paySucceed(new Transaction(orderId, BillingCodes.SUCC, productId, receipt.base64EncodedStringWithOptions(0), null, new Date((long) (time.timeIntervalSince1970() * 1000))));
                } else {
                    // 刷新票据
                    SKReceiptRefreshRequest receiptRequest = SKReceiptRefreshRequest.alloc().init();
                    receiptRequest.setDelegate(new SKRequestDelegate() {
                        @Override
                        public void requestDidFailWithError(SKRequest request, NSError error) {
                            if (listener != null)
                                listener.payFailed(new Transaction(orderId, BillingCodes.SERVICE_UNAVAILABLE, null, null, null, new Date((long) (time.timeIntervalSince1970() * 1000))));
                        }
                        @Override
                        public void requestDidFinish(SKRequest request) {
                            NSURL receiptUrl = NSBundle.mainBundle().appStoreReceiptURL();
                            NSData receipt = NSData.dataWithContentsOfURL(receiptUrl);
                            if (listener != null)
                                listener.paySucceed(new Transaction(orderId, BillingCodes.SUCC, productId, receipt.base64EncodedStringWithOptions(0), null, new Date((long) (time.timeIntervalSince1970() * 1000))));
                        }
                    });
                    receiptRequest.start();
                }
            } else if (state == SKPaymentTransactionState.Failed) {
                // 购买失败
                if (listener != null)
                    listener.payFailed(new Transaction(orderId, BillingCodes.UNEXCEPTED, productId, null, null, new Date((long) (time.timeIntervalSince1970() * 1000))));
            }
            paymentQueue.finishTransaction(transaction);
        }
    }

    @Override
    public void paymentQueueRestoreCompletedTransactionsFinished(SKPaymentQueue queue) {
    }

    @Override
    public boolean paymentQueueShouldAddStorePaymentForProduct(SKPaymentQueue queue, SKPayment payment, SKProduct product) {
        return false;
    }

    public void addBillingListener(String productId, BillingListener listener) {
        listenersMap.put(productId, listener);
    }

}
