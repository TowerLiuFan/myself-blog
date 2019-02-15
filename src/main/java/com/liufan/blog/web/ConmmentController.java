package com.liufan.blog.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.liufan.blog.pojo.Comment;
import com.liufan.blog.pojo.User;
import com.liufan.blog.service.BlogService;
import com.liufan.blog.service.CommentService;

@Controller
public class ConmmentController {

				@Autowired
				private CommentService commentService;
				@Autowired
				private BlogService blogService;
				
				@Value("${comment.avatar}")
				private String avatar;
				
				@GetMapping("/comments/{blogId}")
				public String connmets(@PathVariable Long blogId,Model model){
					model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
					
					return "detail :: commentList";
				}
				
				@PostMapping("/comments")
				public String post(Comment comment,HttpSession session){
					Long blogId = comment.getBlog().getId();
					comment.setBlog(blogService.getBlog(blogId));
					User user = (User) session.getAttribute("user");
					if(user!=null){
						comment.setAvatar(user.getAvatar());
						comment.setAdminComment(true);
					}else{
						comment.setAvatar(avatar);
					}
					
					commentService.saveComment(comment);
					return "redirect:/comments/"+ blogId ;
				}
}
