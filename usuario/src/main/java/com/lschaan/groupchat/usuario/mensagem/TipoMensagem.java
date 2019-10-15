package com.lschaan.groupchat.usuario.mensagem;

public enum TipoMensagem {
  NAME("/name"),
  MENSAGEM(null),
  ACAO(null),
  HELP("/help");

  private String comando;

  private TipoMensagem(String comando) {
    this.comando = comando;
  }

  public String getComando() {
    return comando;
  }
}
