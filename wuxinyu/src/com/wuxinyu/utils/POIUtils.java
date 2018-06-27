package com.wuxinyu.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

/**
 * POI 公用类
 * 
 * @since 2016-11-24
 * @version 1.0.0
 */
public class POIUtils {

	private static Logger logger = Logger.getLogger(POIUtils.class.getName());

	private static final DecimalFormat df = new DecimalFormat("#.##");

	private static final String xls = "xls";

	private static final String xlsx = "xlsx";

	private static final String xlsm = "xlsm";

	public static Workbook getWorkBook(File file, InputStream input) throws IOException {
		String fileName = file.getName();
		Workbook workbook = null;
		try {
			// 判定是 文件类别
			if (fileName.endsWith(xls)) {
				// 2003
				workbook = new HSSFWorkbook(input);
			} else if (fileName.endsWith(xlsx)) {
				// 2007+
				workbook = new XSSFWorkbook(input);
			} else if (fileName.endsWith(xlsm)) {
				// 2007+
				workbook = new XSSFWorkbook(input);
			} else {
				throw new IOException("未知文件类型");
			}

			return workbook;
		} catch (IOException e) {
			logger.info("ocoure error -->" + ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	/**
	 * 得到跨行数目
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @param cellIndex
	 * @return
	 */
	public static int cellRange(Sheet sheet, int rowIndex, int cellIndex) {
		Assert.notNull(sheet, "argus sheet must not be null");

		int regionNums = sheet.getNumMergedRegions();
		for (int i = 0; i < regionNums; i++) {
			CellRangeAddress rangeAddress = sheet.getMergedRegion(i);
			if (rangeAddress.getFirstRow() <= rowIndex && rangeAddress.getLastRow() >= rowIndex) {
				if (rangeAddress.getFirstColumn() <= cellIndex && rangeAddress.getLastColumn() >= cellIndex) {
					return rangeAddress.getLastColumn() - cellIndex;
				}
			}
		}

		return 0;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	private static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getValue(fCell);
				}
			}
		}

		return null;
	}

	/**
	 * 得到cell的值
	 * 
	 * @param book
	 * @param cell
	 * @return
	 */
	public static int cellRangeRow(Sheet sheet, int rowIndex) {

		int regionNums = sheet.getNumMergedRegions();
		for (int i = 0; i < regionNums; i++) {
			CellRangeAddress rangeAddress = sheet.getMergedRegion(i);
			if (rangeAddress.getFirstRow() <= rowIndex && rangeAddress.getLastRow() >= rowIndex) {
				return rangeAddress.getLastRow() - rowIndex;
			}
		}

		return 0;
	}

	public static String getCellValueAsString(Workbook book, XSSFCell cell) {
		if (cell == null) {
			return StringUtils.EMPTY;
		}
		CellStyle style = book.createCellStyle();
		DataFormat dateFormat = book.createDataFormat();

		style.setDataFormat(dateFormat.getFormat("@"));
		cell.setCellStyle(style);

		cell.setCellType(Cell.CELL_TYPE_STRING);

		String retVal = StringUtils.EMPTY;

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_FORMULA:
		case Cell.CELL_TYPE_NUMERIC: {
			retVal = String.valueOf(cell.getNumericCellValue());
			if (retVal.indexOf(".") > -1)
				retVal = retVal.substring(0, retVal.indexOf("."));
			break;
		}
		case Cell.CELL_TYPE_STRING: {
			retVal = cell.getStringCellValue().trim();
			break;
		}
		case Cell.CELL_TYPE_BLANK: {
			retVal = "";
			break;
		}
		}

		return retVal;

	}

	/**
	 * 建立单元格
	 * 
	 * @param row
	 * @param columnIndex
	 * @return
	 */
	public static XSSFCell createCellString(XSSFRow row, int columnIndex) {
		Assert.notNull(row, "argus row must not be null");
		XSSFCell cell = row.createCell(columnIndex);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell;
	}

	/**
	 * 从sheet中得到指定的单元格
	 * 
	 * @param sheet
	 * @param rownum
	 * @param cellnum
	 * @return
	 */
	public static XSSFCell getCell(XSSFSheet sheet, int rownum, int cellnum) {
		Assert.notNull(sheet, "argus row must not be null");

		XSSFRow row = sheet.getRow(rownum);
		if (row == null) {
			return null;
		} else {
			return row.getCell(cellnum);
		}
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	public static String getCellValue(FormulaEvaluator evaluator, Cell cell) {
		CellValue cellValue = evaluator.evaluate(cell);

		if (cellValue == null) {
			return null;
		}

		switch (cellValue.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			return String.valueOf(cellValue.getNumberValue());
		case Cell.CELL_TYPE_STRING:
			return cellValue.getStringValue();
		}
		return null;
	}

	/**
	 * 从sheet中得到指定的单元格
	 * 
	 * @param sheet
	 * @param rownum
	 * @param cellnum
	 * @return
	 */
	public static String cellVal(XSSFSheet sheet, int rownum, int cellnum) {
		Assert.notNull(sheet, "argus row must not be null");

		XSSFRow row = sheet.getRow(rownum);
		if (row == null) {
			return StringUtils.EMPTY;
		} else {
			XSSFCell cell = row.getCell(cellnum);
			if (cell == null) {
				return StringUtils.EMPTY;
			} else {
				return getCellValue(cell);
			}

		}
	}

	/**
	 * 得到单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {

		String retVal = StringUtils.EMPTY;

		if (cell != null) {
			if (isMergedRegion(cell.getSheet(), cell.getRowIndex(), cell.getColumnIndex())) {
				retVal = getMergedRegionValue(cell.getSheet(), cell.getRowIndex(), cell.getColumnIndex());
				return retVal;
			}
			
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_FORMULA:
			case Cell.CELL_TYPE_NUMERIC: {
				try {
					retVal = String.valueOf(cell.getNumericCellValue());
				} catch (Exception e) {
					retVal = StringUtils.EMPTY;
				}
				if (retVal.indexOf(".") > -1) {
					String temp = StringUtils.substring(retVal, retVal.indexOf(".") + 1, retVal.length());
					if (temp.length() > 2) {
						retVal = df.format(cell.getNumericCellValue());
					} else if (temp.length() == 1 && "0".equals(temp)) {
						retVal = StringUtils.substring(retVal, 0, retVal.indexOf("."));
					}
				}
				break;
			}
			case Cell.CELL_TYPE_STRING: {
				retVal = cell.getStringCellValue().trim();
				break;
			}
			case Cell.CELL_TYPE_BLANK: {
				retVal = StringUtils.EMPTY;
				break;
			}
			}
		}
		return retVal;

	}
	
	/**
	 * 得到单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getValue(Cell cell) {

		String retVal = StringUtils.EMPTY;

		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_FORMULA:
			case Cell.CELL_TYPE_NUMERIC: {
				try {
					retVal = String.valueOf(cell.getNumericCellValue());
				} catch (Exception e) {
					retVal = StringUtils.EMPTY;
				}
				if (retVal.indexOf(".") > -1) {
					String temp = StringUtils.substring(retVal, retVal.indexOf(".") + 1, retVal.length());
					if (temp.length() > 2) {
						retVal = df.format(cell.getNumericCellValue());
					} else if (temp.length() == 1 && "0".equals(temp)) {
						retVal = StringUtils.substring(retVal, 0, retVal.indexOf("."));
					}
				}
				break;
			}
			case Cell.CELL_TYPE_STRING: {
				retVal = cell.getStringCellValue().trim();
				break;
			}
			case Cell.CELL_TYPE_BLANK: {
				retVal = StringUtils.EMPTY;
				break;
			}
			}
		}
		return retVal;

	}

	public static String getCellValue(XSSFCell cell) {

		String retVal = StringUtils.EMPTY;

		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_FORMULA:
			case Cell.CELL_TYPE_NUMERIC: {
				retVal = String.valueOf(cell.getNumericCellValue());
				if (retVal.indexOf(".") > -1) {
					String temp = StringUtils.substring(retVal, retVal.indexOf(".") + 1, retVal.length());
					if (temp.length() > 2) {
						retVal = df.format(cell.getNumericCellValue());
					} else if (temp.length() == 1 && "0".equals(temp)) {
						retVal = StringUtils.substring(retVal, 0, retVal.indexOf("."));
					}
				}
				break;
			}
			case Cell.CELL_TYPE_STRING: {
				retVal = cell.getStringCellValue().trim();
				break;
			}
			case Cell.CELL_TYPE_BLANK: {
				retVal = StringUtils.EMPTY;
				break;
			}
			}
		}

		return retVal;

	}
	
	public static void copyRow(Row newRow,Row oldRow){
	 	int physicalNumberOfCells = oldRow.getPhysicalNumberOfCells();
	 	for (int i = 0; i < physicalNumberOfCells; i++) {
	 		if (oldRow!=null && newRow!=null) {
	 			Cell createCell = newRow.createCell(i);
	 			if (createCell!=null&&oldRow.getCell(i)!=null) {
	 				createCell.setCellValue(oldRow.getCell(i).toString());
				}
			}
		}
        
    }
	
	@SuppressWarnings("unused")
	private static CellStyle getStyle(Workbook workbook,int fontSize,int bgColor){
		// 定义标题样式
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(false);
		// 单元格字体
		Font font = workbook.createFont();
		font.setFontName("宋体");
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontHeightInPoints((short) fontSize);
		style.setFont(font);
		// 设置背景填充样式
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);//设置单元格颜色
		style.setFillForegroundColor((short)bgColor);
		style.setBorderBottom(CellStyle.BORDER_THIN);// 下边框
		style.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
		style.setBorderRight(CellStyle.BORDER_THIN);// 右边框
		style.setBorderTop(CellStyle.BORDER_THIN);// 上边框
		style.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		
		return style;
	}
	
	/**
	 * 插入一行
	 * @param sheet
	 * @param rowNum  要插入的行号
	 * @param oldRowNum 
	 * @param flag
	 * @return
	 */
	public static Row insertRow(Sheet sheet,int rowNum,int oldRowNum,boolean flag) {
		Row row = sheet.getRow(rowNum);
        sheet.shiftRows(rowNum, sheet.getLastRowNum(), 1); //向下移动一行
        Row newRow = sheet.createRow(rowNum);
        Row oldRow = sheet.getRow(oldRowNum);
        newRow.setHeight(oldRow.getHeight());
        for (int i = 0; i < 10; i++) {
        	if(oldRow.getCell(i) != null){
        		if(oldRow.getCell(i).getCellStyle() != null){
            		newRow.createCell(i).setCellStyle(oldRow.getCell(i).getCellStyle());
            		if(flag){
            			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 4));
            			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 5, 7));
            		}
            	}
        	}
		}
        sheet.getRow(rowNum + 1).setHeight(row.getHeight());
        return newRow;
    }

}
