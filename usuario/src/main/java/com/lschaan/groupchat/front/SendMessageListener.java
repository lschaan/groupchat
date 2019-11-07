package com.lschaan.groupchat.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendMessageListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
    Front.sendMessage();
  }
}
