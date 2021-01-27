package com.appsdeveloperblog.app.ws.io.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);

	UserEntity findByUserId(String id);

	@Query(value = "select * from users u where u.id =:id", nativeQuery = true)
	UserEntity findAllUsersByFirstName(@Param("id") String id);

	@Query(value = "select * from users u where u.first_name =:first_name and u.last_name =:last_name", nativeQuery = true)
	UserEntity findUserDetailsByFirstAndLastName(@Param("first_name") String first_name,
			@Param("last_name") String last_name);

}
