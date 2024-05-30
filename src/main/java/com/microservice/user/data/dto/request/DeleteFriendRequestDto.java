package com.microservice.user.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteFriendRequestDto {
    private String fromUserId;
    private String toUserId;
}
