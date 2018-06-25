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

package com.lwgame.gdx.android.billing;

import android.content.Intent;
import android.content.IntentFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidEventListener;
import com.lwgame.gdx.Lw;
import com.lwgame.gdx.android.ActivityCodes;
import com.lwgame.gdx.android.billing.util.IabBroadcastReceiver;
import com.lwgame.gdx.android.billing.util.IabHelper;
import com.lwgame.gdx.android.billing.util.IabResult;
import com.lwgame.gdx.android.billing.util.Inventory;
import com.lwgame.gdx.android.billing.util.Purchase;
import com.lwgame.gdx.billing.Billing;
import com.lwgame.gdx.billing.BillingCodes;
import com.lwgame.gdx.billing.BillingListener;
import com.lwgame.gdx.billing.Transaction;

import java.util.Date;
import java.util.List;

public class GooglePlayBilling implements Billing, AndroidEventListener,
        IabHelper.OnIabSetupFinishedListener, IabHelper.OnConsumeFinishedListener, IabBroadcastReceiver.IabBroadcastListener,
        IabHelper.QueryInventoryFinishedListener, IabHelper.OnConsumeMultiFinishedListener {

    private AndroidApplication context;
    private IabHelper iabHelper;
    private IabBroadcastReceiver iabBroadcastReceiver;

    public GooglePlayBilling(AndroidApplication context) {
        this.context = context;
        String publicKey = Lw.configuration.getString("iab.publicKey");
        if (publicKey != null) {
            this.iabHelper = new IabHelper(context, publicKey);
            this.iabHelper.enableDebugLogging(Lw.configuration.getBoolean("debug"));
            this.iabHelper.startSetup(this);
            context.addAndroidEventListener(this);
        }
    }

    @Override
    public void pay(final String sku, final BillingListener listener) {
        if (iabHelper == null) {
            listener.payFailed(new Transaction(null, BillingCodes.SDK_UNINITIALIZED, sku, null, null, new Date()));
            return;
        }
        try {
            iabHelper.launchPurchaseFlow(context, sku, ActivityCodes.REQUEST_PAY, new IabHelper.OnIabPurchaseFinishedListener() {
                @Override
                public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                    if (!result.isSuccess()) {
                        int errorCode;
                        if (result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_USER_CANCELED) {
                            errorCode = BillingCodes.CANCELED;
                        } else if (result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE) {
                            errorCode = BillingCodes.PRODUCT_UNAVAILABLE;
                        } else if (result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE) {
                            errorCode = BillingCodes.BILLING_UNAVAILABLE;
                        } else if (result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_SERVICE_UNAVAILABLE) {
                            errorCode = BillingCodes.SERVICE_UNAVAILABLE;
                        } else {
                            errorCode = BillingCodes.UNEXCEPTED;
                        }
                        listener.payFailed(purchase != null
                                ? new Transaction(purchase.getOrderId(), errorCode, purchase.getSku(), null, null, new Date(purchase.getPurchaseTime()))
                                : new Transaction(null, errorCode, sku, null, null, new Date()));
                        return;
                    }
                    Transaction transaction = new Transaction(purchase.getOrderId(), BillingCodes.SUCC, purchase.getSku(), purchase.getSignature(), purchase.getOriginalJson(), new Date(purchase.getPurchaseTime()));
                    listener.paySucceed(transaction);
                    try {
                        iabHelper.consumeAsync(purchase, GooglePlayBilling.this);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        Gdx.app.error("GooglePlayBilling Consume Failed", e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            listener.payFailed(new Transaction(null, BillingCodes.UNEXCEPTED, sku, null, null, new Date()));
            Gdx.app.error("GooglePlayBilling", "Pay Error", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityCodes.REQUEST_PAY && iabHelper != null) {
            iabHelper.handleActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onIabSetupFinished(IabResult result) {
        if (!result.isSuccess()) {
            destroy();
            return;
        }
        try {
            if (iabBroadcastReceiver != null) {
                context.unregisterReceiver(iabBroadcastReceiver);
                iabBroadcastReceiver = null;
            }
            iabBroadcastReceiver = new IabBroadcastReceiver(this);
            IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
            context.registerReceiver(iabBroadcastReceiver, broadcastFilter);
        } catch (Exception e) {
            Gdx.app.error("GooglePlayBilling", "initialize failed", e);
        }

        try {
            iabHelper.queryInventoryAsync(this);
        } catch (IabHelper.IabAsyncInProgressException e) {
            Gdx.app.error("GooglePlayBilling", "query inventory failed", e);
        }
    }

    @Override
    public void onConsumeFinished(Purchase purchase, IabResult result) {
        if (!result.isSuccess()) {
            Gdx.app.error("GooglePlayBilling", result.getMessage());
        }
    }

    @Override
    public void onConsumeMultiFinished(List<Purchase> purchases, List<IabResult> results) {
        for (int i = 0; i < purchases.size(); i++) {
            Purchase purchase = purchases.get(i);
            IabResult result = results.get(i);
            onConsumeFinished(purchase, result);
        }
    }

    @Override
    public void receivedBroadcast() {
        if (iabHelper == null)
            return;
        try {
            iabHelper.queryInventoryAsync(this);
        } catch (IabHelper.IabAsyncInProgressException e) {
            Gdx.app.error("GooglePlayBilling", "query inventory failed", e);
        }
    }

    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        if (!result.isSuccess()) {
            return;
        }
        if (iabHelper == null)
            return;
        List<Purchase> purchases = inv.getAllPurchases();
        if (purchases.isEmpty()) {
            return;
        }
        try {
            iabHelper.consumeAsync(purchases, this);
        } catch (IabHelper.IabAsyncInProgressException e) {
            Gdx.app.error("GooglePlayBilling", "consume failed", e);
        }
    }

    public void destroy() {
        if (iabBroadcastReceiver != null) {
            context.unregisterReceiver(iabBroadcastReceiver);
            iabBroadcastReceiver = null;
        }
        if (iabHelper != null) {
            try {
                iabHelper.disposeWhenFinished();
            } catch (Exception e) {
                e.printStackTrace();
            }
            iabHelper = null;
        }
    }

}
