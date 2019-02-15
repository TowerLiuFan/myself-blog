package com.liufan.blog.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.liufan.blog.pojo.Blog;
import com.liufan.blog.pojo.Tag;
import com.liufan.blog.pojo.Type;
import com.liufan.blog.pojo.User;
import com.liufan.blog.service.BlogService;
import com.liufan.blog.service.TagService;
import com.liufan.blog.service.TypeService;
import com.liufan.blog.vo.BlogQuery;

@Controller
@RequestMapping("/admin")
public class BlogController {
	
			private static final String INPUT = "admin/blog-input";
			private static final String LIST = "admin/blog-manager";
			private static final String REDIRECT_LIST = "redirect:/admin/blogs";

			@Autowired
			private BlogService blogService;
			@Autowired
			private TypeService typeService;
			@Autowired
			private TagService  tagService;
			
	
			@GetMapping("/blogs")
			public String blogPage(@PageableDefault(size=6,sort={"updateTime"},direction=Direction.DESC) Pageable pageable,BlogQuery blog, Model model){
				Page<Blog> listBlog = blogService.listBlog(pageable, blog);
				List<Type> listAll = typeService.listAll();
				model.addAttribute("page", listBlog);
				model.addAttribute("types", listAll);
				return LIST;
			}
			
			@PostMapping("/blogs/search")
			public String blogSearch(@PageableDefault(size=5,sort={"updateTime"},direction=Direction.DESC) Pageable pageable,BlogQuery blog, Model model){
				Page<Blog> listBlog = blogService.listBlog(pageable, blog);
				model.addAttribute("page", listBlog);
				return "admin/blog-manager :: blogList";
			}
			
			@GetMapping("/blogs/input")
			public String toAddBlogs(Model model){
				setTypeAndTag(model);
				model.addAttribute("blog", new Blog());
				
				return INPUT;
			}
			
			public void setTypeAndTag(Model model){
				List<Type> listAll = typeService.listAll();
				List<Tag> listAll2 = tagService.listAll();
				model.addAttribute("types", listAll);
				model.addAttribute("tags", listAll2);
			}
			
			@GetMapping("/blogs/{id}/input")
			public String toEditBlogs(@PathVariable Long id,Model model){
				setTypeAndTag(model);
				Blog blog = blogService.getBlog(id);
				blog.init();
				model.addAttribute("blog",blog);
				return INPUT;
			}
			
			
			
			@PostMapping("/blogs/submit")
			public String addOrEditForSubmitBlogs(Blog blog,HttpSession session,RedirectAttributes attributes){
				blog.setUser((User)session.getAttribute("user"));
				blog.setType(typeService.getType(blog.getType().getId()));
				blog.setTags(tagService.listTags(blog.getTagIds()));
				Blog b ;
				if (blog.getId() == null) {
		            b =  blogService.saveBlog(blog);
		        } else {
		            b = blogService.updateBlog(blog.getId(), blog);
		        }
				if(b == null){
					attributes.addFlashAttribute("message", "操作失败");
				}else{
					attributes.addFlashAttribute("message", "操作成功");
				}
				return REDIRECT_LIST;
			}
			
			@GetMapping("/blogs/{id}/delete")
			public String deleteBlog(@PathVariable Long id,RedirectAttributes attributes){
				blogService.deleteBlog(id);
				attributes.addFlashAttribute("message", "删除成功");
				return REDIRECT_LIST; 
			}
			
			
			
			
}
