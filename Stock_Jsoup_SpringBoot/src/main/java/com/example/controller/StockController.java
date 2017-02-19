package com.example.controller;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.models.Stock_infoBean;
import com.example.service.Parse_HTML_Service;
import com.example.service.Stock_Service;

@Controller
public class StockController {
	 @Value("${numException}")
	 private String numException;
	 @Value("${noResult}")
	 private String noResult;
	 @Value("${recordCount}")
	 private String recordCount;
	
	@Autowired
	Stock_Service stockService;
	@Autowired
	Parse_HTML_Service pHService;

	@RequestMapping("/")
	public String helloPage(Map<String, Object> model, HttpSession httpSession) {
		httpSession.setAttribute("recordCount", recordCount + stockService.recordCount());
		return "welcome";
	}

	@RequestMapping(value = "/UserUpdate", method = RequestMethod.POST)
	public String userUpdate(@RequestParam(value = "stockID", required = true) String stockID, Model model,
			HttpSession httpSession) {
		// System.out.println(stockID);
		try {
			int stockID_Int = Integer.valueOf(stockID);
			if (stockService.checkExist(stockID_Int)) {
				model.addAttribute("stockInfo", stockService.getStock(stockID_Int));
				return "info";
			} 
			Stock_infoBean info = pHService.getStockInfo(stockID_Int);
			if (info != null) {
				model.addAttribute("stockInfo", stockService.addStock(info));
			}else{
				model.addAttribute("noResult", noResult);
				return "welcome";
			}
		} catch (NumberFormatException n) {
			model.addAttribute("noResult", numException);
			return "welcome";
		}
		return "info";
	}
	
	@RequestMapping(value = "/UserSubmit", method = RequestMethod.POST)
	public String userSubmit(@RequestParam(value = "stockID", required = true) String stockID, Model model,
			HttpSession httpSession) {
		// System.out.println(stockID);
		try {
			int stockID_Int = Integer.valueOf(stockID);
			if (stockService.checkExist(stockID_Int)) {
				model.addAttribute("stockInfo", stockService.getStock(stockID_Int));
				return "info";
			} 
			Stock_infoBean info = pHService.getStockInfo(stockID_Int);
			if (info != null) {
				model.addAttribute("stockInfo", stockService.addStock(info));
			}else{
				model.addAttribute("noResult", noResult);
				return "welcome";
			}
		} catch (NumberFormatException n) {
			model.addAttribute("noResult", numException);
			return "welcome";
		}
		return "info";
	}

	@RequestMapping("/getImageMonth/{id}")
	public void getMonthImage(@PathVariable("id") String stockID, HttpServletResponse response) {
		try {
			int stockID_Int = Integer.valueOf(stockID);
			byte[] image = stockService.getStock(stockID_Int).getMon_annu_graph();
			try (OutputStream os = response.getOutputStream();) {
				os.write(image);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getImageQuart/{id}")
	public void getQuartImage(@PathVariable("id") String stockID, HttpServletResponse response) {
		try {
			int stockID_Int = Integer.valueOf(stockID);
			byte[] image = stockService.getStock(stockID_Int).getQuart_annu_graph();
			try (OutputStream os = response.getOutputStream();) {
				os.write(image);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
