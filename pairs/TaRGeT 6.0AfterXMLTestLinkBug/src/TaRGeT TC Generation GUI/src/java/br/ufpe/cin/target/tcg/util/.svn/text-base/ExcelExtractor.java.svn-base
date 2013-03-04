package br.ufpe.cin.target.tcg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ExcelExtractor {
	
	private HSSFWorkbook workBook;
	
	private HSSFSheet sheet;
	
	private Iterator<Row> rowIterator;

	private Row currentRow;

    private FileInputStream fileInputStream;
	
	public final static int CELL_TYPE_NUMERIC = HSSFCell.CELL_TYPE_NUMERIC;
	
	public final static int CELL_TYPE_STRING = HSSFCell.CELL_TYPE_STRING;
	
	public void inicializeExcelFile(File file) throws FileNotFoundException, IOException
	{
	    this.fileInputStream = new FileInputStream(file);
	    
	    this.workBook = new HSSFWorkbook(this.fileInputStream);
	}
	
	public void closeStream() throws IOException
	{
	    this.fileInputStream.close();
	}
	
	public void setSheet(int n)
	{
		this.sheet = this.workBook.getSheetAt(n);
		
		this.rowIterator = this.sheet.rowIterator();
	}
	
	public void setCurrentRow(int rowNumber)
	{
	    this.currentRow = this.sheet.getRow(rowNumber);
	    
	    System.out.println();
	}
	
	 /**
     * Auxiliary method to get the next row of the file being read.
     * 
     * @return The next row of the test suite file.
     */
	public void nextRow()
	{
		if (this.rowIterator.hasNext())
        {
			this.currentRow = this.rowIterator.next();
        }
        else
        {
        	this.currentRow = null;
        }
	}
	
	public boolean finished(){
		return this.currentRow == null;
	}
	
	public String getCellString(int n)
	{
		String retorno = "";
		
		if(this.currentRow != null && this.currentRow.getCell(n) != null)
		{
		    retorno = this.currentRow.getCell(n).getRichStringCellValue().getString();
		}
	    
	    return retorno;
	}
	
	public int getCellType(int cell)
	{
		int retorno = -1;
	    
	    if(this.currentRow.getCell(cell) != null)
		{
	        retorno = this.currentRow.getCell(cell).getCellType();
		}
	    
	    return retorno;
	}
	
	public int getColumnIndex(int cell)
	{
		return this.currentRow.getCell(cell).getColumnIndex();
	}

}
