package com.lschaan.groupchat.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadServidor extends Thread {
  private static List<ThreadUsuario> listaUsuarios;
  private ServerSocket socketRecepcao;
  private static int id;

  public ThreadServidor(ServerSocket socketRecepcao) {
    this.socketRecepcao = socketRecepcao;
    listaUsuarios = new ArrayList<ThreadUsuario>();
  }

  public void run() {
    try {
      while (true) {
        Socket socketConexao = socketRecepcao.accept();

        listaUsuarios.add(new ThreadUsuario(socketConexao, id++));
        new Thread(listaUsuarios.get(listaUsuarios.size() - 1)).start();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void removerUsuario(int id) {
    listaUsuarios
        .stream()
        .filter(usuario -> usuario.getIdUsuario() == id)
        .findFirst()
        .ifPresent(listaUsuarios::remove);
  }

  public static void enviarMensagem(String mensagem) {
    listaUsuarios
        .stream()
        .forEach(
            destinatario -> {
              if (destinatario.isAvaliable()) {
                escreverMensagem(mensagem, destinatario);
              }
            });
  }

  private static void escreverMensagem(String mensagem, ThreadUsuario usuario) {
    try {
      System.out.println("Enviando \"" + mensagem + "\" para usuário " + usuario);
      new DataOutputStream(usuario.getSocket().getOutputStream()).writeBytes(mensagem + '\n');
    } catch (Exception e) {
      System.out.println(usuario + " - ERROR: Problema no recebimento da mensagem.");
      removerUsuario(usuario.getIdUsuario());
    }
  }
}
