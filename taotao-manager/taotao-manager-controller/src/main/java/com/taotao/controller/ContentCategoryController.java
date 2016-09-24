package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ContentCategoryService;
import com.taotao.util.TaotaoResult;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService categoryService;

	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContenCategoryList(
			@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		
		List<EUTreeNode> categorylist = categoryService.getCategorylist(parentId);
		
		return categorylist;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult insertContentCategory(Long parentId, String name){
		
		TaotaoResult restlt = categoryService.insertContentCategory(parentId, name);
		
		return restlt;
		
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long parentId, Long id){
		
		TaotaoResult restlt = categoryService.deleteContentCategory(parentId, id);
		
		return restlt;
		
	}
	
	@RequestMapping("/update")
	public void updateContentCategory(Long id, String name){
		categoryService.updateContentCategory(id, name);
	}
}
