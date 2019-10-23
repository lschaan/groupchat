package com.lschaan.groupchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lschaan.groupchat.server.mensagem.Mensagem;
import com.lschaan.groupchat.server.mensagem.TipoComando;
import com.lschaan.groupchat.server.mensagem.TipoMensagem;
import com.lschaan.groupchat.server.mensagem.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUsuario extends Thread {

  private Usuario usuario;
  private Socket socketCliente;
  private BufferedReader doUsuario;
  private ObjectMapper objectMapper;
  private Logger logger;

  public ThreadUsuario(Socket socketCliente, int id) throws IOException {
    this.socketCliente = socketCliente;
    this.doUsuario = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
    this.objectMapper = new ObjectMapper();
    this.usuario = new Usuario(id);
    this.logger = LoggerFactory.getLogger(ThreadUsuario.class);
  }

  public void run() {
    try {
      logger.info(this + " - Conexão estabelecida.");
      Mensagem mensagemNome = objectMapper.readValue(doUsuario.readLine(), Mensagem.class);
      mensagemNome.setRemetente(usuario);
      processarMensagem(mensagemNome);

      if (usuario.getNome() == null) throw new Exception();

      processarMensagem(new Mensagem(usuario, "entrou.", TipoMensagem.ACAO));

      while (true) {
        Mensagem mensagem = objectMapper.readValue(doUsuario.readLine(), Mensagem.class);
        mensagem.setRemetente(usuario);

        if (mensagem.getMensagem() == null) {
          logger.warn(this + " - O usuário não foi encontrado.");
          throw new Exception();
        }
        processarMensagem(mensagem);
      }
    } catch (Exception e) {
      logger.error(this + " - Conexão perdida com usuário.");
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
      case COMANDO:
        if (mensagem.getMensagem().equals(TipoComando.NAME.getComando())
            && mensagem.getComplemento() != null) {
          if (usuario.getNome() != null) {
            processarMensagem(
                new Mensagem(
                    usuario, "renomeado para " + mensagem.getComplemento(), TipoMensagem.ACAO));
          }
          usuario.setNome(mensagem.getComplemento());
          logger.info("Nome de " + this + " definido.");
        } else if (mensagem.getMensagem() == TipoComando.HELP.getComando()) {
          String mensagemAjuda = "Comandos: \n";
          for (TipoMensagem tipoMensagem : Arrays.asList(TipoMensagem.values())) {
            mensagemAjuda +=
                (tipoMensagem.getComando() != null ? tipoMensagem.getComando() + "\n" : "");
          }
          ThreadServidor.enviarMensagem(mensagemAjuda, this);
        } else {
          processarMensagem(new Mensagem(usuario, "Comando inexistente.", TipoMensagem.RESPOSTA));
        }
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
