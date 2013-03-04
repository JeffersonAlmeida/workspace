package br.ufpe.cin.target.tcg.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelGenerator {

	private HSSFSheet sheet;
	
	private HSSFRow currentRow;

	/** The work book that contains all sheets. Represents the excel file */
	protected HSSFWorkbook workBook;

	/** The style of the header cells */
	protected HSSFCellStyle headerCellStyle;

	/** The style of the header cells with borders*/
	protected HSSFCellStyle headerCellStyleWithBorders;

	/** The style of the blue cells */
	protected HSSFCellStyle blueAreaStyle;

	/** The style of the grey cells */
	protected HSSFCellStyle greyAreaStyle;

	/** The style of the text cells */
	protected HSSFCellStyle textAreaStyle;

	/** The number of the next sheet row */
	protected int nextLine;

	private HSSFCellStyle hiddenCellStyle;

	private HSSFCellStyle headerCellStyleWithBordersGrey;

	private HSSFCellStyle textAreaStyleWithBorders;
	
	private HSSFCellStyle introSheetStyle;

	private HSSFCellStyle targetCellStyle;

	public ExcelGenerator() {
	}

	public void initWorkbookAndStyles() {
		this.workBook = new HSSFWorkbook();

		this.blueAreaStyle = this.getBlueAreaStyle();
		this.greyAreaStyle = this.getGreyAreaStyle();
		this.headerCellStyle = this.getHeaderCellStyle();
		this.headerCellStyleWithBorders = this.getHeaderCellStyleWithBorders();
		this.textAreaStyle = this.getTextAreaStyle();
		this.headerCellStyleWithBordersGrey = this.getHeaderCellStyleWithBordersGrey();
		this.textAreaStyleWithBorders = this.getTextAreaStyleWithBorders();
		this.hiddenCellStyle = this.getHiddenCellStyle();
		this.introSheetStyle = this.getIntroSheetStyle();
		this.targetCellStyle = this.getTargetCellStyle();
	}

	private HSSFCellStyle getTargetCellStyle() {
		HSSFCellStyle style = this.workBook.createCellStyle();
		HSSFFont font = this.workBook.createFont();
		
		font.setFontHeightInPoints((short) 72);
		font.setFontName("Arial");
		style.setFont(font);
		
		return style;
	}

	/**
	 * Constructs the HSSFCellStyle object of a header cell.
	 * 
	 * @return The style of a header area.
	 */
	private HSSFCellStyle getHeaderCellStyleWithBordersGrey() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		return cellStyle;
	}

	/**
	 * Constructs the HSSFCellStyle object of a text cell.
	 * 
	 * @return The style of a text area.
	 */
	private HSSFCellStyle getTextAreaStyleWithBorders() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 8);
		font.setFontName("Arial");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		return cellStyle;
	}

	/**
	 * Constructs the HSSFCellStyle object of a hidden cell.
	 * 
	 * @return The style of a hidden area.
	 */
	private HSSFCellStyle getHiddenCellStyle() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 2);
		font.setFontName("Arial");
		font.setColor(HSSFColor.WHITE.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

		return cellStyle;
	}

	/**
	 * Creates a merged line with the given text.
	 * @param row
	 * @param text
	 * @param line
	 * @param startCell
	 * @param endCell
	 * @param cellStyle
	 */
	private void createMergedLine(HSSFRow row, String text, int line, int startCell, int endCell, HSSFCellStyle cellStyle)
	{
		this.sheet.addMergedRegion(new CellRangeAddress(line, line, startCell, endCell));
		this.createCell(row, startCell, text, cellStyle);

		for(int i = startCell+1; i <= endCell; i++)
		{
			this.createCell(row, i, "", cellStyle);
		}
	}

	public void createMergedLineWithBlueAreaStyle(String text, int startCell, int endCell)
	{
		this.createMergedLine(this.currentRow, text, this.nextLine-1, startCell, endCell, this.blueAreaStyle);
	}

	/**
	 * Creates a field with the given text.
	 * @param cellText1
	 * @param merge
	 * @return The new line.
	 */
	public void createNewField(String cellText1, String cellText2, boolean merge)
	{
		this.currentRow = this.sheet.createRow(this.nextLine++);

		this.createCell(this.currentRow,1,cellText1,this.headerCellStyle);
		this.createCell(this.currentRow,4,cellText2,this.textAreaStyle);
	}

	public void createNextRow()
	{
		this.currentRow = this.sheet.createRow(this.nextLine++);
	}
	
	public void setCurrentRowHeight(float height)
	{
	    this.currentRow.setHeightInPoints(height);
	}

	public void setCellStyleHeader(int nCell)
	{
		this.currentRow.getCell(nCell).setCellStyle(this.headerCellStyle);
	}

	/**
	 * Creates a cell in the given row with the given text and style.
	 * @param row
	 * @param cellNumber
	 * @param text
	 * @param style
	 * @return The created cell.
	 */
	public void createCell(HSSFRow row, int cellNumber, String text, HSSFCellStyle style)
	{
		HSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(new HSSFRichTextString(text));
		cell.setCellStyle(style);
	}

	public void createEmptyFields(int inicialCell, int finalCell) 
	{
		for(int i = inicialCell; i <= finalCell; i++)
		{
			this.createCell(this.currentRow, i, "", this.textAreaStyle);
		}
	}

	public void createCellTextAreaStyle(int cellNumber, String text)
	{
		this.createCell(this.currentRow,cellNumber,text,this.textAreaStyle);
	}
	
	public void createCellTextAreaStyleWithBorders(int cellNumber, String text)
	{
		this.createCell(this.currentRow,cellNumber,text,this.textAreaStyleWithBorders);
	}
	
	public void createCellHeaderCellStyleWithBordersGrey(int cellNumber, String text)
	{
		this.createCell(this.currentRow,cellNumber,text,this.headerCellStyleWithBordersGrey);
	}

	public void createCellHeaderCellStyleWithBorders(int cellNumber, String text)
	{
		this.createCell(this.currentRow,cellNumber,text,this.headerCellStyleWithBorders);
	}

	public void createCellGreyAreaStyle(int cellNumber, String text)
	{
		this.createCell(this.currentRow,cellNumber,text,this.greyAreaStyle);
	}

	public void createCellBlueAreaStyle(int cellNumber, String text)
	{
		this.createCell(this.currentRow,cellNumber,text,this.blueAreaStyle);
	}
	
	public void createCellIntroSheetStyle(int cellNumber, String text)
	{
		this.createCell(this.currentRow,cellNumber,text,this.introSheetStyle);
	}

	public void setColumnHidden(int collumn, boolean hidden)
	{
		this.sheet.setColumnHidden(collumn, hidden);//hidden the column
	}
	
	public void createCellTargetStyle(int cellNumber,String text)
	{
		this.createCell(this.currentRow, cellNumber, text, this.targetCellStyle);
	}
	
	public void createCellHiddenCellStyle(int cellNumber,String text)
	{
		this.createCell(this.currentRow, cellNumber, text, this.hiddenCellStyle);
	}

	/**
	 * Create a sheet with a given title.
	 * @param title
	 * @return A sheet with the given title.
	 */
	public void createSheet(String title)
	{
		this.sheet = this.workBook.createSheet(title);

		sheet.setDisplayGridlines(false);
		sheet.setDefaultColumnWidth(27);

		this.nextLine = 0;
	}

	/**
	 * Constructs the HSSFCellStyle object of a header cell.
	 * 
	 * @return The style of a header area.
	 */
	private HSSFCellStyle getHeaderCellStyle() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 8);
		font.setFontName("Arial");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index); // the default color is black.
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		return cellStyle;
	}

	/**
	 * Constructs the HSSFCellStyle object of a cell with borders.
	 * 
	 * @return The style of a grey area.
	 */
	private HSSFCellStyle getHeaderCellStyleWithBorders()
	{
		HSSFCellStyle cellStyle = this.getHeaderCellStyle();

		return this.setBorders(cellStyle);
	}

	/**
	 * Constructs the HSSFCellStyle object of a blue cell.
	 * 
	 * @return The style of a blue area.
	 */
	private HSSFCellStyle getBlueAreaStyle() 
	{
		HSSFCellStyle cellStyle = getHeaderCellStyleWithBorders();

		cellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);


		return cellStyle;
	}

	/**
	 * Constructs the HSSFCellStyle object of a grey cell.
	 * 
	 * @return The style of a grey area.
	 */
	private HSSFCellStyle getGreyAreaStyle() 
	{
		HSSFCellStyle cellStyle = getHeaderCellStyleWithBorders();

		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

		return cellStyle;
	}


	/**
	 * Constructs the HSSFCellStyle object of a text cell.
	 * 
	 * @return The style of a text area.
	 */
	private HSSFCellStyle getTextAreaStyle() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();

		font.setFontHeightInPoints((short) 8);
		font.setFontName("Arial");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

		this.setBorders(cellStyle);

		return cellStyle;
	}
	
	private HSSFCellStyle getIntroSheetStyle()
	{
		HSSFCellStyle style1 = this.workBook.createCellStyle();
		HSSFFont font1 = this.workBook.createFont();
		
		font1.setFontHeightInPoints((short) 14);
		font1.setFontName("Arial");
		font1.setItalic(true);
		style1.setFont(font1);
		
		return style1;
	}

	/**
	 * Sets the borders of a given cell.
	 * @param cellStyle
	 * @return The given cell with borders.
	 */
	private HSSFCellStyle setBorders(HSSFCellStyle cellStyle) 
	{
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		return cellStyle;
	}
	
	/**
	 * Creates a cell style and set the cell borders according to the given parameters.
	 * This method is used to create the template information area in the intro sheet.
	 * @param borderBottom if the cell style will have a bottom border
	 * @param borderTop if the cell style will have a top border
	 * @param borderLeft if the cell style will have a left border
	 * @param borderRight if the cell style will have a right border
	 * @return a cell style with the desired borders
	 */
	public HSSFCellStyle createTemplateInfoCellStyle(boolean borderBottom, boolean borderTop, 
			boolean borderLeft, boolean borderRight)
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();
		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		if(borderBottom){
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		}
		if(borderTop){
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		}
		if(borderLeft){
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		}
		if(borderRight){
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		}
		return cellStyle;
	}

	public void write(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);

		this.workBook.write(fos);
		fos.close();
	}

	public HSSFWorkbook getWorkbook() {
		return this.workBook;
	}

	public void setHeightCurrentRow(short height) {
		this.currentRow.setHeight((short) height);
	}

	public void setColumnWidth(int cell, int width) {
		this.sheet.setColumnWidth(cell, width);
	}
	
	/**
	 * Builds the beginning grey area of a test case row.
	 * 
	 * @param row
	 *            The row where the grey are will be built
	 */
	public void mountGreyArea() 
	{
		HSSFCell cell = this.currentRow.createCell(0);
		cell.setCellStyle(this.greyAreaStyle);

		cell = this.currentRow.createCell(1);
		cell.setCellStyle(this.greyAreaStyle);

		cell = this.currentRow.createCell(2);
		cell.setCellStyle(this.greyAreaStyle);
	}

    public void createEmptyFieldsGrey(int inicialCell, int finalCell)
    {
        for(int i = inicialCell; i <= finalCell; i++)
        {
            this.createCell(this.currentRow, i, "", this.greyAreaStyle);
        }
    }
    
    /**
     * 
     * Creates a grey area with text cell style.
     * @param initialCell initial cell from the grey area
     * @param finalCell final cell from the grey style
     */
    public void createTextEmptyFieldsGrey(int initialCell, int finalCell)
    {
        HSSFCellStyle textGreyAreaStyle = this.workBook.createCellStyle();

        HSSFFont font = this.workBook.createFont();

        font.setFontHeightInPoints((short) 8);
        font.setFontName("Arial");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

        textGreyAreaStyle.setFont(font);
        textGreyAreaStyle.setWrapText(true);
        textGreyAreaStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        this.setBorders(textGreyAreaStyle);
        
        textGreyAreaStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        textGreyAreaStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        
        for(int i = initialCell; i <= finalCell; i++)
        {
            this.createCell(this.currentRow, i, "", textGreyAreaStyle);
            
        }
    }

}
