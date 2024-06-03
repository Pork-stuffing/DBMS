package entity;
/*
 * Õº È¿‡±
 */

public class TypeDB {

	private Integer tid;
	private String typeName;
	
	@Override
	public String toString() {
		return "TypeDB [tid=" + tid + ", typeName=" + typeName + "]";
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
