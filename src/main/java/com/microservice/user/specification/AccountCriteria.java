package com.microservice.user.specification;

import com.microservice.user.data.entity.Account;
import com.microservice.user.data.entity.User;
import jakarta.persistence.criteria.Join;
import org.hibernate.query.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface AccountCriteria {
    default Specification<Account> hasUsernameLike(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("username"), "%"+(username == null ? "" : username)+"%");
    }

    default Specification<Account> hasEmailLike(String email) {
        return (root, query, criteriaBuilder) -> {
            Join<Account, User> accountUserJoin = root.join("user");
            return criteriaBuilder.like(accountUserJoin.get("email"), "%"+(email == null ? "" : email)+"%");
        };
    }

    default Pageable pageOrderByUsername(Integer pageIndex, Integer pageSize, String orderByUsername) {
        return PageRequest.of(pageIndex, pageSize,
                orderByUsername.equals("desc") ?
                        Sort.by("username").descending() :
                        Sort.by("username").ascending());
    }
    default Pageable pageOrderByCreateDate(Integer pageIndex, Integer pageSize, String orderByCreateDate) {
        return PageRequest.of(pageIndex, pageSize,
                orderByCreateDate.equals("desc") ?
                        Sort.by("createDate").descending() :
                        Sort.by("createDate").ascending());
    }


}
