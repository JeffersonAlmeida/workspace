/*
 * @(#)UseCaseDocument.java
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
 * wdt022   Nov 25, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 02, 2006    LIBkk11577   Added id checking.
 * wdt022   Jan 10, 2007    LIBkk11577   Java doc modificationss.
 * wdt022   Jan 12, 2007    LIBkk11577   Modifications due to inspection LX128956.
 * dhq348   Apr 26, 2007    LIBmm09879   Modifications according to CR. 
 * dhq348   Apr 28, 2007    LIBmm09879   Rework of inspection LX168613.
 * dhq348   Jul 11, 2007    LIBmm47221   Rework after inspection LX185000.
 * dhq348   Nov 08, 2007    LIBnn34008   Modifications according to CR.
 * dhq348   Jan 16, 2008    LIBnn34008   Rework after inspection LX229627.
 * jrm687   Dec 18, 2008    LIBnn34008	 Modifications to add document revision history.
 */
package br.ufpe.cin.target.common.ucdoc;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * CLASS:
 * -) Represents an entire use case document. It contains a list of Features.
 *
 * RESPONSIBILITIES:
 * -) Encapsulates 
 *      -) a list of features described in a single use case document
 * </pre>
 */
public class UseCaseDocument
{
    /** The document file path */
    private String docFilePath;

    /** The time of last modification of the use case document */
    private long lastDocumentModification;

    /** The list of features */
    private List<Feature> features;
    
    /**
     * Constructor for well formed documents. It is necessary to inform the document file path, the
     * time of the last document modification and the list of features.
     * 
     * @param docFilePath Represents the use case document file path
     * @param lastDocumentModification Stores the time of last modification
     * @param features A list that contains all features described in the document
     */
    public UseCaseDocument(String docFilePath, long lastModification, List<Feature> features)
    {
        this.docFilePath = docFilePath;
        this.lastDocumentModification = lastModification;
        this.features = features;
    }

    /**
     * Constructor for documents that are not well formed. The feature's list is and the revision history 
     * are set to <code>null</code>.
     * 
     * @param docFilePath Represents the use case document file path.
     * @param lastDocumentModification Stores the time of last modification.
     */
    public UseCaseDocument(String docFilePath, long lastModification)
    {
        this(docFilePath, lastModification, null);
    }


    /**
     * Constructor. It is not necessary to inform the file name neither the modification time nor the 
     * revision history. These attributes are set to <code>null</code>, 0 and <code>null</code>, respectively.
     * 
     * @param features A list that contains all features described in the document.
     */
    public UseCaseDocument(List<Feature> features)
    {
        this(null, 0, features);
    }
    
    /**
     * Indicates if a file is well formed or not.
     * 
     * @return <i>True</i> if well formed, <i>false</i> if not.
     */
    public boolean isDocumentWellFormed()
    {
        return this.features != null;
    }

    /**
     * Get method for <code>features</code> attribute.
     * 
     * @return Returns the feature list.
     */
    public List<Feature> getFeatures()
    {
        return new ArrayList<Feature>(features);
    }

    /**
     * Returns a <code>Feature</code> object, according to the passed Id.
     * 
     * @param id The Id of the feature to be retrieved.
     * @return The <code>Feature</code> object.
     */
    public Feature getFeature(String id)
    {
        Feature result = null;
        for (Feature f : this.features)
        {

            if (f.getId().equals(id))
            {
                result = f;
                break;
            }
        }
        return result;
    }

    /**
     * Get method for <code>lastDocumentModification</code> attribute.
     * 
     * @return Returns last use case document modification time in milliseconds since 00:00:00 GMT,
     * January 1, 1970.
     */
    public long getLastDocumentModification()
    {
        return lastDocumentModification;
    }

    /**
     * Sets the last modification time of the use case document.
     * 
     * @param lastDocumentModification The last modification time in milliseconds since 00:00:00
     * GMT, January 1, 1970.
     */
    public void setLastDocumentModification(long lastModification)
    {
        this.lastDocumentModification = lastModification;
    }

    /**
     * Get method for <code>docFilePath</code> attribute.
     * 
     * @return Returns the <code>docFilePath</code>.
     */
    public String getDocFilePath()
    {
        return docFilePath;
    }

    /**
     * Sets the <code>docFilePath</code> attribute.
     * 
     * @param docFilePath The new <code>docFilePath</code> value.
     */
    public void setDocFilePath(String docFilePath)
    {
        this.docFilePath = docFilePath;
    }
    
  //INSPECT mcms this method was created to allow adding a feature in the list of features from 
  //UseCaseDocument to be used in the class SaveAction 
    public void addFeature(Feature feature){
        this.features.add(feature);
    }
    
  //INSPECT mcms 28/01 this method was created to allow removing a feature in the list of features from 
    //UseCaseDocument to be used in the class SaveAction 
    public void removeFeature(Feature feature){
    	this.features.remove(feature);
    }
}
