package com.abapi.cloud.common.utils.pager;

import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pager {

	public static Pager initPage(PagerModel pagerModel){
		return new Pager(pagerModel);
	}

	private Pager(PagerModel pagerModel){
		this.m = pagerModel;
		this.currentPage = pagerModel.getCurrentPage();
		this.pageSize = pagerModel.getPageSize();
	}

	/** 结果集 */
	private List<?> result;
	/** 记录数 */
	private int totalRecords;
	/** 每页多少条数据 */
	private int pageSize = 10;
	/** 第几页 */
	private int currentPage = 1;
	/***参数模型1*/
	private Object m;
	/** 参数列表2 */
	private Map<String, Object> p = new HashMap<String, Object>();
	/** 排序 */
	private String orderBy;
	/** asc/顺序、desc/倒序 */
	private String order;
	/**页条显示数量**/
	private int displayCount = 10;
	/**当前页条**/
	private int [] rainbow;
	/**
	 * 返回总页数
	 * 
	 * @return
	 */
	public int getTotalPages() {
		return (totalRecords + pageSize - 1) / pageSize;
	}

	/**
	 * 首页
	 */
	public int getTopcurrentPage() {
		return 1;
	}

	/**
	 * 上一页
	 */
	public int getPreviouscurrentPage() {
		if (this.currentPage <= 1) {
			return 1;
		}
		return this.currentPage - 1;
	}

	/**
	 * 下一页
	 */
	public int getNextcurrentPage() {
		if (this.currentPage >= getButtomcurrentPage()) {
			return getButtomcurrentPage();
		}
		return this.currentPage + 1;
	}

	/**
	 * 尾页
	 */
	public int getButtomcurrentPage() {
		return getTotalPages();
	}

	/**
	 * @Description: TODO 获取当前页的记录数
	 */
	public int getCurrentPageRecords() {
		return result == null ? 0 : result.size();
	}

	public int getFirstResult() {
		return (currentPage - 1) * pageSize;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Object getM() {
		return m;
	}

	public void setM(Object m) {
		this.m = m;
	}

	public Map<String, Object> getP() {
		return p;
	}

	public void setP(Map<String, Object> p) {
		this.p = p;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		if (StrUtil.isNotEmpty(order)
				&& (order.equalsIgnoreCase("asc") || order
						.equalsIgnoreCase("desc"))) {
			this.order = order;
		} else {
			this.order = "asc";
		}
	}

	public int [] getRainbow(){
		return PageUtil.rainbow(currentPage,getTotalPages(),displayCount);
	}

}