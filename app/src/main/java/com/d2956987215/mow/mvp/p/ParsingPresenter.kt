package com.d2956987215.mow.mvp.p


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.d2956987215.mow.activity.APP
import com.d2956987215.mow.dialog.AuthTaoBaoDialog
import com.d2956987215.mow.dialog.LoadingDialog
import com.d2956987215.mow.mvp.m.moudle.HomeModel
import com.d2956987215.mow.mvp.m.dynamic.Dynamic
import com.d2956987215.mow.mvp.v.Contract
import com.d2956987215.mow.rxjava.response.BaseResponse
import com.d2956987215.mow.util.*
import io.reactivex.Observable

/**
 * Created by lk on 2018/6/8.
 */
class ParsingPresenter(view: Contract.View) : Contract.Presenter {


    var mView: Contract.View? = null
    internal var loadingDialog: LoadingDialog? = null
    var isShowDialogFlag: Boolean = false
    var mContext: Context? = null
    val mModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        mView = view
    }


    override fun setDialog(context: Context): ParsingPresenter {
        this.isShowDialogFlag = true
        this.mContext = context
        return this
    }

    /**
     * value[0]  返回类型  多个请求同时进行 ，根据此字段来判断  必传
     * value[1]  请求网络的方法名  必传
     * value[2]  如果是用rf  直接用 URL 来请求的url地址
     * value[3]  如果 是请求 网络的时候，有表单  此map为表单
     * value[4]  用 rf 请求所带的参数     如 fun <T> getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>
     * date  num  传递的就是这两个的值
     */
    override fun <T : BaseResponse> start(vararg value: Any) {
        if (value == null || value.size < 2) {
            return
        }
        var type: String = value[0] as String
        var method: String = value[1] as String
        var url: String? = null
        if (value.size > 2) {
            url = value[2] as String
        }

        var map: HashMap<String, Any>? = hashMapOf()
        if (value.size == 3) {
            requestData<T>(type, method, url, map, "")
        } else {
            map = value[3] as HashMap<String, Any>
            if (value.size > 4) {
                requestData<T>(type, method, url, map, value[4])
            } else {
                requestData<T>(type, method, url, map, "")
            }
        }


    }

    /**
     * 传递参数 通过map的方式
     */
    override fun <T : BaseResponse> start(value: HashMap<String, Any>) {

        requestData<T>(
                value["type"] as String,//返回类型  多个请求同时进行 ，根据此字段来判断
                value["method"] as String,  //请求网络的方法名
                value["url"] as? String,  //如果是用rf  直接用 URL 来请求的url地址
                value["map"] as? HashMap<String, Any>,//如果 是请求 网络的时候，有表单  此map为表单
                value["param"] //用 rf 请求所带的参数     如 fun <T> getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>
                //date  num  传递的就是这两个的值
        )
    }

    override fun <T : BaseResponse> requestData(type: String, method: String, url: String?, map: HashMap<String, Any>?, vararg param: Any?) {
        if (!NetworkUtils.isNetworkAvailable(APP.getApplication())) {
            Toast.makeText(APP.getApplication(), "$type->没有网络，请稍后重试", Toast.LENGTH_SHORT).show()
            return
        }
        if (mContext != null && isShowDialogFlag) {
            showProgress("加载")
        }
        //通过反射进行动态代理
        val observable: Observable<T>? = let { Dynamic.methodInvoke(mModel.javaClass.name, method, url, map, param) }
        //p层与v层的交互     对view 返回数据
        CustomData(observable, type)
    }

    /**
     * 加载更多  可以用start  代替
     * 也可以改写为  动态代理 的 方式来实现  根据自己的项目  需求来进行抉择
     */
    fun <T> moreData(data: String?, url: String, type: String, map: HashMap<String, Any>?) {

        when (type) {
//            "loadData" -> {
//                val observable: Observable<T>? = let { mModel.loadData(url, map, false, data!!) }
//                CustomData(observable, type)
//            }


        }
    }

    @SuppressLint("CheckResult")
    fun <T : BaseResponse> CustomData(observable: Observable<T>?, type: String) {
        loadingDialog?.stop()
        this.isShowDialogFlag = false
        if (mView == null) {
            return
        }
        Log.e("tag", "请求网络方法--》$type")
        observable?.applySchedulers()?.subscribe({ o: T ->
            when {
                o.status_code == 200 -> mView?.setData(type, o)
                o.status_code == 401 -> {
                    SP.putInt("userId", 0)
                    SP.putString("token", null)
                    ActivityManager.finishguoAllActivity()
                    ToastUtil.show(APP.getApplication(), o.message)
                    ActivityUtils.startLoginAcitivy(APP.getApplication())

                }
                o.status_code == 206 ->
                    AuthTaoBaoDialog(APP.getApplication())
                else -> {
                    ToastUtil.show(APP.getApplication(), o.message)
                    Log.e("AppOnError", type + "->onError: errorCode:" + o.status_code + ",msg:" + o.message)

                }
            }

        }, { error: Throwable ->
            mView?.onError(type, error)
        })

    }


    fun showProgress(message: String) {
        if (loadingDialog == null) {
            loadingDialog = createProgressDialog(message, mContext)
        }
        if (loadingDialog != null) {
            loadingDialog?.show()
        }
    }


    private fun createProgressDialog(message: String, context: Context?): LoadingDialog? {
        if (context != null) {
            val dialog = LoadingDialog(context)
            dialog.setCancelable(true)
            return dialog
        } else {
            return null
        }
    }

//    @Override
//    fun setDialog(context: Context) {
//        this.isShowDialogFlag = true
//    }
}


