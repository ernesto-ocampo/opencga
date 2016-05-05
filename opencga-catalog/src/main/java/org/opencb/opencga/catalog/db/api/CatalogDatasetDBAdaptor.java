package org.opencb.opencga.catalog.db.api;

import org.opencb.commons.datastore.core.Query;
import org.opencb.commons.datastore.core.QueryOptions;
import org.opencb.commons.datastore.core.QueryParam;
import org.opencb.commons.datastore.core.QueryResult;
import org.opencb.commons.datastore.mongodb.MongoDBCollection;
import org.opencb.opencga.catalog.exceptions.CatalogDBException;
import org.opencb.opencga.catalog.models.Dataset;

import java.util.HashMap;
import java.util.Map;

import static org.opencb.commons.datastore.core.QueryParam.Type.*;

/**
 * Created by pfurio on 04/05/16.
 */
public interface CatalogDatasetDBAdaptor extends CatalogDBAdaptor<Dataset> {

    enum QueryParams implements QueryParam {

        ID("id", DOUBLE, ""),
        NAME("name", TEXT, ""),
        CREATION_DATE("creationDate", TEXT, ""),
        DESCRIPTION("description", TEXT, ""),
        FILES("files", INTEGER_ARRAY, ""),
        STATUS("status", TEXT_ARRAY, ""),
        STATUS_STATUS("status.status", TEXT, ""),
        STATUS_MSG("status.msg", TEXT, ""),
        STATUS_DATE("status.date", TEXT, ""),

        ATTRIBUTES("attributes", TEXT, ""), // "Format: <key><operation><stringValue> where <operation> is [<|<=|>|>=|==|!=|~|!~]"
        NATTRIBUTES("nattributes", DECIMAL, ""), // "Format: <key><operation><numericalValue> where <operation> is [<|<=|>|>=|==|!=|~|!~]"
        BATTRIBUTES("battributes", BOOLEAN, ""), // "Format: <key><operation><true|false> where <operation> is [==|!=]"

        STUDY_ID("studyId", INTEGER_ARRAY, "");

        // Fixme: Index attributes
        private static Map<String, QueryParams> map = new HashMap<>();
        static {
            for (QueryParams param : QueryParams.values()) {
                map.put(param.key(), param);
            }
        }

        private final String key;
        private Type type;
        private String description;

        QueryParams(String key, Type type, String description) {
            this.key = key;
            this.type = type;
            this.description = description;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public Type type() {
            return type;
        }

        @Override
        public String description() {
            return description;
        }

        public static Map<String, QueryParams> getMap() {
            return map;
        }

        public static QueryParams getParam(String key) {
            return map.get(key);
        }
    }


    default boolean datasetExists(long datasetId) throws CatalogDBException {
        return count(new Query(QueryParams.ID.key(), datasetId)).first() > 0;
    }

    default void checkDatasetId(long datasetId) throws CatalogDBException {
        if (datasetId < 0) {
            throw CatalogDBException.newInstance("Dataset id '{}' is not valid: ", datasetId);
        }

        long count = count(new Query(QueryParams.ID.key(), datasetId)).first();
        if (count <= 0) {
            throw CatalogDBException.newInstance("Dataset id '{}' does not exist", datasetId);
        } else if (count > 1) {
            throw CatalogDBException.newInstance("'{}' documents found with the Dataset id '{}'", count, datasetId);
        }
    }

    long getStudyIdByDatasetId(long datasetId) throws CatalogDBException;

    QueryResult<Dataset> createDataset(long studyId, Dataset dataset, QueryOptions options) throws CatalogDBException;

    QueryResult<Dataset> getDataset(long datasetId, QueryOptions options) throws CatalogDBException;

    MongoDBCollection getDatasetCollection();

}