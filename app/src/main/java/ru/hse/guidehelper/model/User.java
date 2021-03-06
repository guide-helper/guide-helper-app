package ru.hse.guidehelper.model;

import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class User implements IUser, Serializable {
    private String userMail;

    private Boolean isGuide;

    private String name;

    private String phoneNumber; // для гидов

    private String city; // для гидов

    private String description; // для гидов

    private String avatarUrl;

    private String token;

    @Override
    public String getId() {
        return userMail;
    }

    @Override
    public String getAvatar() {
        return avatarUrl;
    }
}
