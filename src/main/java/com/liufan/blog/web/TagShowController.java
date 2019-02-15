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

import com.liufan.blog.pojo.Tag;
import com.liufan.blog.service.BlogService;
import com.liufan.blog.service.TagService;
import com.liufan.blog.vo.BlogQuery;
/**
 * @author moqi
 * 前端分类展示
 */

@Controller
public class TagShowController {
	
		@Autowired
		private TagService tagService;
		@Autowired
		private BlogService blogService;

		@GetMapping("/tags/{id}")	
		public String tags(@PageableDefault(size=6,sort={"updateTime"},direction=Direction.DESC) Pageable pageable,@PathVariable Long id,Model model){
			List<Tag> tags = tagService.listTagTop(10000);
			if(id==-1){
				id = tags.get(0).getId();
			}
			BlogQuery blogQuery = new BlogQuery();
			model.addAttribute("tags", tags);
			model.addAttribute("page", blogService.listBlog(id, pageable));
			model.addAttribute("activeTagId", id);
			
			return "tags";
		}		
}
