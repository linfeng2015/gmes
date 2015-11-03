/**
 * 
 */
package com.yfsy.gems.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yfsy.gems.entity.Recaddr;

/**
 * @author lyf
 *
 */
public interface RecaddrDao extends PagingAndSortingRepository<Recaddr,Long>, JpaSpecificationExecutor<Recaddr> {
	@Query("select a from Recaddr a where a.addrSheet.id = :sheetId") 
	Page<Recaddr> findBySheetId(@Param("sheetId")Long sheetId,Pageable pageRequest); 
	
	@Modifying
	@Query("delete from Recaddr a where a.addrSheet.id = :sheetId")
	void deleteBySheetId(@Param("sheetId")Long sheetId);
}
