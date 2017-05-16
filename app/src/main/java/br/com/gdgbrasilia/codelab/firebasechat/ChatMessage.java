package br.com.gdgbrasilia.codelab.firebasechat;

/**
 * Created by patricknasc on 15/05/17.
 */

public class ChatMessage {
    public String name;
    public String message;

    public ChatMessage() {
    }

    public ChatMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }
}