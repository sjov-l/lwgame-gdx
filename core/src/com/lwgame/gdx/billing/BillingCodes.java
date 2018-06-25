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

public interface BillingCodes {

//    /** 支付成功 */
//    SUCC(0, "success"),
    int SUCC = 0;
//    /** 支付sdk未初始化或初始化失败 */
//    SDK_UNINITIALIZED(1001, "billing sdk doesn't initlaize."),
    int SDK_UNINITIALIZED = 1001;
//    /** 商品未找到(商品编号错误) */
//    PRODUCT_UNAVAILABLE(1002, "didn't find the match product."),
    int PRODUCT_UNAVAILABLE = 1002;
//    /** 余额不足 */
//    CREDIT_NOT_ENOUGH(1003, "your credit is not enough."),
    int CREDIT_NOT_ENOUGH = 1003;
//    /** 用户主动取消支付 */
//    CANCELED(1004, "user canceled."),
    int CANCELED = 1004;
//    /** 支付不可用(权限问题或不支持支付) */
//    BILLING_UNAVAILABLE(1005, "the billing is unavailable."),
    int BILLING_UNAVAILABLE = 1005;
//    /** 系统错误 */
//    SERVICE_UNAVAILABLE(1006, "service unavailable."),
    int SERVICE_UNAVAILABLE = 1006;
//    /** 未知错误 */
//    UNEXCEPTED(9000, "unknown error.")
    int UNEXCEPTED = 9000;
//    ;
//
//    public final int code;
//    public final String message;
//
//    BillingCodes(int code, String message) {
//        this.code = code;
//        this.message = message;
//    }

}
