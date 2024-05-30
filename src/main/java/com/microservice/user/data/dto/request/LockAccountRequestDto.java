package com.microservice.user.data.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LockAccountRequestDto {
    private int lockedTime;

    @JsonIgnore
    public long getLockedTimeInLong() {
        return lockedTime * 60*60*1000;
    }
}
