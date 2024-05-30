package com.microservice.user.service;

import com.microservice.user.common.ResponseBody;
import com.microservice.user.data.dto.request.*;
import com.microservice.user.specification.UserCriteria;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends UserCriteria {
    public ResponseBody<?> loadUser(String userId);
    public ResponseBody<?> loadUser(LoadUserListRequestDto requestDto);
    public ResponseBody<?> updateUser(String userId, UpdateUserRequestDto requestDto, MultipartFile file);
    public ResponseBody<?> addFriend(AddFriendRequestDto requestDto);
    public ResponseBody<?> deleteFriend(DeleteFriendRequestDto requestDto);
}
