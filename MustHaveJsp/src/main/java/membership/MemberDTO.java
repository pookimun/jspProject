package membership;

public class MemberDTO { //member테이블의 정보 객체화
	private String id;
	private String pass;
	private String name;
	private String regidate;
	//자바빈즈 규약에 따라 생성자도 생성해 주었음
	public MemberDTO() {
	}
	public MemberDTO(String id, String pass, String name, String regidate) {
		super();
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.regidate = regidate;
	}
	public String getId() {return id;}
	public String getPass() {return pass;}
	public String getName() {return name;}
	public String getRegidate() {return regidate;}
	public void setId(String id) {this.id = id;}
	public void setPass(String pass) {this.pass = pass;}
	public void setName(String name) {this.name = name;}
	public void setRegidate(String regidate) {this.regidate = regidate;}
}
