package entity;

import java.util.Date;

/*
 * ͼ�������ʷ��¼
 */
public class HistoryDB {
	
	private Integer hid;//��¼id
	private Integer uid;//�û�id
	private String name;//�û�����
	private String account;//�û��˺�
	private Integer bid;//ͼ��id
	private String bookName;//����
	private Date beginTime;//����ʱ��
	private Date endTime;//����ʱ��
	private Integer status;//����״̬��1Ϊ���ڽ��ģ�2Ϊ�Ѿ�����
	public Integer getHid() {
		return hid;
	}
	public void setHid(Integer hid) {
		this.hid = hid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "HistoryDB [hid=" + hid + ", uid=" + uid + ", name=" + name + ", account=" + account + ", bid=" + bid
				+ ", bookName=" + bookName + ", beginTime=" + beginTime + ", endTime=" + endTime + ", status=" + status
				+ "]";
	}
	
	
	
}
