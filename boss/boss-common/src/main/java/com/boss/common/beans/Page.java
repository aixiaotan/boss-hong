package com.boss.common.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page<T> implements Serializable {

	public Page() {
	}

	@SuppressWarnings("rawtypes")
	public Page(List list, int pageIndex, int pageSize, long totalSize) {
		this.list = list;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
	}

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	@SuppressWarnings("rawtypes")
	public static Map createResultMap(Page pageObj) {
		return createResultMap(pageObj, "list");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map createResultMap(Page pageObj, String listName) {
		Map result = new HashMap();
		if (pageObj == null) {
			return result;
		} else {
			Map pageMap = new HashMap();
			pageMap.put("count", Integer.valueOf(
					Integer.parseInt((new StringBuilder()).append("").append(pageObj.getTotalSize()).toString())));
			pageMap.put("page", Integer.valueOf(pageObj.getPageIndex()));
			pageMap.put("size", Integer.valueOf(pageObj.getPageSize()));
			result.put(listName, pageObj.getList());
			result.put("pages", pageMap);
			return result;
		}
	}

	private static final long serialVersionUID = -5638486944555354183L;
	@SuppressWarnings("rawtypes")
	List list;
	int pageIndex;
	int pageSize;
	long totalSize;
}
