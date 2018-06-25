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

package com.lwgame.gdx.android.platform;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidEventListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.lwgame.gdx.ActivityCodes;
import com.lwgame.gdx.platform.GameCenter;

public class GooglePlayGameCenter implements GameCenter, AndroidEventListener {

    private AndroidApplication context;
    private GoogleSignInClient client;
    private GoogleSignInAccount account;
    private LeaderboardsClient leaderboardsClient;

    public GooglePlayGameCenter(AndroidApplication context) {
        this.context = context;
        this.client = GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        context.addAndroidEventListener(this);
        signIn();
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        signOut();
        account = null;
        leaderboardsClient = null;
        client = null;
    }

    @Override
    public void submitScore(String leaderboardId, long score) {
        if (leaderboardsClient != null) {
            leaderboardsClient.submitScore(leaderboardId, score);
        } else {
            signIn();
        }
    }

    @Override
    public void showLeaderboard(String leaderboardId) {
        if (leaderboardsClient != null) {
            leaderboardsClient.getLeaderboardIntent(leaderboardId).addOnSuccessListener(new OnSuccessListener<Intent>() {
                @Override
                public void onSuccess(Intent intent) {
                    context.startActivityForResult(intent, ActivityCodes.REQUEST_SHOW_LEADERBOARD);
                }
            });
        } else {
            signIn();
        }
    }

    @Override
    public void goMarketRating() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName()));
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    @Override
    public void shareGame(String title, String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, text + "\nhttps://play.google.com/store/apps/details?id=" + context.getPackageName());
            intent.setType("text/plain");
            context.startActivity(Intent.createChooser(intent, title));
        } catch (Exception e) {}
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityCodes.REQUEST_GOOGLE_ACCOUNT_SIGN_IN) {
//            GoogleSignIn.getSignedInAccountFromIntent(data)
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                onConnected(result.getSignInAccount());
            }
        }
    }

    private void signIn() {
        if (client != null
                && GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
            GoogleSignInAccount last = GoogleSignIn.getLastSignedInAccount(context);
            if (last != null) {
                onConnected(last);
                return;
            }
            // 1. 尝试静默登录
            client.silentSignIn().addOnCompleteListener(context, new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                    if (task.isSuccessful()) {
                        onConnected(task.getResult());
                    } else {
                        // 2. 显式登录
                        context.startActivityForResult(client.getSignInIntent(), ActivityCodes.REQUEST_GOOGLE_ACCOUNT_SIGN_IN);
                    }
                }
            });
        }
    }

    private void signOut() {
        if (client != null && GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
            client.signOut().addOnCompleteListener(context, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    onDisconnected();
                }
            });
        }
    }

    private void onConnected(GoogleSignInAccount account) {
        if (account != this.account) {
            this.account = account;
            this.leaderboardsClient = Games.getLeaderboardsClient(context, account);
        }
    }

    private void onDisconnected() {
        this.account = null;
    }

}
