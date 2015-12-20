package com.easychat.model.msg;

/**
 * Created by xhans on 2015/12/20.
 */
public class CommandMsg {
    private String command;
    private String type;

    public CommandMsg() {
    }

    public CommandMsg(String command) {
        this.type = "COMMAND";
        this.type = "COMMAND";
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
