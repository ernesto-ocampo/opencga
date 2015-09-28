package org.opencb.opencga.storage.mongodb.variant.stats;

import org.opencb.opencga.storage.core.variant.stats.VariantStatisticsManagerAggregatedExacTest;
import org.opencb.opencga.storage.core.variant.stats.VariantStatisticsManagerAggregatedTest;
import org.opencb.opencga.storage.mongodb.variant.MongoDBVariantStorageManager;
import org.opencb.opencga.storage.mongodb.variant.MongoVariantStorageManagerTestUtils;

/**
 * Created by hpccoll1 on 01/06/15.
 */
public class MongoVariantStatisticsManagerAggregatedExacTest extends VariantStatisticsManagerAggregatedExacTest {

    @Override
    protected MongoDBVariantStorageManager getVariantStorageManager() throws Exception {
        return MongoVariantStorageManagerTestUtils.getVariantStorageManager();
    }

    @Override
    protected void clearDB(String dbName) throws Exception {
        MongoVariantStorageManagerTestUtils.clearDB(dbName);
    }
}
