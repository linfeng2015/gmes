/**
 * 
 */
package com.yfsy.gems.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yfsy.gems.entity.AddrSheet;
import com.yfsy.gems.entity.Recaddr;

/**
 * @author Administrator
 *
 */
public interface AddrSheetDao extends PagingAndSortingRepository<AddrSheet,Long>, JpaSpecificationExecutor<AddrSheet> {
}
