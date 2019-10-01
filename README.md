# GroupChat

## Dependências 

Para rodar o projeto, você precisa das seguintes dependências instaladas em sua máquina:

* Java 8+
* Gradle 5.6+ [Opcional]

> Você pode usar o wrapper do Gradle, ao invés de instala-lo em sua máquina.

## Como rodar?

O projeto tem dois pacotes que precisam ser executados para que tudo funcione, o primeiro é o `server` que é o servidor e o segundo é o `usuario` onde fica a interface do usuário (cliente).

### Rodando o servidor

Você precisa executar o servidor primeiro, para que os usuários consigam conectar-ser, para isso basta executar os seguintes comandos:

```shell
cd server
./gradlew build
java -jar build/libs/server.jar
```

Você deve ver a mensagem `Servidor iniciado.`, depois disso o servidor está pronto para receber conexões.

### Rodando o cliente
Para executar o cliente, por onde os usuários enviaram mensagens ao servidor, você precisa executar os sequintes comandos:
```shell
cd usuario
./gradlew run
java -jar build/libs/usuario.jar
```

Uma janela com um campo para colocar seu nome deve aparecer na tela, depois de preencher, clique em *Entrar*.

> Você pode executar vários clientes, basta executar `java -jar build/libs/usuario.jar` em outra aba do seu terminal.
