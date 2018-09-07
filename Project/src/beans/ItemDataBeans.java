package beans;

import java.io.Serializable;

/**
 * アイテム
 * @author d-yamaguchi
 *
 */
public class ItemDataBeans implements Serializable {
	private int id;
	private String name;
	private String detail;
	private int price;
	private String filename;

	public ItemDataBeans(int id, String name, String detail, int price,String filename) {
		this.id = id;
		this.name = name;
		this.detail = detail;
		this.price = price;
		this.filename = filename;
	}

	public ItemDataBeans() {
	}

	public int getId() {
		return id;
	}
	public void setId(int Id) {
		this.id = Id;
	}
	public String getName() {
		return name;
	}
	public void setName(String Name) {
		this.name = Name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int Price) {
		this.price = Price;
	}
	public String getFileName() {
		return filename;
	}
	public void setFileName(String filename) {
		this.filename = filename;
	}




}
