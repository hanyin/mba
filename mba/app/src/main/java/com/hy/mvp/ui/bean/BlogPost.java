package com.hy.mvp.ui.bean;

import java.util.List;


public class BlogPost {
	
	private String id;				//部落圈帖子ID
	private String publisherId;		//帖子发布人
	private String content;			//帖子内容
	private long typeId = 1;		//帖子类型的Id
	private Long publish_time;		//帖子发布时间
	private int like_count = 0;		//点赞次数
	private int comment_count = 0;	//评论次数
	private int post_status = 1;	//帖子状态  0:未发布;  1：已发布;  2：已暂停发布;   3：草稿 ; -1  已被撤销
	
	//关联信息
	private String realname; //帖子发布人姓名
	private String nickname;
	private String publisher_school;
	private LinkFile portrait;
	private List<LinkFile> blogpost_files;
	private boolean hasOwnedLike; 
	
	private String mycomment_id; 
	private String mycomment_content; 
	private Long mycomment_time; 
	
	
	private String selectedFileIds;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public Long getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(Long publish_time) {
		this.publish_time = publish_time;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public int getPost_status() {
		return post_status;
	}
	public void setPost_status(int post_status) {
		this.post_status = post_status;
	}
	
	//关联信息
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public LinkFile getPortrait() {
		return portrait;
	}
	public void setPortrait(LinkFile portrait) {
		this.portrait = portrait;
	}
	public List<LinkFile> getBlogpost_files() {
		return blogpost_files;
	}
	public void setBlogpost_files(List<LinkFile> blogpost_files) {
		this.blogpost_files = blogpost_files;
	}
	
	public String getPublisher_school() {
		return publisher_school;
	}
	public void setPublisher_school(String publisher_school) {
		this.publisher_school = publisher_school;
	}
	public boolean isHasOwnedLike() {
		return hasOwnedLike;
	}
	public void setHasOwnedLike(boolean hasOwnedLike) {
		this.hasOwnedLike = hasOwnedLike;
	}
	public String getSelectedFileIds() {
		return selectedFileIds;
	}
	public void setSelectedFileIds(String selectedFileIds) {
		this.selectedFileIds = selectedFileIds;
	}
	
	public String getMycomment_id() {
		return mycomment_id;
	}
	public void setMycomment_id(String mycomment_id) {
		this.mycomment_id = mycomment_id;
	}
	public String getMycomment_content() {
		return mycomment_content;
	}
	public void setMycomment_content(String mycomment_content) {
		this.mycomment_content = mycomment_content;
	}
	public Long getMycomment_time() {
		return mycomment_time;
	}
	public void setMycomment_time(Long mycomment_time) {
		this.mycomment_time = mycomment_time;
	} 

	

	

}
