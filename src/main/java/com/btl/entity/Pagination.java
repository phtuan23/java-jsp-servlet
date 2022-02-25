package com.btl.entity;

import java.util.List;

public class Pagination<T> {
	private int totalPage;
	private List<T> lists;
	private int currentPage;
	
	public Pagination() {
	}

	public Pagination(int totalPage, List<T> lists, int currentPage) {
		this.totalPage = totalPage;
		this.lists = lists;
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getLists() {
		return lists;
	}

	public void setLists(List<T> lists) {
		this.lists = lists;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
