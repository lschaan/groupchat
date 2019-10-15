package com.lschaan.groupchat.usuario.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lschaan.groupchat.usuario.ThreadUserEscrita;

public class ServerButtonListener implements ActionListener {
  String username;

  public void actionPerformed(ActionEvent event) {
    username = Front.usernameChooser.getText();
    if (username.length() > 0) {
      Front.preFrame.setVisible(false);
      Front.display();
      ThreadUserEscrita.lerInput(username);
    }
  }
}
