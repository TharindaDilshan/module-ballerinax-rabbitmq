/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.messaging.rabbitmq;

import io.ballerina.runtime.api.async.Callback;
import org.ballerinalang.messaging.rabbitmq.observability.RabbitMQMetricsUtil;
import org.ballerinalang.messaging.rabbitmq.observability.RabbitMQObservabilityConstants;

import java.util.concurrent.CountDownLatch;

/**
 * The error resource callback implementation for RabbitMQ async consumer.
 *
 * @since 1.2.0
 */
public class RabbitMQErrorResourceCallback implements Callback {
    private CountDownLatch countDownLatch;

    RabbitMQErrorResourceCallback(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void notifySuccess() {
        countDownLatch.countDown();
    }

    @Override
    public void notifyFailure(io.ballerina.runtime.api.values.BError error) {
        countDownLatch.countDown();
        RabbitMQMetricsUtil.reportError(RabbitMQObservabilityConstants.ERROR_TYPE_ERROR_DISPATCH);
        error.printStackTrace();
    }
}
