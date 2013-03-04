package br.ufpe.cin.target.monitoring;

import org.eclipse.swt.events.SelectionListener;


public abstract aspect AbstractListener {
		
	public abstract pointcut getListener(SelectionListener sl);
	
	pointcut SelectionListenerFlow(SelectionListener sl):
        cflow(execution(* * (..)) && this(sl));

}
