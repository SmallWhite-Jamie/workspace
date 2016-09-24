package com.taotao.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemParamItemService;
import com.taotao.util.JsonUtils;

/**
 * 
 * @author lizheng
 *
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService{
	
	@Autowired
	private TbItemParamItemMapper ipiMapper;
	
	@Override
	public String getItemParamItem(Long itemId) {
		TbItemParamItemExample example =new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//查询
		List<TbItemParamItem> result = ipiMapper.selectByExampleWithBLOBs(example);
		if(result == null || result.size() == 0){
			return "";
		}
		//获取数据
		TbItemParamItem paramData = result.get(0);
		//转化为Java 集合
		List<Map> list = JsonUtils.jsonToList(paramData.getParamData(), Map.class);
		
		//生成html
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("		<tbody>\n");
		for (Map map : list) {
			sb.append("			<tr>\n");
			sb.append("				<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
			sb.append("			</tr>\n");
			List<Map> paramsMaps = (List<Map>) map.get("params");
			for (Map map2 : paramsMaps) {
				
				sb.append("			<tr>\n");
				sb.append("				<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
				sb.append("				<td>"+map2.get("v")+"</td>\n");
				sb.append("			</tr>\n");
			}
		}
		sb.append("		</tbody>\n");
		sb.append("	</table>");
		
		return sb.toString();
	}

}
