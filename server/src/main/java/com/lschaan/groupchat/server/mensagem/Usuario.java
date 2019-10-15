package com.lschaan.groupchat.server.mensagem;

public class Usuario {
  private String nome;
  private int id;

  public Usuario(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String toString() {
    return "[User " + id + ", " + nome + "]";
  }
}
