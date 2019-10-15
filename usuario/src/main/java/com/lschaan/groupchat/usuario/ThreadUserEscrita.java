package com.lschaan.groupchat.usuario;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lschaan.groupchat.usuario.mensagem.Mensagem;
import com.lschaan.groupchat.usuario.mensagem.TipoMensagem;

public class ThreadUserEscrita extends Thread {
  private static Socket socketCliente;
  private static DataOutputStream paraServidor;
  private static ObjectMapper objectMapper;

  public ThreadUserEscrita(Socket socketCliente) throws IOException {
    ThreadUserEscrita.socketCliente = socketCliente;
    ThreadUserEscrita.paraServidor = new DataOutputStream(socketCliente.getOutputStream());
    ThreadUserEscrita.objectMapper = new ObjectMapper();
  }

  public void run() {
    while (true) {
      if (socketCliente.isClosed()) break;
    }
  }

  public static void lerInput(String mensagem) {
    try {
      Mensagem mensagemJson = new Mensagem(mensagem, TipoMensagem.MENSAGEM);

      if (mensagem.startsWith("/" + TipoMensagem.HELP.toString().toLowerCase())) {
        mensagemJson.setMensagem(mensagem.substring(("/" + TipoMensagem.HELP).length()));
        mensagemJson.setTipo(TipoMensagem.HELP);
      }

      if (mensagem.startsWith("/" + TipoMensagem.NAME.toString().toLowerCase())) {
        mensagemJson.setMensagem(mensagem.substring(("/" + TipoMensagem.NAME + " ").length()));
        mensagemJson.setTipo(TipoMensagem.NAME);
      }

      paraServidor.writeBytes(objectMapper.writeValueAsString(mensagemJson) + '\n');
    } catch (IOException ioe) {
      System.out.println("Erro no envio da mensagem.");
    }
  }
}
