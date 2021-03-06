/*
 * Copyright 2018 Dynatrace LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dynatrace.oneagent.sdk.impl.proxy;

import java.lang.reflect.Method;

import com.dynatrace.oneagent.sdk.impl.OneAgentSDKFactoryImpl;

/**
 * class forwards every API call to OneAgent impl via pre loaded reflection calls.
 *
 * @author Alram.Lechner
 *
 */
public class SDK2AgentInternalApiProxy {

    private Object agentImpl;
    private final Method oneAgentSDKFactory_createSdk;
    private final Method oneAgentSDK_traceIncomingRemoteCall;
    private final Method oneAgentSDK_traceOutgoingRemoteCall;
    private final Method oneAgentSDK_createInProcessLink;
    private final Method oneAgentSDK_traceInProcessLink;
    private final Method oneAgentSDK_setLoggingCallback;
    private final Method oneAgentSDK_isCapturing;
    private final Method oneAgentSDK_addCustomRequestAttribute_1; // String, String 
    private final Method oneAgentSDK_addCustomRequestAttribute_2; // String, long
    private final Method oneAgentSDK_addCustomRequestAttribute_3; // String, double
    private final Method tracer_start;
    private final Method tracer_end;
    private final Method tracer_error_1; // string
    private final Method tracer_error_2; // throwable
    private final Method outgoingTaggable_getDynatraceStringTag;
    private final Method outgoingTaggable_getDynatraceByteTag;
    private final Method incomingTaggable_setDynatraceStringTag;
    private final Method incomingTaggable_setDynatraceByteTag;
    private final Method outgoingRemoteCallTracer_setProtocolName;
    private final Method incomingRemoteCallTracer_setProtocolName;

    public SDK2AgentInternalApiProxy(Object agentImpl) throws NoSuchMethodException, SecurityException {
        this.agentImpl = agentImpl;
        oneAgentSDKFactory_createSdk = findMethod("oneAgentSDKFactory_createSDK", new Class[]{});
        oneAgentSDK_traceIncomingRemoteCall = findMethod("oneAgentSDK_traceIncomingRemoteCall", new Class[]{Object.class, String.class, String.class, String.class});
        oneAgentSDK_traceOutgoingRemoteCall = findMethod("oneAgentSDK_traceOutgoingRemoteCall", new Class[]{Object.class, String.class, String.class, String.class,Integer.TYPE, String.class});
        oneAgentSDK_createInProcessLink = findMethod("oneAgentSDK_createInProcessLink", new Class[]{Object.class});
        oneAgentSDK_traceInProcessLink = findMethod("oneAgentSDK_traceInProcessLink", new Class[]{Object.class, Object.class});
        oneAgentSDK_setLoggingCallback = findMethod("oneAgentSDK_setLoggingCallback", new Class[] {Object.class, Object.class});
        oneAgentSDK_isCapturing = findMethod("oneAgentSDK_isCapturing", new Class[] {Object.class});
        oneAgentSDK_addCustomRequestAttribute_1 = findMethod("oneAgentSDK_addCustomRequestAttribute", new Class[] {Object.class, String.class, String.class});
        oneAgentSDK_addCustomRequestAttribute_2 = findMethod("oneAgentSDK_addCustomRequestAttribute", new Class[] {Object.class, String.class, Long.TYPE});
        oneAgentSDK_addCustomRequestAttribute_3 = findMethod("oneAgentSDK_addCustomRequestAttribute", new Class[] {Object.class, String.class, Double.TYPE});
        tracer_start = findMethod("tracer_start", new Class[]{Object.class});
        tracer_end = findMethod("tracer_end", new Class[]{Object.class});
        tracer_error_1 = findMethod("tracer_error", new Class[]{Object.class,String.class});
        tracer_error_2 = findMethod("tracer_error", new Class[]{Object.class,Throwable.class});
        outgoingTaggable_getDynatraceStringTag = findMethod("outgoingTaggable_getDynatraceStringTag", new Class[] {Object.class});
        outgoingTaggable_getDynatraceByteTag = findMethod("outgoingTaggable_getDynatraceByteTag", new Class[] {Object.class});
        incomingTaggable_setDynatraceStringTag = findMethod("incomingTaggable_setDynatraceStringTag", new Class[] {Object.class, String.class});
        incomingTaggable_setDynatraceByteTag = findMethod("incomingTaggable_setDynatraceByteTag", new Class[] {Object.class, byte[].class});
        outgoingRemoteCallTracer_setProtocolName = findMethod("outgoingRemoteCallTracer_setProtocolName", new Class[] {Object.class, String.class});
        incomingRemoteCallTracer_setProtocolName = findMethod("incomingRemoteCallTracer_setProtocolName", new Class[] {Object.class, String.class});
    }

    private Method findMethod(String name, Class<?>... args) throws NoSuchMethodException, SecurityException {
        return agentImpl.getClass().getMethod(name, args);
    }

    private Object invoke(Method m, Object... args) {
        try {
            return m.invoke(agentImpl, args);
        } catch (Exception e) {
            if (OneAgentSDKFactoryImpl.debugOneAgentSdkStub) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public Object oneAgentSDKFactory_createSdk() {
        return invoke(oneAgentSDKFactory_createSdk);
    }

    Object oneAgentSDK_traceIncomingRemoteCall(Object sdk, String remoteMethod, String remoteService, String serviceEndpoint) {
        return invoke(oneAgentSDK_traceIncomingRemoteCall, sdk, remoteMethod, remoteService, serviceEndpoint);
    }

    Object oneAgentSDK_traceOutgoingRemoteCall(Object sdk, String remoteMethod, String remoteService,
            String serverEndpoint, int channelType, String channelEndpoint) {
        return invoke(oneAgentSDK_traceOutgoingRemoteCall, sdk, remoteMethod, remoteService, serverEndpoint,
                channelType, channelEndpoint);
    }
    
	Object oneAgentSDK_createInProcessLink(Object agentSdkImpl) {
		return invoke(oneAgentSDK_createInProcessLink, agentSdkImpl);
	}

	Object oneAgentSDK_traceInProcessLink(Object agentSdkImpl, InProcessLinkImpl inProcessLink) {
		return invoke(oneAgentSDK_traceInProcessLink, agentSdkImpl, inProcessLink.getAgentProvidedLink());
	}

    void oneAgentSDK_setLoggingCallback(Object sdk, Object loggingCallback) {
        invoke(oneAgentSDK_setLoggingCallback, sdk, loggingCallback);
    }

    Boolean oneAgentSDK_isCapturing(Object sdk) {
        return (Boolean) invoke(oneAgentSDK_isCapturing, sdk);
    }

	public void oneAgentSDK_addCustomRequestAttribute(Object agentSdkImpl, String key, String value) {
        invoke(oneAgentSDK_addCustomRequestAttribute_1, agentSdkImpl, key, value);
	}

	public void oneAgentSDK_addCustomRequestAttribute(Object agentSdkImpl, String key, long value) {
		invoke(oneAgentSDK_addCustomRequestAttribute_2, agentSdkImpl, key, value);
	}

	public void oneAgentSDK_addCustomRequestAttribute(Object agentSdkImpl, String key, double value) {
		invoke(oneAgentSDK_addCustomRequestAttribute_3, agentSdkImpl, key, value);
	}

    void tracer_start(Object node) {
        invoke(tracer_start, node);
    }

    void tracer_end(Object node) {
        invoke(tracer_end, node);
    }

    void tracer_error(Object node, String message) {
        invoke(tracer_error_1, node, message);
    }

    void tracer_error(Object node, Throwable error) {
        invoke(tracer_error_2, node, error);
    }

    String outgoingTaggable_getDynatraceStringTag(Object taggableClient) {
        return (String) invoke(outgoingTaggable_getDynatraceStringTag, taggableClient);
    }

    byte[] outgoingTaggable_getDynatraceByteTag(Object taggableClient) {
        return (byte[]) invoke(outgoingTaggable_getDynatraceByteTag, taggableClient);
    }

    void incomingTaggable_setDynatraceStringTag(Object taggableServer, String tag) {
        invoke(incomingTaggable_setDynatraceStringTag, taggableServer, tag);
    }

    void incomingTaggable_setDynatraceByteTag(Object taggableServer, byte[] tag) {
        invoke(incomingTaggable_setDynatraceByteTag, taggableServer, tag);
    }

    void outgoingRemoteCallTracer_setProtocolName(Object remoteCallClient, String protocolname) {
        invoke(outgoingRemoteCallTracer_setProtocolName, remoteCallClient, protocolname);
    }

    void incomingRemoteCallTracer_setProtocolName(Object remoteCallServer, String protocolname) {
        invoke(incomingRemoteCallTracer_setProtocolName, remoteCallServer, protocolname);
    }
}
