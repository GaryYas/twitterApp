package com.example.demo.profile;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Component
@Scope(value="session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserProfileSession implements Serializable {

    private String twitterHandle;
    private String email;
    private java.time.LocalDate birthDate;
    private List<String> tastes = new ArrayList<>();


    public void saveFrom(ProfileForm profileForm){
        this.twitterHandle=profileForm.getTwitterHandle();
        this.birthDate=profileForm.getBirthDate();
        this.email=profileForm.getEmail();
        this.tastes=profileForm.getTastes();
    }

    public ProfileForm toForm(){

        ProfileForm profileForm =new ProfileForm();
        profileForm.setBirthDate(birthDate);
        profileForm.setEmail(email);
        profileForm.setTwitterHandle(twitterHandle);
        profileForm.setTastes(tastes);
        return profileForm;
    }


}
