/*
 * @(#)TargetProject.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wra050   Jul 21, 2006         -       Initial creation.
 * dhq348   Nov 30, 2006    LIBkk11577   Added new constructor
 * wdt022   Jan 10, 2007    LIBkk11577   Java doc modifications and refactorings.
 * wdt022   Jan 18, 2007    LIBkk11577   Modifications after inspection (LX128956).
 * wsn013   Fev 26, 2007    LIBll29555   Changes according to LIBll29555.
 * dhq348   Apr 26, 2007    LIBmm09879   Modifications according to CR.
 * dhq348   Apr 28, 2007    LIBmm09879   Rework of inspection LX168613.
 * dhq348   Jun 21, 2007    LIBmm25975   Rework after meeting 3 of inspection LX179934.
 * dhq348   Jul 10, 2007    LIBmm76995   Added the method getUseCase(String usecaseId).
 * dhq348   Jul 11, 2007    LIBmm47221   Rework after inspection LX185000.
 * dhq348   Aug 24, 2007    LIBmm93130   Rework after inspection LX201888.
 * dhq348   Oct 23, 2077    LIBnn34008   Added method getInterruptions().
 */
package com.motorola.btc.research.target.pm.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.util.FileUtil;

/**
 * <pre>
 * CLASS:
 * Represents a TargetProject
 * 
 * RESPONSABILITIES:
 * Abstract a TargetProject information
 * 
 * USAGE:
 * 
 * TargetProject project = new TargetProject(name, rootDir);
 * 
 * </pre>
 */
public class TargetProject
{

    /** Extension of the project file */
    public static final String PROJECT_FILE_EXTENSION = ".tgt";

    /** Name of the project file */
    public static final String PROJECT_FILE_NAME = "target" + PROJECT_FILE_EXTENSION;

    /** Test suites' folder */
    public static final String TC_DIR = "testsuites";

    /** Use cases' folder */
    public static final String UC_DIR = "usecases";

    /** The file separator used by the system */
    private static final String FILE_SEPARATOR = FileUtil.getSeparator();

    /** The project name */
    private String name;

    /** The project folder */
    private String rootDir;

    /** The loaded documents */
    private Collection<PhoneDocument> phoneDocuments;

    /**
     * Constructor. Creates an empty project.
     * 
     * @param name The project name.
     * @param rootDir The project root directory.
     */
    public TargetProject(String name, String rootDir)
    {
        this.name = name;
        this.rootDir = rootDir;
        this.phoneDocuments = new ArrayList<PhoneDocument>();
    }

    /**
     * Constructor. Creates a project with an initial documentation.
     * 
     * @param name The project name.
     * @param rootDir The project root directory.
     * @param phoneDocuments The initial documentation.
     */
    public TargetProject(String name, String rootDir, Collection<PhoneDocument> phoneDocuments)
    {
        this.name = name;
        this.rootDir = rootDir;
        this.phoneDocuments = new ArrayList<PhoneDocument>(phoneDocuments);
    }

    /**
     * Get method for <code>name</code> attribute.
     * 
     * @return Returns the Project name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get method for <code>rootDir</code> attribute.
     * 
     * @return The Project root directory.
     */
    public String getRootDir()
    {
        return rootDir;
    }

    /**
     * Method used to get the location of the test suite documents.
     * 
     * @return The test suites' directory.
     */
    public String getTestSuiteDir()
    {
        return rootDir + FILE_SEPARATOR + TC_DIR;
    }

    /**
     * Method used to get the location of the use case documents.
     * 
     * @return The use cases' directory.
     */
    public String getUseCaseDir()
    {
        return rootDir + FILE_SEPARATOR + UC_DIR;
    }

    /**
     * Get method for <code>phoneDocuments</code> attribute.
     * 
     * @return The list of <code>PhoneDocument</code> instances
     */
    public Collection<PhoneDocument> getPhoneDocuments()
    {
        return new ArrayList<PhoneDocument>(this.phoneDocuments);
    }

    /**
     * Verifies if a phonedocument with name equals to <b>filename</b> exists.
     * 
     * @param filename The name of the file to be compared with the existing document names.
     * @return <b>true</b> if exists any document with the same name or <b>false</b> otherwise.
     */
    public boolean existPhoneDocument(String filename)
    {
        boolean result = false;
        for (PhoneDocument document : this.phoneDocuments)
        {
            if (FileUtil.getFileName(document.getDocFilePath()).equalsIgnoreCase(filename))
            {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * This method returns the name of all loaded documents file paths.
     * 
     * @return The list of document file paths.
     */
    public Collection<String> getPhoneDocumentFilePaths()
    {

        Collection<String> result = new ArrayList<String>();
        for (PhoneDocument pDoc : this.phoneDocuments)
        {

            result.add(pDoc.getDocFilePath());
        }
        return result;
    }

    /**
     * It verifies if the passed document is updated, according to the timestamp stored the
     * <code>PhoneDocument</code> instances. It returns <i>false</i> if the document is not
     * found.
     * 
     * @param file The <code>File</code> instance of the document.
     * @return <i>True</i> if is updated, <i>false</i> otherwise.
     */
    public boolean isDocumentUpdated(File file)
    {
        boolean result = false;

        for (PhoneDocument document : this.phoneDocuments)
        {
            if (document.getDocFilePath().equals(file.getAbsolutePath())
                    && document.getLastDocumentModification() >= file.lastModified())
            {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * It retrieves the <code>PhoneDocument</code> instance, according to the informed document
     * name. If there is no loaded document with the informed name, <code>null</code> is returned.
     * 
     * @param filePath The document name.
     * @return The <code>PhoneDocument</code> instance for the informed name. <code>null</code>
     * if no document is loaded.
     */
    public PhoneDocument getPhoneDocumentFromFilePath(String filePath)
    {
        PhoneDocument result = null;

        for (PhoneDocument document : this.phoneDocuments)
        {
            if (document.getDocFilePath().equals(filePath))
            {
                result = document;
                break;
            }
        }
        return result;
    }

    /**
     * Method used to get all features contained in the loaded documents.
     * 
     * @return All loaded features.
     */
    public Collection<Feature> getFeatures()
    {

        Collection<Feature> features = new ArrayList<Feature>();
        for (PhoneDocument document : this.phoneDocuments)
        {
            if (document.isDocumentWellFormed())
            {
                features.addAll(document.getFeatures());
            }
        }

        return features;
    }

    /**
     * Verifies if <code>feature</code> is composed by more than one feature. The method iterates
     * over all features in the project, counts the feature duplication and verifies if it is
     * greater than one.
     * 
     * @param feature The feature to be verified.
     * @return <code>true</code> if the features was composed by more than one feature or
     * <code>false</code> otherwise.
     */
    public boolean isFeatureMerged(Feature feature)
    {
        int count = 0;
        for (Feature current : this.getFeatures())
        {
            if (current.getId().equals(feature.getId()))
            {
                count++;
                if (count > 1)
                {
                    break;
                }
            }
        }
        return count > 1;
    }

    /**
     * Returns a phone document instance by its path. If no document has the given path, then
     * <code>null</code> is returned.
     * 
     * @param path The path of the phone document.
     * @return A phone document or <code>null</code>.
     */
    public PhoneDocument getPhoneDocumentByPath(String path)
    {
        PhoneDocument result = null;
        for (PhoneDocument document : this.phoneDocuments)
        {
            if (document.getDocFilePath().equals(path))
            {
                result = document;
                break;
            }
        }
        return result;
    }

}
