package com.lschaan.groupchat.server.mensagem;

public enum TipoMensagem {
  MENSAGEM(null),
  ACAO(null),
  COMANDO("/"),
  RESPOSTA(null);

  private String comando;

  private TipoMensagem(String comando) {
    this.comando = comando;
  }

  public String getComando() {
    return comando;
  }
}
