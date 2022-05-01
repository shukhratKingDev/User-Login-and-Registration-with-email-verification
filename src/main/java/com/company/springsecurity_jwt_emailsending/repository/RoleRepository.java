package com.company.springsecurity_jwt_emailsending.repository;

import com.company.springsecurity_jwt_emailsending.entity.Role;
import com.company.springsecurity_jwt_emailsending.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(RoleName roleName);
}
