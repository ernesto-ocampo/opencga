package org.opencb.opencga.storage.core.variant.annotation;

import org.opencb.datastore.core.QueryOptions;
import org.opencb.opencga.lib.common.TimeUtils;
import org.opencb.opencga.storage.core.variant.adaptors.VariantDBAdaptor;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by jacobo on 9/01/15.
 */
public class VariantAnnotationManager {

    public static final String CLEAN = "clean";
    public static final String ANNOTATE_ALL = "annotateAll";
    public static final String OVERRIDE = "override";
    public static final String FILE_NAME = "fileName";
    public static final String OUT_DIR = "outDir";

    private VariantDBAdaptor dbAdaptor;
    private VariantAnnotator variantAnnotator;

    public VariantAnnotationManager(VariantAnnotator variantAnnotator, VariantDBAdaptor dbAdaptor) {
        this.dbAdaptor = dbAdaptor;
        this.variantAnnotator = variantAnnotator;
    }

    public void annotate(QueryOptions options) throws IOException {
        URI annotationFile = createAnnotation(
                Paths.get(options.getString(OUT_DIR, "/tmp")),
                options.getString(FILE_NAME, "annotation_" + TimeUtils.getTime()),
                options);

        loadAnnotation(annotationFile, options.getBoolean(CLEAN, false));
    }

    public URI createAnnotation(Path outDir, String fileName, QueryOptions options) throws IOException {
        return this.variantAnnotator.createAnnotation(dbAdaptor, outDir, fileName, options);
    }

    public void loadAnnotation(URI uri, boolean clean) throws IOException {
        variantAnnotator.loadAnnotation(dbAdaptor, uri, clean);
    }

}
