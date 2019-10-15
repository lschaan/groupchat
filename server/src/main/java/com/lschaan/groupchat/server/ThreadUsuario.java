package com.lschaan.groupchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lschaan.groupchat.server.mensagem.Mensagem;
import com.lschaan.groupchat.server.mensagem.TipoMensagem;
import com.lschaan.groupchat.server.mensagem.Usuario;

public class ThreadUsuario extends Thread {

  private Usuario usuario;
  private Socket socketCliente;
  private BufferedReader doUsuario;
  private ObjectMapper objectMapper;

  public ThreadUsuario(Socket socketCliente, int id) throws IOException {
    this.socketCliente = socketCliente;
    this.doUsuario = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
    this.objectMapper = new ObjectMapper();
    usuario = new Usuario(id);
  }

  public void run() {
    try {
      System.out.println(this + " - Conexão estabelecida.");
      Mensagem mensagemNome = objectMapper.readValue(doUsuario.readLine(), Mensagem.class);
      mensagemNome.setTipo(TipoMensagem.NAME);
      mensagemNome.setRemetente(usuario);
      processarMensagem(mensagemNome);

      if (usuario.getNome() == null) throw new Exception();

      processarMensagem(new Mensagem(usuario, "entrou.", TipoMensagem.ACAO));

      while (true) {
        Mensagem mensagem = objectMapper.readValue(doUsuario.readLine(), Mensagem.class);
        mensagem.setRemetente(usuario);

        if (mensagem.getMensagem() == null) {
          System.out.println(this + " - O usuário não foi encontrado.");
          throw new Exception();
        }
        processarMensagem(mensagem);
      }
    } catch (Exception e) {
      System.out.println(this + " - ERROR: Conexão perdida com usuário.");
      ThreadServidor.removerUsuario(usuario.getId());
    }
    if (usuario.getNome() != null)
      processarMensagem(new Mensagem(usuario, "saiu.", TipoMensagem.ACAO));
  }

  public void processarMensagem(Mensagem mensagem) {
    switch (mensagem.getTipo()) {
      case MENSAGEM:
        ThreadServidor.enviarMensagem(
            mensagem.getRemetente().getNome() + ": " + mensagem.getMensagem());
        break;
      case ACAO:
        ThreadServidor.enviarMensagem(
            "*" + mensagem.getRemetente().getNome() + " " + mensagem.getMensagem());
        break;
      case NAME:
        if (usuario.getNome() != null) {
          processarMensagem(
              new Mensagem(usuario, "renomeado para " + mensagem.getMensagem(), TipoMensagem.ACAO));
        }
        usuario.setNome(mensagem.getMensagem());
        System.out.println("Nome de " + this + " definido.");
        break;
      case HELP:
        String mensagemAjuda =
            Arrays.asList(TipoMensagem.values())
                .stream()
                .map(TipoMensagem::toString)
                .collect(Collectors.joining(", "));
        ThreadServidor.enviarMensagem(mensagemAjuda, this);
        break;
      default:
        break;
    }
  }

  public int getIdUsuario() {
    return usuario.getId();
  }

  public Socket getSocket() {
    return socketCliente;
  }

  public String getNome() {
    return usuario.getNome();
  }

  public boolean isAvaliable() {
    return (socketCliente == null ? false : (socketCliente.isClosed() ? false : true));
  }

  public String toString() {
    return usuario.toString();
  }
}
