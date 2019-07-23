package com.d2956987215.mow.rxjava.xapi;

/*
 * Created by fizz on 2017/8/9.
 */


import com.d2956987215.mow.bean.AreaBean;
import com.d2956987215.mow.bean.CheckMobileBean;
import com.d2956987215.mow.bean.LinkBean;
import com.d2956987215.mow.rxjava.response.AccuntListResponse;
import com.d2956987215.mow.rxjava.response.AddVipResponse;

import com.d2956987215.mow.rxjava.response.ArticleResponse;
import com.d2956987215.mow.rxjava.response.AuthTaoBaoResponse;
import com.d2956987215.mow.rxjava.response.BannerResponse;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.BrandListResponse;
import com.d2956987215.mow.rxjava.response.BrandResponse;
import com.d2956987215.mow.rxjava.response.CopyContentResponse;
import com.d2956987215.mow.rxjava.response.CopyResponse;
import com.d2956987215.mow.rxjava.response.CurrentStatusResponse;
import com.d2956987215.mow.rxjava.response.DefaultLocationResponse;
import com.d2956987215.mow.rxjava.response.ErJiListResponse;
import com.d2956987215.mow.rxjava.response.FactoryProListResponse;
import com.d2956987215.mow.rxjava.response.FenLeiListResponse;
import com.d2956987215.mow.rxjava.response.FourResponse;
import com.d2956987215.mow.rxjava.response.GetCouponResponse;
import com.d2956987215.mow.rxjava.response.GetRedPacket;
import com.d2956987215.mow.rxjava.response.GoodDetailResponse;
import com.d2956987215.mow.rxjava.response.GuiDetailResponse;
import com.d2956987215.mow.rxjava.response.GuiListResponse;
import com.d2956987215.mow.rxjava.response.GuideResponse;
import com.d2956987215.mow.rxjava.response.HaiBaoResponse;
import com.d2956987215.mow.rxjava.response.HomeAds;
import com.d2956987215.mow.rxjava.response.HomeAdsResponse;
import com.d2956987215.mow.rxjava.response.HomeDataResponse;
import com.d2956987215.mow.rxjava.response.HomeHotResponse;
import com.d2956987215.mow.rxjava.response.HomeListResponse;
import com.d2956987215.mow.rxjava.response.HotKetResponse;
import com.d2956987215.mow.rxjava.response.JuHuaSuanResponse;
import com.d2956987215.mow.rxjava.response.LanMuListResponse;
import com.d2956987215.mow.rxjava.response.LoginResponse;
import com.d2956987215.mow.rxjava.response.LunTanSchoolCateResponse;
import com.d2956987215.mow.rxjava.response.LunTanTitleResponse;
import com.d2956987215.mow.rxjava.response.MemberUpgradeResponse;
import com.d2956987215.mow.rxjava.response.MessageTypeResponse;
import com.d2956987215.mow.rxjava.response.MonitorResponse;
import com.d2956987215.mow.rxjava.response.MsgActivityResponse;
import com.d2956987215.mow.rxjava.response.MsgEverydayResponse;
import com.d2956987215.mow.rxjava.response.MsgMoneyListResponse;
import com.d2956987215.mow.rxjava.response.MsgSystemResponse;
import com.d2956987215.mow.rxjava.response.MyFavariteResponse;
import com.d2956987215.mow.rxjava.response.MyOrderList;
import com.d2956987215.mow.rxjava.response.MyShouYiResponse;
import com.d2956987215.mow.rxjava.response.MyTeamResponse;
import com.d2956987215.mow.rxjava.response.MyZuJiResponse;
import com.d2956987215.mow.rxjava.response.PersonInfoResponse;
import com.d2956987215.mow.rxjava.response.PictureResponse;
import com.d2956987215.mow.rxjava.response.ProductListResponse;
import com.d2956987215.mow.rxjava.response.QiagGouListResponse;
import com.d2956987215.mow.rxjava.response.RegisterResponse;
import com.d2956987215.mow.rxjava.response.RulesResponse;
import com.d2956987215.mow.rxjava.response.SaoMaResponse;
import com.d2956987215.mow.rxjava.response.SchoolListResponse;
import com.d2956987215.mow.rxjava.response.SelfSupportResponse;
import com.d2956987215.mow.rxjava.response.ShareResponse;
import com.d2956987215.mow.rxjava.response.ShopAllCouponResponse;
import com.d2956987215.mow.rxjava.response.ShopInfoResponse;
import com.d2956987215.mow.rxjava.response.ShopResponse;
import com.d2956987215.mow.rxjava.response.ShouTitleResponse;
import com.d2956987215.mow.rxjava.response.ShouYeErjiResponse;
import com.d2956987215.mow.rxjava.response.SiTuListResponse;
import com.d2956987215.mow.rxjava.response.TimeListResponse;
import com.d2956987215.mow.rxjava.response.TklCopyResponse;
import com.d2956987215.mow.rxjava.response.UpdateIdResponse;
import com.d2956987215.mow.rxjava.response.UpdateResponse;
import com.d2956987215.mow.rxjava.response.UploadFileResponse;
import com.d2956987215.mow.rxjava.response.VipDetailResponse;
import com.d2956987215.mow.rxjava.response.VipSubmitResponse;
import com.d2956987215.mow.rxjava.response.VipUpdateResponse;
import com.d2956987215.mow.rxjava.response.XiTonfListResponse;
import com.d2956987215.mow.rxjava.response.XiaoYanResponse;
import com.d2956987215.mow.rxjava.response.XinShouResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface XApi {
    //登录
    @FormUrlEncoded
    @POST("v3/Login")
    Observable<LoginResponse> login(@FieldMap Map<String, String> map);


    //手机号验证码登陆
    @FormUrlEncoded
    @POST("v3/CodeLogin")
    Observable<LoginResponse> loginphone(@FieldMap Map<String, String> map);

    //注册
    @POST("v3/Register")
    @FormUrlEncoded
    Observable<RegisterResponse> register(@FieldMap Map<String, String> map);

    //手机验证邀请码注册
    @POST("v3/RegVerifyCode")
    @FormUrlEncoded
    Observable<BaseResponse> yanzhengzhuce(@FieldMap Map<String, String> map);

    //验证邀请码注册
    @POST("v3/CheckLcode")
    @FormUrlEncoded
    Observable<SaoMaResponse> saoma(@FieldMap Map<String, String> map);


    //获取验证码
    @POST("v3/LoginVerifyCode")
    @FormUrlEncoded
    Observable<BaseResponse> getcode(@FieldMap Map<String, String> map);


    //修改信息获取验证码
    @POST("v3/GetVerifyCode")
    @FormUrlEncoded
    Observable<BaseResponse> xiugaicode(@FieldMap Map<String, String> map, @Header("authorization") String lang);


    //找回密码验证码
    @POST("index.php?m=Api&c=Users&a=getPhoneVerify")
    @FormUrlEncoded
    Observable<BaseResponse> findcode(@FieldMap Map<String, String> map);


    //获取首页头数据
    @Headers({"Connection:close"})
    @POST("v3/CateAndBanner")
    @FormUrlEncoded
    Observable<ShouTitleResponse> gettitlelist(@FieldMap Map<String, String> map);

    //根据剪贴板获取数据
    @POST("v3/GetTkl")
    @FormUrlEncoded
    Observable<LinkBean> jiantie(@FieldMap Map<String, String> map);


    //获取首页广告

    @POST("v3/HomeAds")
    @FormUrlEncoded
    Observable<HomeAdsResponse> homeAds(@FieldMap Map<String, String> map,@Header("Connection") String close);

    //新手指南
    @POST("v3/HelpCate")
    Observable<XinShouResponse> getzhinanlist();


    //v3/HomeMenu
    @POST("v3/HomeMenu")
    Observable<FourResponse> getfour();

    //获取分类数据
    @POST("v3/GoodCate")
    Observable<FenLeiListResponse> getfeileilist();

    //获取论坛头部list
    @POST("v3/DayHotCate")
    Observable<LunTanTitleResponse> getluntantitlelist();

    //获取素才头部list
    @POST("v3/AdResouseCate")
    Observable<LunTanTitleResponse> getsucaititlelist();


    //获取广告位
    @POST("v3/StartImages")
    @FormUrlEncoded
    Observable<GuideResponse> getguide(@FieldMap Map<String, Object> map);


    //获取首页liebiao
    @POST("v3/HomeGoodList")
    @FormUrlEncoded
    Observable<HomeListResponse> getshouyelist(@FieldMap Map<String, String> map);

    //v3/HomeMenus
    @POST("v3/HomeMenus")
    @FormUrlEncoded
    Observable<SiTuListResponse> getsitulist(@FieldMap Map<String, String> map);

    //
    //获取二级列表
    @POST("v3/search")
    @FormUrlEncoded
    Observable<ErJiListResponse> erjidetail(@FieldMap Map<String, String> map);

    //自营
    @POST("v3/SelfSupport")
    @FormUrlEncoded
    Observable<SelfSupportResponse> selfSupport(@FieldMap Map<String, String> map, @Header("authorization") String token);

    //
    //店鋪所有优惠券
    @POST("v3/ShopGoodCoupon")
    @FormUrlEncoded
    Observable<ShopAllCouponResponse> shopAllCoupon(@FieldMap Map<String, String> map, @Header("authorization") String token);

    //热词搜索
    @POST("v3/HotSearchWord")
    @FormUrlEncoded
    Observable<HotKetResponse> hotKeySearch(@FieldMap Map<String, String> map);


    @POST("v3/LimitTimeBuy")
    @FormUrlEncoded
    Observable<QiagGouListResponse> qianggoulist(@FieldMap Map<String, String> map);

    @POST("v3/LimitTimeCate")
    @FormUrlEncoded
    Observable<TimeListResponse> timelist(@FieldMap Map<String, String> map);

    //获取新手指导
    @POST("v3/ArticleList")
    @FormUrlEncoded
    Observable<GuiListResponse> xinshouzhidao(@FieldMap Map<String, String> map);

    //
    @POST("v3/DetailArticle")
    @FormUrlEncoded
    Observable<GuiDetailResponse> xinshoudetail(@FieldMap Map<String, String> map);

    //获取二级列表
    @POST("v3/NextCate")
    @FormUrlEncoded
    Observable<ShouYeErjiResponse> geterjilist(@FieldMap Map<String, String> map);

    //
    @POST("v3/TbLogin")
    @FormUrlEncoded
    Observable<LoginResponse> taobaologin(@FieldMap Map<String, String> map);

    //v3/BindTb
    @POST("v3/BindTb")
    @FormUrlEncoded
    Observable<LoginResponse> taobaobangding(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //获取每日爆款
    @POST("v3/DayHot")
    @FormUrlEncoded
    Observable<LanMuListResponse> getdayhot(@FieldMap Map<String, String> map);

    //文章点赞
    @POST("v3/ClickZan")
    @FormUrlEncoded
    Observable<BaseResponse> dianzan(@FieldMap Map<String, String> map);


    //获取素材
    @POST("v3/AdResouse")
    @FormUrlEncoded
    Observable<LanMuListResponse> getsucaihot(@FieldMap Map<String, String> map);

    //获取素材
    @POST("v3/BuyQuanShareNum")
    @FormUrlEncoded
    Observable<BaseResponse> buyQuanShareNum(@FieldMap Map<String, String> map);

    //获取详情
    @POST("v3/GoodDetail")
    @FormUrlEncoded
    Observable<GoodDetailResponse> getlistdetail(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //复制剪切板
    @POST("v3GetTkl")
    @FormUrlEncoded
    Observable<CopyContentResponse> getCopyContent(@FieldMap Map<String, String> map);


    //获取耳机分类列表
    @POST("v3/search")
    @FormUrlEncoded
    Observable<HomeListResponse> getsearchlist(@FieldMap Map<String, String> map);

    //获取耳机分类列表
    @POST("v3/NextCate")
    @FormUrlEncoded
    Observable<HomeListResponse> getshouerjilist(@FieldMap Map<String, String> map);

    //获取系统消息
    @POST("v3/SysMessage")
    @FormUrlEncoded
    Observable<HomeListResponse> getxitonglist(@FieldMap Map<String, String> map);

    //sousuo
    @FormUrlEncoded
    @POST("v3/search")
    Observable<ErJiListResponse> shouyelist(@FieldMap Map<String, String> map);

    //获取首页的banner
    @GET("index/index?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<BannerResponse> banner(@QueryMap Map<String, String> map);


    //判断是否需要更新
    @GET("index.php?m=Api&c=Messages&a=updateVersition")
    Observable<UpdateResponse> update(@Query("type") String type);

    //获取首页的热门商品
    @GET("index/getMoreGoods?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<HomeHotResponse> homeHotProduct(@QueryMap Map<String, String> map);

    //获取本地的热门商品
    @GET("index/getMoreGoods?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<HomeHotResponse> homebendiProduct(@QueryMap Map<String, String> map);

    //修改密码短信验证码
    @POST("index.php?m=Api&c=Users&a=getPhoneVerify")
    Observable<BaseResponse> getChangePassAuthCode(@Query("userPhone") String userPhone);

    //删除地址
    @FormUrlEncoded
    @POST("address/delete?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<BaseResponse> deleteLocation(@FieldMap Map<String, String> map);

    //系统消息
    @FormUrlEncoded
    @POST("v3/SysMessage")
    Observable<XiTonfListResponse> xitongxiaoxi(@FieldMap Map<String, String> map);


    //奖金消息
    @FormUrlEncoded
    @POST("v3/RewardMessage")
    Observable<XiTonfListResponse> bonusMessage(@FieldMap Map<String, String> map, @Header("authorization") String lang);


    //删除购物车商品
    @FormUrlEncoded
    @POST("cart/delete?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<BaseResponse> deleteshopcar(@FieldMap Map<String, String> map);

    //设置默认地址
    @FormUrlEncoded
    @POST("address/setDefault?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<BaseResponse> setdefalutLocation(@FieldMap Map<String, String> map);

    //获取个人信息
    @FormUrlEncoded
    @POST("v3/Uinfo")
    Observable<PersonInfoResponse> personInfo(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //v3/MyFootRecored
    @FormUrlEncoded
    @POST("v3/DelMyFootRecored")
    Observable<PersonInfoResponse> qingchuzuji(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //会员支付
    @FormUrlEncoded
    @POST("v3/VipSubmit")
    Observable<VipSubmitResponse> vipSubmit(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //会员升级
    @FormUrlEncoded
    @POST("v3/VipUpdate")
    Observable<VipUpdateResponse> vipUpdate(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //当前会员状态
    @FormUrlEncoded
    @POST("v3/VipDetails")
    Observable<CurrentStatusResponse> currentStatus(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //生成海报
    @FormUrlEncoded
    @POST("v3/PosterShare")
    Observable<HaiBaoResponse> haibao(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //淘宝授权
    @FormUrlEncoded
    @POST("v3/GetChannel")
    Observable<AuthTaoBaoResponse> authTaoBao(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //淘宝授权通知
    @FormUrlEncoded
    @POST("v3/GetNotice")
    Observable<AuthTaoBaoResponse> authTaoBaoNotice(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //获取zhanghu
    @FormUrlEncoded
    @POST("v3/MyBill")
    Observable<AccuntListResponse> accountlist(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //提现记录
    @FormUrlEncoded
    @POST("v3/SettleMent")
    Observable<AccuntListResponse> settleMent(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //添加班长
    @FormUrlEncoded
    @POST("v3/Monitor")
    Observable<MonitorResponse> tianjiabanzhang(@FieldMap Map<String, String> map, @Header("authorization") String lang);


    //我的受益
    @FormUrlEncoded
    @POST("v3/Commision")
    Observable<MyShouYiResponse> myshouyi(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //我的团队
    @FormUrlEncoded
    @POST("v3/MyTeam")
    Observable<MyTeamResponse> mytuandui(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //开通
    @FormUrlEncoded
    @POST("v3/UpdateMs")
    Observable<BaseResponse> openMs(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //会员详情页面数据
    @FormUrlEncoded
    @POST("v3/VipDetail")
    Observable<VipDetailResponse> vipDetail(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //加入会员
    @FormUrlEncoded
    @POST("v3/AddVip")
    Observable<AddVipResponse> addVip(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //买手团队
    @FormUrlEncoded
    @POST("v3/ABTeam")
    Observable<MyTeamResponse> maishoutuandui(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //我的订单
    @FormUrlEncoded
    @POST("v3/OrderDetail")
    Observable<MyOrderList> mydingdan(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //获取收藏列表
    @FormUrlEncoded
    @POST("v3/ListCollect")
    Observable<MyFavariteResponse> shoucanglist(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //v3/MyFootRecored
    @FormUrlEncoded
    @POST("v3/MyFootRecored")
    Observable<MyZuJiResponse> zujilist(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //添加收藏
    @FormUrlEncoded
    @POST("v3/AddCollect")
    Observable<BaseResponse> addshoucang(@FieldMap Map<String, String> map, @Header("authorization") String lang);//添加收藏

    //调用优惠券接口
    @FormUrlEncoded
    @POST("v3/GetQuan")
    Observable<GetCouponResponse> getQuan(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //调用分享接口
    @FormUrlEncoded
    @POST("v3/ShareQuan")
    Observable<ShareResponse> getShare(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    @FormUrlEncoded
    @POST("v3/DelCollect")
    Observable<BaseResponse> deleteshoucang(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //厂商店铺列表
    @GET("index.php?m=Api&c=Shops&a=getShopInfo")
    Observable<FactoryProListResponse> factoryProList(@Query("shopId") int shopId,
                                                      @Query("pcurr") int p);

    //获取默认地址
    @GET("index.php?m=Api&c=UserAddress&a=getUserDefaultDelivery")
    Observable<DefaultLocationResponse> defaultLocation(@Query("userId") int userId);


    //添加到购物车
    @FormUrlEncoded
    @POST("cart/add?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<BaseResponse> addToShopcar(@FieldMap Map<String, String> map);

    //立即购买的订单校验
    @FormUrlEncoded
    @POST("order/index/add?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<XiaoYanResponse> goumaixiaoyan(@FieldMap Map<String, String> map);

    //立即购买
    @FormUrlEncoded
    @POST("index.php?m=Api&c=Cart&a=addToCartAjax")
    Observable<BaseResponse> buyNow(@Query("userId") int userId,
                                    @Query("goodsId") int goodsId,
                                    @Query("goodsAttrId") int goodsAttrId,
                                    @Query("goodsAttrIdt") int goodsAttrIdt,
                                    @Query("gcount") int gcount);

    //修改个人信息
    @FormUrlEncoded
    @POST("v3/UinfoUpdate")
    Observable<BaseResponse> changepeson(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //验证手机
    @FormUrlEncoded
    @POST("v3/CodeCheck")
    Observable<BaseResponse> verifyPhone(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //升级列表
    @FormUrlEncoded
    @POST("v3/VipList")
    Observable<UpdateIdResponse> updateIdList(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //设置个人密码
    @FormUrlEncoded
    @POST("v3/UpdatePw")
    Observable<BaseResponse> setPwd(@FieldMap Map<String, String> map, @Header("authorization") String lang);


    //更新提现账户
    @FormUrlEncoded
    @POST("v3/Cash")
    Observable<BaseResponse> tixian(@FieldMap Map<String, String> map, @Header("authorization") String lang);


    //更新提现账户
    @FormUrlEncoded
    @POST("v3/CaseUpdate")
    Observable<BaseResponse> changetixian(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //上传头像接口
    @Multipart
    @POST("v3/UinfoUpdate")
    Observable<BaseResponse> touxiangloadPic(@Part MultipartBody.Part file, @Part("user_id") RequestBody user_id, @Header("authorization") String lang);

    /**
     * 上传微信二维码几口
     *
     * @param file
     * @param user_id
     * @param lang
     * @return
     */
    @Multipart
    @POST("v3/UinfoUpdate")
    Observable<UploadFileResponse> wxQRloadPic(@Part MultipartBody.Part file, @Part("user_id") RequestBody user_id, @Header("authorization") String lang);

    //找回密码
    @GET("index.php?m=Api&c=Users&a=findPass")
    Observable<BaseResponse> findPswd(@Query("userPhone") String userPhone,
                                      @Query("newPass") String newPass,
                                      @Query("rePass") String rePass,
                                      @Query("mobileCode") String mobileCode);

    //单张上传图像
    @Multipart
    @POST("upload/apply?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<PictureResponse> uploadPic(@Part MultipartBody.Part file,
                                          @Part("token") RequestBody toke,
                                          @Part("uid") RequestBody uid,
                                          @Part("sign") RequestBody sign,
                                          @Part("timestamp") RequestBody timestamp

    );

    //意见反馈
    @FormUrlEncoded
    @POST("center/feedback?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<BaseResponse> feedback(@FieldMap Map<String, String> map);

    //商品意见反馈
    @FormUrlEncoded
    @POST("v3/FeedBack")
    Observable<BaseResponse> goodsFeedBack(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //修改个人信息
    @POST("index.php?m=Api&c=Users&a=editUser")
    Observable<BaseResponse> changePerInfo(@Query("userId") int uid,
                                           @Query("userName") String userName,
                                           @Query("userPhoto") String userPhoto);


    //商品列表
    @GET("index.php?m=Api&c=Goods&a=getGoodsList")
    Observable<ProductListResponse> productList(@Query("c1Id") int c1Id,
                                                @Query("pcurr") int p,
                                                @Query("mark") int mark,
                                                @Query("keyWords") String keyWords);

    //商品列表
    @GET("index.php?m=Api&c=Goods&a=getGoodsList")
    Observable<ProductListResponse> productList(@Query("pcurr") int p,
                                                @Query("mark") int mark,
                                                @Query("keyWords") String keyWords);

    //添加地址
    @FormUrlEncoded
    @POST("address/add?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<BaseResponse> addLocation(@FieldMap Map<String, String> map);

    //编辑地址
    @FormUrlEncoded
    @POST("address/edit?XDEBUG_SESSION_START=ECLIPSE_DBGP&KEY=15226383027112")
    Observable<BaseResponse> changeLocation(@FieldMap Map<String, String> map);


    //发放红包
    @GET("index.php?m=Api&c=Coupons&a=getRedPacket")
    Observable<GetRedPacket> getRedPacket();

    //领取红包
    @GET("index.php?m=Api&c=Coupons&a=receiveCoupon")
    Observable<GetRedPacket> receiveCoupon(@Query("userId") int userId, @Query("id") String couponId);


    //v3/MyFootRecored
    @FormUrlEncoded
    @POST("v3/CodeCheck")
    Observable<BaseResponse> makeSureCode(@FieldMap Map<String, String> map, @Header("authorization") String lang);


    //获取首页数据
    @POST("v3/HomeIndex")
    @FormUrlEncoded
    Observable<HomeDataResponse> homeData(@FieldMap Map<String, String> map);

    //获取首页弹窗广告
    @Headers({"Connection:close"})
    @POST("v3/HomeStart")
    @FormUrlEncoded
    Observable<HomeAds> homeDialogAds(@FieldMap Map<String, String> map);

    //消息类型
    @POST("v3/PushCate")
    @FormUrlEncoded
    Observable<MessageTypeResponse> megType(@FieldMap Map<String, String> map);

    //奖金消息列表
    @POST("v3/PushList")
    @FormUrlEncoded
    Observable<MsgMoneyListResponse> megMoneyList(@FieldMap Map<String, String> map);

    //系统消息列表
    @POST("v3/PushList")
    @FormUrlEncoded
    Observable<MsgSystemResponse> megSystemList(@FieldMap Map<String, String> map);

    //活动消息列表
    @POST("v3/PushList")
    @FormUrlEncoded
    Observable<MsgActivityResponse> megActivityList(@FieldMap Map<String, String> map);

    //每日爆款消息列表
    @POST("v3/PushList")
    @FormUrlEncoded
    Observable<MsgEverydayResponse> megEverydayList(@FieldMap Map<String, String> map);

    //品牌特卖：顶部品牌联盟+分类
    @POST("v3/GoodBrandCate")
    @FormUrlEncoded
    Observable<BrandResponse> brand(@FieldMap Map<String, String> map);

    //品牌特卖：列表
    @POST("v3/GoodBrand")
    @FormUrlEncoded
    Observable<BrandListResponse> brandList(@FieldMap Map<String, String> map);

    //品牌特卖：店铺更多
    @POST("v3/OneGoodBrand")
    @FormUrlEncoded
    Observable<ShopResponse> shopList(@FieldMap Map<String, String> map);

    //每日爆款头部
    @POST("v3/DayHotCatev3")
    Observable<LunTanTitleResponse> getDayHotHeader();

    //每日爆款列表
    @POST("v3/DayHotv3")
    @FormUrlEncoded
    Observable<LanMuListResponse> getDayHotList(@FieldMap Map<String, String> map);

    //每日爆款文章列表
    @POST("v3/DayHotv3")
    @FormUrlEncoded
    Observable<ArticleResponse> getDayHotArticleList(@FieldMap Map<String, String> map);

    //获取素材
    @POST("v3/AdResousev3")
    @FormUrlEncoded
    Observable<LanMuListResponse> getResouseList(@FieldMap Map<String, String> map);

    //会员升级
    @POST("v3/RoleType")
    @FormUrlEncoded
    Observable<MemberUpgradeResponse> memberUpgrade(@FieldMap Map<String, String> map);

    //规则
    @POST("v3/TbHost")
    Observable<RulesResponse> rules();

    //搜索优惠券
    @POST("v3/GoodsSearchList")
    @FormUrlEncoded
    Observable<HomeListResponse> searchTicket(@FieldMap Map<String, String> map);

    //买手圈复制评论
    @POST("v3/CopyComment")
    @FormUrlEncoded
    Observable<CopyResponse> copyComment(@FieldMap Map<String, String> map);

    //获取首页liebiao
    @POST("v3/HomeGoodList")
    @FormUrlEncoded
    Observable<HomeListResponse> getHomeGoodsList(@FieldMap Map<String, String> map);

    //获取个人信息
    @FormUrlEncoded
    @POST("v3/Uinfo")
    Observable<PersonInfoResponse> personalInfo(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //商学院标题列表
    @POST("v3/SchoolCate")
    Observable<LunTanSchoolCateResponse> SchoolCate();

    //商学院列表
    @FormUrlEncoded
    @POST("v3/SchoolList")
    Observable<SchoolListResponse> SchoolList(@FieldMap Map<String, String> map);

    //聚划算
    @FormUrlEncoded
    @POST("v3/ActivityGoodList")
    Observable<JuHuaSuanResponse> ActivityGoodList(@FieldMap Map<String, String> map);


    //商品详情店铺信息接口
    @FormUrlEncoded
    @POST("v3/DetailAnalysis")
    Observable<ShopInfoResponse> DetailAnalysis(@FieldMap Map<String, String> map);

    //商品详情店铺信息接口
    @FormUrlEncoded
    @POST("v3/CopyTkl")
    Observable<TklCopyResponse> CopyTkl(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //商品详情店铺信息接口
    @FormUrlEncoded
    @POST("v3/IsNeedRecord")
    Observable<BaseResponse> IsNeedRecord(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //商品详情店铺信息接口
    @FormUrlEncoded
    @POST("v3/SchoolAddShareNum")
    Observable<BaseResponse> SchoolAddShareNum(@FieldMap Map<String, String> map);

    //驗證手機號是否已經註冊
    @FormUrlEncoded
    @POST("/api/v3/CheckMobile")
    Observable<CheckMobileBean> CheckMobile(@FieldMap Map<String, String> map);

    //國家區域
    @POST
    Observable<AreaBean> ChangeCountry(@Url String url);

    //微信登录
    @POST("v3/WxLogin")
    @FormUrlEncoded
    Observable<LoginResponse> WxLogin(@FieldMap Map<String, String> map);


    //京东搜索
    @POST("v3/JdSearch")
    @FormUrlEncoded
    Observable<ErJiListResponse> JdSearch(@FieldMap Map<String, String> map);


    @POST("v3/PddSearch")
    @FormUrlEncoded
    Observable<ErJiListResponse> PddSearch(@FieldMap Map<String, String> map);


    //京东详情
    @POST("v3/JdGoodDetail")
    @FormUrlEncoded
    Observable<GoodDetailResponse> JdGoodDetail(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //京东详情
    @POST("v3/PddGoodDetail")
    @FormUrlEncoded
    Observable<GoodDetailResponse> PddGoodDetail(@FieldMap Map<String, String> map, @Header("authorization") String lang);




    //调用jd分享接口
    @FormUrlEncoded
    @POST("v3/JdShareQuan")
    Observable<ShareResponse> JdShareQuan(@FieldMap Map<String, String> map, @Header("authorization") String lang);



    //调用pdd分享接口
    @FormUrlEncoded
    @POST("v3/PddShareQuan")
    Observable<ShareResponse> PddShareQuan(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //调用jd优惠券接口
    @FormUrlEncoded
    @POST("v3/JdGetQuan")
    Observable<GetCouponResponse> JdGetQuan(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    //调用pdd优惠券接口
    @FormUrlEncoded
    @POST("v3/PddGetQuan")
    Observable<GetCouponResponse> PddGetQuan(@FieldMap Map<String, String> map, @Header("authorization") String lang);


    @FormUrlEncoded
    @POST("v3/JdCopyTkl")
    Observable<TklCopyResponse> JdCopyTkl(@FieldMap Map<String, String> map, @Header("authorization") String lang);

    @FormUrlEncoded
    @POST("v3/PddCopyTkl")
    Observable<TklCopyResponse> PddCopyTkl(@FieldMap Map<String, String> map, @Header("authorization") String lang);


//智能搜索转链接口
    @POST("v3/GetTkl")
    @FormUrlEncoded
    Observable<LinkBean> GetTkl (@FieldMap Map<String, String> map);


    //转链接口
    @POST("v3/chainLink")
    @FormUrlEncoded
    Observable<LinkBean> chainLink (@FieldMap Map<String, String> map);
}
