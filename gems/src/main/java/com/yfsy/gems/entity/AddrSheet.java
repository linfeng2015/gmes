/**
 * 
 */
package com.yfsy.gems.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="gm_addrsheet")
public class AddrSheet extends IdEntity{
	
	private String name ;
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
