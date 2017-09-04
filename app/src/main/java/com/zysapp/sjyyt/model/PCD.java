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
 *
 */

public class PCD extends XtomObject  {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String name;
    private String id;
    private String children;//
    private ArrayList<JsonBean> cityList;

    public PCD(JSONObject jsonObject) throws DataParseException {
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
                        cityList.add(new JsonBean(json));
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

    public ArrayList<JsonBean> getCityList() {
        return cityList;
    }

}
