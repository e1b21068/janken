package oit.is.z2173.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import oit.is.z2173.kaizi.janken.model.Entry;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String userName = prin.getName();
    this.entry.addUser(userName);
    model.addAttribute("entry", this.entry);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String PlayerHand, ModelMap model) {
    String Result = "";
    String ComputerHand = "gu";
    switch (PlayerHand) {
      case "gu":
        Result = "Draw";
        break;
      case "choki":
        Result = "You Lose";
        break;
      case "pa":
        Result = "You Win";
        break;
    }
    model.addAttribute("PlayerHand", PlayerHand);
    model.addAttribute("ComputerHand", ComputerHand);
    model.addAttribute("Result", Result);
    return "janken.html";
  }
}
