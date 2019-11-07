package com.lschaan.groupchat.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerButtonListener implements ActionListener {
  String mensagem;

  public void actionPerformed(ActionEvent event) {
    mensagem = Front.usernameChooser.getText();
    if (mensagem.length() > 0) {
      Front.preFrame.setVisible(false);
      Front.display();
      Front.sendMessage(mensagem);
    }
  }
}
