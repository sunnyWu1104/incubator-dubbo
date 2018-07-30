/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.rpc.proxy;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.fqgj.log.util.Constants;
import org.apache.logging.log4j.ThreadContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * InvokerHandler
 */
public class InvokerInvocationHandler implements InvocationHandler {

    private final Invoker<?> invoker;

    public InvokerInvocationHandler(Invoker<?> handler) {
        this.invoker = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(invoker, args);
        }
        if ("toString".equals(methodName) && parameterTypes.length == 0) {
            return invoker.toString();
        }
        if ("hashCode".equals(methodName) && parameterTypes.length == 0) {
            return invoker.hashCode();
        }
        if ("equals".equals(methodName) && parameterTypes.length == 1) {
            return invoker.equals(args[0]);
        }
        RpcInvocation rpcInvocation = new RpcInvocation(method, args);
        // 这里将cosumer端的traceId放入RpcInvocation
        String traceId = ThreadContext.get("traceId");
        if (StringUtils.isNotEmpty(traceId)){
            rpcInvocation.setAttachment("traceId", traceId);
        }
        // 这里将cosumer端的spanId放入RpcInvocation
        String spanId = ThreadContext.get("spanId");
        if (StringUtils.isNotEmpty(spanId)){
            rpcInvocation.setAttachment("spanId", spanId);
        }
        return invoker.invoke(rpcInvocation).recreate();
    }

}
