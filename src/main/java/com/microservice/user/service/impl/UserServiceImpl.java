package com.microservice.user.service.impl;

import com.microservice.user.common.EventType;
import com.microservice.user.common.ResponseBody;
import com.microservice.user.common.ResponseStatus;
import com.microservice.user.config.SystemConfig;
import com.microservice.user.data.dto.request.*;
import com.microservice.user.data.dto.response.LoadUserResponseDto;
import com.microservice.user.data.entity.User;
import com.microservice.user.repository.ActionLogRepository;
import com.microservice.user.repository.UserRepository;
import com.microservice.user.service.ActionLogService;
import com.microservice.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActionLogService actionLogService;
    @Override
    public ResponseBody<?> loadUser(String userId) {
        User user = userRepository.findById(userId).get();
        actionLogService.logAction(userId, EventType.LOAD_DATA, "load data from user", "success");
        return new ResponseBody<>(ResponseStatus.LOAD_USER_DETAIL_SUCCESSFUL, new LoadUserResponseDto().getDetail(user));
    }

    @Override
    public ResponseBody<?> loadUser(LoadUserListRequestDto requestDto) {
        requestDto.getFormatted();
        Specification<User> specification = hasEmailLike(requestDto.getEmail())
                .and(hasNameLike(requestDto.getName()))
                .and(hasPhoneLike(requestDto.getPhone()));
        Pageable pageable = PageRequest.of(requestDto.getPageIndex()-1, 20);
        Page<User> users = userRepository.findAll(specification, pageable);

        return new ResponseBody<>(ResponseStatus.LOAD_USER_LIST_SUCCESSFUL,
                users.map(user -> new LoadUserResponseDto().getBrief(user)));
    }

    @Override
    @Transactional
    public ResponseBody<?> updateUser(String userId, UpdateUserRequestDto requestDto, MultipartFile file) {
        User user = userRepository.findById(userId).get();
        user.setBio(requestDto.getBio());
        user.setPhone(requestDto.getPhone());
        user.setName(requestDto.getName());
        user.setImage(updateImage(file, user.getImage()));

        userRepository.save(user);
        return new ResponseBody<>(ResponseStatus.USER_INFO_UPDATE_SUCCESSFUL, new LoadUserResponseDto().getDetail(user));
    }

    @Override
    @Transactional
    public ResponseBody<?> addFriend(AddFriendRequestDto requestDto) {
        List<User> users = userRepository.findAllById(Arrays.asList(requestDto.getToUserId(), requestDto.getFromUserId()));
        users.get(0).getFriends().add(users.get(1));
        users.get(1).getFriends().add(users.get(0));

        userRepository.saveAll(users);
        return new ResponseBody<>(ResponseStatus.ADD_FRIEND_SUCCESSFUL);
    }

    @Override
    @Transactional
    public ResponseBody<?> deleteFriend(DeleteFriendRequestDto requestDto) {
        List<User> users = userRepository.findAllById(Arrays.asList(requestDto.getToUserId(), requestDto.getFromUserId()));
        users.get(0).getFriends().remove(users.get(1));
        users.get(1).getFriends().remove(users.get(0));

        userRepository.saveAll(users);
        return new ResponseBody<>(ResponseStatus.ADD_FRIEND_SUCCESSFUL);
    }

    private String updateImage(MultipartFile file, String oldImageName) {
        String dir = SystemConfig.UPLOAD_FILE_DIRECTORY + "image/";
        if (!file.isEmpty()) {
            try {
                log.info(file.getOriginalFilename());
                String fileName = System.currentTimeMillis() + "." + file.getOriginalFilename().split("\\.")[1];
                if (oldImageName != null) {
                    File oldImage = new File(dir + oldImageName);
                    oldImage.delete();
                }
                File newImage = new File(dir + fileName);
                file.transferTo(newImage);
                return fileName;
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        } else {
            throw new RuntimeException("file is empty");
        }
    }
}
