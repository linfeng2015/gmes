package com.yfsy.gems.web.addr;

import java.io.InputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value = "/recaddr")
public class RecaddrControl{

	@RequestMapping(method = RequestMethod.GET)
	  public String addr(){
			System.out.println("in the addr");
			return "recaddr/addr";
	  }

	@RequestMapping(value="uploadFile",method = RequestMethod.POST)
	public String uploadFile(@RequestParam("addrFile") CommonsMultipartFile[] files,HttpServletRequest request){
		
		System.out.println("in the uploadFile");
		  for(int i = 0 ; i < files.length ;i++){
			  try{
				  InputStream in = files[i].getInputStream();
				  HSSFWorkbook workBook = new HSSFWorkbook(in);
				  HSSFSheet sheet = workBook.getSheetAt(0);
				  Iterator<Row> rowIterator = sheet.iterator();
				  while(rowIterator.hasNext()){
					  Row row = rowIterator.next();
					  Iterator<Cell> cellIterator = row.cellIterator();
					  while(cellIterator.hasNext()){
						  Cell cell = cellIterator.next();
						  switch(cell.getCellType()){
						  		case Cell.CELL_TYPE_NUMERIC :
						  			System.out.print(cell.getNumericCellValue()+"\t\t");
						  			break ;
						  		case Cell.CELL_TYPE_STRING :
						  			System.out.print(cell.getStringCellValue()+"\t\t");
						  			break ;	
						  }
					  }
					  System.out.println("");
				  }
				  in.close();
			  }catch(Exception e){
				  e.printStackTrace();
				  System.out.println("上传出错");
			  }
		  }	
		  return "recaddr/addr";
	  }
	
	
}
