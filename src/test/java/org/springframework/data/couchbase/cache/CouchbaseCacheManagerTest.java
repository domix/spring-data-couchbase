/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.couchbase.cache;

import com.couchbase.client.CouchbaseClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.TestApplicationConfig;
import org.springframework.data.couchbase.util.BucketCreationListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Verifies the correct functionality of the CouchbaseCacheManager.
 *
 * @author Michael Nitschinger
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfig.class)
@TestExecutionListeners(BucketCreationListener.class)
public class CouchbaseCacheManagerTest {

  /**
   * Contains a reference to the actual CouchbaseClient.
   */
  private CouchbaseClient client;

  @Autowired
  private String couchbaseHost;

  @Autowired
  private String couchbaseBucket;

  @Autowired
  private String couchbasePassword;

  @Before
  public void setup() throws Exception {
    client = new CouchbaseClient(Arrays.asList(new URI(couchbaseHost)), couchbaseBucket, couchbasePassword);
  }

  /**
   * Tests the main functionality of the manager: loading the caches.
   */
  @Test
  public void testCacheInit() {
    HashMap<String, CouchbaseClient> instances =
      new HashMap<String, CouchbaseClient>();
    instances.put("test", client);

    CouchbaseCacheManager manager = new CouchbaseCacheManager(instances);
    assertEquals(instances, manager.getClients());
  }

}
