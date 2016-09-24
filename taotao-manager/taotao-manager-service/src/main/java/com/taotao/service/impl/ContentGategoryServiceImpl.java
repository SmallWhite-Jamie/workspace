package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
import com.taotao.util.TaotaoResult;


@Service
public class ContentGategoryServiceImpl implements ContentCategoryService{
	
	@Autowired
	private TbContentCategoryMapper contentGategoryMapper;
	
	/**
	 * 更具id查询分类列表
	 */
	@Override
	public List<EUTreeNode> getCategorylist(Long parentId) {
		// TODO Auto-generated method stub
		
		TbContentCategoryExample example =new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> lists = contentGategoryMapper.selectByExample(example);
		List<EUTreeNode> treeNodes = new ArrayList<EUTreeNode>();
		for (TbContentCategory list : lists) {
			EUTreeNode node =new EUTreeNode();
			node.setId(list.getId());
			node.setState(list.getIsParent()?"closed":"open");
			node.setText(list.getName());
			treeNodes.add(node);
		}
		return treeNodes;
	}
	/**
	 * 创建新的内容分类
	 */
	@Override
	public TaotaoResult insertContentCategory(Long parentId, String name) {
		//创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		contentCategory.setCreated(new Date());
		contentCategory.setIsParent(false);
		//状态。可选值:1(正常),2(删除)'
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(1);
		contentGategoryMapper.insert(contentCategory);
		//查看父节点的isParent是否为true如果不是就改为true；
		TbContentCategory parent = contentGategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			contentGategoryMapper.updateByPrimaryKey(parent);
		}
		return TaotaoResult.ok(contentCategory);
	}
	
	/**
	 * 删除内容分类
	 */
	@Override
	public TaotaoResult deleteContentCategory(Long parentId, Long id) {
		if(id != null){
			parentId = contentGategoryMapper.selectByPrimaryKey(id).getParentId();
			int delete = contentGategoryMapper.deleteByPrimaryKey(id);
			if(delete!=0){//删除成功
				TbContentCategoryExample example = new TbContentCategoryExample();
				Criteria criteria = example.createCriteria();
				criteria.andParentIdEqualTo(parentId);
				List<TbContentCategory> lists = contentGategoryMapper.selectByExample(example);
				if(lists == null || lists.size() == 0){//如果没有节点的parentId = ..的,需要讲id = parentId的节点的isParent改为false
					TbContentCategory parent = contentGategoryMapper.selectByPrimaryKey(parentId);
					parent.setIsParent(false);
					contentGategoryMapper.updateByPrimaryKey(parent);
				}
			}
		}
		return TaotaoResult.ok();
		
	}
	
	/**
	 * 根据id更新分类信息
	 */
	@Override
	public void updateContentCategory(Long id, String nme) {
		
		if(id != null){
			TbContentCategory record = new TbContentCategory();
			record.setId(id);
			record.setName(nme);
			record.setUpdated(new Date());
			contentGategoryMapper.updateByPrimaryKeySelective(record);
		}
		
	}
	
	
}
