/*
 * @(#)InputExtension.java
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
 * fsf2      Feb 27, 2009                Initial creation.
 * lmn3		 May 14, 2009				 Changes due code inspection
 * lmn3		 Jun 20, 2009				 Changes on class inheritance due different output extension implementations.
 * fsf2		 Jun 20, 2009				 Changes on method mountTests.
 */
package br.ufpe.cin.target.tcg.extensions.output;

import java.util.HashMap;
import java.util.List;

import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.util.ExcelGenerator;
import br.ufpe.cin.target.tcg.util.TraceabilityMatrixGenerator;

/**
 * <pre>
 * This class represents the extension point for different kinds of generation Excel output documents. This way, 
 * this class must be extended by clients that need to implement a new output document extension to the
 * TaRGeT system.
 * 
 * USAGE:
 * Generates the xls output file.
 * </pre>
 */

public abstract class OutputDocumentExtensionExcel extends
		OutputDocumentExtension {
	
	protected ExcelGenerator excelGenerator;
	
	/**
	 * This method mounts the test case info: case number, status, regression
	 * level, execution type, test case name, objective and expected results.
	 * 
	 * @param tcCount
	 *            The case number inside the generated test suite.
	 * @param testCase
	 *            The test case object where the information will be retrieved.
	 */
	protected abstract void mountTestCaseInfo(/*int tcCount,*/TextualTestCase testCase);

	/**
	 * Adds a row with the use case references.
	 * 
	 * @param testCase
	 *            The test case from which the use case references will be
	 *            extracted.
	 */
	protected abstract void mountUseCaseReferences(TextualTestCase testCase);

	/**
	 * Mounts the test case notes field.
	 * 
	 * @param testCase
	 *            The <code>testCase</code> that contains the notes.
	 */
	protected abstract void mountTestCaseNotes(TextualTestCase testCase);

	/**
	 * Mounts the test case steps fields.
	 * 
	 * @param testCase
	 *            The test case object that contains the step fields.
	 */
	protected abstract void mountTestCaseSteps(TextualTestCase testCase);

	/**
	 * This method mounts the test case final conditions and cleanup
	 * information.
	 * 
	 * @param testCase
	 *            The test case that contains the final conditions and cleanup
	 *            information
	 */
	protected abstract void mountTestCaseFinalFields(TextualTestCase testCase);

	/**
	 * This method mounts the test case initial conditions, setup information
	 * and related requirements.
	 * 
	 * @param testCase
	 *            The test case that contains the initial conditions, setup
	 *            information and related requirements.
	 */
	protected abstract void mountTestCaseInitialFields(TextualTestCase testCase);

	/**
	 * This method mounts the test suite header.
	 */
	protected abstract void mountHeader();
	
	//INSPECT Lais - metodos para a criação das traceability matrix são chamados agora a partir de TraceabilityMatrixGenerator
	
	/**
	 * Creates a traceability matrix that links requirements to use cases.
	 */
	protected void createRTMRequirementsSheet() {
	    
	    HashMap<String, List<String>> columns = TraceabilityMatrixGenerator.getInstance().createRTMRequirements();

		TraceabilityMatrixGenerator.getInstance().createMatrix(
				"RTM - Requirement",
				"Requirements Traceability Matrix - Requirements",
				"Requirement", "Use Case", columns, "REQ", "UC",
				this.excelGenerator);
		
	}

	/**
	 * Creates a traceability matrix that links requirements to system test
	 * cases.
	 */
	protected void createRTMSystemTestSheet() {
	    
	    //INSPECT - Lais inclusão de information test case para geração da traceability matrix
	    HashMap<String, List<String>> columns = TraceabilityMatrixGenerator.getInstance().createRTMSystemTest(this.testCases, this.informationTestCase);

		TraceabilityMatrixGenerator.getInstance().createMatrix(
				"RTM - System Test",
				"Requirements Traceability Matrix - System Test",
				"Requirement", "System Test Case", columns, "REQ", "TES",
				this.excelGenerator);
		
	}

	/**
	 * Creates a traceability matrix that links use cases to the system test
	 * cases.
	 */
	protected void createRTMUseCaseSheet() {
	    //INSPECT - Lais inclusão de information test case para geração da traceability matrix
	    HashMap<String, List<String>> columns = TraceabilityMatrixGenerator.getInstance().createRTMUseCase(this.testCases, this.informationTestCase);

		TraceabilityMatrixGenerator.getInstance().createMatrix(
				"RTM - Use Case",
				"Requirements Traceability Matrix - Use Case", "Use Case",
				"System Test Case", columns, "UC", "TES", this.excelGenerator);
	}

	/**
	 * Mounts the text case fields
	 */
	protected void mountTests() {
		this.mountHeader();

		for (TextualTestCase testCase : this.testCases) {
			this.mountTestCaseInfo(testCase);
			this.mountUseCaseReferences(testCase);
			this.mountTestCaseInitialFields(testCase);
			this.mountTestCaseNotes(testCase);
			this.mountTestCaseSteps(testCase);
			this.mountTestCaseFinalFields(testCase);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufpe.cin.target.tcg.extensions.output.
	 * OutputDocumentExtension#getExtensionFile()
	 */
	public String getExtensionFile() {
		return ".xls";
	}
}
