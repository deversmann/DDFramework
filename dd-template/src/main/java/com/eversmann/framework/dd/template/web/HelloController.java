package com.eversmann.framework.dd.template.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.eversmann.framework.dd.template.domain.Hello;
import com.eversmann.framework.dd.template.service.HelloService;
import com.eversmann.framework.dd.template.web.form.validator.HelloFormValidator;

@Controller("helloController")
@RequestMapping("/hello")
@SessionAttributes({"helloNew","helloEdit"})
public class HelloController {
	private static Logger LOG = Logger.getLogger(HelloController.class); 
	
	@Autowired
	private HelloService helloService;
	
	@Autowired
	private HelloFormValidator validator;
	
	@RequestMapping("/world.htm")
	public ModelAndView helloWorld() {
		return new ModelAndView("helloWorld");
	}
	
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam(required=false, defaultValue="") String name) {
		ModelAndView mav = new ModelAndView("helloList");
		List<Hello> hellos = helloService.findHellosByName(name.trim());
		mav.addObject("helloList", hellos);
		return mav;
	}
	
	@RequestMapping("/list")
	public ModelAndView list() {
		LOG.debug("Calling list");
		ModelAndView mav = new ModelAndView("helloList");
		List<Hello> hellos = helloService.getHellos();
		if (LOG.isDebugEnabled()) {
			LOG.debug("Retrieved " + hellos.size() + " Hello entities from persistence");
		}
		mav.addObject("helloList", hellos);
		return mav;
	}
	
	@RequestMapping("/new")
	public ModelAndView newHelloForm() {
		ModelAndView mav = new ModelAndView("helloEdit");
		Hello hello = new Hello();
		mav.addObject("helloNew", hello);
		return mav;
	}
	
	@RequestMapping("/save")
	public String create(@ModelAttribute("helloNew") Hello hello, BindingResult result, SessionStatus status) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Saving new Hello: " + hello);
		}
		validator.validate(hello, result);
		if (result.hasErrors()) {
			return "helloEdit";
		}
		helloService.createHello(hello);
		status.setComplete();
		return "redirect:list";
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("helloEdit");
		Hello hello = helloService.getHello(id);
		mav.addObject("helloEdit", hello);
		return mav;
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute("helloEdit") Hello hello, BindingResult result, SessionStatus status) {
		validator.validate(hello, result);
		if (result.hasErrors()) {
			return "helloEdit";
		}
		helloService.updateHello(hello);
		status.setComplete();
		return "redirect:list";
	}
	
        
    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam("id") Long id) {
        ModelAndView mav = new ModelAndView("redirect:list");
        helloService.deleteHello(id);
        return mav;
    }    

}
