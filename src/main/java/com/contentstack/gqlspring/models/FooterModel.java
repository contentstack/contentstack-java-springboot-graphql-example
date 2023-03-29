package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FooterModel {

    @JsonProperty
    public Object logoConnection;
    @JsonProperty
    public Object navigation;
    @JsonProperty
    public String title;
    //    @JsonProperty
//    public String copyright;
    @JsonProperty
    public Object social;


//    public String getHTML() {
//        JSONObject jsonCopyright = new JSONObject(this.copyright);
//        jsonCopyright.put("data", jsonCopyright);
//        String[] path = {"data"};
//        Utils.render(jsonCopyright, path, new DefaultOption());
//        return jsonCopyright.optString("data");
//    }
}
