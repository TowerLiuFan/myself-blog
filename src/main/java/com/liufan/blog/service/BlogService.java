package com.liufan.blog.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.liufan.blog.pojo.Blog;
import com.liufan.blog.vo.BlogQuery;

public interface BlogService {

			Blog getBlog(Long id);
			
			Blog getAndConvert(Long id);
			
			Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
			
			Page<Blog> listBlog(Pageable pageable);
			
			Page<Blog> listBlog(String query,Pageable pageable);
			
			Page<Blog> listBlog(Long tagId,Pageable pageable);
			
			Blog saveBlog(Blog blog);
			
			Blog updateBlog(Long id,Blog blog);
			
			void deleteBlog(Long id);
			
			List<Blog> listRecomendBlogTop(Integer size);
			
			Map<String,List<Blog>> archivesBlog();
			
			Long countBlog();
			
			
}
