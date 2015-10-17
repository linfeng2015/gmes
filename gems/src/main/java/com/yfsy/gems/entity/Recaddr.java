package com.yfsy.gems.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gm_recaddr")
public class Recaddr extends IdEntity {
	private String name ;
	private String addr ;
	private String company ;	
	private int flag ;
	

	public Recaddr() {
	}

	public Recaddr(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}