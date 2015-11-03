package com.yfsy.gems.web.addr;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.yfsy.gems.entity.AddrSheet;
import com.yfsy.gems.entity.Recaddr;
import com.yfsy.gems.service.addr.AddrSheetService;
import com.yfsy.gems.service.addr.RecaddrService;
import com.yfsy.gems.util.CheckEmail;
import com.yfsy.gems.util.StringUtil;

@Controller
@RequestMapping(value = "/recaddr")
public class RecaddrController{
	
	@Autowired
	private RecaddrService recaddrService ;
	@Autowired
	private AddrSheetService sheetService ;
	
	private static final int PAGE_SIZE = 10;

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "姓名");
	}
	
	@RequestMapping(value="delete/{ids}",method = RequestMethod.GET)
	public String delete(Model model ,@PathVariable("ids") String ids,@RequestParam("sheetId")Long sheetId,HttpServletRequest request,RedirectAttributes redirectAttributes){
	    //System.out.println("in the delete sheetId:"+sheetId);
		String[] id = null;
	    boolean success = false;
	    List<Long> idList = new ArrayList();
		if(ids != null){
			id = StringUtil.split(ids, ":");
			for(int i = 0 ; i < id.length ; i++){
				idList.add(Long.parseLong(id[i]));
			}
			try{
				recaddrService.delete(idList);
				success = true ;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(success){
			redirectAttributes.addFlashAttribute("message", "删除成功");
		}
		HttpSession session = request.getSession();
		session.setAttribute("sheetId", sheetId);
		return "redirect:/recaddr/" ;
	}
	
	@RequestMapping(value="view/{sheetId}",method = RequestMethod.GET)
	public String view(@PathVariable("sheetId") Long sheetId,@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
	@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request){
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Recaddr> recaddrs =  recaddrService.getRecaddrsBySheet(sheetId,searchParams,pageNumber,PAGE_SIZE,sortType);
		model.addAttribute("recaddrs", recaddrs);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("sheetId",sheetId);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "recaddr/addr";
	}
	
  @RequestMapping(method = RequestMethod.GET)
  public String addr(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,Model model, HttpServletRequest request){
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
    	HttpSession session = request.getSession();
		Long sheetId = (Long)session.getAttribute("sheetId");
		if(sheetId == null){
			sheetId = Long.parseLong(request.getParameter("sheetId"));
		}
		Page<Recaddr> recaddrs = recaddrService.getRecaddrs(searchParams, pageNumber, PAGE_SIZE, sortType,sheetId);
		//System.out.println("recaddrs length:"+recaddrs.getContent().size());
		model.addAttribute("recaddrs", recaddrs);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		model.addAttribute("sheetId", sheetId);
		return "recaddr/addr";
  }
	  
	@RequestMapping(value="uploadFile",method = RequestMethod.POST)
	public String uploadFile(@RequestParam("addrFile") CommonsMultipartFile[] files,@RequestParam("sheetId")Long sheetId,HttpServletRequest request,Model model){
		  for(int i = 0 ; i < files.length ;i++){
			  try{
				  InputStream in = files[i].getInputStream();
				  HSSFWorkbook workBook = new HSSFWorkbook(in);
				  HSSFSheet sheet = workBook.getSheetAt(0);
				  Iterator<Row> rowIterator = sheet.iterator();
				  int colNum = 0 ;
				  while(rowIterator.hasNext()){
					  Row row = rowIterator.next();
					  //获取表头信息
					  if(row.getRowNum() == 0){
						  colNum = row.getPhysicalNumberOfCells();
						  continue ;
					  }
					  Iterator<Cell> cellIterator = row.cellIterator();
					  int cellNumber = 0 ;
					  double number = 0 ;
					  String emailStr = null ;
					  String companyName = null ;
					  String name = null ;
					  Recaddr recaddr = new Recaddr();
					  while(cellIterator.hasNext()){
					      if(cellNumber < colNum){
					    	  cellNumber ++ ;
					      }
						  Cell cell = cellIterator.next();
						  switch(cell.getCellType()){
						  		case Cell.CELL_TYPE_NUMERIC :
						  			number = cell.getNumericCellValue();
						  			//System.out.print(cellNumber+":"+cell.getNumericCellValue()+"\t\t");
						  			break;
						  		case Cell.CELL_TYPE_STRING :
						  			if(cellNumber == 2){
						  				emailStr = cell.getStringCellValue(); 
						  			}else if(cellNumber == 3){
						  				name = cell.getStringCellValue(); 
						  			}else{
						  				companyName = cell.getStringCellValue();
						  			}
						  			//System.out.print(cellNumber+":"+cell.getStringCellValue()+"\t\t");
						  			break ;	
						  }
					  }
   					  //进行邮箱地址封装
					  if(emailStr == null && name ==null && companyName == null){
						  break ;
					  }
					  if(CheckEmail.isEmail(emailStr)){
						  AddrSheet addrSheet = sheetService.getAddrSheet(sheetId);
						  recaddr.setAddrSheet(addrSheet);
						  recaddr.setAddr(emailStr);
						  recaddr.setName(name);
						  recaddr.setCompany(companyName);
						  recaddr.setFlag(0);
						  recaddrService.save(recaddr);
					  }
				  }
				  in.close();
			  }catch(Exception e){
				  e.printStackTrace();
			  }
		  }	
		 HttpSession session = request.getSession();
		 session.setAttribute("sheetId", sheetId);
		 return "redirect:/recaddr/";
	  }
	
	@RequestMapping(value="save",method=RequestMethod.GET)
	 public String save(Model model){
		 return "";
	 }
}
