package com.alphaking.controller.personal;

import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alphaking.model.Collection;
import com.alphaking.service.CollectionService;
/**
 * 登录用户收藏请求处理器
 * @author lindanpeng
 *
 */
@Controller
@RequestMapping("/personal")
public class PersonalCollectionController {
	@Resource
	private CollectionService collectionService;

	   /**
	    * 收藏
	    */
			@RequestMapping("/collectTwitter")
			@ResponseBody
			public void collectTwitter(Integer twitterId,HttpSession session){
		    	Integer userId=(Integer) session.getAttribute("loginedId");
				Collection collection=new Collection();
				collection.setCollectedTwitterId(twitterId);
				collection.setCollectTime(new Timestamp(System.currentTimeMillis()));
				collection.setUserId(userId);
				collectionService.addCollection(collection);
				
			}
		/**
		 * 取消收藏
		 */
		  @RequestMapping("/cancelCollect")
		  @ResponseBody
		  public void cancelCollect(Integer twitterId,HttpSession session){
		    	Integer userId=(Integer) session.getAttribute("loginedId");
			   collectionService.removeCollection(userId, twitterId);
		  }
}
