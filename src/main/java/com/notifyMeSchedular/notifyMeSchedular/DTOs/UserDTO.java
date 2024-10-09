package com.notifyMeSchedular.notifyMeSchedular.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String userId;
    private String userName;
    private HashMap<String,String> userNotificationTypes;
}
