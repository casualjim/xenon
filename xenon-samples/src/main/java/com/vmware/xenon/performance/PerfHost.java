/*
 * Copyright (c) 2015 VMware, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, without warranties or
 * conditions of any kind, EITHER EXPRESS OR IMPLIED.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.vmware.xenon.performance;

import java.util.logging.Level;

import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.ServiceHost;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.services.common.ExampleFactoryService;
import com.vmware.xenon.services.common.RootNamespaceService;

/**
 * Host a number of services targeting performance benchrmaks
 */
public class PerfHost extends ServiceHost {

    public static void main(String[] args) throws Throwable {
        PerfHost h = new PerfHost();
        h.initialize(args);
        h.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            h.log(Level.WARNING, "Host stopping ...");
            h.stop();
            h.log(Level.WARNING, "Host is stopped");
        }));
    }

    /**
     * Currently: Start a continuum of example services
     * TODO -- test UI rendering
     */
    @Override
    public ServiceHost start() throws Throwable {
        super.start();

        startDefaultCoreServicesSynchronously();

        super.startService(
                Operation.createPost(UriUtils.buildUri(this, RootNamespaceService.class)),
                new RootNamespaceService()
        );

        super.startService(
                Operation.createPost(UriUtils.buildUri(this,
                        SimpleStatelessService.class)),
                new SimpleStatelessService());

        super.startService(
                Operation.createPost(UriUtils.buildUri(this,
                        SimpleStatefulService.SimpleStatefulFactoryService.class)),
                new SimpleStatefulService.SimpleStatefulFactoryService(PerfUtils.SimpleState.class));

        super.startService(
                Operation.createPost(UriUtils.buildUri(this,
                        PersistedService.PersistedFactoryService.class)),
                new PersistedService.PersistedFactoryService(PerfUtils.SimpleState.class));

        super.startService(
                Operation.createPost(UriUtils.buildUri(this,
                        ReplicatedService.ReplicatedFactoryService.class)),
                new ReplicatedService.ReplicatedFactoryService(PerfUtils.SimpleState.class));

        super.startService(
                Operation.createPost(UriUtils.buildUri(this,
                        OwnerSelectedService.OwnerSelectedFactoryService.class)),
                new OwnerSelectedService.OwnerSelectedFactoryService(PerfUtils.SimpleState.class));

        super.startService(
                Operation.createPost(UriUtils.buildUri(this,
                        QuorumEnforcedService.QuorumEnforcedFactoryService.class)),
                new QuorumEnforcedService.QuorumEnforcedFactoryService(PerfUtils.SimpleState.class));

        super.startService(
                Operation.createPost(UriUtils.buildUri(this,
                        FullCapService.FullCapFactoryService.class)),
                FullCapService.FullCapFactoryService.create(PerfUtils.SimpleState.class));

        super.startService(
                Operation.createPost(UriUtils.buildUri(this, ExampleFactoryService.class)),
                new ExampleFactoryService());

        return this;
    }
}
