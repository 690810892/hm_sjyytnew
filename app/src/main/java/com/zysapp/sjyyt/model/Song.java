package com.zysapp.sjyyt.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**

 */
public class Song extends XtomObject implements Serializable {

    private String id;//	主键id
    private String name;//	名称
    private String author;//	作者
    private String author_imgurl;//	作者头像
    private String author_content;//	作者头像
    private String description;//	简介
    private String imgurl;//	图片
    private String url;//	音频地址
    private String replycount;//	评论数
    private String listencount;//收听数
    private String sharecount;//	分享数
    private String startdate;//	开始时间	客户端根据此时间判断音频进度
    private String enddate;//	结束时间	客户端根据此时间判断音频进度
    private String channel_id;//
    private String type_id;//
    private String dyflag;//
    private String loveflag;//
    private String state = "0";
    private String channel = "0";
    private ArrayList<Author> authors=new ArrayList<>();
    public Song(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                loveflag = get(jsonObject, "loveflag");
                id = get(jsonObject, "id");
                dyflag = get(jsonObject, "dyflag");
                name = get(jsonObject, "name");
                author = get(jsonObject, "author");
                author_imgurl = get(jsonObject, "author_imgurl");
                author_content = get(jsonObject, "author_content");
                description = get(jsonObject, "description");
                imgurl = get(jsonObject, "imgurl");
                channel_id = get(jsonObject, "channel_id");
                type_id = get(jsonObject, "type_id");
                url = get(jsonObject, "url");
                replycount = get(jsonObject, "replycount");
                listencount = get(jsonObject, "listencount");
                sharecount = get(jsonObject, "sharecount");
                startdate = get(jsonObject, "startdate");
                enddate = get(jsonObject, "enddate");
                if (!jsonObject.isNull("author_list")
                        && !isNull(jsonObject.getString("author_list"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("author_list");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++) {
                        authors.add(new Author(jsonList.getJSONObject(i)));
                    }
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public Song(String id,String name, String url, String imgurl, String channel) {
        this.name = name;
        this.url = url;
        this.imgurl = imgurl;
        this.id = id;
        this.channel = channel;
        authors.clear();
    }
    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", author_imgurl='" + author_imgurl + '\'' +
                ", author_content='" + author_content + '\'' +
                ", description='" + description + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", url='" + url + '\'' +
                ", replycount='" + replycount + '\'' +
                ", listencount='" + listencount + '\'' +
                ", sharecount='" + sharecount + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", type_id='" + type_id + '\'' +
                ", dyflag='" + dyflag + '\'' +
                ", loveflag='" + loveflag + '\'' +
                '}';
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_imgurl() {
        return author_imgurl;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public String getType_id() {
        return type_id;
    }

    public String getLoveflag() {
        return loveflag;
    }

    public void setLoveflag(String loveflag) {
        this.loveflag = loveflag;
    }

    public void setAuthor_imgurl(String author_imgurl) {
        this.author_imgurl = author_imgurl;
    }

    public String getAuthor_content() {
        return author_content;
    }

    public void setAuthor_content(String author_content) {
        this.author_content = author_content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReplycount() {
        return replycount;
    }

    public void setReplycount(String replycount) {
        this.replycount = replycount;
    }

    public String getListencount() {
        return listencount;
    }

    public void setListencount(String listencount) {
        this.listencount = listencount;
    }

    public String getSharecount() {
        return sharecount;
    }

    public String getDyflag() {
        if (isNull(dyflag))
            dyflag = "0";
        return dyflag;
    }

    public void setDyflag(String dyflag) {
        this.dyflag = dyflag;
    }

    public void setSharecount(String sharecount) {
        this.sharecount = sharecount;
    }

    public String getStartdate() {
        if (isNull(startdate))
            startdate="0";
        return startdate;
    }

    public String getChannel() {
        return channel;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        if (isNull(enddate))
            enddate="0";
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
