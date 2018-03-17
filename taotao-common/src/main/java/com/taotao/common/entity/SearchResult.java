package com.taotao.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchResult implements Serializable{

	private long totalPages;
	private long recordCount;
	private List<SearchItem> itemList;

}
