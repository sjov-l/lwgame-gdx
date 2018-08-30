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

package com.lwgame.gdx.ios.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.lwgame.gdx.Lw;
import com.lwgame.gdx.platform.GameCenter;

import apple.coregraphics.struct.CGPoint;
import apple.coregraphics.struct.CGRect;
import apple.coregraphics.struct.CGSize;
import apple.foundation.NSArray;
import apple.foundation.NSData;
import apple.foundation.NSError;
import apple.foundation.NSMutableArray;
import apple.foundation.NSURL;
import apple.gamekit.GKGameCenterViewController;
import apple.gamekit.GKLeaderboard;
import apple.gamekit.GKLocalPlayer;
import apple.gamekit.GKScore;
import apple.gamekit.enums.GKGameCenterViewControllerState;
import apple.gamekit.enums.GKLeaderboardPlayerScope;
import apple.gamekit.enums.GKLeaderboardTimeScope;
import apple.gamekit.protocol.GKGameCenterControllerDelegate;
import apple.uikit.UIActivityViewController;
import apple.uikit.UIApplication;
import apple.uikit.UIDevice;
import apple.uikit.UIPopoverController;
import apple.uikit.UIScreen;
import apple.uikit.UIViewController;
import apple.uikit.c.UIKit;
import apple.uikit.enums.UIPopoverArrowDirection;
import apple.uikit.enums.UIUserInterfaceIdiom;

public class IOSGameCenter implements GameCenter {

    private IOSApplication app;

    private boolean signed;

    static {
        try {
            Class.forName(GKScore.class.getName());
        } catch (Exception e) {}
    }

    public IOSGameCenter(IOSApplication app) {
        this.app = app;
        doAuthenticate();
    }

    private void doAuthenticate() {
        final GKLocalPlayer player = GKLocalPlayer.localPlayer();
        if (player == null) return;

        if (!player.isAuthenticated()) {
            player.setAuthenticateHandler(new GKLocalPlayer.Block_setAuthenticateHandler() {
                @Override
                public void call_setAuthenticateHandler(UIViewController viewController, NSError error) {
                    if (viewController != null) {
                        app.getUIViewController().showDetailViewControllerSender(viewController, null);
                    } else if (player.isAuthenticated()) {
                        doSignature(player);
                    }
                }
            });
        } else if (!signed) {
            doSignature(player);
        }
    }

    private void doSignature(GKLocalPlayer player) {
        player.generateIdentityVerificationSignatureWithCompletionHandler(new GKLocalPlayer.Block_generateIdentityVerificationSignatureWithCompletionHandler() {
            @Override
            public void call_generateIdentityVerificationSignatureWithCompletionHandler(NSURL publicKeyUrl, NSData signature, NSData salt, long timestamp, NSError error) {
                Gdx.app.log("ios gen id verify sign", error != null ? error.toString() : "succ");
                signed = true;
            }
        });
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void submitScore(String leaderboardId, long score) {
        doAuthenticate();
        GKScore gkScore = GKScore.alloc().initWithLeaderboardIdentifier(leaderboardId);
        gkScore.setValue(score);
        NSArray scoreArr = NSArray.arrayWithObject(gkScore);
        GKScore.reportScoresWithCompletionHandler(scoreArr, new GKScore.Block_reportScoresWithCompletionHandler() {
            @Override
            public void call_reportScoresWithCompletionHandler(NSError error) {
                Gdx.app.log("ios leaderboard submit", error != null ? error.toString() : "succ");
            }
        });
    }

    @Override
    public void showLeaderboard(final String leaderboardId) {
        doAuthenticate();
        GKLeaderboard.loadLeaderboardsWithCompletionHandler(new GKLeaderboard.Block_loadLeaderboardsWithCompletionHandler() {
            @Override
            public void call_loadLeaderboardsWithCompletionHandler(NSArray<? extends GKLeaderboard> leaderboards, NSError error) {
                if (error != null) {
                    Gdx.app.error("ios load leaderboards failed", error.toString());
                    return;
                }
                for (int i = leaderboards.size() - 1; i >= 0; i--) {
                    GKLeaderboard leaderboard = leaderboards.objectAtIndex(i);
                    if (leaderboardId.equals(leaderboard.identifier())) {
                        leaderboard.setTimeScope(GKLeaderboardPlayerScope.Global);
                        leaderboard.setTimeScope(GKLeaderboardTimeScope.AllTime);
                        leaderboard.loadScoresWithCompletionHandler(new GKLeaderboard.Block_loadScoresWithCompletionHandler() {
                            @Override
                            public void call_loadScoresWithCompletionHandler(NSArray<? extends GKScore> scores, NSError error) {
                                // do nothing
                                if (error != null) {
                                    Gdx.app.error("ios load leaderboard scores failed", leaderboardId + "->" + error.toString());
                                    return;
                                }
                                GKGameCenterViewController gameCenterView = GKGameCenterViewController.alloc().init();
                                gameCenterView.setViewState(GKGameCenterViewControllerState.Leaderboards);
                                gameCenterView.setLeaderboardIdentifier(leaderboardId);
                                gameCenterView.setLeaderboardTimeScope(GKLeaderboardTimeScope.AllTime);
                                gameCenterView.setGameCenterDelegate(new GKGameCenterControllerDelegate() {
                                    @Override
                                    public void gameCenterViewControllerDidFinish(GKGameCenterViewController gameCenterViewController) {
                                        gameCenterViewController.dismissViewControllerAnimatedCompletion(true, null);
                                    }
                                });
                                app.getUIViewController().presentViewControllerAnimatedCompletion(gameCenterView, true, null);
                            }
                        });
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void goMarketRating() {
        String url = "itms-apps://itunes.apple.com/WebObjects/MZStore.woa/wa/viewContentsUserReviews?type=Purple+Software&id=" + Lw.configuration.getString("ios.appstoreId");
        UIApplication.sharedApplication().openURL(NSURL.URLWithString(url));
    }

    @Override
    public void shareGame(String title, String text) {
        NSArray activityItems = NSMutableArray.alloc().init();
        activityItems.add(title);
        activityItems.add(text);
        activityItems.add(NSURL.URLWithString("itms-apps://itunes.apple.com/WebObjects/MZStore.woa/wa/viewSoftware?id=" + Lw.configuration.getString("ios.appstoreId")));
        UIActivityViewController activityViewController = UIActivityViewController.alloc().initWithActivityItemsApplicationActivities(activityItems, null);
        NSArray excludeTypes = NSMutableArray.alloc().init();
        excludeTypes.add(UIKit.UIActivityTypePrint());
        excludeTypes.add(UIKit.UIActivityTypeCopyToPasteboard());
        excludeTypes.add(UIKit.UIActivityTypeAssignToContact());
        excludeTypes.add(UIKit.UIActivityTypeSaveToCameraRoll());
        activityViewController.setExcludedActivityTypes(excludeTypes);
        activityViewController.setCompletionWithItemsHandler(new UIActivityViewController.Block_setCompletionWithItemsHandler() {
            @Override
            public void call_setCompletionWithItemsHandler(String activityType, boolean completed, NSArray<?> returnedItems, NSError error) {
            }
        });
        if (UIDevice.currentDevice().userInterfaceIdiom() == UIUserInterfaceIdiom.Pad) {
            UIPopoverController popoverController = UIPopoverController.alloc().initWithContentViewController(activityViewController);
            CGSize screen = UIScreen.mainScreen().bounds().size();
            CGPoint org = new CGPoint(screen.width() / 4, 0);
            CGSize size = new CGSize(screen.width() / 2, screen.height() / 4);
            CGRect rect = new CGRect(org, size);
            popoverController.presentPopoverFromRectInViewPermittedArrowDirectionsAnimated(rect, app.getUIViewController().view(), UIPopoverArrowDirection.Any, true);
        } else {
            app.getUIViewController().presentViewControllerAnimatedCompletion(activityViewController, true, null);
        }
    }
}
