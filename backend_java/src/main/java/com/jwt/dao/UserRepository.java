package com.jwt.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jwt.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
}
