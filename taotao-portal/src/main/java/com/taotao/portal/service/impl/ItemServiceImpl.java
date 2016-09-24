package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.JsonUtils;
import com.taotao.util.TaotaoResult;

/**
 * 商品信息管理service
 * @author  李峥
 * @date    2016年7月13日 下午9:42:14
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	
	@Override
	public ItemInfo getItemById(Long itemId) {
		//调用rest服务
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if(!StringUtils.isBlank(json)){
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);
				if(taotaoResult.getStatus() == 200){
					return (ItemInfo) taotaoResult.getData();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemDescById(Long itemId) {
		
		try {
			String doGet = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			if(!StringUtils.isBlank(doGet)){
				TaotaoResult taotao = TaotaoResult.formatToPojo(doGet, TbItemDesc.class);
				if(taotao.getStatus()==200){
					TbItemDesc itemDesc = (TbItemDesc) taotao.getData();
					String desc = itemDesc.getItemDesc();
					return desc;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getItemParamById(Long itemId) {
		
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			if(json!=null){
				//json转Java
				TaotaoResult taotao = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
				if(taotao.getStatus() == 200){
					TbItemParamItem itemParamItem = (TbItemParamItem) taotao.getData();
					String paramData = itemParamItem.getParamData();
					//转化为Java 集合
					List<Map> list = JsonUtils.jsonToList(paramData, Map.class);
					
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
