package com.yfsy.gems.web.addr;

import java.util.Map;
import javax.servlet.ServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;
import com.google.common.collect.Maps;
import com.yfsy.gems.entity.AddrSheet;
import com.yfsy.gems.service.addr.AddrSheetService;

/**
 * @author lyf
 *
 */
@Controller
@RequestMapping(value = "/addrsheet")
public class AddrSheetController{
	private static final int PAGE_SIZE = 5;
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "名称");
	}
	
	@Autowired
	private AddrSheetService addrSheetService ;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request){
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<AddrSheet> addrSheets = addrSheetService.getAddrSheets(searchParams, pageNumber, PAGE_SIZE, sortType);
		model.addAttribute("addrSheets", addrSheets);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "/recaddr/addrSheet";
	}
    
	@RequestMapping(value="create",method = RequestMethod.GET)
	public String create(Model model){
		model.addAttribute("addrSheet", new AddrSheet());
		model.addAttribute("action", "create");
		return "/recaddr/addrSheetForm";
	}
	
	@RequestMapping(value="create",method = RequestMethod.POST)
	public String create(@Valid AddrSheet addrSheet, RedirectAttributes redirectAttributes){
	    boolean success = false ;
	    success = addrSheetService.save(addrSheet);
	    if(success){
	    	redirectAttributes.addFlashAttribute("message", "工作簿增加成功");
	    }else{
	    	redirectAttributes.addFlashAttribute("message", "工作簿增加失败");
	    }
		return "redirect:/addrsheet/";
	}
	
	@RequestMapping(value="delete/{id}" ,method = RequestMethod.GET)
	public String delete(@PathVariable("id")Long id,RedirectAttributes redirectAttributes){
		boolean success = false ;
	    success = addrSheetService.deleteAddrSheet(id);
	    if(success){
	    	redirectAttributes.addFlashAttribute("message", "工作簿删除成功");
	    }else{
	    	redirectAttributes.addFlashAttribute("message", "工作簿删除失败");
	    }
		return "redirect:/addrsheet/";
	}
	
	@RequestMapping(value="update/{id}" ,method = RequestMethod.GET)
	public String update(@PathVariable("id")Long id,Model model){
		AddrSheet addrSheet = new AddrSheet();
		addrSheet = addrSheetService.getAddrSheet(id);
	    model.addAttribute("addrSheet", addrSheet);
		model.addAttribute("action", "update");
		return "/recaddr/addrSheetForm";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("preloadUser") AddrSheet addrSheet, RedirectAttributes redirectAttributes) {
		addrSheetService.update(addrSheet);
		redirectAttributes.addFlashAttribute("message", "更新工作簿成功");
		return "redirect:/addrsheet/";
	}
}
