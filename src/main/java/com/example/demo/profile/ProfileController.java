package com.example.demo.profile;


import com.example.demo.date.USLocalDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class ProfileController {



    @RequestMapping("/profile")
    public String displayProfile(ProfileForm profileForm,HttpServletRequest req){
        return "profile/profilePage";
    }


    private UserProfileSession userProfileSession;

    @Autowired
    public ProfileController(UserProfileSession userProfileSession){
        this.userProfileSession=userProfileSession;
    }

    @ModelAttribute
    public ProfileForm getProfileForm(){
        return userProfileSession.toForm();
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST )
    public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return "profile/profilePage";

        userProfileSession.saveFrom(profileForm);
        System.out.println("save ok " + profileForm);
        return "redirect:/profile";
    }

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale) {
        return USLocalDateFormatter.getPattern(locale);
    }

    @RequestMapping(value = "/profile", params = {"addTaste"})
    public String addRow(ProfileForm profileForm,HttpServletRequest req) {
        profileForm.getTastes().add(null);
        return "profile/profilePage";
    }


    @RequestMapping(value = "/profile", params = {"removeTaste"})
    public String removeRow(ProfileForm profileForm,HttpServletRequest req) {
        Integer rowId = Integer.valueOf(req.getParameter("removeTaste"));
        profileForm.getTastes().remove(rowId.intValue());
        return "profile/profilePage";
    }



}
