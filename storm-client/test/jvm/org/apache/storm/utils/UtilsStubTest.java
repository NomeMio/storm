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

package org.apache.storm.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Stub test for trying out the my-tests tooling (jacoco/pitest) against Utils.
 */
public class UtilsStubTest {

    @Test
    public void testIsSystemId() {
        assertTrue(Utils.isSystemId("__system"));
        assertFalse(Utils.isSystemId("not-a-system-id"));
    }

    @Test
    public void testUrlEncodeDecodeRoundTrip() {
        String original = "hello world";
        String encoded = Utils.urlEncodeUtf8(original);
        assertTrue(encoded.contains("+") || encoded.contains("%20"));
        assertTrue(Utils.urlDecodeUtf8(encoded).equals(original));
    }
}
