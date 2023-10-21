package oit.is.z2173.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import oit.is.z2173.kaizi.janken.model.*;

@Controller
public class JankenController {

  private String userName;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    ArrayList<User> users = userMapper.selectByUsers();
    ArrayList<Match> matches = matchMapper.selectByMatches();
    this.userName = prin.getName();
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    model.addAttribute("userName", this.userName);
    return "janken.html";
  }
  
  @GetMapping("/match")
  public String match(@RequestParam int id, ModelMap model){
    User user = userMapper.selectById(id);
    model.addAttribute("user", user);
    model.addAttribute("userName", this.userName);
    return "match.html";
  }
  
  @GetMapping("/fight")
  public String jankengame(@RequestParam int id, @RequestParam String PlayerHand, ModelMap model) {
    Match match = new Match();
    Janken janken = new Janken(PlayerHand);

    match.setUser1(userMapper.selectByName(this.userName).getId());
    match.setUser2(id);
    match.setUser1Hand(janken.getPlayerHand());
    match.setUser2Hand(janken.getComputerHand());
    matchMapper.insertMatch(match);

    model.addAttribute("user",userMapper.selectById(id));
    model.addAttribute("user1", janken.getPlayerHand());
    model.addAttribute("user2", janken.getComputerHand());
    model.addAttribute("result", janken.getResult());
    model.addAttribute("userName", this.userName);

    return "match.html";
  }
}
