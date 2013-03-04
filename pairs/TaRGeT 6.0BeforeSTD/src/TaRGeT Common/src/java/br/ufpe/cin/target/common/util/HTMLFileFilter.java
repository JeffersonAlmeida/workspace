package br.ufpe.cin.target.common.util;

import java.io.File;
import java.io.FileFilter;

public class HTMLFileFilter implements FileFilter {

	 /**
    * This method accepts file names that ends with '.html'.
    * 
    * @param file The file which extension will be checked.
    * @return <i>True</i> if the file ends with '.html' or <i>false</i> otherwise.
    */
	
	public boolean accept(File file) {
		return !file.isDirectory() && file.getAbsolutePath().endsWith(".html");
	}
}

