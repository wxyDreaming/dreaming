package com.wuxinyu.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("serial")
public class PageController implements Serializable{
	private String statment; //执行数据库操作的语句，可以简单的SQL语句、带参数的SQL或存储过程
	@SuppressWarnings("rawtypes")
	private List items;

	@SuppressWarnings("rawtypes")
	private Vector args; //参数列表

	private String classOfItem; //单个记录所对应的JAVA类

	private String methodOfItem; //获取单个记录所用方法，该方法必须classOfItem类中定义

	public final static int SIMPLE_SQL = 0; //常量

	public final static int PRESTATMENT = 1; //常量

	public final static int CALLABLESTATMENT = 2; //常量

	private int totalRowsAmount; //总行数

	@SuppressWarnings("unused")
	private boolean rowsAmountSet; //是否设置过totalRowsAmount

	private int pageSize = 10; //每页行数

	private int currentPage = 1; //当前页码

	private int nextPage;

	private int previousPage;

	private int totalPages; //总页数

	private boolean hasNext; //是否有下一页

	private boolean hasPrevious; //是否有前一页

	private int pageStartRow;

	private int pageEndRow;

	public PageController(int totalRows) {

		setTotalRowsAmount(totalRows);

	}
	@SuppressWarnings("rawtypes")
	public PageController(List items,int totalCount,int pageSize, int startIndex) {
		this.items=items;
		this.pageSize=pageSize;
		setTotalRowsAmount(totalCount);
		setCurrentPage(startIndex);
	}
	
	public PageController(int pageNo, int pageSize) {
		this.pageSize=pageSize;
		setCurrentPage(pageNo);
	}

	/**
	 * @param i
	 *            设定总行数
	 */
	public void setTotalRowsAmount(int i) {

		//if (!this.rowsAmountSet) {
			totalRowsAmount = i;
			totalPages = totalRowsAmount / pageSize
					+ (totalRowsAmount % pageSize > 0 ? 1 : 0);
			setCurrentPage(1);
			this.rowsAmountSet = true;

		//}
	}

	public void setStatment(String statment) {
		this.statment = statment;
	}

	public String getStatment() {
		return statment;
	}

	@SuppressWarnings("rawtypes")
	public void setArgs(Vector args) {
		this.args = args;
	}

	@SuppressWarnings("rawtypes")
	public Vector getArgs() {
		return args;
	}

	public void setClassOfItem(String classOfItem) {
		this.classOfItem = classOfItem;
	}

	public String getClassOfItem() {
		return classOfItem;
	}

	public void setMethodOfItem(String methodOfItem) {
		this.methodOfItem = methodOfItem;
	}

	public String getMethodOfItem() {
		return methodOfItem;
	}

	/**
	 * @param i
	 * 
	 * 当前页
	 *  
	 */

	public void setCurrentPage(int i) {
		currentPage = i;
		nextPage = currentPage + 1;
		previousPage = currentPage - 1;
		//计算当前页开始行和结束行
		if (currentPage * pageSize < totalRowsAmount) {
			pageEndRow = currentPage * pageSize;
			pageStartRow = pageEndRow - pageSize + 1;
		} else {
			pageEndRow = totalRowsAmount;
			pageStartRow = pageSize * (totalPages - 1) + 1;
		}

		//是否存在前页和后页
		if (nextPage > totalPages) {
			hasNext = false;
		} else {
			hasNext = true;
		}
		if (previousPage == 0) {
			hasPrevious = false;
		} else {
			hasPrevious = true;
		}
	}

	/**
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @return
	 */
	public boolean isHasNext() {
		return hasNext;
	}

	/**
	 * @return
	 */
	public boolean isHasPrevious() {
		return hasPrevious;
	}

	/**
	 * @return
	 */
	public int getNextPage() {
		return nextPage;
	}

	/**
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return
	 */
	public int getPreviousPage() {
		return previousPage;
	}

	/**
	 * @return
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @return
	 */

	public int getTotalRowsAmount() {
		return totalRowsAmount;
	}

	/**
	 * @param b
	 */

	public void setHasNext(boolean b) {
		hasNext = b;
	}

	/**
	 * @param b
	 */

	public void setHasPrevious(boolean b) {
		hasPrevious = b;
	}

	/**
	 * @param i
	 */

	public void setNextPage(int i) {
		nextPage = i;
	}

	/**
	 * @param i
	 */

	public void setPageSize(int i) {
		pageSize = i;
	}

	/**
	 * @param i
	 */

	public void setPreviousPage(int i) {
		previousPage = i;
	}

	/**
	 * @param i
	 */

	public void setTotalPages(int i) {
		totalPages = i;
	}

	/**
	 * @return
	 */

	public int getPageEndRow() {
		return pageEndRow;
	}

	/**
	 * @return
	 */

	public int getPageStartRow() {
		return pageStartRow;
	}

	public String getDescription() {
		String description = "Total:" + this.getTotalRowsAmount() + " items "
				+ this.getTotalPages() + " pages, Current page:"
				+ this.currentPage;
		return description;

	}
	
	public String getDescription2() {
		return getDescription2(0);
	}
	
	/**
	 * 传统的分页显示方式
	 * @param i
	 * @return
	 */
	public String getDescription2(int i) {
		StringBuffer description = new StringBuffer("");
		description
				.append("<table border=0 width=100% cellspacing=0 cellpadding=0>\n<tr>\n<td style='border:0px;' align='right'>&nbsp;&nbsp;第&nbsp;<b>")
				.append(this.currentPage)
				.append("</b>&nbsp;页&nbsp;&nbsp;共&nbsp;<b>")
				.append(this.totalPages)
				.append("</b>&nbsp;页&nbsp; <b>")
				.append(this.totalRowsAmount)
				.append("</b>&nbsp;条记录");
		if(this.currentPage==1&&hasNext){
			description.append("首页&nbsp;&nbsp;|&nbsp;&nbsp;前页&nbsp;&nbsp;|&nbsp;&nbsp;<a style='color: #15428B;' href=\"javascript:goPage(")
			.append(this.nextPage+","+totalRowsAmount+","+totalPages+");\">后页</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a style='color: #15428B;' href=\"javascript:goPage(")
			.append(totalPages+","+totalRowsAmount+","+totalPages+");\">末页</a>")
			.append("&nbsp;&nbsp;跳至&nbsp;<input style=\"border:1 solid #C5CDD4\" type=text name=\"Pagenum"+i+"\" size=\"1\"  >&nbsp;页&nbsp;<input class='btn' onmouseover=\"this.style.color='red'\" style=\"cursor:hand\"  onmouseout=\"this.style.color='#000000'\" type=\"button\" name=\"go\" value=\"Go\" onclick=\"javascript:isNaN(document.all.Pagenum"+i+".value) || eval(document.all.Pagenum"+i+".value)<1 || eval(document.all.Pagenum"+i+".value)>"+totalPages+"?alert('无效页码'):goPage(")
			.append("document.all.Pagenum"+i+".value"+","+totalRowsAmount+","+totalPages+");\" >");
			
		}else if(this.currentPage==this.totalPages&&hasPrevious){
			description.append("&nbsp;<a style='color: #15428B;' href=\"javascript:goPage(")
			.append(1+","+totalRowsAmount+","+totalPages+");\">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a style='color: #15428B;' href=\"javascript:goPage(")
			.append(this.previousPage+","+totalRowsAmount+","+totalPages+");\">前页</a>&nbsp;&nbsp;|&nbsp;&nbsp;后页&nbsp;&nbsp;|&nbsp;&nbsp;末页")
			.append("&nbsp;&nbsp;跳至&nbsp;<input style=\"border:1 solid #C5CDD4\" type=text name=\"Pagenum"+i+"\" size=\"1\"  >&nbsp;页&nbsp;<input class='btn' onmouseover=\"this.style.color='red'\" style=\"cursor:hand\"  onmouseout=\"this.style.color='#000000'\" type=\"button\" name=\"go\" value=\"Go\" onclick=\"javascript:isNaN(document.all.Pagenum"+i+".value) || eval(document.all.Pagenum"+i+".value)<1 || eval(document.all.Pagenum"+i+".value)>"+totalPages+"?alert('无效页码'):goPage(")
			.append("document.all.Pagenum"+i+".value"+","+totalRowsAmount+","+totalPages+");\" >");
			
		}else if(currentPage>1&&currentPage<totalPages){
			description.append("&nbsp;<a style='color: #15428B;' href=\"javascript:goPage(")
			.append(1+","+totalRowsAmount+","+totalPages+");\">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a style='color: #15428B;' href=\"javascript:goPage(")
			.append(this.previousPage+","+totalRowsAmount+","+totalPages+");\">前页</a>&nbsp;&nbsp;|&nbsp;&nbsp;");
			description.append("<a style='color: #15428B;' href=\"javascript:goPage(")
			.append(this.nextPage+","+totalRowsAmount+","+totalPages+");\">后页</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a style='color: #15428B;' href=\"javascript:goPage(")
			.append(totalPages+","+totalRowsAmount+","+totalPages+");\">末页</a>")
			.append("&nbsp;&nbsp;跳至&nbsp;<input style=\"border:1 solid #C5CDD4\" type=text name=\"Pagenum"+i+"\" size=\"1\"  >&nbsp;页&nbsp;<input class='btn' onmouseover=\"this.style.color='red'\" style=\"cursor:hand\"  onmouseout=\"this.style.color='#000000'\" type=\"button\" name=\"go\"  value=\"Go\" onclick=\"javascript:isNaN(document.all.Pagenum"+i+".value) || eval(document.all.Pagenum"+i+".value)<1 || eval(document.all.Pagenum"+i+".value)>"+totalPages+"?alert('无效页码'):goPage(")
			.append("document.all.Pagenum"+i+".value"+","+totalRowsAmount+","+totalPages+");\" >");
		}
		description.append("&nbsp;&nbsp;</td></tr>" )
				.append("\n</table>");
		if(i==1){
			description.append("&nbsp;&nbsp;<script type='text/javascript'>function goPage(pageno,total,pagesizze){document.getElementById('pageno').value=pageno;document.forms[0].submit();}</script>");	
		}
		
		return description.toString();
	}
	
	
	/**
	 * 另外一种分页的现实方式
	 * @return
	 */
	public String getDescription3(){
		StringBuffer description = new StringBuffer("");
		int startno=pageSize*(currentPage-1)+1;   //开始行
		int endno=totalRowsAmount<(pageSize*currentPage)?totalRowsAmount:pageSize*currentPage; //结束行
		int prepage=0;
		int endpage=0;
		if(currentPage%6!=0) {
			prepage=currentPage/6*6+1;
		    endpage=(currentPage/6+1)*6;
		    if(currentPage==totalPages) endpage=totalPages;
		    if(endpage>=totalPages) endpage=totalPages;
		}else{
			prepage=(currentPage/6-1)*6+1;
			endpage=currentPage;
			if(prepage<1) prepage=1;
			if(currentPage==totalPages) endpage=totalPages;
			if(endpage>=totalPages) endpage=totalPages;
		}
		description
		  .append("<table class=\"PageTable\" border=0 width=100% cellspacing=0 cellpadding=2>\n<tr>\n<td width=40% nowrap>&nbsp;&nbsp;第<b>&nbsp;")
		  .append(startno)
		  .append("-"+endno+"&nbsp;</b>条&nbsp;&nbsp;共&nbsp;<b>")
		  .append(this.totalRowsAmount)
		  .append("</b>&nbsp;条记录\n")
		  .append("</b>共&nbsp;<b>")
		  .append(this.totalPages)
		  .append("</b>&nbsp;页&nbsp; <b>");
		if(currentPage!=1)
			description.append("<a href=\"javascript:goPage("+1+","+totalRowsAmount+","+totalPages+")\">首页</a>");
		else
			description.append("首页");
		for(int i=prepage;i<currentPage;i++){
			description.append("&nbsp;<a href=\"javascript:goPage("+i+","+totalRowsAmount+","+totalPages+")\">["+i+"]</a>&nbsp;");
		}
		description.append("&nbsp;"+currentPage+"&nbsp;");
		for(int i=(currentPage+1);i<=endpage;i++){
			description.append("&nbsp;<a href=\"javascript:goPage("+i+","+totalRowsAmount+","+totalPages+")\">["+i+"]</a>&nbsp;");
		}
		if(endpage!=currentPage){
		  description.append("&nbsp;&nbsp;<a href=\"javascript:goPage("+(currentPage+1)+","+totalRowsAmount+","+totalPages+")\">下一页</a>");
		  description.append("&nbsp;&nbsp;<a href=\"javascript:goPage("+totalPages+","+totalRowsAmount+","+totalPages+")\">尾页</a>&nbsp;&nbsp;");
		}else{
			  description.append("&nbsp;&nbsp;下一页");
			  description.append("&nbsp;&nbsp;尾页&nbsp;&nbsp;");
		}	
		description.append("<select name=pagenum onchange=\"javascript:javascript:goPage(this.value,"+totalRowsAmount+","+totalPages+")\">");
		description.append("<option value='1'>转到</option>");
		for(int i=0;i<totalPages;i++){
			description.append("<option value='"+(i+1)+"'>第&nbsp;"+(i+1)+"&nbsp;页</option>");
		}
		description.append("</select></td></tr></table>");
		description.append("&nbsp;&nbsp;<script>function goPage(pageno,total,pagesizze){document.all.pageno.value=pageno;document.form.submit();}</script>");
		return description.toString();
	}

	public String getDescription1() {
		StringBuffer description = new StringBuffer("");
		description
				.append("<table  border=0 width=100% cellspacing=0 cellpadding=0>\n<tr>\n<td style=\"border: 0px;\" >&nbsp;&nbsp;第&nbsp;<b>")
				.append(this.currentPage)
				.append("</b>&nbsp;页&nbsp;&nbsp;共&nbsp;<b>")
				.append(this.totalPages)
				.append("</b>&nbsp;页&nbsp; <b>")
				.append(this.totalRowsAmount)
				.append("</b>&nbsp;条记录</td>")
				.append("<td style=\"border: 0px;\">");
		if(this.currentPage==1&&hasNext){
			description.append("&nbsp;<a href=\"javascript:goPage(")
			.append(this.nextPage+","+totalRowsAmount+","+totalPages+");\">后页</a>&nbsp;<a  href=\"javascript:goPage(")
			.append(totalPages+","+totalRowsAmount+","+totalPages+");\">末页</a>");
			
		}else if(this.currentPage==this.totalPages&&hasPrevious){
			description.append("&nbsp;<a href=\"javascript:goPage(")
			.append(1+","+totalRowsAmount+","+totalPages+");\">首页</a>&nbsp;<a  href=\"javascript:goPage(")
			.append(this.previousPage+","+totalRowsAmount+","+totalPages+");\">前页</a>");
			
		}else if(currentPage>1&&currentPage<totalPages){
			description.append("&nbsp;<a href=\"javascript:goPage(")
			.append(1+","+totalRowsAmount+","+totalPages+");\">首页</a>&nbsp;<a href=\"javascript:goPage(")
			.append(this.previousPage+","+totalRowsAmount+","+totalPages+");\">前页</a>");
			description.append("&nbsp;<a href=\"javascript:goPage(")
			.append(this.nextPage+","+totalRowsAmount+","+totalPages+");\">后页</a>&nbsp;<a href=\"javascript:goPage(")
			.append(totalPages+","+totalRowsAmount+","+totalPages+");\">末页</a>");
		}
		description.append("</td></tr>" )
				.append("</table>");
		description.append("&nbsp;&nbsp;<script>function goPage(pageno,total,pagesizze){document.all.pageno.value=pageno;document.form.submit();}</script>");
		return description.toString();

	}

	public String description() {
		String description = "Total:" + this.getTotalRowsAmount() + " items "
				+ this.getTotalPages() + " pages,Current page:"
				+ this.currentPage + " Previous " + this.hasPrevious + " Next:"
				+ this.hasNext + " start row:" + this.pageStartRow
				+ " end row:" + this.pageEndRow;
		return description;

	}

	@SuppressWarnings("rawtypes")
	public List getItems() {
		return items;
	}
	@SuppressWarnings("rawtypes")
	public void setItems(List items) {
		this.items = items;
		if(totalPages == 0){
			setTotalRowsAmount(items.size());
		}
	}
}