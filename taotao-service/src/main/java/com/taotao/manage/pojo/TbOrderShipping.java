package com.taotao.manage.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TbOrderShipping implements Serializable{
    private String orderId;

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverState;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

    private Date created;

    private Date updated;

}