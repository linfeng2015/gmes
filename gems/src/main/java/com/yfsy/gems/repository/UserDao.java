package com.yfsy.gems.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.yfsy.gems.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
}
