package com.example.demo.controllers;


import com.example.demo.date.USLocalDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class TweetController {

    @Autowired
    private Twitter twitter;

    /*
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String hello(@RequestParam(defaultValue = "world") String userName, Model model){
        model.addAttribute("message","hello "+ userName);
        return "resultPage";
    }*/
    @RequestMapping("/t")
    public String hellot(@RequestParam(defaultValue = "gary coleman") String search,Model model){
        SearchResults searchResults = twitter.searchOperations().search(search);
       // String text = searchResults.getTweets().get(0).getText();

        List<String> tweets =  searchResults.getTweets().stream().map(Tweet::getText).collect(Collectors.toList());
        model.addAttribute("tweets",tweets);
        return "resultPage";
    }

    @RequestMapping("/result")
    public String hello(@RequestParam(defaultValue = "gary coleman") String search,Model model){
        SearchResults searchResults = twitter.searchOperations().
                search(search);
        List<Tweet> tweets = searchResults.getTweets();
        model.addAttribute("tweets", tweets);
        model.addAttribute("search", search);
        return "resultPage";
    }

    @RequestMapping("/")
    public String home(){
        return "searchPage";
    }

    @RequestMapping(value = "/postSearch",method =RequestMethod.POST)
    public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String search = request.getParameter("search");
        if(search.toLowerCase().equals("struts")){
            redirectAttributes.addFlashAttribute("error","Try using spring instead");
            return "redirect:/";
        }

        redirectAttributes.addAttribute("search",search);
        return "redirect:result";
    }




}
