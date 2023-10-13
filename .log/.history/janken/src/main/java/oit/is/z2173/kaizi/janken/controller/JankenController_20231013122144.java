package oit.is.z2173.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import oit.is.z2173.kaizi.janken.model.Entry;
import oit.is.z2173.kaizi.janken.model.Janken;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String userName = prin.getName();
    this.entry.addUser(userName);
    model.addAttribute("users", this.entry.getUsers());
    model.addAttribute("userName", userName);
    model.addAttribute("entry", this.entry);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String PlayerHand, ModelMap model) {
    Janken janken = new Janken(PlayerHand);
    model.addAttribute("users", this.entry.getUsers());
    model.addAttribute("PlayerHand", janken.getPlayerHand());
    model.addAttribute("ComputerHand", janken.getComputerHand());
    model.addAttribute("Result", janken.getResult());
    return "janken.html";
  }
}
