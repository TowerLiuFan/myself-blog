package com.liufan.blog.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.liufan.blog.pojo.Type;
import com.liufan.blog.service.BlogService;
import com.liufan.blog.service.TypeService;
import com.liufan.blog.vo.BlogQuery;
/**
 * @author moqi
 * 前端分类展示
 */

@Controller
public class TypeShowController {
	
		@Autowired
		private TypeService typeService;
		@Autowired
		private BlogService blogService;

		@GetMapping("/types/{id}")	
		public String types(@PageableDefault(size=6,sort={"updateTime"},direction=Direction.DESC) Pageable pageable,@PathVariable Long id,Model model){
			List<Type> types = typeService.listTypeTop(10000);
			if(id==-1){
				id = types.get(0).getId();
			}
			BlogQuery blogQuery = new BlogQuery();
			blogQuery.setTypeId(id);
			model.addAttribute("types", types);
			model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
			model.addAttribute("activeTypeId", id);
			
			return "types";
		}		
}
