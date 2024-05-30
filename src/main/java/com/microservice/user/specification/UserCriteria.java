package com.microservice.user.specification;


import com.microservice.user.data.entity.User;
import org.springframework.data.jpa.domain.Specification;

public interface UserCriteria {
    default Specification<User> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%"+name+"%");
    }

    default Specification<User> hasEmailLike(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("email"), "%"+email+"%");
    }

    default Specification<User> hasPhoneLike(String phone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("email"), "%"+phone+"%");
    }

}
