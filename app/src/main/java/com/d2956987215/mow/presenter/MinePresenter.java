package com.d2956987215.mow.presenter;

import com.d2956987215.mow.DESUtils.JiaMiUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.eventbus.AddLocationSuccess;
import com.d2956987215.mow.eventbus.DeleteLocation;
import com.d2956987215.mow.eventbus.GetCodeSuccess;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.AddVipResponse;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.CurrentStatusResponse;
import com.d2956987215.mow.rxjava.response.VipDetailResponse;
import com.d2956987215.mow.rxjava.response.VipSubmitResponse;
import com.d2956987215.mow.rxjava.response.VipUpdateResponse;
import com.d2956987215.mow.util.DialogUtil;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lq on 2018/1/30.
 */

public class MinePresenter {
    private BaseActivity activity;
    private Date date;

    public MinePresenter(BaseActivity activity) {
        this.activity = activity;
    }


    public void deleteLocation(String addressId) {
        Map<String, String> map = new HashMap<>();
        date = new Date();
        long time = date.getTime() / 1000;
        String key = JiaMiUtils.getkey(User.uid() + User.token(), String.valueOf(time));
        map.put("uid", String.valueOf(User.uid()));
        map.put("addr_id", addressId);
        map.put("timestamp", String.valueOf(time));
        map.put("sign", key);
        map.put("token", User.token());
        new Request<>().request(RxJavaUtil.xApi().deleteLocation(map), "删除地址", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, response.message);
                EventBus.getDefault().post(new DeleteLocation());
            }
        });
    }


    public void addLocation(String name, String phone, String provinceId, String cityId, String countyId, String address, int isDefault, String listid) {
        if (name.equals("") || phone.equals("") || address.equals("")) {
            ToastUtil.show(activity, activity.getString(R.string.please_complete));
            return;
        }
        if (provinceId != null && provinceId.length() > 0) {

        } else {
            ToastUtil.show(activity, "请选择省份");
            return;
        }
        if (cityId != null && cityId.length() > 0) {

        } else {
            ToastUtil.show(activity, "请选择城市");
            return;
        }
        if (countyId != null && countyId.length() > 0) {

        } else {
            ToastUtil.show(activity, "请选择县/区");
            return;
        }
        date = new Date();
        long time = date.getTime() / 1000;
        String key = JiaMiUtils.getkey(User.uid() + User.token(), String.valueOf(time));
        Map<String, String> map = new HashMap<>();
        map.put("uid", String.valueOf(User.uid()));
        map.put("consigner", name);
        map.put("mobile", phone);
        map.put("province", provinceId);
        map.put("city", cityId);
        map.put("district", countyId);
        map.put("address", address);
        map.put("is_default", "1");
        map.put("token", User.token());
        map.put("sign", key);
        map.put("timestamp", String.valueOf(time));
        if (Const.type != null && Const.type.equals("1")) {
            map.put("addr_id", listid);
            new Request<>().request(RxJavaUtil.xApi().changeLocation(map), "编辑地址", activity, false, new Result<BaseResponse>() {
                @Override
                public void get(BaseResponse response) {
                    ToastUtil.show(activity, response.message);
                    EventBus.getDefault().post(new AddLocationSuccess());
                }
            });
        } else {
            new Request<>().request(RxJavaUtil.xApi().addLocation(map), "添加地址", activity, false, new Result<BaseResponse>() {
                @Override
                public void get(BaseResponse response) {
                    ToastUtil.show(activity, response.message);
                    EventBus.getDefault().post(new AddLocationSuccess());
                }
            });
        }


    }


    public void feedBack(final String content) {
        if (content.equals("")) {
            ToastUtil.show(activity, activity.getString(R.string.feedback_content_cant_be_null));
            return;
        }
//
//        if (!img.equals("")) {
//            File file = new File(img);
//            RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
//            final MultipartBody.Part part = MultipartBody.Part.createFormData("pic", file.getName(), requestFile);
//            new Request<PictureResponse>().request(RxJavaUtil.xApi().uploadPic(part), "意见反馈上传图片", activity, true, new Result<PictureResponse>() {
//                @Override
//                public void get(PictureResponse response) {
//                    new Request<>().request(RxJavaUtil.xApi().feedback(User.uid(), response.getPic(), content), "意见反馈", activity, false, new Result<BaseResponse>() {
//                        @Override
//                        public void get(BaseResponse response) {
//                            ToastUtil.show(activity, response.msg);
//                        }
//                    });
//                }
//            });
//        }

        //      else {
        date = new Date();
        long time = date.getTime() / 1000;
        String key = JiaMiUtils.getkey(User.uid() + User.token(), String.valueOf(time));
        Map<String, String> map = new HashMap<>();
        map.put("uid", User.uid() + "");
        map.put("token", User.token());
        map.put("sign", key);
        map.put("timestamp", String.valueOf(time));
        map.put("content", content);
        new Request<>().request(RxJavaUtil.xApi().feedback(map), "意见反馈", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, response.message);
                DialogUtil.dialogsuccess(activity);

            }
        });
        //  }
    }

    //商品详情反馈
    public void feedBackGoods(final String content, String itemId, List<String> type) {
        if (content.equals("")) {
            ToastUtil.show(activity, activity.getString(R.string.feedback_content_cant_be_null));
            return;
        }
        date = new Date();
        long time = date.getTime() / 1000;
        String key = JiaMiUtils.getkey(User.uid() + User.token(), String.valueOf(time));
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
//        map.put("token", User.token());
        Gson gson = new Gson();
        map.put("item_id", itemId);
        map.put("type", gson.toJson(type));
//        map.put("sign", key);
//        map.put("timestamp", String.valueOf(time));
        map.put("content", content);
        new Request<>().request(RxJavaUtil.xApi().goodsFeedBack(map, "Bearer " + User.token()), "意见反馈", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, response.message);
                activity.finish();
            }
        });
    }

    //修改密码获取验证码
    public void getAuthCode(String phone) {
        if (phone.equals("")) {
            ToastUtil.show(activity, activity.getString(R.string.input_right_phone));
            return;
        }
        new Request<>().request(RxJavaUtil.xApi().getChangePassAuthCode(phone), "修改密码获取验证码", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, response.message);
            }
        });
    }


    public void changePass(String phone, String code, String newPass) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("code", code);
        map.put("is_editpassword", "1");
        map.put("user_id", User.uid() + "");
        map.put("new_password", newPass);
        new Request<>().request(RxJavaUtil.xApi().changepeson(map, "Bearer " + User.token()), "修改密码", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, response.message);
                activity.finish();
            }
        });
    }


    public void changephone(final String phone, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("new_mobile", phone);
        map.put("code", code);
        map.put("is_editmobile", "1");
        map.put("user_id", User.uid() + "");
        new Request<>().request(RxJavaUtil.xApi().changepeson(map, "Bearer " + User.token()), "修改手机号", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                SP.putString("phone", phone);
                ToastUtil.show(activity, response.message);
                activity.finish();
            }
        });
    }


    //修改密码发送验证码
    public void registerAuthCode(String userPhone) {
        String time = String.valueOf(DisplayUtil.getCurrTime());
        String key = "T#rr#6ZS6lexJHDD";
        String code = DisplayUtil.md5(key + userPhone + time);
        Map<String, String> map = new HashMap<>();
        map.put("send_type", "1");
        map.put("user_id", User.uid() + "");
        map.put("mobile", userPhone);
        map.put("time", time);
        map.put("code", code);
        new Request<>().request(RxJavaUtil.xApi().xiugaicode(map, "Bearer " + User.token()), "获取验证码", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, activity.getString(R.string.send_success));
                EventBus.getDefault().post(new GetCodeSuccess());
            }
        });
    }

    //修改手机发送验证码
    public void changeAuthCode(String userPhone) {
        if ("".equals(userPhone)) {
            ToastUtil.show(activity, activity.getString(R.string.phone_not_null));
            return;
        }
        String time = String.valueOf(DisplayUtil.getCurrTime());
        String key = "T#rr#6ZS6lexJHDD";
        String code = DisplayUtil.md5(key + userPhone + time);
        Map<String, String> map = new HashMap<>();
        map.put("send_type", "2");
        map.put("user_id", User.uid() + "");
        map.put("mobile", userPhone);
        map.put("time", time);
        map.put("code", code);
        new Request<>().request(RxJavaUtil.xApi().xiugaicode(map, "Bearer " + User.token()), "获取验证码", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, activity.getString(R.string.send_success));
                EventBus.getDefault().post(new GetCodeSuccess());
            }
        });
    }

    //绑定支付宝发送验证码
    public void changezhifubao(String userPhone) {
        if ("".equals(userPhone)) {
            ToastUtil.show(activity, activity.getString(R.string.phone_not_null));
            return;
        }
        String time = String.valueOf(DisplayUtil.getCurrTime());
        String key = "T#rr#6ZS6lexJHDD";
        String code = DisplayUtil.md5(key + userPhone + time);
        Map<String, String> map = new HashMap<>();
        map.put("send_type", "3");
        map.put("user_id", User.uid() + "");
        map.put("mobile", userPhone);
        map.put("time", time);
        map.put("code", code);
        new Request<>().request(RxJavaUtil.xApi().xiugaicode(map, "Bearer " + User.token()), "获取验证码", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, activity.getString(R.string.send_success));
                EventBus.getDefault().post(new GetCodeSuccess());
            }
        });
    }

    //vip详情界面
    public void vipDetail(String roletype) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        map.put("roletype", roletype);
        new Request<VipDetailResponse>().request(RxJavaUtil.xApi().vipDetail(map, "Bearer " + User.token()), "获取会员详情", activity, false, new Result<VipDetailResponse>() {
            @Override
            public void get(VipDetailResponse response) {
                activity.show(response);
            }
        });
    }

    //加入会员
    public void addVip() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        new Request<AddVipResponse>().request(RxJavaUtil.xApi().addVip(map, "Bearer " + User.token()), "加入会员", activity, false, new Result<AddVipResponse>() {
            @Override
            public void get(AddVipResponse response) {

            }
        });
    }

    //加入会员
    public void vipSubmit(String roleType) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        map.put("roletype", roleType);
//        map.put("clicktype", "7");
        new Request<VipSubmitResponse>().request(RxJavaUtil.xApi().vipSubmit(map, "Bearer " + User.token()), "加入会员", activity, true, new Result<VipSubmitResponse>() {
            @Override
            public void get(VipSubmitResponse response) {
                activity.show(response);
            }
        });
    }

    //立即升级
    public void vipUpdate(String roleType) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        map.put("roletype", roleType);
//        map.put("optionid", optionid);
        new Request<VipUpdateResponse>().request(RxJavaUtil.xApi().vipUpdate(map, "Bearer " + User.token()), "加入会员", activity, true, new Result<VipUpdateResponse>() {
            @Override
            public void get(VipUpdateResponse response) {
                activity.show(response);
            }
        });
    }

    //加入会员
    public void currentStatus(String roleType) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        map.put("roletype", roleType);
        new Request<CurrentStatusResponse>().request(RxJavaUtil.xApi().currentStatus(map, "Bearer " + User.token()), "当前会员状态", activity, true, new Result<CurrentStatusResponse>() {
            @Override
            public void get(CurrentStatusResponse response) {
                activity.show(response);
            }
        });
    }


}
