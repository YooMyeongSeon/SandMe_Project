package com.green.sandme.order;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.green.sandme.order.vo.OrderAddressVo;

@Controller
public class OrderController {
	
	@Autowired
	SqlSession sqlSession;
	
	@RequestMapping("/order")
	public String oder(HttpServletRequest request, Model model) {
		String order = request.getParameter("order");
		
		if (order == null || order.equals("home")) {
			model.addAttribute("order", "home");
		} else {
			model.addAttribute("order", order);
		}
		
		model.addAttribute("chapter", "chapter01");
		return "order";
	}
	
	@PostMapping("/order")
	public String oderChapter(HttpServletRequest request, Model model) {
		String order = request.getParameter("order");
		String chapter = request.getParameter("chapter");

		if (chapter.equals("chapter01")) {
			if (order.equals("home")) {
				String address = request.getParameter("address") + " " + request.getParameter("detailAddress");
				model.addAttribute("address", address);
			}
			
			int shopNum = Integer.parseInt(request.getParameter("shopNum"));
			model.addAttribute("shop", shopNum);
			
			model.addAttribute("chapter", "chapter02");
		} else if (chapter.equals("chapter02")) {
			
			model.addAttribute("chapter", "chapter03");
		} else if (chapter.equals("chapter03")) {
			
			model.addAttribute("chapter", "chapter04");
		}
		
		model.addAttribute("order", order);
		return "order";
	}
	
	@PostMapping("/order/home")
	public void oderHome(@RequestBody String address, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		OrderAddressVo oAv = sqlSession.selectOne("com.green.sandme.order.dao.OrderDao.selectShopByAddress", address);
		
		Gson gson = new Gson();
		
		String data = gson.toJson(oAv);
		PrintWriter out = response.getWriter();
		out.print(data);
	}
	
	@PostMapping("/order/pickUp")
	public void oderPickUp(@RequestBody String address, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		OrderAddressVo oAv = sqlSession.selectOne("com.green.sandme.order.dao.OrderDao.selectShopByName", address);
		
		Gson gson = new Gson();
		
		String data = gson.toJson(oAv);
		PrintWriter out = response.getWriter();
		out.print(data);
	}
}