package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadServidor extends Thread {
  private static List<Usuario> listaUsuarios;
  private static int id;
  private ServerSocket socketRecepcao;

  public ThreadServidor(ServerSocket socketRecepcao) {
    this.socketRecepcao = socketRecepcao;
    listaUsuarios = new ArrayList<Usuario>();
  }

  public static void enviarMensagem(String mensagem) {
    listaUsuarios
        .forEach(
            destinatario -> {
              if (destinatario.isAvaliable()) {
                escreverMensagem(mensagem, destinatario);
              }
            });
  }

  public static void removerUsuario(int id) {
    listaUsuarios.stream()
        .filter(usuario -> usuario.getIdUsuario() == id)
        .findFirst()
        .ifPresent(listaUsuarios::remove);
  }

  private static void escreverMensagem(String mensagem, Usuario usuario) {
    try {
      System.out.println("Enviando \"" + mensagem + "\" para usuário " + usuario);
      new DataOutputStream(usuario.getSocket().getOutputStream()).writeBytes(mensagem + '\n');
    } catch (Exception e) {
      System.out.println(usuario + " - ERROR: Problema no recebimento da mensagem.");
      removerUsuario(usuario.getIdUsuario());
    }
  }

  public void run() {
    try {
      while (true) {
        Socket socketConexao = socketRecepcao.accept();

        listaUsuarios.add(new Usuario(socketConexao, id++));
        new Thread(listaUsuarios.get(listaUsuarios.size() - 1)).start();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
