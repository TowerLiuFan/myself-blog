package com.liufan.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.liufan.blog.pojo.Type;

public interface TypeService {

	  Type saveType(Type type);
	  
	  Type getType(Long id);
	  
	  Type getTypeByName(String name);
	  
	  Page<Type> listType(Pageable pageable);
	  
	  List<Type> listTypeTop(Integer size);
	  
	  List<Type> listAll();
	  
	  Type updateType(Long id,Type type);
	  
	  void deleteType(Long id);
	  
	  
}
