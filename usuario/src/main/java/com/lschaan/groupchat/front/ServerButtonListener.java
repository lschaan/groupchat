package com.lschaan.groupchat.front;

import com.lschaan.groupchat.usuario.ThreadUserEscrita;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
