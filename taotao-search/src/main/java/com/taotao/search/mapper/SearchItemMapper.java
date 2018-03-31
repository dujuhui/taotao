package com.taotao.search.mapper;

import com.taotao.common.entity.SearchItem;

import java.util.List;


public interface SearchItemMapper {

	List<SearchItem> getItemList();

	SearchItem getItemById(long itemId);
}
