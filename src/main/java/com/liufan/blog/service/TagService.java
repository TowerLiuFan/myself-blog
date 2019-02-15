package com.liufan.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.liufan.blog.pojo.Tag;

public interface TagService {

		Tag saveTag(Tag Tag);
		  
		Tag getTag(Long id);
		  
		Tag getTagByName(String name);
	  
	    Page<Tag> listTag(Pageable pageable);
	    
	    List<Tag> listTagTop(Integer size);
	    
	    List<Tag> listAll();
	    
	    List<Tag> listTags(String ids);
	  
	    Tag updateTag(Long id,Tag tag);
	  
	    void deleteTag(Long id);
	  
	  
}
