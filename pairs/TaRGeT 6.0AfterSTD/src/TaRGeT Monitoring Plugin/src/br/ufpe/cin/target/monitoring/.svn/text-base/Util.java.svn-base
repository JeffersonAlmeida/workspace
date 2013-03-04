package br.ufpe.cin.target.monitoring;

import java.sql.SQLException;

import br.ufpe.cin.target.tcg.filters.TestSuiteFilterAssembler;


public class Util {
	
	

	public static String loadFilterJoinPoint = "execution(void com.motorola.btc.research.target.tcg.editors.OnTheFlyHeader.addLoadFilterButtonListener(SelectionListener))";
										
	public static String saveFilterJoinPoint = "execution(void com.motorola.btc.research.target.tcg.editors.OnTheFlyHeader.addSaveFilterButtonListener(SelectionListener))";
												
	public static void clearScreen() {
		for (int i=0;i<100;++i)
			System.out.println("\n");
	}
	
	public static void trackingFilters(TestSuiteFilterAssembler assembler, char type) throws SQLException {
		
		Util.clearScreen();
		String useCase = "None", req = "None", path = "None";
		if (assembler.getRequirementsFilter() != null) {
			req = assembler.getRequirementsFilter().getRequirements().toString();
			System.out.println("Selected Requirements: \n" +
			Util.splitJoin(req, ",", "\n")+"\n");		
		} else {
			System.out.println("None Selected Requirements. \n");
		}
		
		if (assembler.getUseCasesFilter() != null) {
			useCase = assembler.getUseCasesFilter().getUsecases().toString();
			System.out.println("Selected Use Cases:\n"+
			Util.splitJoin(useCase, ",", "\n")+"\n");
			
			
		} else {
			System.out.println("None Selected Use Cases.\n");
		}
				
		if (assembler.getSimilarityFilter() != null) {
			path = Double.toString(assembler.getSimilarityFilter().getSimilarity());
			System.out.println("Similarity Filter (Path Coverage): " +
					path+"%\n");			
		}
		
		if (assembler.getPurposeFilter() != null) {
			System.out.println("Selected Purposes:\n" +
			Util.splitJoin(assembler.getPurposeFilter().getPurposes().toString(), ";", "\n"));
		} else {
			System.out.println("None Selected Purposes.\n");
		}
		CharSequence none = new String("None");
		
		if (!path.contains(none)) {
			JdbcConnection.generateFilter("p"+type, useCase, req, path+"%");
		}
	}
	
	public static String splitJoin(String str, String splitStr, String joinStr) {
		String [] temp = str.split(splitStr);
		
		String ret = "";
		ret = temp[0];
		for (int i = 1; i < temp.length; i++) {
			ret += joinStr + "," + temp[i];
		}
		
		return ret;
		
	}

}
