/*
 * @(#)Constants.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    May 17, 2007   LIBmm25975   Initial creation.
 * dhq348    Jun 06, 2007   LIBmm25975   Rework after first meeting of inspection LX179934.
 */
package com.motorola.btc.research.target.pm.search.util;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 * Utility class for searching.
 * 
 * <pre>
 * CLASS:
 * This class contains some utility methods and constants for the searching functionality.
 * </pre>
 */
public class SearchUtil
{

    /**
     * The use case id constant.
     */
    public static final String UC_ID = "ucid";

    /**
     * The use case name constant.
     */
    public static final String UC_NAME = "ucname";

    /**
     * The use case description constant.
     */
    public static final String UC_DESCRIPTION = "ucdesc";

    /**
     * The document name constant.
     */
    public static final String DOCUMENT_NAME = "DOCUMENT_NAME";

    /**
     * The feature id constant.
     */
    public static final String FEATURE_ID = "featureid";

    /**
     * The flow description constant.
     */
    public static final String FLOW_DESCRIPTION = "flowdesc";

    /**
     * The from step constant.
     */
    public static final String FROM_STEP = "fromstep";

    /**
     * The to step constant.
     */
    public static final String TO_STEP = "tostep";

    /**
     * The step id constant.
     */
    public static final String STEP_ID = "stepid";

    /**
     * The step action constant.
     */
    public static final String STEP_ACTION = "stepaction";

    /**
     * The step condition constant.
     */
    public static final String STEP_CONDITION = "stepcond";

    /**
     * The step response constant.
     */
    public static final String STEP_RESPONSE = "stepresponse";

    /**
     * The document body constant.
     */
    public static final String DOCUMENT_BODY = "DOCUMENT_BODY";

    /**
     * Returns the default index. It can be a FSDirectory (file system) or RAMDirectory (in memory).
     * 
     * @return The default index.
     */
    public static final Directory getDefaultIndex()
    {
        return new RAMDirectory();
    }

}
