package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Usuario extends Thread {

  private int id;
  private String nome;
  private Socket socketCliente;
  private BufferedReader doUsuario;

  public Usuario(Socket socketCliente, int id) throws IOException {
    this.socketCliente = socketCliente;
    this.id = id;
    this.doUsuario = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
  }

  public void run() {
    try {
      System.out.println(this + " - Conexão estabelecida.");
      this.nome = doUsuario.readLine();

      if (nome == null) throw new Exception();

      System.out.println(this + " - Nome \"" + nome + "\" definido.");
      ThreadServidor.enviarMensagem(nome + " entrou.");

      while (true) {
        String mensagem = doUsuario.readLine();
        if (mensagem == null) {
          System.out.println(this + " - O usuário não foi encontrado.");
          throw new Exception();
        }
        ThreadServidor.enviarMensagem(nome + ": " + mensagem);
      }
    } catch (Exception e) {
      System.out.println(this + " - ERROR: Conexão perdida com usuário.");
      ThreadServidor.removerUsuario(id);
    }
    if (nome != null) ThreadServidor.enviarMensagem(nome + " saiu.");
  }

  public int getIdUsuario() {
    return id;
  }

  public Socket getSocket() {
    return socketCliente;
  }

  public boolean isAvaliable() {
    return (socketCliente != null && (!socketCliente.isClosed()));
  }

  public String toString() {
    return "[User " + id + ", " + nome + "]";
  }
}
