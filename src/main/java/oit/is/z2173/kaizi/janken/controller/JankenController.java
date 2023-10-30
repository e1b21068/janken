package oit.is.z2173.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import oit.is.z2173.kaizi.janken.model.*;
import oit.is.z2173.kaizi.janken.service.AsyncKekka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class JankenController {

  private final Logger logger = LoggerFactory.getLogger(JankenController.class);

  @Autowired
  private AsyncKekka ak;

  private String userName;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  private MatchInfoMapper matchInfoMapper;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    ArrayList<User> users = userMapper.selectByUsers();
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    ArrayList<MatchInfo> matchInfos = matchInfoMapper.selectAllMatchInfo();
    ArrayList<MatchInfo> activeMatches = matchInfoMapper.selectActiveMatchInfo();
    this.userName = prin.getName();
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    model.addAttribute("matchInfos", matchInfos);
    model.addAttribute("activeMatches", activeMatches);
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
  public String wait(@RequestParam int id, @RequestParam String PlayerHand, ModelMap model){
    MatchInfo matchInfo = matchInfoMapper.selectByUsers(id, userMapper.selectByName(this.userName).getId());
    if(matchInfo == null){
      makeMatch(id, PlayerHand, model);
    }else{
      addMatch(id, matchInfo.getUser1Hand(), PlayerHand, model);
    }
    return "wait.html";
  }

  private void makeMatch(int id, String PlayerHand, ModelMap model){
    MatchInfo matchInfo = new MatchInfo();
    matchInfo.setUser1(userMapper.selectByName(this.userName).getId());
    matchInfo.setUser2(id);
    matchInfo.setUser1Hand(PlayerHand);
    matchInfo.setIsActive(true);
    matchInfoMapper.insertMatchInfo(matchInfo);

    model.addAttribute("user",userMapper.selectById(id));
    model.addAttribute("userName", this.userName);
  }

  private void addMatch(int id, String Player1Hand, String Player2Hand, ModelMap model){
    Match match = new Match();
    MatchInfo matchInfo = matchInfoMapper.selectByUsers(id, userMapper.selectByName(this.userName).getId());
    Janken janken = new Janken(Player1Hand, Player2Hand);

    match.setUser1(id);
    match.setUser2(userMapper.selectByName(this.userName).getId());
    match.setUser1Hand(janken.getPlayer1Hand());
    match.setUser2Hand(janken.getPlayer2Hand());
    match.setIsActive(true);
    matchInfo.setIsActive(false);
    matchInfoMapper.updateMatchInfo(matchInfo);
    matchMapper.insertMatch(match);
    this.ak.setDbUpdated(true);

    model.addAttribute("user1", janken.getPlayer1Hand());
    model.addAttribute("user2", janken.getPlayer2Hand());
    model.addAttribute("userName", this.userName);
  }

  @GetMapping("/result")
  public SseEmitter printKekka(){
    logger.info("printKekka");
    final SseEmitter emitter = new SseEmitter();
    this.ak.printKekka(emitter);
    return emitter;
  }

}
