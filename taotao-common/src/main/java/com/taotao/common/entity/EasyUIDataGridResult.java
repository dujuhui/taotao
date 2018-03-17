package com.taotao.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EasyUIDataGridResult implements Serializable{

	private long total;
	private List rows;

	
}
