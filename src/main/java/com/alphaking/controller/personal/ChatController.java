package com.alphaking.controller.personal;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alphaking.constant.MessageConstant;
import com.alphaking.model.Message;
import com.alphaking.model.User;
import com.alphaking.pojo.weibo.ConversationDto;
import com.alphaking.pojo.weibo.ParsedText;
import com.alphaking.pojo.weibo.UserDto;
import com.alphaking.service.CommonService;
import com.alphaking.service.ConversationService;
import com.alphaking.service.MessageService;
import com.alphaking.service.UserRelationService;
import com.alphaking.util.HtmlTextUtil;
/**
 * 登录用户的聊天处理器
 * @author lindanpeng
 *
 */
@Controller
@RequestMapping("/personal")
public class ChatController {
@Resource
private MessageService messageService;
@Resource
private ConversationService conversationService;
@Resource
private UserRelationService userRelationService;
@Resource
private CommonService commonService;
    /**
     * 访问聊天页面
     */
    @RequestMapping("/chat")
    public String chat(HttpServletRequest request){

    	Integer userId=(Integer) request.getSession().getAttribute("loginedId");

    	List<ConversationDto> conversationDtos=conversationService.getConversations(userId);
    	UserDto userDto=commonService.wragUser(null,userId);
    	request.setAttribute("userDto", userDto);
    	request.setAttribute("conversationDtos", conversationDtos);

    	return "personal/chat";
    }
    /**
     * 删除对话
     */
    @RequestMapping("/deleteConversation")
    @ResponseBody
    public void deleteConversation(Integer conversationId){
    	conversationService.deleteConversation(conversationId);
    }
    /**
     * 新建对话
     */
    @RequestMapping("/addConversation")
    @ResponseBody
    public void addConversation(Integer userId,Integer friendUserId){
       conversationService.addConversation(userId, friendUserId);
    }
	/**
	 * 发送消息
	 */
	@RequestMapping("/sendMessage")
	@ResponseBody
	public void sendMessage(Message message,HttpSession session) {
		Integer userId=(Integer)session.getAttribute("loginedId");
        message.setFromUserId(userId);
        message.setSendTime(new Timestamp(System.currentTimeMillis()));
        message.setIsRead(MessageConstant.UNKOWN);
        ParsedText parsedText=HtmlTextUtil.parseText(message.getContent());        
        message.setContent(parsedText.getParsedContent());
		messageService.saveMessage(message);
	}
    /**
     * 更新对话
     */
	@RequestMapping("/refreshConversation")
	@ResponseBody
	public List<ConversationDto> refleshConversation(Integer chatingFriendId,HttpSession session){
		Integer userId=(Integer)session.getAttribute("loginedId");
		List<ConversationDto> newConversationDtos=conversationService.newConversations(userId);
		messageService.setUnReadToRead(userId,chatingFriendId);
		return newConversationDtos;
	}

	/**
	 * 获取与某个朋友的所有消息
	 */
	@RequestMapping("/getMessages")
	@ResponseBody
	public List<Message> getMessages(Integer friendUserId, HttpSession session) {
		Integer userId=(Integer) session.getAttribute("loginedId");
		List<Message> messages = messageService.getMessagesOfFriend(userId, friendUserId);
		messageService.hasReadMessages(userId, friendUserId);
		return messages;
	}
	/**
	 * 搜索好友
	 */
	@RequestMapping("/searchFriends")
	@ResponseBody
	public List<User> searchFriends(String nickname,HttpSession session){
		Integer userId=(Integer) session.getAttribute("loginedId");
		List<User> friends=userRelationService.getFriends(userId, nickname);
		return friends;
	}
}
