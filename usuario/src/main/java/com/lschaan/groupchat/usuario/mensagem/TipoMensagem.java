package com.lschaan.groupchat.usuario.mensagem;

public enum TipoMensagem {
  COMANDO("/"),
  MENSAGEM(null),
  ACAO(null);

  private String comando;

  private TipoMensagem(String comando) {
    this.comando = comando;
  }

  public String getComando() {
    return comando;
  }
}
