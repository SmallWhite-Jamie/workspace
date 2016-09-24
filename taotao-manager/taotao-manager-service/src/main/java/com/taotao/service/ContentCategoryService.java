package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.util.TaotaoResult;

public interface ContentCategoryService {
	List<EUTreeNode> getCategorylist(Long parentId);
	TaotaoResult insertContentCategory(Long parentId, String name);
	TaotaoResult deleteContentCategory(Long parentId, Long id);
	void updateContentCategory(Long id, String nme);
}
