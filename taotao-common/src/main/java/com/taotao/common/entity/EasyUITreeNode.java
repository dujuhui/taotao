package com.taotao.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EasyUITreeNode implements Serializable{

	private long id;
	private String text;
	private String state;

	
}
