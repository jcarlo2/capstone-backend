package com.capstone.backend.repository;

import com.capstone.backend.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,String> {
    boolean existsUserByIdAndPassword(String username, String password);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    User findByIdAndPassword(String username, String password);
    List<User> findAllByIdNotContainsAndFirstNameNotContainingAndLastNameNotContaining(String id, String firstName, String lastName);

    @Transactional @Modifying
    void removeById(String id);

    @Transactional @Modifying
    @Query(value = "UPDATE user SET password = ?3 WHERE id = ?1 AND password = ?2 AND id != '7777' AND last_name != 'admin' AND  first_name != 'admin'",nativeQuery = true)
    void changePassword(String id, String oldPassword, String newPassword);
}
