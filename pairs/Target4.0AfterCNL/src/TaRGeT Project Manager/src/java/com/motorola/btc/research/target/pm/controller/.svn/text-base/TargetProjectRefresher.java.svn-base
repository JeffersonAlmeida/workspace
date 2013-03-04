/*
 * @(#)TargetProjectRefresher.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Feb 28, 2007   LIBll29572   Initial creation.
 * wdt022    Mar 08, 2007   LIBll29572   Modification according to CR LIBll29572.
 * dhq348    Mar 14, 2007   LIBll66163   Modification according to CR LIBll66163.
 * dhq348    Apr 26, 2007   LIBmm09879   Modifications according to CR.
 * wdt022    May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing.
 * dhq348    May 18, 2007   LIBmm25975   Modification according to CR.
 * dhq348    Jun 5, 2007    LIBmm47221   Updated verifyDuplicatedFeatureId to support the merge of features.
 * dhq348    Jun 21, 2007   LIBmm25975   Rework after meeting 3 of inspection LX179934.
 * dhq348    Jul 03, 2007   LIBmm63120   Modification according to CR.
 * dhq348    Jul 04, 2007   LIBmm63120   Rework after inspection LX188519.
 * dhq348    Jul 11, 2007   LIBmm47221   Rework after inspection LX185000.
 * dhq348    Oct 18, 2007   LIBnn34008   Added method verifyDuplicatedInterruptionId().
 * dhq348    Jan 18, 2008   LIBnn34008   Rework after inspection LX229628.
 * dhq348    Feb 21, 2008   LIBoo89937   Added interruptions flow steps verification.
 * dhq348    Feb 22, 2008   LIBoo89937   Rework after inspection LX246152.
 * dwvm83	 Nov 25, 2008				 Changes in method getEmptyFieldName() to check if the To/From steps are empty.
 * wmr068    Aug 07, 2008   LIBqq64190   Method reloadProject now calls ProjectManagerController.getPhoneDocumentFiles() instead of FileUtil.getPhoneDocumentFiles.
 */
package com.motorola.btc.research.target.pm.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.Flow;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.errors.DifferentMergedFeatureNameError;
import com.motorola.btc.research.target.pm.errors.DuplicatedFeatureIdInsideDocumentError;
import com.motorola.btc.research.target.pm.errors.DuplicatedStepIdError;
import com.motorola.btc.research.target.pm.errors.DuplicatedUseCaseIdError;
import com.motorola.btc.research.target.pm.errors.DuplicatedUseCaseIdErrorBetweenDocuments;
import com.motorola.btc.research.target.pm.errors.EmptyUseCaseFieldError;
import com.motorola.btc.research.target.pm.errors.Error;
import com.motorola.btc.research.target.pm.errors.InvalidStepIdReferenceError;
import com.motorola.btc.research.target.pm.errors.UnableToLoadDocumentError;
import com.motorola.btc.research.target.pm.exceptions.TargetProjectLoadingException;

/**
 * This class encapsulates all the implementation responsible for analyzing the document files,
 * reload then when necessary and generate a list of errors (if they exist).
 * 
 * <pre>
 * CLASS:
 * 
 * In order to perform the refreshing, the class stores the current TaRGeT 
 * project and the current list of errors. The only public methods are 
 * <code>refreshProject</code>, <code>loadOpenedProject</code> and
 * <code>loadDocumentsImportedToProject</code>, that are called by the 
 * controller in order to, respectively, refresh the project, load the 
 * documents of an opened project and load imported documents. 
 * These methods return an instance of <code>TargetProjectRefreshInformation</code>
 * class, which contains information about the project refreshing.
 */
public class TargetProjectRefresher
{
    /** The current TaRGeT project */
    private TargetProject currentProject;

    /** The current project errors */
    private Collection<Error> currentErrors;

    /**
     * Builds the <code>TargetProjectRefresher</code> instance. It receives as parameter the
     * current TaRGeT project and the current list of errors.
     * 
     * @param targetProject The TaRGeT project.
     * @param currentErrors The list of errors.
     */
    public TargetProjectRefresher(TargetProject targetProject, Collection<Error> currentErrors)
    {
        this.currentErrors = currentErrors;
        this.currentProject = targetProject;
    }

    /**
     * Method used to refresh the project when the focus goes back to the project area.
     * 
     * @param throwExceptionOnFatalError If <code>true</code>, an exception is thrown when the
     * first fatal error is encountered.
     * @return The <code>TargetProjectRefreshInformation</code> instance.
     * @throws TargetException In case of a fatal error (if the
     * <code>throwExceptionOnFatalError</code> parameter is <code>true</code>), or any other
     * error.
     */
    public TargetProjectRefreshInformation refreshProject(boolean throwExceptionOnFatalError)
            throws TargetException
    {
        return this.reloadProject(throwExceptionOnFatalError,
                TargetProjectRefreshInformation.REFRESH);
    }

    /**
     * Method used to load documents during a project opening.
     * 
     * @param throwExceptionOnFatalError If <code>true</code>, an exception is thrown when the
     * first fatal error is encountered.
     * @return The <code>TargetProjectRefreshInformation</code> instance.
     * @throws TargetException In case of a fatal error (if the
     * <code>throwExceptionOnFatalError</code> parameter is <code>true</code>), or any other
     * error.
     */
    public TargetProjectRefreshInformation loadOpenedProject(boolean throwExceptionOnFatalError)
            throws TargetException
    {
        return this.reloadProject(throwExceptionOnFatalError, TargetProjectRefreshInformation.OPEN);
    }

    /**
     * Method used to load documents that are being imported.
     * 
     * @param documentsToImport The collection of documents to import.
     * @param throwExceptionOnFatalError If <code>true</code>, an exception is thrown when the
     * first fatal error is encountered.
     * @return The <code>TargetProjectRefreshInformation</code> instance.
     * @throws TargetException In case of a fatal error (if the
     * <code>throwExceptionOnFatalError</code> parameter is <code>true</code>), or any other
     * error.
     */
    public TargetProjectRefreshInformation loadDocumentsImportedToProject(
            Collection<File> documentsToImport, boolean throwExceptionOnFatalError)
            throws TargetException
    {
        Collection<String> fileNames = FileUtil.getAbsolutePaths(documentsToImport);
        /* Loading the word documents */
        Collection<PhoneDocument> loadedDocuments = new ArrayList<PhoneDocument>();
        Collection<Error> errors = new ArrayList<Error>();

        this.loadDocuments(loadedDocuments, fileNames, throwExceptionOnFatalError);

        /* Verifies all kind of errors in the project */
        this.verifyErrors(loadedDocuments, this.currentProject.getPhoneDocuments(),
                throwExceptionOnFatalError);

        List<PhoneDocument> allDocuments = new ArrayList<PhoneDocument>();
        allDocuments.addAll(this.currentProject.getPhoneDocuments());
        allDocuments.addAll(loadedDocuments);

        /* Verify errors if all the documents are imported */
        errors.addAll(this.verifyErrors(allDocuments, new ArrayList<PhoneDocument>(), false));

        /*
         * Since FATAL_ERROR documents have their features set to null, the verifyErrors() method
         * cannot verify the existence of errors in these documents. So we reuse the existing fatal
         * errors of the current project.
         */
        for (Error error : currentErrors)
        {
            if (error.getType() == Error.FATAL_ERROR)
            {
                errors.add(error);
            }
        }

        /* Creates the new TargetProject with all processed documents */
        TargetProject newProject = new TargetProject(this.currentProject.getName(),
                this.currentProject.getRootDir(), allDocuments);

        TargetProjectRefreshInformation projectRefreshInfo = new TargetProjectRefreshInformation(
                newProject, this.currentProject, errors, this.currentErrors,
                TargetProjectRefreshInformation.IMPORT, fileNames);

        return projectRefreshInfo;
    }

    /**
     * Generic method to reload the TaRGeT project. This method was design for loading opened
     * project documents and for refreshing a project. Is must not be used for importing documents.
     * 
     * @param throwExceptionOnFatalError If <code>true</code>, an exception is thrown when the
     * first fatal error is encountered.
     * @param operation The operation that is responsible for the refreshing (refresh or open
     * project)
     * @return The <code>TargetProjectRefreshInformation</code> instance containing information
     * about the refreshing process..
     * @throws TargetException In case of a fatal error (if the
     * <code>throwExceptionOnFatalError</code> parameter is <code>true</code>), or any other
     * error.
     */
    private TargetProjectRefreshInformation reloadProject(boolean throwExceptionOnFatalError,
            int operation) throws TargetException
    {
        Collection<PhoneDocument> currentDocs = this.currentProject.getPhoneDocuments();
        Collection<File> folderDocs = null;

        try
        {
            folderDocs = ProjectManagerController.getInstance().getPhoneDocumentFiles();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new TargetException("Error during the project loading operation. \n"
                    + e.getMessage());
        }

        /* List of documents that will be reloaded */
        Collection<String> toBeReloaded = new LinkedHashSet<String>();
        /* List of documents that were added */
        Collection<String> added = this.getAddedDocuments(currentDocs, folderDocs);
        /* List of documents that were modified */
        Collection<String> changed = this.getChangedDocuments(currentDocs, folderDocs);
        /* List of documents that were removed */
        Collection<String> removed = this.getRemovedDocuments(folderDocs);

        toBeReloaded.addAll(changed);

        /*
         * It is not necessary to always reload the fatal error documents. However, in order to not
         * reload all documents, it is necessary to change the PhoneDocument implementation, adding
         * the fatal error instance in the PhoneDocument, and make a selection based on the errors.
         */
        toBeReloaded.addAll(this.getFatalErrorDocuments(currentDocs));
        toBeReloaded.addAll(added);

        /* Loading the word documents */
        Collection<PhoneDocument> loadedDocuments = new ArrayList<PhoneDocument>();
        Collection<Error> errors = this.loadDocuments(loadedDocuments, toBeReloaded,
                throwExceptionOnFatalError);

        /* retrieves the documents that were not reloaded */
        Collection<PhoneDocument> notLoadedDocuments = new ArrayList<PhoneDocument>();
        for (PhoneDocument curDoc : currentDocs)
        {
            boolean loaded = false;
            for (PhoneDocument loadedDoc : loadedDocuments)
            {
                if (curDoc.getDocFilePath().equals(loadedDoc.getDocFilePath()))
                {
                    loaded = true;
                    break;
                }
            }
            /*
             * If the document was not reloaded and the document still exists in the file system
             * (see folderDocs)
             */
            if (!loaded && folderDocs.contains(new File(curDoc.getDocFilePath())))
            {
                notLoadedDocuments.add(curDoc);
            }
        }

        List<PhoneDocument> allDocuments = new ArrayList<PhoneDocument>();
        allDocuments.addAll(notLoadedDocuments);
        allDocuments.addAll(loadedDocuments);

        /*
         * Verifies feature/use case/step ID and empty use case fields errors in the project. This
         * method does not verify errors during the document loading, like XML errors.
         */
        errors.addAll(this.verifyErrors(allDocuments, new ArrayList<PhoneDocument>(),
                throwExceptionOnFatalError));

        /* Creates the new TargetProject with all processed documents */
        TargetProject newProject = new TargetProject(this.currentProject.getName(),
                this.currentProject.getRootDir(), allDocuments);

        TargetProjectRefreshInformation projectRefreshInfo = new TargetProjectRefreshInformation(
                newProject, this.currentProject, errors, this.currentErrors, operation, added,
                removed, changed);

        return projectRefreshInfo;
    }

    /**
     * Method for loading the documents. It receives the list of documents to be loaded, see
     * attribute <code>docsToBeLoaded</code>. It returns the collection of encountered errors and
     * fills the parameter <code>loadedDocuments</code> with the loaded documents. It also throws
     * an exception if the attribute <code>throwExceptionOnError</code> is set to
     * <code>true</code> and a fatal error is encountered.
     * 
     * @param loadedDocuments It is used as a return parameter. The instance is filled with the
     * loaded documents.
     * @param docsToBeLoaded The list of absolute path of documents to be loaded.
     * @param throwExceptionOnError If <code>true</code>, an exception is launched if a fatal
     * error is encountered.
     * @return The list of found errors.
     * @throws TargetException In case of a fatal error (if the
     * <code>throwExceptionOnFatalError</code> parameter is <code>true</code>), or any other
     * error.
     */
    private Collection<Error> loadDocuments(Collection<PhoneDocument> loadedDocuments,
            Collection<String> docsToBeLoaded, boolean throwExceptionOnError)
            throws TargetException
    {

        Collection<Error> result = new ArrayList<Error>();

        ProjectManagerController controller = ProjectManagerController.getInstance();

        Collection<PhoneDocument> justLoadedDocuments = controller.loadPhoneDocumentFiles(FileUtil
                .loadFiles(new ArrayList<String>(docsToBeLoaded).toArray(new String[] {})),
                throwExceptionOnError);

        /* Creating the error instances and adding the not loaded documents */
        for (PhoneDocument pDoc : justLoadedDocuments)
        {
            if (!pDoc.isDocumentWellFormed())
            {
                Error newError = new UnableToLoadDocumentError(new File(pDoc.getDocFilePath()));
                result.add(newError);
            }
        }

        /* updating the loadedDocuments instance with the loaded documents */
        loadedDocuments.clear();
        loadedDocuments.addAll(justLoadedDocuments);

        return result;
    }

    /**
     * This method is used to search for errors in the documents. The documents in
     * <code>newDocuments</code> collection are verified against the documents in
     * <code>currentDocuments</code> collection. An exception is thrown if the attribute
     * <code>throwExceptionOnError</code> is set to <code>true</code> and a fatal error is
     * encountered. The documents collection <code>newDocuments</code> is used as return
     * parameter, since its components are updated inside the method.
     * 
     * @param newDocuments The list of documents that will be verified.
     * @param currentDocuments The documents in <code>newDocuments</code> that will be verified.
     * @param throwExceptionOnFatalError If <code>true</code>, an exception is launched if a
     * fatal error is encountered.
     * @return The list of found errors.
     * @throws TargetProjectLoadingException It is thrown if the attribute
     * <code>throwExceptionOnError</code> is set to <code>true</code> and a fatal error is
     * encountered.
     */
    private Collection<Error> verifyErrors(Collection<PhoneDocument> newDocuments,
            Collection<PhoneDocument> currentDocuments, boolean throwExceptionOnFatalError)
            throws TargetProjectLoadingException
    {

        Collection<Error> result = new ArrayList<Error>();

        /* A new Collection is created to not change the currentDocuments */
        currentDocuments = new ArrayList<PhoneDocument>(currentDocuments);
        /* A clone collection is created. This collection will not be changed */
        Collection<PhoneDocument> processedDocuments = new ArrayList<PhoneDocument>();

        /* the for run over the clone collection */
        for (PhoneDocument pDoc : newDocuments)
        {
            if (pDoc.isDocumentWellFormed())
            {
                /* Verifies duplicated feature ID error inside the same document. */
                List<Error> errors = this.verifyDuplicatedFeatureId(pDoc);

                /* Verifies duplicated use case ID error inside the same document */
                errors.addAll(this.verifyDuplicatedUseCaseIds(pDoc));

                /* Verifies duplicated use case ID error between different documents */
                errors.addAll(this.verifyDuplicatedUseCaseIds(currentDocuments, pDoc));

                /* If there is any fatal error and throwExceptionOnFatalError is true */
                if (!errors.isEmpty() && throwExceptionOnFatalError)
                {
                    throw new TargetProjectLoadingException(errors);
                }

                /* Verifies features with the same id but different names */
                errors.addAll(this.verifyDifferentMergedFeatureName(currentDocuments, pDoc));

                if (!errors.isEmpty() && this.hasFatalErrors(errors))
                {
                    /* in case of any fatal error, the document must be rejected */
                    pDoc = new PhoneDocument(pDoc.getDocFilePath(), pDoc
                            .getLastDocumentModification());
                }

                result.addAll(errors);
            }
            processedDocuments.add(pDoc);
            currentDocuments.add(pDoc);
        }

        /* Updating the newDocuments parameter with the new processed documents */
        newDocuments.clear();
        newDocuments.addAll(processedDocuments);

        /* Verify step ID errors */
        result.addAll(this.verifyStepIdErrors(newDocuments));
        /* Verify empty use case field errors */
        result.addAll(this.getEmptyFieldErrors(newDocuments));

        return result;
    }

    /**
     * Verifies if a list of flows has an empty step field. It returns the name of the empty field,
     * or <code>null</code> if there is no empty field.
     * 
     * @param flows The flows to be verified.
     * @return The name of the empty field, or <code>null</code> if there is no empty field.
     */
    private String getEmptyFieldName(UseCase usecase)
    {
        String result = null;
        
        

        for (Flow flow : usecase.getFlows())
        {
        	//Checks if the To/From steps are empty
        	if (flow.getFromSteps().size() < 1) 
        	{
        		result = EmptyUseCaseFieldError.FROM_STEP;
        		break;
        	}
        	
        	if (flow.getToSteps().size() < 1) 
        	{
        		result = EmptyUseCaseFieldError.TO_STEP;
        		break;
        	}
        	
        	if (usecase.getId().length() < 1) 
        	{
        		result = EmptyUseCaseFieldError.USECASE_ID;
        		break;
        	}
        	
            for (FlowStep flowStep : flow.getSteps())
            {
                if (flowStep.getId().getStepId().equals(""))
                {
                    result = EmptyUseCaseFieldError.STEP_ID;
                    break;
                }
                else if (flowStep.getUserAction().equals(""))
                {
                    result = EmptyUseCaseFieldError.STEP_ACTION;
                    break;
                }
                else if (flowStep.getSystemResponse().equals(""))
                {
                    result = EmptyUseCaseFieldError.STEP_RESPONSE;
                    break;
                }
            }
            if (result != null)
            {
                break;
            }
        }
        return result;
    }

    /**
     * Verifies if a use case has an empty step field. It returns the flow step or <code>null</code>
     * if the use case has no empty field.
     * 
     * @param useCase The use case to be verified.
     * @return The flow steps, or <code>null</code> if there is no empty field.
     */
    private FlowStep getFlowStepWithEmptyFieldName(UseCase useCase)
    {
        FlowStep result = null;

        for (Flow flow : useCase.getFlows())
        {
            for (FlowStep flowStep : flow.getSteps())
            {
                if (flowStep.getId().getStepId().equals("") || flowStep.getUserAction().equals("")
                        || flowStep.getSystemResponse().equals(""))
                {
                    result = flowStep;
                    break;
                }
            }
            if (result != null)
            {
                break;
            }
        }
        return result;
    }

    /**
     * Verifies if a use case has an empty step field. It returns the step index.
     * 
     * @param useCase The use case to be verified.
     * @return The step index.
     */
    private int getStepIndexWithEmptyFieldName(UseCase useCase)
    {
        int result = 0;
        boolean found = false;

        for (Flow flow : useCase.getFlows())
        {
            result = 0;
            for (FlowStep flowStep : flow.getSteps())
            {
                if (flowStep.getId().getStepId().equals("") || flowStep.getUserAction().equals("")
                        || flowStep.getSystemResponse().equals(""))
                {
                    found = true;
                    break;
                }
                result++;
            }
            if (found)
            {
                break;
            }
        }
        return result;
    }

    /**
     * Verifies if a use case has an empty step field. It returns the flow index.
     * 
     * @param useCase The use case to be verified.
     * @return The flow index.
     */
    private int getFlowIndexWithEmptyFieldName(UseCase useCase)
    {
        int result = 0;
        boolean found = false;

        for (Flow flow : useCase.getFlows())
        {
            for (FlowStep flowStep : flow.getSteps())
            {
                if (flowStep.getId().getStepId().equals("") || flowStep.getUserAction().equals("")
                        || flowStep.getSystemResponse().equals(""))
                {
                    found = true;
                    break;
                }
            }
            if (found)
            {
                break;
            }
            result++;
        }
        return result;
    }

    /**
     * Search for empty use case fields (see <code>EmptyUseCaseFieldError</code> 
     * in the informed documents.
     * 
     * @param documents The documents to be verified.
     * @return The list of encountered error.
     */
    private Collection<Error> getEmptyFieldErrors(Collection<PhoneDocument> documents)
    {
        Collection<Error> result = new ArrayList<Error>();

        for (PhoneDocument pDoc : documents)
        {
            if (pDoc.isDocumentWellFormed())
            {
                for (Feature feature : pDoc.getFeatures())
                {
                    for (UseCase useCase : feature.getUseCases())
                    {
                        String emptyFieldName = this.getEmptyFieldName(useCase);
                        if (emptyFieldName != null)
                        {
                            FlowStep flowStep = this.getFlowStepWithEmptyFieldName(useCase);
                            int stepIndex = 0;
                            int flowIndex = 0;
                            // the flowStep is null when the empty field is a To/From step field.
                            if (flowStep != null) {
                            	stepIndex = this.getStepIndexWithEmptyFieldName(useCase);
                            	flowIndex = this.getFlowIndexWithEmptyFieldName(useCase);
                            }
                            result.add(new EmptyUseCaseFieldError(feature, useCase, flowStep,
                                    flowIndex, stepIndex, pDoc.getDocFilePath(), emptyFieldName));
                        } 
                    }
                }

            }
        }
        return result;
    }
    
     /**
     * Verifies the documents in the parameter <code>phoneDocuments</code>, and returns a
     * collection containing the absolute paths of the documents that are not well-formed.
     * 
     * @param documents The documents to be verified.
     * @return The absolute paths of the documents that are not well-formed.
     */
    private Collection<String> getFatalErrorDocuments(Collection<PhoneDocument> phoneDocuments)
    {
        List<String> result = new ArrayList<String>();

        for (PhoneDocument pDoc : phoneDocuments)
        {
            /* The fatal error document should be reloaded in case of it was not removed */
            if ((new File(pDoc.getDocFilePath())).exists() && !pDoc.isDocumentWellFormed())
            {
                result.add(pDoc.getDocFilePath());
            }
        }

        return result;
    }

    /**
     * Search for the documents that were changed. The verification is done according to the last
     * modification date.
     * 
     * @param phoneDocuments The documents to be verified.
     * @param folderFiles The list of documents that were recently read from the documents folder.
     * @return The collection of documents that were changed.
     */
    private Collection<String> getChangedDocuments(Collection<PhoneDocument> phoneDocuments,
            Collection<File> folderFiles)
    {
        List<String> result = new ArrayList<String>();

        for (PhoneDocument pDoc : phoneDocuments)
        {
            for (File file : folderFiles)
            {
                if (pDoc.getDocFilePath().equals(file.getAbsolutePath())
                        && pDoc.getLastDocumentModification() != file.lastModified())
                {
                    result.add(file.getAbsolutePath());
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Search for the removed documents. Compares the files in the current project with the files in
     * <code>foldeFiles</code>.
     * 
     * @param folderFiles The files to be verified against the files in memory.
     * @return The removed documents.
     */
    private Collection<String> getRemovedDocuments(Collection<File> folderFiles)
    {
        List<String> result = new ArrayList<String>();
        Set<String> filesInDisk = new HashSet<String>(FileUtil.getAbsolutePaths(folderFiles));

        for (String filePath : this.currentProject.getPhoneDocumentFilePaths())
        {
            if (!filesInDisk.contains(filePath))
            {
                result.add(filePath);
            }
        }

        return result;
    }

    /**
     * Search for the documents that were added in the documents folder.
     * 
     * @param phoneDocuments The documents to be verified.
     * @param folderFiles The list of documents that were recently read from the documents folder.
     * @return The collection of documents that were added in the documents folder.
     */
    private Collection<String> getAddedDocuments(Collection<PhoneDocument> phoneDocuments,
            Collection<File> folderFiles)
    {
        List<String> result = new ArrayList<String>();

        for (File file : folderFiles)
        {
            result.add(file.getAbsolutePath());
        }

        for (PhoneDocument pDoc : phoneDocuments)
        {
            for (int i = 0; i < result.size(); i++)
            {
                String fileName = result.get(i);
                if (pDoc.getDocFilePath().equals(fileName))
                {
                    result.remove(i);
                }
            }
        }

        return result;
    }

    /**
     * This method verifies if there are step id duplicity errors and/or invalid step id references
     * in the given documents.
     * 
     * @param documentList The documents to be verified.
     * @return The found step id errors.
     */
    private List<Error> verifyStepIdErrors(Collection<PhoneDocument> documentList)
    {

        List<Error> result = new ArrayList<Error>();
        Collection<StepId> ucAllActualIds = new HashSet<StepId>();

        // Fill collection with all actual ids
        for (PhoneDocument pDoc : documentList)
        {
            if (pDoc.isDocumentWellFormed())
            {
                for (Feature feature : pDoc.getFeatures())
                {
                    ucAllActualIds.addAll(feature.getActualStepIds());
                }
            }
        }
        /* The steps START and END always exist */
        ucAllActualIds.add(StepId.START);
        ucAllActualIds.add(StepId.END);

        for (PhoneDocument pDoc : documentList)
        {
            if (pDoc.isDocumentWellFormed())
            {
                for (Feature feature : pDoc.getFeatures())
                {
                    for (UseCase uc : feature.getUseCases())
                    {
                        // Verify if all steps that are referred in this use case really exist.
                        for (StepId stepId : uc.getReferredStepIds())
                        {
                            if (!ucAllActualIds.contains(stepId))
                            {
                                Error error = new InvalidStepIdReferenceError(feature, uc, stepId,
                                        pDoc.getDocFilePath());

                                result.add(error);
                            }
                        }

                        HashMap<StepId, Integer> map = new HashMap<StepId, Integer>();
                        Collection<StepId> actualIds = uc.getActualStepIds();
                        // Verify duplicity errors
                        for (StepId stepId : actualIds)
                        {
                            Integer num = map.get(stepId);
                            if (num != null)
                            {
                                map.put(stepId, new Integer(num.intValue() + 1));

                                Error error = new DuplicatedStepIdError(feature, uc, stepId, pDoc
                                        .getDocFilePath());

                                result.add(error);
                            }
                            else
                            {
                                map.put(stepId, new Integer(1));
                            }
                        }
                    }
                }


            }
        }

        return result;
    }

    /**
     * Verifies if the document contains a feature with duplicated use case ids.
     * 
     * @param phoneDocument The document to be verified
     * @return The list that contains the found duplicity errors
     */
    private List<Error> verifyDuplicatedUseCaseIds(PhoneDocument phoneDocument)
    {
        List<Error> result = new ArrayList<Error>();

        for (Feature feature : phoneDocument.getFeatures())
        {

            Set<UseCase> duplicatedIdUCs = this.getUseCaseWithDuplicatedIds(feature);

            for (UseCase uc : duplicatedIdUCs)
            {
                result
                        .add(new DuplicatedUseCaseIdError(feature, uc, phoneDocument
                                .getDocFilePath()));
            }
        }

        return result;
    }

    /**
     * Verifies if the document contains a feature with duplicated use case ids. It also verifies
     * the duplicity against the already loaded documents.
     * 
     * @param documentList The list of documents which phoneDocument will be verified against.
     * @param phoneDocument The document to be verified.
     * @return The list that contains the found duplicity errors
     */
    private List<Error> verifyDuplicatedUseCaseIds(Collection<PhoneDocument> documentList,
            PhoneDocument phoneDocument)
    {
        List<Error> result = new ArrayList<Error>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Feature feature : phoneDocument.getFeatures())
        {
            Integer num = map.get(feature.getId());
            map.put(feature.getId(), (num != null) ? (num.intValue() + 1) : 1);
        }

        for (PhoneDocument pd : documentList)
        {
            if (pd.isDocumentWellFormed())
            {
                List<Feature> pdFeatures = pd.getFeatures();
                for (Feature feature : pdFeatures)
                {
                    // the current feature appears more than once
                    if (map.get(feature.getId()) != null)
                    {
                        // the duplicated feature appears
                        Feature duplicatedFeature = phoneDocument.getFeature(feature.getId());
                        List<UseCase> usecases = null;
                        /* verifies use case duplication */
                        if (!(usecases = ProjectManagerController.getInstance().hasCommonUseCase(
                                feature, duplicatedFeature)).isEmpty())
                        {
                            /* duplicated use case!!! */
                            result.add(new DuplicatedUseCaseIdErrorBetweenDocuments(feature,
                                    usecases.iterator().next(), pd.getDocFilePath(), phoneDocument
                                            .getDocFilePath()));
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * This method verifies if the the document itself contains duplicated feature id. It is only
     * necessary the feature duplication in the same document because feature duplication between
     * different documents now creates a duplicated use case id between different documents error.
     * 
     * @param phoneDocument The document to be verified the feature id duplicity
     * @return A list that contains the found duplicity errors
     */
    private List<Error> verifyDuplicatedFeatureId(PhoneDocument phoneDocument)
    {
        List<Error> result = new ArrayList<Error>();

        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (Feature feature : phoneDocument.getFeatures())
        {
            Integer num = map.get(feature.getId());
            if (num != null)
            {
                map.put(feature.getId(), new Integer(num.intValue() + 1));

                result.add(new DuplicatedFeatureIdInsideDocumentError(feature, phoneDocument
                        .getDocFilePath()));
            }
            else
            {
                map.put(feature.getId(), new Integer(1));
            }
        }

        return result;
    }

    /**
     * Verifies if the features that have the same ID (forming a merged feature) have the same name.
     * 
     * @param documentList The already loaded documents
     * @param phoneDocument The document to be verified the feature name difference.
     * @return A list of errors.
     */
    private List<Error> verifyDifferentMergedFeatureName(Collection<PhoneDocument> documentList,
            PhoneDocument phoneDocument)
    {
        List<Error> result = new ArrayList<Error>();
        documentList = new ArrayList<PhoneDocument>(documentList);

        List<Feature> phoneDocumentFeatures = phoneDocument.getFeatures();

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Feature feature : phoneDocumentFeatures)
        {
            Integer num = map.get(feature.getId());
            map.put(feature.getId(), (num != null) ? (num.intValue() + 1) : 1);
        }

        for (PhoneDocument pd : documentList)
        {
            if (pd.isDocumentWellFormed())
            {
                List<Feature> pdFeatures = pd.getFeatures();
                for (Feature feature : pdFeatures)
                {
                    // the current feature appears more than once
                    if (map.get(feature.getId()) != null)
                    {
                        Feature duplicatedFeature = phoneDocument.getFeature(feature.getId());
                        /* same id but different names */
                        if (feature != null && duplicatedFeature != null
                                && !feature.getName().equals(duplicatedFeature.getName()))
                        {
                            /* duplicated feature name!!! */
                            result.add(new DifferentMergedFeatureNameError(duplicatedFeature,
                                    phoneDocument.getDocFilePath()));
                            phoneDocumentFeatures.remove(duplicatedFeature);
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Verifies if <code>errors</code> contains at least one FATAL_ERROR.
     * 
     * @param errors The errors to be checked.
     * @return <code>true</code> if <code>errors</code> has at least one FATAL_ERROR or
     * <code>false</code> otherwise.
     */
    private boolean hasFatalErrors(List<Error> errors)
    {
        boolean result = false;
        for (Error error : errors)
        {
            if (error.getType() == Error.FATAL_ERROR)
            {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Returns all use cases with repeated ids.
     * 
     * @return A set containing all use cases with repeated ids.
     */
    public Set<UseCase> getUseCaseWithDuplicatedIds(Feature feature)
    {
        Set<UseCase> result = new HashSet<UseCase>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (UseCase usecase : feature.getUseCases())
        {
            Integer num = map.get(usecase.getId());
            if (num != null)
            {
                map.put(usecase.getId(), new Integer(num.intValue() + 1));
                result.add(usecase);
            }
            else
            {
                map.put(usecase.getId(), new Integer(1));
            }
        }

        return result;
    }
}
