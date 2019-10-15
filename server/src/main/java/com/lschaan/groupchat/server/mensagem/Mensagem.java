package com.lschaan.groupchat.server.mensagem;

public class Mensagem {
  private Usuario remetente;
  private String mensagem;
  private TipoMensagem tipo;

  public Mensagem(Usuario remetente, String mensagem, TipoMensagem tipo) {
    this.remetente = remetente;
    this.mensagem = mensagem;
    this.tipo = tipo;
  }
  
  public Mensagem () {
	  
  }

  public Usuario getRemetente() {
    return remetente;
  }

  public void setRemetente(Usuario remetente) {
    this.remetente = remetente;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public TipoMensagem getTipo() {
    return tipo;
  }

  public void setTipo(TipoMensagem tipo) {
    this.tipo = tipo;
  }
}
