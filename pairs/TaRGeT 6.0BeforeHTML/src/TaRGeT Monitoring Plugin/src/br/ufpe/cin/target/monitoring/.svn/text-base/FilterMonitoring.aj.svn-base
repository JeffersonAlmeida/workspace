package br.ufpe.cin.target.monitoring;

import java.sql.SQLException;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;


import br.ufpe.cin.target.monitoring.AbstractListener;
import br.ufpe.cin.target.tcg.filters.TestSuiteFilterAssembler;


public aspect FilterMonitoring extends AbstractListener   {	
	
	public SelectionListener saveListener;
	
	public SelectionListener loadListener;
	
	pointcut saveFilter() : execution(* OnTheFlyTestSelectionPage.getFilterAssembler())
	&& cflowbelow(execution(* widgetSelected(SelectionEvent)));
	
	pointcut loadFilter(TestSuiteFilterAssembler assembler) :
		execution(* TestSuiteFilterAssembler.assemblyFilter()) && target(assembler)
		&& cflowbelow(execution(* OnTheFlyTestSelectionPage.getCurrentTestSuite()));
	
	pointcut changeTab(TestSuiteFilterAssembler assembler) : 
		loadFilter(assembler)
		&& cflowbelow(execution(* OnTheFlyGeneratedTestCasesPage.setFocus()));
		
	
	public pointcut getListener(SelectionListener sl) : 
		(execution(* OnTheFlyHeader.addSaveFilterButtonListener(SelectionListener))
		 ||execution(* OnTheFlyHeader.addLoadFilterButtonListener(SelectionListener)))		
		&& (cflowbelow(execution(* OnTheFlyGeneratedTestCasesPage.createHeaderSection(ScrolledForm, FormToolkit))) 
		&& args(sl));
	
	
	before(SelectionListener sl) : getListener(sl) {
		if (Util.loadFilterJoinPoint.equals(thisJoinPoint.toString())) {
			loadListener = sl;
		} else {
			saveListener = sl;
		}
	}
	
	after(SelectionListener sfl, TestSuiteFilterAssembler assembler) throws SQLException  : 
		loadFilter(assembler) && SelectionListenerFlow(sfl) {		
		if (sfl == loadListener) {
			Util.trackingFilters(assembler, 'l');
		}		
	}	
	after(SelectionListener sfl) returning (TestSuiteFilterAssembler assembler) throws SQLException :
		saveFilter() && SelectionListenerFlow(sfl)   {		
		if (sfl == saveListener) {
			Util.trackingFilters(assembler, 's');			
		}		
	}	
	after(TestSuiteFilterAssembler assembler) throws SQLException : changeTab(assembler) {
		Util.trackingFilters(assembler, 'c');
	}
}
