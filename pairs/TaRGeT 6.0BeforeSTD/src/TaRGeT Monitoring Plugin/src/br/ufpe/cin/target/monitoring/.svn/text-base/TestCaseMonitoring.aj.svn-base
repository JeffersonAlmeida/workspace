package br.ufpe.cin.target.monitoring;

import java.sql.SQLException;
import java.util.List;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.onthefly.editors.OnTheFlyTestSelectionPage;
import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentExtension;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

public aspect TestCaseMonitoring extends AbstractListener {
	
	public SelectionListener selectionListener;
	
	public pointcut getListener(SelectionListener sl) : 
		execution(*  Button.addSelectionListener(SelectionListener))
	&& cflowbelow(execution(*  OnTheFlyGeneratedTestCasesPage.createSaveTestSuiteButton(Composite))) && args(sl);
	
	pointcut generateTests(OnTheFlyTestSelectionPage selectPage) :
		execution(* OnTheFlyTestSelectionPage.getCurrentTestSuite()) 
		&& target(selectPage);
	
	
	before(SelectionListener sl) : getListener(sl) {
		selectionListener = sl;
	}
	
	after (SelectionListener stl, OnTheFlyTestSelectionPage selectPage) 
	returning (TestSuite<TestCase<FlowStep>> testSuite):
		generateTests(selectPage) && SelectionListenerFlow(stl) {		
		
		if (stl == this.selectionListener) {
			System.out.println("Intercepting Filters from Save Test Suite Event...");
		}
    }
	
	pointcut interceptTestGeneration(OutputDocumentExtension alvo) : 
		call(* OutputDocumentExtension.writeTestCaseDataInFile(..)) && target(alvo);	

	
	after(OutputDocumentExtension alvo)  throws SQLException : interceptTestGeneration(alvo) {
		List<TextualTestCase> testCases = alvo.getTestCases();
		System.out.println("OutputDocumentExtension!!!");
		for (TextualTestCase textualTestCase : testCases) {
			JdbcConnection.generateTestCase( textualTestCase.getTcIdHeader()+textualTestCase.getId(), textualTestCase.getUseCaseReferences());
		}
		
		
	}
	
	
	
}
