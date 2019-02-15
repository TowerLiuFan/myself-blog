package com.liufan.blog.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.liufan.blog.pojo.Tag;
import com.liufan.blog.service.TagService;

@Controller
@RequestMapping("/admin")
public class TagController {

		@Autowired
		private TagService tagService;
		
		@GetMapping("/tags")
		public String list(@PageableDefault(size=8,sort={"id"},direction=Direction.DESC) Pageable pageable,Model model){
			Page<Tag> listTag = tagService.listTag(pageable);
			model.addAttribute("page", listTag);
			return "admin/tags";
		}
		
		@GetMapping("/tags/input")
		public String toAddTag(Model model){
			model.addAttribute("tag", new Tag());
			return "admin/tags-input";
		}
		
		@GetMapping("tags/{id}/input")
		public String toEditTag(@PathVariable Long id,Model model){
			Tag tag = tagService.getTag(id);
			model.addAttribute("tag", tag);
			return "admin/tags-input";
			
		}
		
		@PostMapping("/tags/addSubmit")
		public String saveTag(@Valid Tag tag,BindingResult result,RedirectAttributes attributes){
			Tag repeatTag = tagService.getTagByName(tag.getName());
			if(repeatTag != null){
				result.rejectValue("name", "nameError","不能重复添加标签");
			}
			if(result.hasErrors()){
				return "admin/tags-input";
			}
			Tag t = tagService.saveTag(tag);
			if(t == null){
				attributes.addFlashAttribute("message", "新增失败");
			}else{
				attributes.addFlashAttribute("message", "新增成功");
			}
			return "redirect:/admin/tags";
			
		}
		
		@PostMapping("/tags/addSubmit/{id}")
		public String edipTag(@Valid Tag tag,BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
			Tag repeatTag = tagService.getTagByName(tag.getName());
			if(repeatTag != null){
				result.rejectValue("name", "nameError","不能重复添加标签");
			}
			if(result.hasErrors()){
				return "admin/tags-input";
			}
			Tag t = tagService.updateTag(id,tag);
			if(t == null){
				attributes.addFlashAttribute("message", "修改失败");
			}else{
				attributes.addFlashAttribute("message", "修改成功");
			}
			return "redirect:/admin/tags";
		}
		
		@GetMapping("/tags/{id}/delete")
		public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
			
			tagService.deleteTag(id);
			attributes.addFlashAttribute("message", "删除成功");
			return "redirect:/admin/tags";
		}
			
}
