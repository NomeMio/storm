/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.storm.scheduler.resource.normalization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.apache.storm.Constants;
import org.junit.jupiter.api.Test;

/**
 * Stub test for trying out the my-tests tooling (jacoco/pitest) against NormalizedResourceOffer.
 */
public class NormalizedResourceOfferStubTest {

    @Test
    public void testEmptyOfferHasZeroMemory() {
        NormalizedResourceOffer offer = new NormalizedResourceOffer();
        assertEquals(0.0, offer.getTotalMemoryMb());
    }

    @Test
    public void testOfferFromMapPicksUpMemory() {
        Map<String, Number> resources = new HashMap<>();
        resources.put(Constants.COMMON_TOTAL_MEMORY_RESOURCE_NAME, 512.0);
        NormalizedResourceOffer offer = new NormalizedResourceOffer(resources);
        assertEquals(512.0, offer.getTotalMemoryMb());
    }
}
