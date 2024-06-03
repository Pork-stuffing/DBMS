package com.sxt.utils;

import java.util.List;

public class PageTool<E> {
	
	public PageTool(Integer totalCount, String currentPage, String pageSize) {
		this.totalCount = totalCount;
		initCurrentPage(currentPage);
		initPageSize(pageSize);
		initTotalPages();
		initPrePage();
		initNextPage();
		initStartIndex();
	}
	
	private Integer pageSize;//ҳ����
	private Integer totalCount;//�ܼ�¼��
	private Integer totalPages;//��ҳ��
	private Integer currentPage;//��ǰҳ��
	private Integer prePage;//��һҳ
	private Integer nextPage;//��һҳ
	private Integer startIndex;//ÿҳ��һ����¼����ʼ�±�
	private List<E> rows; //�����ǰҳ������
	
	//����ǰҳ���ʼ��
	private void initCurrentPage(String currentPage) {
		if (currentPage == null) {
			this.currentPage = 1;
		}else {
			this.currentPage=Integer.valueOf(currentPage);
		}
	}
	
	//Ĭ�Ϸ�ҳ����
	private void initPageSize(String pageSize) {
		if (pageSize == null) {
			this.pageSize = 5;
		}else {
			this.pageSize=Integer.valueOf(pageSize);
		}
	}
	
	//������ҳ��
	private void initTotalPages() {
		this.totalPages= (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);
	}
	
	//��һҳ
	private void initPrePage() {
		if (currentPage == 1) {
			this.prePage = 1;
		}else {
			this.prePage=currentPage - 1;
		}
	}
	//��һҳ
	private void initNextPage() {
		if (currentPage == totalPages) {
			this.nextPage = totalPages;
		}else {
			this.nextPage=currentPage + 1;
		}
	}
	
	//ÿҳ��һ����¼�ĳ�ʼ�±�
	private void initStartIndex() {
		this.startIndex = pageSize * (currentPage - 1);
	}
	
	
	
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPrePage() {
		return prePage;
	}

	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PageTool [pageSize=" + pageSize + ", totalCount=" + totalCount + ", totalPages=" + totalPages
				+ ", currentPage=" + currentPage + ", prePage=" + prePage + ", nextPage=" + nextPage + ", startIndex="
				+ startIndex + ", rows=" + rows + "]";
	}
	
}