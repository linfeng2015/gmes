package com.yfsy.gems.web.addr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/recaddr")
public class RecaddrControl {

	@RequestMapping(method = RequestMethod.GET)
	  public String addr(){
			return "recaddr/addr";
	  }
	
	
}
