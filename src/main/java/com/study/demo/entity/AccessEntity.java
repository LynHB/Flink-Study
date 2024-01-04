package com.study.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccessEntity {
    private Long id;

    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("access_url")
    private String accessUrl;
    @JsonProperty("access_module_code")
    private String accessModuleCode;
    @JsonProperty("url_method")
    private String urlMethod;
    @JsonProperty("url_params")
    private String urlParams;
    @JsonProperty("url_ip")
    private String urlIp;
    @JsonProperty("response_code")
    private String responseCode;
    @JsonProperty("response_msg")
    private String responseMsg;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("create_time")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("update_time")
    private Date updateTime;
    @JsonProperty("del_flag")
    private String delFlag;

    private String createBy;
    private String updateBy;
}
