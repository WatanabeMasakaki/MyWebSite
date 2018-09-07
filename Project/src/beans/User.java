package beans;

import java.util.Date;


public class User {
	private int id;
	private String login_id;
	private String name;
	private Date birth_date;
	private String password;
	private String create_date;
	private String update_date;



	public User(String login_id, String name) {
		this.login_id = login_id;
		this.name = name;
	}


	public User(int id,String login_id,String name,Date birth_date,String password,String create_date,String update_date) {
		this.id = id;
		this.login_id = login_id;
		this.name = name;
		this.birth_date = birth_date;
		this.password = password;
		this.create_date = create_date;
		this.update_date = update_date;

	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getlogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}



	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}



	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}



	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
}
