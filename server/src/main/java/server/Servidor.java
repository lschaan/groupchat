package server;

import java.net.BindException;
import java.net.ServerSocket;

public class Servidor {
  private static final int PORT = 6789;

  public static void main(String[] args) throws Exception {
    try {
      ServerSocket chat = new ServerSocket(PORT);
      System.out.println("Servidor iniciado.");
      new Thread(new ThreadServidor(chat)).start();
    } catch (BindException bind) {
      System.out.println("O endereço já está em uso.");
    }
  }
}
