package com.alphaking.service;

import javax.annotation.Resource;

import com.alphaking.dao.CollectionDao;
import com.alphaking.dao.CommentAtDao;
import com.alphaking.dao.CommentDao;
import com.alphaking.dao.ConversationDao;
import com.alphaking.dao.ForwardDao;
import com.alphaking.dao.MessageDao;
import com.alphaking.dao.SearchRecordDao;
import com.alphaking.dao.ThumbsupDao;
import com.alphaking.dao.TwitterAtDao;
import com.alphaking.dao.TwitterDao;
import com.alphaking.dao.TwitterPictureDao;
import com.alphaking.dao.UserDao;
import com.alphaking.dao.UserRelationDao;

public class ImportDaoService {
@Resource
protected UserDao userDao;
@Resource
protected MessageDao messageDao;
@Resource
protected CommentDao commentDao;
@Resource
protected UserRelationDao userRelationDao;
@Resource
protected TwitterDao twitterDao;
@Resource
protected TwitterPictureDao twitterPictureDao;
@Resource
protected ThumbsupDao thumbsupDao;
@Resource
protected CollectionDao collectionDao;
@Resource
protected ForwardDao forwardDao;
@Resource
protected CommentAtDao commentAtDao;
@Resource
protected TwitterAtDao twitterAtDao;
@Resource
protected ConversationDao conversationDao;
@Resource
protected SearchRecordDao searchRecordDao;
}
