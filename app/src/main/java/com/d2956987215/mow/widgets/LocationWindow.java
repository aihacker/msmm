package com.d2956987215.mow.widgets;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.d2956987215.mow.widgets.model.CityModel;
import com.d2956987215.mow.widgets.model.DistrictModel;
import com.d2956987215.mow.widgets.model.ProvinceModel;
import com.d2956987215.mow.widgets.views.OnWheelChangedListener;
import com.d2956987215.mow.widgets.views.WheelView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.d2956987215.mow.R;
import com.d2956987215.mow.widgets.adapters.ArrayWheelAdapter;
import com.d2956987215.mow.widgets.model.CityModel;
import com.d2956987215.mow.widgets.model.DistrictModel;
import com.d2956987215.mow.widgets.model.ProvinceModel;
import com.d2956987215.mow.widgets.views.OnWheelChangedListener;
import com.d2956987215.mow.widgets.views.WheelView;


/**
 * Created by Administrator on 2015/11/11.
 */
public class LocationWindow extends PopupWindow implements View.OnClickListener, OnWheelChangedListener {
    private Activity activity;
    WheelSelctInterface itemlisener;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;


    public LocationWindow(Activity ac, WheelSelctInterface wheelSelctInterface) {
        activity = ac;
        this.itemlisener = wheelSelctInterface;
        init();
    }

    public LocationWindow(Activity ac, int r, WheelSelctInterface itemlisener) {
        this.itemlisener = itemlisener;
        this.activity = ac;
        init();
    }

    private int id = 1000;

    public void init() {
        View contentView = LayoutInflater.from(activity).inflate(R.layout.province_city_district, null);
        setUpViews(contentView);
        setUpListener();
        setUpData();

        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
    }


    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";

    /**
     * 解析省市区的XML数据
     */
    List<CityModel> cityList;
    HashMap idsmap = new HashMap();

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = activity.getAssets();
        try {
            //   FileInputStream input = new FileInputStream(new File(Utils.initDownload() + "/shengshi.xml"));

             /* InputStream input = asset.open("shengshi.xml");
             TextParserHandler handler = new TextParserHandler();
              handler.setInputStream(input);
              input.close();*/

            InputStream input = asset.open("shengshi.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();

            //  input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区

            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                idsmap.put(mProvinceDatas[i], provinceList.get(i).getId() + "");
                cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    idsmap.put(cityNames[j], cityList.get(j).getId());
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        idsmap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }


    private void setUpViews(View contentView) {
        mViewProvince = (WheelView) contentView.findViewById(R.id.id_province);
         mViewCity = (WheelView) contentView.findViewById(R.id.id_city);
         mViewDistrict = (WheelView) contentView.findViewById(R.id.id_district);
         contentView.findViewById(R.id.quxiao).setOnClickListener(this);
        contentView.findViewById(R.id.queding).setOnClickListener(this);
        contentView.findViewById(R.id.ll_beijing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(activity, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }


    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(activity, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        if (mProvinceDatas != null)
            mCurrentProviceName = mProvinceDatas[pCurrent];

        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(activity, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.quxiao:
                    dismiss();
                    break;
                case R.id.queding:
                    dismiss();
                    try {
                        mCurrentProviceName= ((ArrayWheelAdapter)mViewProvince.getViewAdapter()).getItemText(mViewProvince.getCurrentItem()).toString();
                        mCurrentCityName= ((ArrayWheelAdapter)mViewCity.getViewAdapter()).getItemText(mViewCity.getCurrentItem()).toString();
                        mCurrentDistrictName= ((ArrayWheelAdapter)mViewDistrict.getViewAdapter()).getItemText(mViewDistrict.getCurrentItem()).toString();
                        ids[0]=(String)idsmap.get(mCurrentProviceName) ;
                        ids[1]=(String)idsmap.get(mCurrentCityName) ;
                        ids[2]=(String)idsmap.get(mCurrentDistrictName) ;
                    }catch (Exception e){

                    }
                     itemlisener.selectItem(mCurrentProviceName + " " + mCurrentCityName + " " + mCurrentDistrictName, ids);
                    break;
            }
    }
    String[] ids = new String[3];

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
        {


        }
    }


    public interface WheelSelctInterface {
        public void selectItem(String item, String[] ids);
    }

}
