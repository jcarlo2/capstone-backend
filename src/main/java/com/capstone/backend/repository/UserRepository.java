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
    boolean existsUserByIdAndPasswordAndIsActive(String id, String password, String isActive);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    User findByIdAndPasswordAndIsActive(String username, String password,String isActive);
    List<User> findAllByIdNotContainsAndFirstNameNotContainingAndLastNameNotContainingAndIsActive(String id, String firstName, String lastName,String isActive);

    @Transactional @Modifying
    @Query(value = "UPDATE user SET is_active = 0 WHERE id = ?1",nativeQuery = true)
    void archiveUserAccount(String id);

    @Transactional @Modifying
    void removeById(String id);

    @Transactional @Modifying
    @Query(value = "UPDATE user SET password = ?3 WHERE id = ?1 AND password = ?2 AND id != '7777' AND last_name != 'admin' AND  first_name != 'admin'",nativeQuery = true)
    void changePassword(String id, String oldPassword, String newPassword);
}
