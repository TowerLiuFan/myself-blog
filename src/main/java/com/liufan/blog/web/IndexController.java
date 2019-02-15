package com.liufan.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.liufan.blog.service.BlogService;
import com.liufan.blog.service.TagService;
import com.liufan.blog.service.TypeService;
/**
 * 前端controller
 * @author moqi
 *
 */
@Controller
public class IndexController {
	
		@Autowired
		private BlogService blogService;
		@Autowired
		private TypeService typeService;
		@Autowired
		private TagService tagService;

		@RequestMapping("/")
		public String index(@PageableDefault(size=6,sort={"updateTime"},direction=Direction.DESC) Pageable pageable,Model model){
			model.addAttribute("page", blogService.listBlog(pageable));
			model.addAttribute("types",typeService.listTypeTop(6));
			model.addAttribute("tags",tagService.listTagTop(10));
			model.addAttribute("recommendBlogs",blogService.listRecomendBlogTop(7));
			
			return "index";
		}
		
		@GetMapping("/blog/{id}")
		public String blog(@PathVariable Long id,Model model){
			model.addAttribute("blog", blogService.getAndConvert(id));
			
			return "detail";
		}
		
		@PostMapping("/search")
		public String search(@PageableDefault(size=4,sort={"updateTime"},direction=Direction.DESC) Pageable pageable,
							 @RequestParam String query,Model model){
			model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
			model.addAttribute("query", query);
			return "search";
		}
		
		@GetMapping("/footer/newblog")
		public String newBlogs(Model model){
			model.addAttribute("newBlogs", blogService.listRecomendBlogTop(3));
			return "_fragments :: newBlogList";
		}
		
		@GetMapping("/footer/admin/newblog")
		public String newAdminBlogs(Model model){
			model.addAttribute("newBlogs", blogService.listRecomendBlogTop(3));
			return "admin/_fragments :: newBlogList";
		}
		
		
}
