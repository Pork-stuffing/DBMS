package entity;

/*
 * �û�ʵ����
 */
public class UserDB {
	
	private Integer uid;//id
	private String account;//�˺�
	private String password;//����
	private String name;//����
	private String phone;//�绰
	private Integer times;//���Ĵ���
	private Integer lendNum;//�ɽ�������
	private Integer maxNum;//���ɽ���
	private Integer role;//��ɫ 1�û� 2����Ա
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getAccount() {
		return account;
	}
	@Override
	public String toString() {
		return "UserDB [uid=" + uid + ", account=" + account + ", password=" + password + ", name=" + name + ", phone="
				+ phone + ", times=" + times + ", lendNum=" + lendNum + ", maxNum=" + maxNum + ", role=" + role + "]";
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Integer getLendNum() {
		return lendNum;
	}
	public void setLendNum(Integer lendNum) {
		this.lendNum = lendNum;
	}
	public Integer getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	
}
