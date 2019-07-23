package com.d2956987215.mow.widgets;

import android.util.Log;

import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.d2956987215.mow.widgets.model.CityModel;
import com.d2956987215.mow.widgets.model.DistrictModel;
import com.d2956987215.mow.widgets.model.ProvinceModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.d2956987215.mow.widgets.bean.ProvinceBean;
import com.d2956987215.mow.widgets.model.CityModel;
import com.d2956987215.mow.widgets.model.DistrictModel;
import com.d2956987215.mow.widgets.model.ProvinceModel;


public class TextParserHandler  {

	/**
	 * 存储所有的解析对象
	 */
	private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();
	private InputStream inputStream;

	public TextParserHandler() {

	}

	public List<ProvinceModel> getDataList() {
		Type t= new TypeReference<ArrayList<ProvinceBean>>() {}.getType();
		ArrayList<ProvinceBean> provinceBean=new Gson().fromJson(startParse(),t);
		for (ProvinceBean provinceBean1:provinceBean){
 			ProvinceModel p=new ProvinceModel();
			p.setId(provinceBean1.getValue() + "");
			p.setName(provinceBean1.getText());
			ArrayList<CityModel> cityModelArrayList=new ArrayList<CityModel>();

			for (ProvinceBean.City  city :provinceBean1.getChildren()){
				CityModel  cityModel=new CityModel();
				cityModel.setName(city.getText());
				cityModel.setId(city.getValue() + "");
				ArrayList<DistrictModel> districtArrayList=new ArrayList<DistrictModel>();
				for (ProvinceBean.City.District district:city.getChildren()){
					DistrictModel  districtModel=new DistrictModel();
					districtModel.setId(district.getValue()+"");
					districtModel.setName(district.getText());
					districtModel.setZipcode(district.getValue() + "");
					districtArrayList.add(districtModel);
 				}
 				cityModel.setDistrictList(districtArrayList);
				cityModelArrayList.add(cityModel);
			}
			p.setCityList(cityModelArrayList);
			provinceList.add(p);
		}


		return provinceList;
	}




	private String startParse(){
		// 当遇到开始标记的时候，调用这个方法

		InputStreamReader read = new InputStreamReader(inputStream);//考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		StringBuffer sb=new StringBuffer();
		try {
			while((lineTxt = bufferedReader.readLine()) != null){
				sb.append(lineTxt);
				Log.e("provinceList", sb.toString());

			}
			read.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}




	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}