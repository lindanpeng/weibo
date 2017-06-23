package com.alphaking.controller.personal;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alphaking.model.SearchRecord;
import com.alphaking.model.TwitterAt;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.CommentDto;
import com.alphaking.pojo.weibo.TwitterDto;
import com.alphaking.pojo.weibo.UserDto;
import com.alphaking.service.CollectionService;
import com.alphaking.service.CommentService;
import com.alphaking.service.CommonService;
import com.alphaking.service.ForwardService;
import com.alphaking.service.SearchRecodService;
import com.alphaking.service.TwitterAtService;
import com.alphaking.service.TwitterService;
import com.alphaking.service.UserService;
import com.alphaking.util.DataConvertorUtil;
/**
 * 登录用户的与微博动态相关的请求处理器
 * @author lindanpeng
 *
 */
@Controller
@RequestMapping("/personal")
public class PersonalTwitterController {
	@Resource
	private TwitterService twitterService;
	@Resource
	private ForwardService forwardService;
	@Resource
	private TwitterAtService twitterAtService;
	@Resource
	private UserService userService;
	@Resource
	private CollectionService collectionService;
	@Resource
	private CommonService commonService;
    @Resource
    private SearchRecodService searchRecodService;
    @Resource
    private CommentService commentService;
	/**
 * 发表微博动态
 */
	@RequestMapping("/publishTwitter")
	public String publishTwitter(String jsonStr,HttpServletRequest request){
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
		UserDto userDto=commonService.wragUser(userId, userId);
		TwitterDto twitterDto=DataConvertorUtil.json2object(jsonStr, TwitterDto.class);
		twitterService.addTwitterDto(twitterDto);
		request.setAttribute("userDto",userDto);
		return "redirect:home";
	}
/**
 * 删除微博动态
 */
	@RequestMapping("/deleteTwitter")
	@ResponseBody
	public void deleteTwitter(Integer twitterId,HttpServletRequest request){	
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
			twitterService.deleteTwitter(userId,twitterId);
	}
  /**
   * 搜索微博
   */
	@RequestMapping("/searchTwitter")
	public String searchTwitter(String searchKey,Integer pageNum,HttpServletRequest request){
		if (pageNum==null) {
			pageNum=1;
		}
		
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
		UserDto userDto=commonService.wragUser(userId, userId);
		if (searchKey!=null&&!searchKey.equals("")) {
			SearchRecord searchRecord=new SearchRecord();
			searchRecord.setSearchKey(searchKey);
			searchRecord.setUserId(userId);
			searchRecodService.saveSearchRecord(searchRecord);
		}
		SearchCondition searchCondition2=new SearchCondition();
		searchCondition2.addCondition("userId", SearchBean.OPERATOR_EQ, userId, 0);
		List<SearchRecord> searchRecords=searchRecodService.getByCondition(searchCondition2);
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.setPageNum(pageNum);
		searchCondition.addCondition("textContent", SearchBean.OPERATOR_LIKE,searchKey,0);
		searchCondition.addCondition("publicTime", SearchBean.OPERATOR_SORT, "desc", 0);
		PagerResult<TwitterDto> pagerResult=twitterService.getTwittersByCondition(userId,searchCondition); 
	    request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("userDto",userDto);
		request.setAttribute("searchRecords", searchRecords);
		return "personal/searchTwitter";
	}
	/**
	 * 在微博里@用户
	 */
	@RequestMapping("/twitterAtUser")
	@ResponseBody
	public void twitterAtUser(TwitterAt twitterAt){
		twitterAtService.saveTwitterAtUser(twitterAt);
	}
	/**
	 * 获取@我的微博
	 */
	@RequestMapping("/atMeTwitters")
	public String atMeTwitters(Integer pageNum,HttpServletRequest request ){
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
		UserDto userDto=commonService.wragUser(userId,userId);
		if (pageNum==null) {
			pageNum=1;
		}
		
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.setPageNum(pageNum);
		searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ,userId, 0);
		searchCondition.addCondition("twitterAtTime", SearchBean.OPERATOR_SORT, "desc",0);
		PagerResult<TwitterDto> pagerResult=twitterAtService.getTwitterAtList(searchCondition);
		
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("userDto", userDto);	
		return "personal/twitterAtMe";
	}
	/**
	 * 前往登录后的首页，获取全部微博
	 * 
	 * @return
	 */
	@RequestMapping("/home")
	public String home(Integer pageNum, HttpServletRequest request) {
		
		SearchCondition searchCondition = new SearchCondition();
		if (pageNum==null) {
			pageNum=1;
		}
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
		UserDto userDto=commonService.wragUser(userId, userId);
		searchCondition.setPageNum(pageNum);
		searchCondition.addCondition("publicTime",SearchBean.OPERATOR_SORT,"desc",0);
		PagerResult<TwitterDto> pagerResult = twitterService.getTwittersByCondition(userId,searchCondition);
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("userDto", userDto);
		return "personal/home";
	}

	/**
	 * 获取关注人微博列表
	 */
	@RequestMapping("/concernedUserTwitters")
	public String concernedUserTwitters(Integer pageNum,HttpServletRequest request){
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");	
		UserDto userDto=commonService.wragUser(userId, userId);
		if (pageNum==null) {
			pageNum=1;
		}
       PagerResult<TwitterDto> pagerResult=twitterService.getConcernedUserTwittersByCondition(userId, pageNum, 15);
       request.setAttribute("pagerResult", pagerResult);
       request.setAttribute("userDto", userDto);	
       return "personal/concernedUserTwitters";
	}
	/**
	 * 获取收藏微博列表
	 */
	@RequestMapping("/collectedTwitters")
	public String collectedTwitters(Integer pageNum,HttpServletRequest request){
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");	
		UserDto userDto=commonService.wragUser(userId, userId);
		SearchCondition searchCondition=new SearchCondition();
		if (pageNum==null) {
			pageNum=1;
		}
		searchCondition.setPageNum(pageNum);
		searchCondition.addCondition("collectTime",SearchBean.OPERATOR_SORT,"desc", 0);
		searchCondition.addCondition("userId",SearchBean.OPERATOR_EQ,userId,0);
		PagerResult<TwitterDto> pagerResult=collectionService.getCollectionList(searchCondition);
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("userDto", userDto);	
		return "personal/collectedTwitters";
	}
	/**
	 * 获取某用户发布的微博
	 */
	@RequestMapping("/myTwitterList")
	public String getMyTwitterList(Integer pageNum,HttpServletRequest request){
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");	
		UserDto userDto=commonService.wragUser(userId, userId);
		SearchCondition searchCondition=new SearchCondition();
		if (pageNum==null) {
			pageNum=1;
		}
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(10);
		searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ, userId,0);
		searchCondition.addCondition("publicTime",SearchBean.OPERATOR_SORT,"desc", 0);
		PagerResult<TwitterDto> pagerResult=twitterService.getTwittersByCondition(userId, searchCondition);
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("userDto", userDto);	
		return "personal/myTwitterList";
	}
	/**
	 * 获取用户发布的微博
	 */
	@RequestMapping("/hisTwitterList")
	public String getHisTwitterList(Integer userId,Integer pageNum,HttpServletRequest request){
		Integer myUserId=(Integer) request.getSession().getAttribute("loginedId");	
		UserDto userDto=commonService.wragUser(myUserId, myUserId);
		UserDto checkedUser=commonService.wragUser(myUserId, userId);
		SearchCondition searchCondition=new SearchCondition();
		if (pageNum==null) {
			pageNum=1;
		}
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(10);
		searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ, userId,0);
		searchCondition.addCondition("publicTime",SearchBean.OPERATOR_SORT,"desc", 0);
		PagerResult<TwitterDto> pagerResult=twitterService.getTwittersByCondition(userId, searchCondition);
		request.setAttribute("pagerResult", pagerResult);
        request.setAttribute("checkedUser", checkedUser);
		request.setAttribute("userDto", userDto);	
		return "personal/hisTwitterList";
	}
	/**
	 * 查看单个微博
	 */
	@RequestMapping("/singleTwitter")
	public String singleTwitter(Integer twitterId,Integer pageNum,HttpServletRequest request){
		
		Integer myUserId=(Integer) request.getSession().getAttribute("loginedId");	
		UserDto userDto=commonService.wragUser(myUserId, myUserId);
		TwitterDto twitterDto=twitterService.getTwitterById(myUserId, twitterId);
		SearchCondition searchCondition=new SearchCondition();
		if (pageNum==null) {
			pageNum=1;
		}
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(10);
		searchCondition.addCondition("twitterId", SearchBean.OPERATOR_EQ, twitterId,0);
		searchCondition.addCondition("commentTime", SearchBean.OPERATOR_SORT,"desc", 0);
		PagerResult<CommentDto> pagerResult=commentService.getCommentListByCondition(searchCondition);
		request.setAttribute("userDto",userDto);
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("twitterDto", twitterDto);
		return "personal/singleTwitter";
	}
   	
}
