package com.sxt.utils;
/**
 * ��ҳ������
 *
 */
public class PaginationUtils {
	
	//public static int page_num = 1;
	//public static int page_size = 5;
	public static String baseUrl = "book";
	
		
	/**
	 *  ��ҳ�ؼ�
	 * @param totalNum
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public static String getPagation(long totalNum,int currentPage,int pageSize,String path){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;//7,totalPage��ʾһ���ж���ҳ
		StringBuffer pageCode=new StringBuffer();
		pageCode.append("<div class=\"col-md-7 col-md-push-3\">");
		pageCode.append("<nav ><ul class=\"pagination pagination-md\" id=\"pageSorter\">");
		if(currentPage==1){
			pageCode.append("<li class='disabled'><a>��ҳ</a></li>");
			pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
			
		}else{
			pageCode.append("<li><a href='/" + baseUrl + "/"+ path +"&pageNum=1'>��ҳ</a></li>");
			pageCode.append("<li><a href='/" + baseUrl + "/"+ path +"&pageNum="+(currentPage-1)+"'>��һҳ</a></li>");
		}
		
		for(int i=currentPage-5;i<=currentPage+5;i++){
			if(i<1||i>totalPage){
				continue;
			}
			if(i==currentPage){
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			}else{
				pageCode.append("<li><a href='/" + baseUrl + "/"+path+"&pageNum="+i+"'>"+i+"</a></li>");
			}
		}
		
		if(currentPage==totalPage){
			pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
			pageCode.append("<li class='disabled'><a>βҳ</a></li>");
		}else{
			pageCode.append("<li><a href='/" + baseUrl + "/"+ path +"&pageNum="+(currentPage+1)+"'>��һҳ</a></li>");
			pageCode.append("<li><a href='/" + baseUrl + "/"+ path +"&pageNum="+totalPage+"'>βҳ</a></li>");
		}
		pageCode.append("</ul></nav></div>");
		return pageCode.toString();
	}
}
