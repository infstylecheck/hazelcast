/*
 * Copyright (c) 2008-2017, Hazelcast, Inc. All Rights Reserved.
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
 */
package com.hazelcast.internal.nearcache;

import com.hazelcast.cache.impl.HazelcastServerCacheManager;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.adapter.DataStructureAdapter;
import com.hazelcast.monitor.NearCacheStats;
import com.hazelcast.spi.serialization.SerializationService;

import javax.cache.CacheManager;

/**
 * Context for unified Near Cache tests.
 */
public class NearCacheTestContext<K, V, NK, NV> {

    /**
     * The {@link SerializationService} used by the configured Near Cache.
     */
    public final SerializationService serializationService;
    /**
     * The {@link HazelcastInstance} which has the configured Near Cache.
     *
     * In a scenario with Hazelcast client and member, this will be the client.
     */
    public final HazelcastInstance nearCacheInstance;
    /**
     * The {@link HazelcastInstance} which holds backing data structure for the Near Cache.
     *
     * In a scenario with Hazelcast client and member, this will be the member.
     */
    public final HazelcastInstance dataInstance;
    /**
     * The {@link DataStructureAdapter} which has the configured Near Cache.
     *
     * In a scenario with Hazelcast client and member, this will be the near cached data structure on the client.
     */
    public final DataStructureAdapter<K, V> nearCacheAdapter;
    /**
     * The {@link DataStructureAdapter} which has the original data.
     *
     * In a scenario with Hazelcast client and member, this will be the original data structure on the member.
     */
    public final DataStructureAdapter<K, V> dataAdapter;
    /**
     * The {@link NearCacheConfig} of the configured Near Cache.
     */
    public final NearCacheConfig nearCacheConfig;
    /**
     * Specifies if the we are able to retrieve {@link DataStructureAdapter#getLocalMapStats()}.
     */
    public final boolean hasLocalData;

    /**
     * The configured {@link NearCache}.
     */
    public final NearCache<NK, NV> nearCache;
    /**
     * The {@link NearCacheStats} of the configured Near Cache.
     */
    public final NearCacheStats stats;
    /**
     * The {@link NearCacheManager} which manages the configured Near Cache.
     */
    public final NearCacheManager nearCacheManager;
    /**
     * The {@link CacheManager} if the configured {@link DataStructureAdapter} is a JCache implementation.
     */
    public final CacheManager cacheManager;
    /**
     * The {@link HazelcastServerCacheManager} if the configured {@link DataStructureAdapter} is a JCache implementation.
     */
    public final HazelcastServerCacheManager memberCacheManager;

    public NearCacheTestContext(SerializationService serializationService,
                                HazelcastInstance nearCacheInstance,
                                DataStructureAdapter<K, V> nearCacheAdapter,
                                NearCacheConfig nearCacheConfig,
                                boolean hasLocalData,
                                NearCache<NK, NV> nearCache,
                                NearCacheManager nearCacheManager) {
        this(serializationService, nearCacheInstance, nearCacheInstance, nearCacheAdapter, nearCacheAdapter, nearCacheConfig,
                hasLocalData, nearCache, nearCacheManager, null, null);
    }

    public NearCacheTestContext(SerializationService serializationService,
                                HazelcastInstance nearCacheInstance,
                                HazelcastInstance dataInstance,
                                DataStructureAdapter<K, V> nearCacheAdapter,
                                DataStructureAdapter<K, V> dataAdapter,
                                NearCacheConfig nearCacheConfig,
                                boolean hasLocalData,
                                NearCache<NK, NV> nearCache,
                                NearCacheManager nearCacheManager) {
        this(serializationService, nearCacheInstance, dataInstance, nearCacheAdapter, dataAdapter, nearCacheConfig, hasLocalData,
                nearCache, nearCacheManager, null, null);
    }

    public NearCacheTestContext(SerializationService serializationService,
                                HazelcastInstance nearCacheInstance,
                                HazelcastInstance dataInstance,
                                DataStructureAdapter<K, V> nearCacheAdapter,
                                DataStructureAdapter<K, V> dataAdapter,
                                NearCacheConfig nearCacheConfig,
                                boolean hasLocalData,
                                NearCache<NK, NV> nearCache,
                                NearCacheManager nearCacheManager,
                                CacheManager cacheManager,
                                HazelcastServerCacheManager memberCacheManager) {
        this.serializationService = serializationService;
        this.nearCacheInstance = nearCacheInstance;
        this.dataInstance = dataInstance;
        this.nearCacheAdapter = nearCacheAdapter;
        this.dataAdapter = dataAdapter;
        this.nearCacheConfig = nearCacheConfig;
        this.hasLocalData = hasLocalData;

        this.nearCache = nearCache;
        this.stats = (nearCache == null) ? null : nearCache.getNearCacheStats();
        this.nearCacheManager = nearCacheManager;
        this.cacheManager = cacheManager;
        this.memberCacheManager = memberCacheManager;
    }
}
