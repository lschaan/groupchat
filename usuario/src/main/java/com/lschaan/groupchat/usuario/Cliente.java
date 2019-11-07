package com.lschaan.groupchat.usuario;

import com.lschaan.groupchat.front.Front;

import java.net.ConnectException;
import java.net.Socket;

public class Cliente {
  private static String HOST = "127.0.0.1";
  private static int PORT = 6789;
  private static Socket socketCliente;

  public static void main(String argv[]) throws Exception {
    if (argv.length > 0) HOST = argv[0];
    if (argv.length > 1) PORT = Integer.parseInt(argv[1]);

    realizarConexao();
    Front.start();
    new Thread(new ThreadUserLeitura(socketCliente)).start();
    new Thread(new ThreadUserEscrita(socketCliente)).start();
  }

  private static void realizarConexao() throws Exception {
    while (true) {
      try {
        socketCliente = new Socket(HOST, PORT);
        break;
      } catch (ConnectException conexao) {
        System.out.println("Tentando conexão...");
        Thread.sleep(5000);
      }
    }
  }
}
