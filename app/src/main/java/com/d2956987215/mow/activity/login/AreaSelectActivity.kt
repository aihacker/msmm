package com.d2956987215.mow.activity.login

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import com.d2956987215.mow.R
import com.d2956987215.mow.activity.APP
import com.d2956987215.mow.activity.BaseActivity
import com.d2956987215.mow.adapter.SortAdapter
import com.d2956987215.mow.bean.AreaBean
import com.d2956987215.mow.bean.CheckMobileBean
import com.d2956987215.mow.dialog.RegistDialog
import com.d2956987215.mow.rxjava.Request
import com.d2956987215.mow.rxjava.Result
import com.d2956987215.mow.rxjava.RxJavaUtil
import com.d2956987215.mow.util.PinyinComparator
import com.d2956987215.mow.util.StringUtil
import com.d2956987215.mow.util.ToolbarUtils
import com.d2956987215.mow.util.sortlist.CharacterParser
import com.d2956987215.mow.util.sortlist.SortModel
import com.example.urilslibrary.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_area_select.*
import kotlinx.android.synthetic.main.include_header.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import java.util.*


class AreaSelectActivity : BaseActivity() {
    override fun show(obj: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_area_select
    }

    private var characterParser: CharacterParser? = null
    private var SourceDateList: List<AreaBean.DataBean>? = null
    private var adapter: SortAdapter? = null
    private var pinyinComparator: PinyinComparator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_area_select)
        initView()
        setOnClick()
    }

    private fun setOnClick() {
        sortListView.setOnItemClickListener { adapterView, view, i, l ->
            val userBean = this.SourceDateList?.get(i)
            EventBus.getDefault().post(userBean)
//            startActivity(Intent(this, LoginNewActivity::class.java))
            finish()
        }
        rl_back.setOnClickListener {
            finish()
        }
    }

    override fun title(): String {
        return "地区选择"
    }

    private fun initView() {
//        ToolbarUtils.setToolTitle(toolBar, this, "地区选择", null, "", 0)
//        setSupportActionBar(toolBar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance()

        pinyinComparator = PinyinComparator()

//        ConstactAsyncTask().execute(0)

        Request<AreaBean>().request(RxJavaUtil.xApi2().ChangeCountry(RxJavaUtil.URL+"v3/ChangeCountry"), "獲取區域code", this, true, object : Result<AreaBean>() {
            override fun get(response: AreaBean) {

                if (response.status_code == 200) {

                    SourceDateList = filledData(response.data!!)
                    // 根据a-z进行排序源数据
                    Collections.sort(SourceDateList, pinyinComparator)
                    adapter = SortAdapter(this@AreaSelectActivity, SourceDateList)
                    sortListView.adapter = adapter
                    filter_edit.onFocusChangeListener = View.OnFocusChangeListener { arg0, arg1 -> filter_edit.setGravity(Gravity.LEFT or Gravity.CENTER_VERTICAL) }
                    // 根据输入框输入值的改变来过滤搜索
                    filter_edit.addTextChangedListener(object : TextWatcher {

                        override fun onTextChanged(s: CharSequence, start: Int,
                                                   before: Int, count: Int) {
                            // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                            filterData(s.toString())
                        }

                        override fun beforeTextChanged(s: CharSequence, start: Int,
                                                       count: Int, after: Int) {

                        }

                        override fun afterTextChanged(s: Editable) {}
                    })

                } else {
                    Utils.ToastShort(this@AreaSelectActivity, "请求错误")
                }

            }
        })

    }

    private inner class ConstactAsyncTask : AsyncTask<Int, Int, Int>() {
        override fun doInBackground(vararg p0: Int?): Int {
            var result = -1
            val s = StringUtil.getJson(APP.application, "area.json")
//            val fromJson = Gson().fromJson(s, SortModel::class.java)
//            SourceDateList = fromJson.user
            return 1
        }


        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            if (result == 1) {
                SourceDateList = filledData(SourceDateList!!)
                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator)
                adapter = SortAdapter(this@AreaSelectActivity, SourceDateList)
                sortListView.adapter = adapter

                filter_edit.onFocusChangeListener = View.OnFocusChangeListener { arg0, arg1 -> filter_edit.setGravity(Gravity.LEFT or Gravity.CENTER_VERTICAL) }
                // 根据输入框输入值的改变来过滤搜索
                filter_edit.addTextChangedListener(object : TextWatcher {

                    override fun onTextChanged(s: CharSequence, start: Int,
                                               before: Int, count: Int) {
                        // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                        filterData(s.toString())
                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int,
                                                   count: Int, after: Int) {

                    }

                    override fun afterTextChanged(s: Editable) {}
                })
            }
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

    }


    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private fun filledData(date: List<AreaBean.DataBean>): List<AreaBean.DataBean> {
        val mSortList = ArrayList<AreaBean.DataBean>()

        for (i in date.indices) {
//            val sortModel = SortModel.UserBean()
            val sortModel = date[i]


            // 汉字转换成拼音
            val pinyin = characterParser?.getSelling(date[i].country)
            val sortString = pinyin?.substring(0, 1)?.toUpperCase()

            // 正则表达式，判断首字母是否是英文字母
            if (sortString!!.matches("[A-Z]".toRegex())) {
                sortModel.sortLetters = sortString?.toUpperCase()
            } else {
                sortModel.sortLetters = "#"
            }

            mSortList.add(sortModel)
        }
        return mSortList

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private fun filterData(filterStr: String) {
        var filterDateList: MutableList<AreaBean.DataBean> = ArrayList()

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList as MutableList<AreaBean.DataBean>
        } else {
            filterDateList.clear()
            for (sortModel in SourceDateList!!) {
                val name = sortModel.country
                if (name.indexOf(filterStr) != -1 || characterParser?.getSelling(name)!!.startsWith(
                                filterStr)) {
                    filterDateList.add(sortModel)
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator)
        adapter?.updateListView(filterDateList)
    }

}
