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

import com.liufan.blog.pojo.Type;
import com.liufan.blog.service.TypeService;

@Controller
@RequestMapping("/admin")
public class TypeController {

		@Autowired
		private TypeService typeService;
		
		@GetMapping("/types")
		public String list(@PageableDefault(size=8,sort={"id"},direction=Direction.DESC) Pageable pageable,Model model){
			Page<Type> listType = typeService.listType(pageable);
			model.addAttribute("page", listType);
			return "admin/types";
		}
		
		@GetMapping("/types/input")
		public String toAddType(Model model){
			model.addAttribute("type", new Type());
			return "admin/types-input";
		}
		
		@GetMapping("types/{id}/input")
		public String toEditType(@PathVariable Long id,Model model){
			Type type = typeService.getType(id);
			model.addAttribute("type", type);
			return "admin/types-input";
			
		}
		
		@PostMapping("/types/addSubmit")
		public String saveType(@Valid Type type,BindingResult result,RedirectAttributes attributes){
			Type repeatType = typeService.getTypeByName(type.getName());
			if(repeatType != null){
				result.rejectValue("name", "nameError","不能重复添加分类");
			}
			if(result.hasErrors()){
				return "admin/types-input";
			}
			Type t = typeService.saveType(type);
			if(t == null){
				attributes.addFlashAttribute("message", "新增失败");
			}else{
				attributes.addFlashAttribute("message", "新增成功");
			}
			return "redirect:/admin/types";
			
		}
		
		@PostMapping("/types/addSubmit/{id}")
		public String edipType(@Valid Type type,BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
			Type repeatType = typeService.getTypeByName(type.getName());
			if(repeatType != null){
				result.rejectValue("name", "nameError","不能重复添加分类");
			}
			if(result.hasErrors()){
				return "admin/types-input";
			}
			Type t = typeService.updateType(id,type);
			if(t == null){
				attributes.addFlashAttribute("message", "修改失败");
			}else{
				attributes.addFlashAttribute("message", "修改成功");
			}
			return "redirect:/admin/types";
		}
		
		@GetMapping("/types/{id}/delete")
		public String deleteType(@PathVariable Long id,RedirectAttributes attributes){
			
			typeService.deleteType(id);
			attributes.addFlashAttribute("message", "删除成功");
			return "redirect:/admin/types";
		}
			
}
