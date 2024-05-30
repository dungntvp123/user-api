package com.microservice.user.controller;

import com.microservice.user.common.ResponseBody;
import com.microservice.user.data.dto.request.AddFriendRequestDto;
import com.microservice.user.data.dto.request.DeleteFriendRequestDto;
import com.microservice.user.data.dto.request.LoadUserListRequestDto;
import com.microservice.user.data.dto.request.UpdateUserRequestDto;
import com.microservice.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> loadUserList(LoadUserListRequestDto requestDto) {
        ResponseBody<?> body = userService.loadUser(requestDto);
        return ResponseEntity.ok(body);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> loadUserDetail(@PathVariable("userId") String userId) {
        ResponseBody<?> body = userService.loadUser(userId);
        return ResponseEntity.ok(body);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") String userId,
                                        @RequestPart("user") UpdateUserRequestDto requestDto,
                                        @RequestPart("file") MultipartFile file) {
        log.info("updateUser");
        ResponseBody<?> body = userService.updateUser(userId, requestDto, file);
        return ResponseEntity.ok(body);
    }
    @PostMapping("/add-friend")
    public ResponseEntity<?> addFriend(AddFriendRequestDto requestDto) {
        ResponseBody<?> body = userService.addFriend(requestDto);
        return ResponseEntity.ok(body);
    }
    @DeleteMapping("/delete-friend")
    public ResponseEntity<?> deleteFriend(DeleteFriendRequestDto requestDto) {
        ResponseBody<?> body = userService.deleteFriend(requestDto);
        return ResponseEntity.ok(body);
    }
}
