package com.springbootajax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springbootajax.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value="SELECT u FROM User u WHERE CONCAT(u.id,'',u.firstName,'',u.lastName)LIKE %?1%")
	public List<User> search(String keyword);

}
