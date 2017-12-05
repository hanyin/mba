package com.hy.mvp.http;


import com.hy.mvp.ui.bean.AddrBooksData;
import com.hy.mvp.ui.bean.BPTypeData;
import com.hy.mvp.ui.bean.BlogPost;
import com.hy.mvp.ui.bean.CityListData;
import com.hy.mvp.ui.bean.FriendData;
import com.hy.mvp.ui.bean.Invitation;
import com.hy.mvp.ui.bean.Invitations;
import com.hy.mvp.ui.bean.LeaguerData;
import com.hy.mvp.ui.bean.LinkFile;
import com.hy.mvp.ui.bean.ParamVersion;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.ui.bean.VerifyAccount;
import com.hy.mvp.ui.bean.YearSalary;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者：hanyin on 2017/8/3 13:21
 * 邮箱：hanyinit@163.com
 */

public interface ApiService {
    /**
     * 登录
     * @param userName
     * @param passWord
     * @return
     */
    @GET("user/login")
    Observable<ResponseResult<LeaguerData>>  login(@Query("login_name") String userName, @Query("login_pwd") String passWord);

    /**
     * 注册
     * @param leaguer
     * @return
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/save")
    Observable<ResponseResult>  registerLeaguer(@Body RequestBody leaguer);

    /**
     * 获取欢迎图片
     * @return
     */
    @GET("wellcome/loadWellComePics")
    Observable<ResponseResult>  getWelcomePics();
    /**
     * 获取所有市
     * @return
     */
    @GET("region/loadAllCitys")
    Observable<ResponseResult<CityListData>> getCityList();
    /**
     * 获取年薪
     * @return
     */
    @GET("yearsalary/loadYearSalary")
    Observable<YearSalary<YearSalary.RespDataBean>> getYearSalary();
    /**
     * 头像上传
     * @return @Multipart
     */
       @Multipart
    @POST("upload/uploadfile")
    Observable<LinkFile<LinkFile.FileBean>> uploadImageToServer(
            @PartMap Map<String,RequestBody> params,@Part("uploadType") RequestBody uploadType,@Part("category") RequestBody category,@Part("fileItem") RequestBody fileItem,@Part("sortIndex") RequestBody sortIndex,@Part("fileName") RequestBody fileName);

    /**
     *系统参数版本信息
     * @return
     */
    @GET("paramversion/loadAll")
    Observable<ResponseResult<ParamVersion>>  getParamVersions();

    /**
     * 获取部落圈(帖子)类型
     * @return
     */
    @GET("blogposttype/loadBlogPostType")
    Observable<ResponseResult<BPTypeData>> getBPTypeList();

    /**
     * 发布帖子
     * @param blogPost
     * @return
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("blogpost/saveBlogPost")
    Observable<ResponseResult<BlogPost>> sendBlog(@Body RequestBody blogPost);
    /**
     * 账号验证
     * @param loginName
     * @return
     */
    @GET("user/isExistedWithLoginName")
    Observable<ResponseResult<VerifyAccount>> verifyAccount(@Query("loginName") String loginName);

    /**
     * 获取我的所有通讯录
     * @param userId
     * @return
     */
    @GET("addrbook/loadMyAddrBook")
    Observable<ResponseResult<AddrBooksData>>   getAddrbook(@Query("userId") String userId);


    /**
     * 添加新的通讯录记录
     * @param ownerId
     * @param contactsId
     * @return
     */
    @GET("addrbook/addMyAddrBook")
    Observable<ResponseResult>   addMyAddrBook(@Query("ownerId") String ownerId,@Query("contactsId") String contactsId);

    /**
     * 查找好友
     * @param query_item
     * @param exist_contactIds
     * @param query_pageNo
     * @return
     */
    @GET("user/searchFriends")
    Observable<ResponseResult<FriendData>>   searchFriends(@Query("query_item") String query_item, @Query("exist_contactIds") String exist_contactIds, @Query("query_pageNo") String query_pageNo);

    /**
     * 邀请好友
     * @param userId
     * @param friendId
     * @param message
     * @return
     */
    @GET("invitation/inviteFriend")
    Observable<ResponseResult<Invitation>>   inviteFriend(@Query("userId") String userId, @Query("friendId") String friendId, @Query("message") String message);

    /**
     * 获取我的所有好友邀请记录
     * @param userId
     * @return
     */
    @GET("invitation/loadMyInvitation")
    Observable<ResponseResult<Invitations>>   loadMyInvitation(@Query("userId") String userId);

    /**
     * 删除好友邀请记录
     * @param id
     * @return
     */
    @GET("invitation/removeInvitation")
    Observable<ResponseResult>   removeInvitation(@Query("id") String id);

    /**
     * 查找我未加入的部落
     * @param query_item
     * @param exist_familyIds
     * @return
     */
    @GET("tribe/findUnJoined")
    Observable<ResponseResult>   findUnJoined(@Query("query_item") String query_item,@Query("exist_familyIds") String exist_familyIds);

    /**
     * 获取我加入的所有部落
     * @param userId
     * @return
     */
    @GET("tribe/findJoinedForSelf")
    Observable<ResponseResult>   findJoinedForSelf(@Query("userId") String userId);

    /**
     * 获取某个部落及其成员的信息
     * @param tribeId
     * @return
     */
    @GET("tribe/findJoinedForSelf")
    Observable<ResponseResult>   findJoinedForSelfs(@Query("tribeId") String tribeId);

    /**
     * 加入部落
     * @param tribeId
     * @param memberId
     * @return
     */
    @GET("tribeMember/addTribeMember")
    Observable<ResponseResult>   addTribeMember(@Query("tribeId") String tribeId,@Query("memberId") String memberId);

    /**
     * 获取部落成员
     * @param tribeId
     * @return
     */
    @GET("tribeMember/findMembersByTribeId")
    Observable<ResponseResult>   findMembersByTribeId(@Query("tribeId") String tribeId);

    /**
     * 退出部落
     * @param tribeId
     * @param memberId
     * @return
     */
    @GET("tribeMember/quitTribe")
    Observable<ResponseResult>   quitTribe(@Query("tribeId") String tribeId,@Query("memberId") String memberId);
    /**
     * 修改好友邀请记录的状态
     * @param id
     * @return
     */
    @GET("invitation/modifyStatusOfInvitation")
    Observable<ResponseResult>   modifyStatusOfInvitation(@Query("id") String id,@Query("status") String status);

    /**
     * 查找家族
     * @param query_item
     * @param exist_familyIds
     * @return
     */
    @GET("family/findFamily")
    Observable<ResponseResult>   findFamily(@Query("query_item") String query_item,@Query("exist_familyIds") String exist_familyIds);

    /**
     * 获取我加入的家族
     * @param userId
     * @return
     */
    @GET("family/findFamily")
    Observable<ResponseResult>   findFamily(@Query("userId") String userId);

    /**
     * 创建新家族
     * @param userId
     * @return
     */
    @GET("family/addFamily")
    Observable<ResponseResult>   addFamily(@Query("userId") String userId);

    /**
     * 申请加入家族
     * @param familyId
     * @param userId
     * @param message
     * @return
     */
    @GET("familyinvitation/applyToJoinFamily")
    Observable<ResponseResult>   applyToJoinFamily(@Query("familyId") String familyId,@Query("userId") String userId,@Query("message") String message);

    /**
     * 查找我邀请（申请加入）的家族
     * @param userId
     * @return
     */
    @GET("familyinvitation/findInvite")
    Observable<ResponseResult>   findInvite(@Query("userId") String userId);

    /**
     * 添加族长发出的成员邀请
     * @param familyId
     * @param memberIds
     * @return
     */
    @GET("familyinvitation/addInvitationsForFounder")
    Observable<ResponseResult>   addInvitationsForFounder(@Query("familyId") String familyId,@Query("memberIds") String memberIds);

    /**
     * 修改族长发出的成员邀请
     * @param id
     * @return
     */
    @GET("family/modifyStatusOfFamilyInvitation")
    Observable<ResponseResult>   modifyStatusOfFamilyInvitation(@Query("id") String id);

    /**
     * 接受来自族长的成员邀请
     * @param id
     * @return
     */
    @GET("family/acceptInvitationOfHounder")
    Observable<ResponseResult>   acceptInvitationOfHounder(@Query("id") String id);

    /**
     * 删除家族邀请
     * @param id
     * @return
     */
    @GET("familyinvitation/removeFamilyInvitation")
    Observable<ResponseResult>   removeFamilyInvitation(@Query("id") String id);

    /**
     * 添加家族成员
     * @param familyId
     * @param memberId
     * @return
     */
    @GET("familyMember/addFamilyMember")
    Observable<ResponseResult>   addFamilyMember(@Query("familyId") String familyId,@Query("memberId") String memberId);

    /**
     * 查找某个家族的所有家族成员
     * @param familyId
     * @return
     */
    @GET("familyMember/findMembersByFamilyId")
    Observable<ResponseResult>   findMembersByFamilyId(@Query("familyId") String familyId);

    /**
     * 退出家族
     * @param familyId
     * @param memberId
     * @return
     */
    @GET("familyMember/quitFamily")
    Observable<ResponseResult>   quitFamily(@Query("familyId") String familyId,@Query("memberId") String memberId);

}
