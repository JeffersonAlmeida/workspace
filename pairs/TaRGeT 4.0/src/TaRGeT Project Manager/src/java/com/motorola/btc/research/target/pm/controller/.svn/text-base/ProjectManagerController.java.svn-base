/*
 * @(#)ProjectManagerController.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022   Nov 27, 2006    LIBkk11577   Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   Added new methods.
 * dhq348   Dec 05, 2006    LIBkk11577   Added javadoc.
 * dhq348   Dec 16, 2006    LIBkk11577   Inspection (LX124184) faults fixing.
 * wdt022   Jan 07, 2007    LIBkk11577   Load and reload, and methods for verify duplicity (step id, use case and feature).
 * wdt022   Jan 18, 2007    LIBkk11577   Mofifications after inspection (LX128956).
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * dhq348   Feb 12, 2007    LIBll27713   CR LIBll27713 improvements.
 * dhq348   Feb 14, 2007    LIBll27713   Rework of inspection LX144782.
 * dhq348   Feb 15, 2007    LIBkk22912   CR (LIBkk22912) improvements.
 * wsn013   Fev 26, 2007    LIBll29555   Changes according to LIBll29555.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * dhq348   Apr 26, 2007    LIBmm09879   Modifications according to CR.
 * dhq348   Apr 28, 2007    LIBmm09879   Rework of inspection LX168613.
 * dhq348   May 18, 2007    LIBmm25975   Modification according to CR.
 * dhq348   Jun 05, 2007    LIBmm47221   Added merged features funcionalities.
 * dhq348   Jun 06, 2007    LIBmm25975   Rework after first meeting of inspection LX179934.
 * dhq348   Jun 21, 2007    LIBmm25975   Rework after meeting 3 of inspection LX179934.
 * dhq348   Jun 26, 2007    LIBmm25975   Rework after meeting 2 of inspection LX179934.
 * dhq348   Jul 11, 2007    LIBmm47221   Rework after inspection LX185000.
 * dhq348   Aug 24, 2007    LIBmm93130   Rework after inspection LX201888.
 * dhq348   Oct 11, 2007    LIBnn34008   Added method getAllInterruptions().
 * dhq348   Jan 16, 2008    LIBnn34008   Rework after inspection LX229627.
 * wdt022   Mar 05, 2008    LIBoo89937   Method hasInterruptionError added. 
 */
package com.motorola.btc.research.target.pm.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.common.util.ExcelFileFilter;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.errors.Error;
import com.motorola.btc.research.target.pm.errors.UseCaseError;
import com.motorola.btc.research.target.pm.exceptions.TargetSearchException;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;
import com.motorola.btc.research.target.pm.search.SearchController;
import com.motorola.btc.research.target.pm.xml.TargetProjectXMLFactory;

/**
 * This class represents the internal controller of the PM plug-in.
 * 
 * <pre>
 *    CLASS:
 *    This class represents the internal controller of the PM plug-in.
 *    It is responsible for the internal communication between the 
 *    GUI of the plug-in and the functionalities provided by the plug-in.
 *    
 *    RESPONSIBILITIES:
 *    1) Manage the plug-in internal main functionalities
 *    2) Keep track if there is an opened project
 *    3) Manage the opened project
 *   
 *    COLABORATORS:
 *    1) Uses IAction
 *    2) Uses TargetProject
 *    3) Uses IFeatureManager
 *   
 *    USAGE:
 *    ProjectManagerController manager = ProjectManagerController.getInstance();
 */
public class ProjectManagerController
{
    /**
     * The system file separator
     */
    private static final String FILE_SEPARATOR = FileUtil.getSeparator();

    /**
     * The single instance of ProjectManagerController
     */
    private static ProjectManagerController instance;

    /**
     * The current project
     */
    private TargetProject currentProject;

    /** The list of errors that are presented to the user */
    private Collection<Error> errorList;

    /**
     * The default constructor. Initializes some class attributes.
     */
    private ProjectManagerController()
    {
        super();
        this.currentProject = null;
        this.errorList = new ArrayList<Error>();
    }

    /**
     * Manages the singleton instance.
     * 
     * @return The ProjectManagerController instance.
     */
    public static ProjectManagerController getInstance()
    {
        if (instance == null)
        {
            instance = new ProjectManagerController();
        }
        return instance;
    }

    /**
     * Creates a TargetProject.
     * 
     * @param projectName The name of the project to be created.
     * @param destinationFolder The name of the directory in which the project will be created.
     * @return The created project.
     */
    public TargetProject createProject(String projectName, String destinationFolder)
    {
        this.currentProject = new TargetProject(projectName, destinationFolder + FILE_SEPARATOR
                + projectName);

        return currentProject;
    }

    /**
     * This method is responsible for create the project directory tree. The root directory is
     * returned.
     * 
     * @return The root directory of the project.
     */
    public String createProjectDirectories()
    {
        /* The root dir of the project */
        File rootDirectory = new File(this.getCurrentProject().getRootDir());

        /* test cases directory */
        File tcDirectory = new File(this.getCurrentProject().getTestSuiteDir());

        /* use cases directory */
        File ucDirectory = new File(this.getCurrentProject().getUseCaseDir());

        rootDirectory.mkdir();
        ucDirectory.mkdir();
        tcDirectory.mkdir();

        return this.getCurrentProject().getRootDir();
    }

    /**
     * This method is responsible for creating the project properties xml file.
     * 
     * @throws TargetException In case of any error during the project file creation.
     */
    public void createXMLFile() throws TargetException
    {
        String file = this.currentProject.getRootDir() + FILE_SEPARATOR
                + TargetProject.PROJECT_FILE_NAME;

        TargetProjectXMLFactory.saveProject(this.currentProject, file);
    }

    /**
     * Closes a previous opened project.
     */
    public void closeProject()
    {
        this.clearErrorList();
        currentProject = null;
    }

    /**
     * This method is used to reload the project documents. It is used when any document is changed.
     * 
     * @throws TargetException In case of any fatal error is found in the documents.
     * @return An instance of <code>TargetProjectRefreshInformation</code> describing the result
     * of the refreshing.
     */
    public TargetProjectRefreshInformation reloadProject() throws TargetException
    {
        TargetProjectRefresher projectRefresher = new TargetProjectRefresher(this.currentProject,
                this.errorList);

        TargetProjectRefreshInformation projectRefreshInformation = projectRefresher
                .refreshProject(false);

        this.currentProject = projectRefreshInformation.getNewProject();
        this.errorList = projectRefreshInformation.getNewErrors();

        return projectRefreshInformation;
    }

    /**
     * This method is used to load the documents during the project opening.
     * 
     * @throws TargetException In case of any fatal error is found in the documents.
     * @return An instance of <code>TargetProjectRefreshInformation</code> describing the result
     * of the refreshing.
     */
    public TargetProjectRefreshInformation loadOpenedProject() throws TargetException
    {
        TargetProjectRefresher projectRefresher = new TargetProjectRefresher(this.currentProject,
                this.errorList);

        TargetProjectRefreshInformation projectRefreshInformation = projectRefresher
                .loadOpenedProject(true);

        this.currentProject = projectRefreshInformation.getNewProject();
        this.errorList = projectRefreshInformation.getNewErrors();

        return projectRefreshInformation;
    }

    /**
     * This method is used to load the imported documents. They are tested against the already
     * loaded documents in order to verify any new error.
     * 
     * @param files The documents that will be loaded.
     * @throws TargetException In case of any fatal error is found in the documents.
     * @return An instance of <code>TargetProjectRefreshInformation</code> describing the result
     * of the refreshing.
     */
    public TargetProjectRefreshInformation loadImportedDocumentsIntoProject(Collection<File> files)
            throws TargetException
    {
        TargetProjectRefresher projectRefresher = new TargetProjectRefresher(this.currentProject,
                this.errorList);

        TargetProjectRefreshInformation projectRefreshInformation = projectRefresher
                .loadDocumentsImportedToProject(files, true);

        this.currentProject = projectRefreshInformation.getNewProject();
        this.errorList = projectRefreshInformation.getNewErrors();

        return projectRefreshInformation;
    }

    /**
     * Verify if a document is well formed.
     * 
     * @param fileName The document to verify if it is well formed.
     * @return <code>True</code> if the document is well formed.
     */
    public boolean isDocumentWellFormed(String fileName)
    {
        boolean result = false;

        PhoneDocument pDoc = this.currentProject.getPhoneDocumentFromFilePath(fileName);

        if (pDoc != null && pDoc.isDocumentWellFormed())
        {
            result = true;
        }

        return result;
    }

    /**
     * Method used to verify if there is a modification in any document.
     * 
     * @return <code>True</code> if there is a modification in any document.
     * @throws IOException Thrown when it is not possible to read the project use cases directory.
     */
    public boolean hasDocumentModification() throws IOException
    {
        boolean result = false;
        Collection<File> docFiles = FileUtil.getPhoneDocumentFiles(this.currentProject
                .getUseCaseDir());

        for (File file : docFiles)
        {
            PhoneDocument pDoc = this.currentProject.getPhoneDocumentFromFilePath(file
                    .getAbsolutePath());
            // If there is not a document or the file is not updated.
            if (pDoc == null || !this.currentProject.isDocumentUpdated(file))
            {
                result = true;
                break;
            }
        }

        return result || docFiles.size() != this.currentProject.getPhoneDocuments().size();
    }

    /**
     * The method verifies if the documents passed as parameter are referred by other documents,
     * i.e., if there is a step id reference pointing to some passed document. The method returns
     * all documents that refers those documents passed as parameter. If there is a document that is
     * being referred and it is in the list passed as parameter, this document will not be returned.
     * 
     * @param docsToBeChecked The paths to the documents that will be checked.
     * @return The paths to the documents that refers the documents passed as paramenter.
     */
    public Collection<String> areFilesBeingReferred(Collection<String> docsToBeChecked)
    {
        Collection<String> result = new HashSet<String>();

        for (String fileName : docsToBeChecked)
        {
            PhoneDocument referredDoc = this.currentProject.getPhoneDocumentFromFilePath(fileName);

            if (referredDoc != null && referredDoc.isDocumentWellFormed())
            {
                /* retrieves all documents */
                Collection<PhoneDocument> pDocList = this.currentProject.getPhoneDocuments();
                pDocList.remove(referredDoc); // removes the file passed as
                // parameter

                /*
                 * map that contains the documents and their respective step id references
                 */
                Map<PhoneDocument, Set<StepId>> docToReferredStepIdMap = new LinkedHashMap<PhoneDocument, Set<StepId>>();
                for (PhoneDocument pd : pDocList)
                {
                    if (pd.isDocumentWellFormed())
                    {
                        Set<StepId> allSteps = new HashSet<StepId>();
                        for (Feature feature : pd.getFeatures())
                        {
                            allSteps.addAll(feature.getReferredStepIds());
                        }
                        docToReferredStepIdMap.put(pd, allSteps);
                    }
                }

                /* The referred step ids */
                Set<StepId> documentStepIds = new HashSet<StepId>();
                for (Feature feature : referredDoc.getFeatures())
                {
                    documentStepIds.addAll(feature.getActualStepIds());
                }

                /* iterates over all referredStepIds */
                for (StepId documentStepId : documentStepIds)
                {
                    /* iterates over all documents */
                    for (PhoneDocument phoneDocument : docToReferredStepIdMap.keySet())
                    {
                        /*
                         * checks if the step id references contains the referred step id
                         */
                        if (docToReferredStepIdMap.get(phoneDocument).contains(documentStepId)
                                && !docsToBeChecked.contains(phoneDocument.getDocFilePath()))
                        {
                            result.add(phoneDocument.getDocFilePath());
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Opens a project based on the specified project file name.
     * 
     * @param projectFile The project to open.
     * @return The opened project
     * @throws TargetException In case of any problem during the TaRGeT project file processing.
     */
    public TargetProject openProject(String projectFile) throws TargetException
    {
        currentProject = TargetProjectXMLFactory.loadProject(projectFile);

        File ucFolder = new File(currentProject.getUseCaseDir());
        File tcFolder = new File(currentProject.getTestSuiteDir());
        if (!ucFolder.exists())
        {
            ucFolder.mkdir();
        }
        if (!tcFolder.exists())
        {
            tcFolder.mkdir();
        }

        return currentProject;
    }

    /**
     * Returns the current project
     * 
     * @return The current project
     */
    public TargetProject getCurrentProject()
    {
        return currentProject;
    }

    /**
     * Checks if exists an already opened project
     * 
     * @return <code>True</code> if exists an opened project and false otherwise
     */
    public boolean hasOpenedProject()
    {
        return (currentProject != null);
    }

    /**
     * Returns the list of errors that are presented to the user.
     * 
     * @return The list of errors.
     */
    public List<Error> getErrorList()
    {
        return new ArrayList<Error>(this.errorList);
    }

    /**
     * Method used to clear the error list.
     */
    private void clearErrorList()
    {
        this.errorList.clear();
    }

    /**
     * Renames a document from oldValue to newValue.
     * 
     * @param oldValue The name of the file to be renamed.
     * @param newValue The new file name.
     */
    public boolean renameDocument(String oldValue, String newValue)
    {

        Collection<String> names = null;
        boolean result = false;

        if (oldValue.toLowerCase().endsWith(".doc"))
        {
            names = this.currentProject.getPhoneDocumentFilePaths();
            String fullName = "";
            for (String name : names)
            {
                if (FileUtil.getFileName(name).equals(oldValue))
                {
                    fullName = this.currentProject.getUseCaseDir() + FileUtil.getSeparator()
                            + newValue;
                    this.currentProject.getPhoneDocumentFromFilePath(name).setDocFilePath(fullName);
                    result = FileUtil.renameFile(name, fullName);
                    break;
                }
            }
        }
        else if (oldValue.toLowerCase().endsWith(".xls"))
        {
            String suiteDir = getCurrentProject().getTestSuiteDir();
            suiteDir += FileUtil.getSeparator();
            result = FileUtil.renameFile(suiteDir + oldValue, suiteDir + newValue);
        }

        return result;
    }

    /**
     * Converts the return from <code>FileUtil.loadFilesFromDirectory()</code> to a collection of
     * strings.
     * 
     * @return A collection of strings containing the absolute path of the test suite documents.
     */
    public Collection<String> getTestSuiteDocuments()
    {
        Collection<String> testSuites = new ArrayList<String>();
        try
        {
            Collection<File> files = FileUtil.loadFilesFromDirectory(getCurrentProject()
                    .getTestSuiteDir(), new ExcelFileFilter());
            for (File file : files)
            {
                testSuites.add(file.getAbsolutePath());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return testSuites;
    }

    /**
     * Verifies if <code>folderName</code> contains a similar structure of a target project,
     * indicating that already exists a project in that folder.
     * 
     * @param folderName The name of the folder to be verified.
     * @return <code>True</code> if exists a project in that location or <code>false</code>
     * otherwise.
     */
    public boolean existsProjectInDirectory(String folderName)
    {
        File folder = new File(folderName);
        File tcDirFile = new File(folderName + FILE_SEPARATOR + TargetProject.TC_DIR);
        File ucDirFile = new File(folderName + FILE_SEPARATOR + TargetProject.UC_DIR);
        File targetFile = new File(folderName + FILE_SEPARATOR + TargetProject.PROJECT_FILE_NAME);
        boolean result = false;
        if (folder.exists())
        {
            File[] files = folder.listFiles();
            String filename;
            for (int i = 0; i < files.length; i++)
            {
                filename = files[i].getName();
                if ((filename.equalsIgnoreCase(TargetProject.TC_DIR) && tcDirFile.isDirectory())
                        || (filename.equalsIgnoreCase(TargetProject.UC_DIR) && ucDirFile
                                .isDirectory())
                        || (filename.equalsIgnoreCase(TargetProject.PROJECT_FILE_NAME) && !targetFile
                                .isDirectory()))
                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Returns an ordered list of all requirements that are referenced in the project.
     * 
     * @return The ordered list of all referenced requirements.
     */
    public List<String> getAllReferencedRequirementsOrdered()
    {
        Set<String> allReq = new HashSet<String>();

        for (Feature feature : ProjectManagerExternalFacade.getInstance().getCurrentProject()
                .getFeatures())
        {
            for (UseCase useCase : feature.getUseCases())
            {
                allReq.addAll(useCase.getAllRelatedRequirements());
            }
        }
        String[] orderedRequirements = allReq.toArray(new String[] {});
        Arrays.sort(orderedRequirements);

        return new ArrayList<String>(Arrays.asList(orderedRequirements));
    }

    /**
     * Checks if the <code>usecase</code> has any use case error.
     * 
     * @param usecase The feature whose step ids will be verified.
     * @param feature The feature where <code>usecase</code> occurs.
     * @return <i>true</i> if there is a use case error related to <code>usecase</code> or
     * <i>false</i> otherwise.
     */
    public boolean hasUseCaseError(UseCase usecase, Feature feature)
    {
        boolean result = false;
        for (Error error : this.errorList)
        {
            if (error instanceof UseCaseError)
            {
                UseCase errorUseCase = ((UseCaseError) error).getUseCase();
                Feature errorFeature = ((UseCaseError) error).getFeature();

                if (errorUseCase.equals(usecase) && errorFeature.equals(feature))
                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Updates the index. If <code>open</code> is <code>true</code> then the method checks if
     * the default index exists and erases it. The method removes, adds and change documents from
     * the index.
     * 
     * @param information The refresh information containing the added, changed and removed
     * documents of the current project.
     * @param open Indicates if the method is being invoked from an opening project action. If its
     * <code>true</code> then the method erases the default index.
     * @throws TargetSearchException Thrown when it is not possible to finish successfully an
     * operation in the index or to read or write in the index.
     */
    public void updateIndex(TargetProjectRefreshInformation information, boolean open)
            throws TargetSearchException
    {
        SearchController searchController = SearchController.getInstance();
        /* If a project is being opened the index is erased */
        if (open)
        {
            searchController.eraseIndex();
        }

        /* Removed the deleted documents */
        searchController.removeDocumentsFromIndex(searchController
                .getPhoneDocumentsAsTargetIndexDocuments(information.getRemovedDocuments()));

        /* Index the added documents */
        searchController.indexPhoneDocuments(information.getAddedDocuments());

        /* Removes and index again the changed documents */
        searchController.removeDocumentsFromIndex(searchController
                .getPhoneDocumentsAsTargetIndexDocuments(information.getChangedDocuments()));
        searchController.indexPhoneDocuments(information.getChangedDocuments());
    }

    /**
     * Method used to get all features. It generates merged features by creating a new Feature
     * object for features that have the same.
     * 
     * <pre>
     * <ol>
     * <li>Groups all features with same id. They are represented by their id and a stack with the features.
     * <li>Iterates over the grouped features.
     * <li>Uses the content of the stacks (gets the top over and over) and creates the merged feature.
     * </ol>
     * </pre>
     * 
     * @return All features in the project.
     */
    public Collection<Feature> getAllFeatures()
    {
        HashMap<String, Stack<Feature>> groupedFeatures = this.groupFeatures();
        Collection<Feature> features = new ArrayList<Feature>();

        for (String key : groupedFeatures.keySet())
        {
            /* retrieves the feature stack for the current feature id (key) */
            Stack<Feature> dup = groupedFeatures.get(key);

            /* The merged feature. It can be composed by one or more feature with the same id. */
            Feature merged = null;

            /* The feature in the top of the stack */
            Feature top = null;

            /*
             * the loop is responsible for merging the feature 'merged' with the feature in the top
             * of the stack
             */
            while (!dup.empty())
            {
                top = dup.pop();
                merged = top.mergeFeature(merged);
            }

            features.add(merged);
        }
        return features;
    }

    /**
     * Groups the features that have common id. Puts them in a hash map with the feature id as the
     * key and a stack with all the features with same id. The stack can contain one or more
     * features.
     * 
     * @return A hashmap with the ids of the features and the grouped features.
     */
    private HashMap<String, Stack<Feature>> groupFeatures()
    {
        HashMap<String, Stack<Feature>> duplicated = new LinkedHashMap<String, Stack<Feature>>();
        for (PhoneDocument document : this.getCurrentProject().getPhoneDocuments())
        {
            if (document.isDocumentWellFormed())
            {
                for (Feature feature : document.getFeatures())
                {
                    Stack<Feature> dup = duplicated.get(feature.getId());
                    /* duplication */
                    if (dup != null)
                    {
                        dup.push(feature);
                    }
                    else
                    {
                        Stack<Feature> newone = new Stack<Feature>();
                        newone.push(feature);
                        duplicated.put(feature.getId(), newone);
                    }
                }
            }
        }
        return duplicated;
    }

    /**
     * Verifies if <code>feature1</code> and <code>feature2</code> have common use cases. If
     * they have at least one common use case then the method returns it in a list.
     * 
     * @param feature1 The first feature to be compared.
     * @param feature2 The second feature to be compared.
     * @return A list with the common use cases.
     */
    public List<UseCase> hasCommonUseCase(Feature feature1, Feature feature2)
    {
        List<UseCase> result = new ArrayList<UseCase>();

        if (feature1 != null && feature2 != null)
        {
            for (UseCase usecase : feature1.getUseCases())
            {
                if ((feature2.getUseCase(usecase.getId())) != null)
                {
                    result.add(usecase);
                }
            }
        }
        return result;
    }
}
