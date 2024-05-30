package com.microservice.user;

import com.microservice.user.data.dto.request.LoadUserListRequestDto;
import com.microservice.user.repository.UserRepository;
import com.microservice.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class SysUserApplicationTests {
	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		userService.loadUser(new LoadUserListRequestDto());
	}

}
