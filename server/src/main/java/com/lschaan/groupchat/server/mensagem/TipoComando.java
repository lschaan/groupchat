package com.lschaan.groupchat.server.mensagem;

public enum TipoComando {
  NAME("/name"),
  HELP("/help");

  private String comando;

  private TipoComando(String comando) {
    this.comando = comando;
  }

  public String getComando() {
    return comando;
  }
}
