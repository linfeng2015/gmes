package com.yfsy.gems.service.addr;

import java.util.List;
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
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yfsy.gems.entity.Recaddr;
import com.yfsy.gems.repository.RecaddrDao;

@Component
@Transactional(readOnly = true)
public class RecaddrService {
	
	private RecaddrDao recaddrDao ;
	
    public List<Recaddr> getAllRecaddr(){
		return (List<Recaddr>)recaddrDao.findAll();
    }
	public boolean save(Recaddr recaddr){
		boolean flag = false ;
		try{
			recaddrDao.save(recaddr);
			flag = true ;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag ;
	}
	@Transactional(readOnly = false)
	public boolean delete(List<Long> ids){
		boolean success = false ;
		for(Long id :ids){
			try{
				recaddrDao.delete(id);
				success = true ;
			}catch(Exception e){
				success = false ;
				e.printStackTrace();
			}
		}
		return success ;
		
	}
	public Page<Recaddr> getRecaddrsBySheet(Long sheetId,Map<String, Object> filterParams, int pageNumber, int pageSize,
			String sortType){
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,sortType);
		Specification<Recaddr> spec = buildSpecification(filterParams,sheetId);
		return recaddrDao.findBySheetId(sheetId,pageRequest);
		
	}
	public Page<Recaddr> getRecaddrs(Map<String, Object> filterParams, int pageNumber, int pageSize,
			String sortType,Long sheetId) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Recaddr> spec = buildSpecification(filterParams,sheetId);
		return recaddrDao.findAll(spec, pageRequest);
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
	private Specification<Recaddr> buildSpecification(Map<String, Object> filterParams,Long sheetId) {
		//System.out.println("in the buildSpecification sheetId:"+sheetId);
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		filters.put("addrSheet.id", new SearchFilter("addrSheet.id", Operator.EQ, sheetId));
		Specification<Recaddr> spec = DynamicSpecifications.bySearchFilter(filters.values(), Recaddr.class);
		return spec;
	}
	
	@Autowired
	public void setRecaddrDao(RecaddrDao recaddrDao) {
		this.recaddrDao = recaddrDao;
	}
}
