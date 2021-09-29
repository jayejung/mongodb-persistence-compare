package com.kakao.at.ticketdev.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userCol")
public class UserEntity {
    @Id
    private String id;
    private String passwd;
    private String userType;

    public UserEntity(String id, String passwd, String userType) {
        this.id = id;
        this.passwd = passwd;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getUserType() {
        return userType;
    }
}
