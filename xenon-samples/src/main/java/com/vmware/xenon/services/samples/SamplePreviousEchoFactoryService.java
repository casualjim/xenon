/*
 * Copyright (c) 2014-2015 VMware, Inc. All Rights Reserved.
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

package com.vmware.xenon.services.samples;

import com.vmware.xenon.common.FactoryService;
import com.vmware.xenon.common.Service;
import com.vmware.xenon.services.common.ServiceUriPaths;

/**
 * This is a factory for the service that returns the _previous_ message (N-1).
 */
public class SamplePreviousEchoFactoryService extends FactoryService {

    public static final String SELF_LINK = ServiceUriPaths.SAMPLES + "/previous-echoes";

    public SamplePreviousEchoFactoryService() {
        super(SamplePreviousEchoService.EchoServiceState.class);
    }

    @Override
    public Service createServiceInstance() throws Throwable {
        return new SamplePreviousEchoService();
    }

}
