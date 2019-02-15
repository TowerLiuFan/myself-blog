package com.liufan.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liufan.blog.dao.TagRepository;
import com.liufan.blog.pojo.Tag;
import com.liufan.blog.web.NotFoundException;

@Service
public class TagServiceImpl implements TagService{

	@Autowired
	private TagRepository tagRepository ;
	
	@Transactional
	@Override
	public Tag saveTag(Tag tag) {
		
		return tagRepository.save(tag);
	}

	@Override
	public Tag getTag(Long id) {
		
		return tagRepository.getOne(id);
	}

	@Override
	public Tag getTagByName(String name) {
	 
		return tagRepository.findByName(name);
	}

	@Override
	public Page<Tag> listTag(Pageable pageable) {
		 
		return tagRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Tag updateTag(Long id, Tag tag) {
		Tag t = tagRepository.getOne(id); 
		if(t==null){
			throw new NotFoundException("不存在该类型");
		}
		BeanUtils.copyProperties(tag, t);
		return tagRepository.save(t);
	}

	@Transactional
	@Override
	public void deleteTag(Long id) {
		  
		tagRepository.deleteById(id);
	}

	@Override
	public List<Tag> listAll() {
		List<Tag> tagList = tagRepository.findAll();
		return tagList;
	}

	@Override
	public List<Tag> listTags(String ids) {
		List<Tag> listTags = tagRepository.findAllById(convertToList(ids));
		return listTags;
	}

	private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

	@Override
	public List<Tag> listTagTop(Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
		Pageable pageable = PageRequest.of(0, size, sort);
		return tagRepository.findTop(pageable);
	}
}
