/*
 * @(#)TargetProjectRefreshInformation.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022   Feb 28, 2007    LIBll29572   Initial creation.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * dhq348   May 18, 2007    LIBmm25975   Modification according to CR.
 * dhq348   Jun 21, 2007    LIBmm25975   Rework after meeting 3 of inspection LX179934.
 */
package br.ufpe.cin.target.pm.controller;

import java.util.ArrayList;
import java.util.Collection;

import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.pm.errors.Error;


/**
 * This class encapsulates all information related to the project refreshing.
 * 
 * <pre>
 * CLASS:
 * 
 * The class stores the TaRGeT project before the refreshing, 
 * see attribute <code>oldProject</code>, and after the refreshing,
 * see attribute <code>newErrors</code>. It also stores the list of
 * project errors before the refreshing, see attribute 
 * <code>previousErrors</code>, and after the refreshing, see 
 * attribute <code>newErrors</code>. The class stores information 
 * about the refresh information performed (see attribute <code>operation</code>).
 */
public class TargetProjectRefreshInformation
{
    /** The constant for a refreshing performed during a normal project refresh operation */
    public static final int REFRESH = 0;

    /** The constant for a refreshing performed during an open project operation */
    public static final int OPEN = 1;

    /** The constant for a refreshing performed during a document importing operation */
    public static final int IMPORT = 2;

    /** The <code>TargetProjet</code> instance after the refreshing */
    private TargetProject newProject;

    /** The <code>TargetProjet</code> instance before the refreshing */
    private TargetProject oldProject;

    /** The collection of new errors generated after the refreshing */
    private Collection<Error> newErrors;

    /** The collection of errors before the refreshing */
    private Collection<Error> previousErrors;

    /** The operation code that triggered the project refreshing */
    private int operation;

    /**
     * The documents that were added to the new project
     */
    private Collection<UseCaseDocument> addedDocuments;

    /**
     * The documents that were removed and are not in the new project 
     */
    private Collection<UseCaseDocument> removedDocuments;

    /**
     * The documents from the old project that were changed 
     */
    private Collection<UseCaseDocument> changedDocuments;

    /**
     * This constructor mounts a <code>TargetProjectRefreshInformation</code> instance. See the
     * parameters for details.
     * 
     * @param newProject The new TaRGeT project after the refreshing process.
     * @param oldProject The TaRGeT project before the refreshing process.
     * @param newErrors The errors encountered during the refreshing process.
     * @param previousErrors The errors related to the old project.
     * @param operation The operation that triggered the refreshing process. The values here must be
     * <code>REFRESH</code>, <code>OPEN</code> or <code>IMPORT</code>.
     */
    public TargetProjectRefreshInformation(TargetProject newProject, TargetProject oldProject,
            Collection<Error> newErrors, Collection<Error> previousErrors, int operation)
    {
        super();
        this.newProject = newProject;
        this.oldProject = oldProject;
        this.newErrors = newErrors;
        this.previousErrors = previousErrors;
        this.operation = operation;

        this.addedDocuments = new ArrayList<UseCaseDocument>();
        this.changedDocuments = new ArrayList<UseCaseDocument>();
        this.removedDocuments = new ArrayList<UseCaseDocument>();
    }

    /**
     * This constructor mounts a <code>TargetProjectRefreshInformation</code> instance. It also
     * receives the list of the added, removed and changed files.
     * 
     * @param newProject The new TaRGeT project after the refreshing process.
     * @param oldProject The TaRGeT project before the refreshing process.
     * @param newErrors The errors encountered during the refreshing process.
     * @param previousErrors The errors related to the old project.
     * @param operation The operation that triggered the refreshing process. The values here must be
     * <code>REFRESH</code>, <code>OPEN</code> or <code>IMPORT</code>.
     * @param added The list of files that were added to the project.
     * @param removed The list of files that were removed from the project.
     * @param changed The list of files that changed in the project.
     */
    public TargetProjectRefreshInformation(TargetProject newProject, TargetProject oldProject,
            Collection<Error> newErrors, Collection<Error> previousErrors, int operation,
            Collection<String> added, Collection<String> removed, Collection<String> changed)
    {
        this(newProject, oldProject, newErrors, previousErrors, operation);

        this.addedDocuments = this.getUseCaseDocumentsInProject(added, this.newProject);
        this.changedDocuments = this.getUseCaseDocumentsInProject(changed, this.newProject);
        this.removedDocuments = this.getUseCaseDocumentsInProject(removed, this.oldProject);
    }

    /**
     * This constructor mounts a <code>TargetProjectRefreshInformation</code> instance. It also
     * receives the list of the added files.
     * 
     * @param newProject The new TaRGeT project after the refreshing process.
     * @param oldProject The TaRGeT project before the refreshing process.
     * @param newErrors The errors encountered during the refreshing process.
     * @param previousErrors The errors related to the old project.
     * @param operation The operation that triggered the refreshing process. The values here must be
     * <code>REFRESH</code>, <code>OPEN</code> or <code>IMPORT</code>.
     * @param added The list of files that were added to the project.
     */
    public TargetProjectRefreshInformation(TargetProject newProject, TargetProject oldProject,
            Collection<Error> newErrors, Collection<Error> previousErrors, int operation,
            Collection<String> added)
    {
        this(newProject, oldProject, newErrors, previousErrors, operation);

        this.addedDocuments = this.getUseCaseDocumentsInProject(added, this.newProject);
    }

    /**
     * Verifies if a new error with the informed type <code>errorType</code> was generated. The
     * verification is done over the <code>newErrors</code> list.
     * 
     * @param errorType The type of the error to be verified.
     * @return <code>True</code> in case there is an error of the specified type.
     * <code>False</code> otherwise.
     */
    public boolean hasNewError(int errorType)
    {
        boolean result = false;
        for (Error error : this.newErrors)
        {
            if (error.getType() == errorType)
            {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Return the new errors according to their type.
     * 
     * @param errorType The type of the error to be returned.
     * @return The list of new errors of the specified type.
     */
    public Collection<Error> getNewErrors(int errorType)
    {
        Collection<Error> result = new ArrayList<Error>();

        for (Error error : this.newErrors)
        {
            if (error.getType() == errorType)
            {
                result.add(error);
            }
        }
        return result;
    }

    /**
     * Get method for <code>newErrors</code> attribute.
     * 
     * @return Returns the <code>newErrors</code> value.
     */
    public Collection<Error> getNewErrors()
    {
        return new ArrayList<Error>(newErrors);
    }

    /**
     * Get method for <code>newProject</code> attribute.
     * 
     * @return Returns the <code>newProject</code> value.
     */
    public TargetProject getNewProject()
    {
        return newProject;
    }

    /**
     * Get method for <code>oldProject</code> attribute.
     * 
     * @return Returns the <code>oldProject</code> value.
     */
    public TargetProject getOldProject()
    {
        return oldProject;
    }

    /**
     * Get method for <code>operation</code> attribute.
     * 
     * @return Returns the <code>operation</code> value.
     */
    public int getOperation()
    {
        return operation;
    }

    /**
     * Get method for <code>previousErrors</code> attribute.
     * 
     * @return Returns the <code>previousErrors</code> value.
     */
    public Collection<Error> getPreviousErrors()
    {
        return new ArrayList<Error>(previousErrors);
    }

    /**
     * This method returns the list of new different errors of the informed type. The
     * <code>newErrors</code> list is compared with the <code>previousErrors</code> list.
     * 
     * @param errorType The type of the new different errors.
     * @return The list of new different errors.
     */
    public Collection<Error> getNewDifferentErrors(int errorType)
    {
        Collection<Error> result = new ArrayList<Error>();

        for (Error error : this.newErrors)
        {
            if (error.getType() == errorType)
            {
                if (!this.previousErrors.contains(error))
                {
                    result.add(error);
                }
            }
        }
        return result;
    }

    /**
     * Returns the documents that were added in the project.
     * 
     * @return The added documents.
     */
    public Collection<UseCaseDocument> getAddedDocuments()
    {
        return addedDocuments;
    }

    /**
     * Returns the documents that were changed.
     * 
     * @return The changed documents.
     */
    public Collection<UseCaseDocument> getChangedDocuments()
    {
        return changedDocuments;
    }

    /**
     * Returns the removed documents from the project.
     * 
     * @return The removed documents.
     */
    public Collection<UseCaseDocument> getRemovedDocuments()
    {
        return removedDocuments;
    }

    /**
     * Returns a collection with the use case documents referenced in the <code>project</code>.
     * 
     * @param references The names of the files that are being referenced.
     * @param project The project in which the referenced files will be searched.
     * @return A collection with the referenced useCaseDocuments.
     */
    private Collection<UseCaseDocument> getUseCaseDocumentsInProject(Collection<String> references,
            TargetProject project)
    {
        Collection<UseCaseDocument> result = new ArrayList<UseCaseDocument>();
        for (String reference : references)
        {
            UseCaseDocument document = project.getUseCaseDocumentByPath(reference);
            if (document != null)
            {
                result.add(document);
            }
        }
        return result;
    }

}
