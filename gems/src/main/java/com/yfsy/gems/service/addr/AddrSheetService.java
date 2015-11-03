package com.yfsy.gems.service.addr;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.yfsy.gems.entity.AddrSheet;
import com.yfsy.gems.repository.AddrSheetDao;
import com.yfsy.gems.repository.RecaddrDao;

@Component
@Transactional(readOnly = true)
public class AddrSheetService {
	
	@Autowired
	private AddrSheetDao addrSheetDao ;
	
	@Autowired
	private RecaddrDao recaddrDao ;
	
    @Transactional(readOnly = false)
    public boolean save(AddrSheet addrSheet){
    	boolean success = false ;
    	try{
    		addrSheetDao.save(addrSheet);
    		success = true ;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return success ;
    }

    @Transactional(readOnly = false)
    public boolean update(AddrSheet addrSheet){
    	boolean success = false ;
    	try{
    		addrSheetDao.save(addrSheet);
    		success = true ;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return success ;
    }
    
    public AddrSheet getAddrSheet(Long id){
    	AddrSheet addrSheet = new AddrSheet();
    	addrSheet = addrSheetDao.findOne(id);
    	return addrSheet;
    }
    
    @Transactional(readOnly = false)
    public boolean deleteAddrSheet(Long id){
    	boolean success = false ;
    	try{
    		recaddrDao.deleteBySheetId(id);
    		addrSheetDao.delete(id);
    		success = true ;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return success ;
    }
	public Page<AddrSheet> getAddrSheets(Map<String, Object> filterParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<AddrSheet> spec = buildSpecification(filterParams);
		return addrSheetDao.findAll(spec, pageRequest);
	}
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<AddrSheet> buildSpecification(Map<String, Object> filterParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		//filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<AddrSheet> spec = DynamicSpecifications.bySearchFilter(filters.values(), AddrSheet.class);
		return spec;
	}
	
	
}
