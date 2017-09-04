package com.zysapp.sjyyt.model;

import com.bigkoo.pickerview.model.IPickerViewData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * TODO<json数据源>
 *
 * @author: 小嵩
 * @date: 2017/3/16 15:36
 */

public class JsonBean extends XtomObject implements IPickerViewData {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String name;
    private String id;
    private String children;//
    private List<CityModel> cityList;

    public JsonBean(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                name = get(jsonObject, "name");
                children = get(jsonObject, "children");
                if (!jsonObject.isNull("children")
                        && !isNull(jsonObject.getString("children"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("children");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject json = jsonList.getJSONObject(i);
                        if (cityList == null) {
                            cityList = new ArrayList<>();
                        }
                        cityList.add(new CityModel(json));
                    }
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getChildren() {
        return children;
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }


}
