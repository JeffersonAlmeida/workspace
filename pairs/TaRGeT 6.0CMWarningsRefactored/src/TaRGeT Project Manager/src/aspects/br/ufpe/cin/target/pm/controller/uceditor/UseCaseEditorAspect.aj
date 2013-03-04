/*
 * @(#)UseCaseEditorAspect.aj
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * fsf2      08/10/2009                  Initial creation.
 */



import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.ucdoc.xml.XMLFileGenerator;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.controller.TargetProjectRefresher;
import br.ufpe.cin.target.pm.exceptions.TargetProjectLoadingException;




/**
 * << high level description of the aspect >>
 * 
 * <pre>
 * CLASS:
 * << Short description of the Utility Function or Toolkit >>
 * [ Template guidelines:
 *     When the class is an API UF, describe only WHAT the UF shall
 *     do, when it is an implementation UF, describe HOW the UF will
 *     do what it should, and if it is a tailoring to an already existing
 *     UF, describe what are the diferences that made the UF needed.
 * ]
 * RESPONSIBILITIES:
 * <<High level list of things that the class does>>
 * 1) responsibility
 * 2) ...
 * <Example:
 * RESPONSIBILITIES:
 * 1) Provide interface to access navigation related UFs
 * 2) Guarantee easy access to the UFs
 * 3) Provide functional implementation to the ScrollTo UF for P2K.
 * >
 *
 * COLABORATORS:
 * << List of descriptions of relationships with other classes, i.e. uses,
 * contains, creates, calls... >>
 * 1) class relationship
 * 2) ...
 *
 * USAGE:
 * << how to use this class - for UFs show how to use the related 
 * toolkit call, for toolkits show how a test case should access the
 * functions >>
 */
public aspect UseCaseEditorAspect
{
    pointcut pointcutLoadUseCaseDocumentFiles(Collection<String> files,
            boolean throwExceptionOnFatalError) :
        call(public Collection<UseCaseDocument> ProjectManagerController.loadUseCaseDocumentFiles(Collection<String>,boolean)) && args(files,throwExceptionOnFatalError);

    Collection<UseCaseDocument> around(Collection<String> files, boolean throwExceptionOnFatalError) : pointcutLoadUseCaseDocumentFiles(files,throwExceptionOnFatalError){
        Collection<UseCaseDocument> collection = proceed(files, throwExceptionOnFatalError);
        
        TargetProjectRefresher targetProjectRefresher = new TargetProjectRefresher(null,null);

        files.clear();
        
        for (UseCaseDocument phoneDocument : collection)
        {       	
        	Collection<UseCaseDocument> collectionCurrentDocuments = new ArrayList<UseCaseDocument>();
        	
        	collectionCurrentDocuments.addAll(collection);
        	collectionCurrentDocuments.remove(phoneDocument);
        	
        	try {
				if(phoneDocument.isDocumentWellFormed() && targetProjectRefresher.verifyUseCaseDocument(phoneDocument, collectionCurrentDocuments, false).isEmpty()){
					br.ufpe.cin.target.common.ucdoc.xml.XMLFileGenerator xmlGenerator = new XMLFileGenerator(
				            phoneDocument);

				    String fileName = xmlGenerator.getUseCaseDocument().getDocFilePath();
				    File file = new File(fileName);

				    String fileExtension = FileUtil.getFileExtension(file);
				    fileName = fileName.replace(fileExtension, "xml");

				    file.delete();

				    xmlGenerator.createXMLSchema();

				    File newFile = new File(fileName);
				    xmlGenerator.saveXMLFile(newFile);

				    phoneDocument.setDocFilePath(fileName);

				    phoneDocument.setLastDocumentModification(newFile.lastModified());
				    
				    files.add(fileName);
				}
			} catch (TargetProjectLoadingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        return collection;
    }
}
