package com.liufan.blog.service;

import java.util.List;

import com.liufan.blog.pojo.Comment;

public interface CommentService {

	
			List<Comment> listCommentByBlogId(Long blogId);
			
			Comment  saveComment(Comment comment);
}
