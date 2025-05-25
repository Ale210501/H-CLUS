package com.progetto.extension.extension;

/**
 *  Classe eccezione sollevata in caso di errori lato Server.
 */
public class ServerException extends Exception{
    private String message;
    public ServerException(String message){
        this.message=message;
    }
    public String getMessage(){
        return message;
    }
}
