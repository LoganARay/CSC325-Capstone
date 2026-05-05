package edu.farmingdale.csc325capstone.service;

public class SteamGame {
    private String name;
    private String appid;
    private String storage;

    private GameRequirements minReq;
    private GameRequirements recReq;

    public SteamGame(String name, String appid, String storage, GameRequirements minReq, GameRequirements recReq) {
        this.name = name;
        this.appid = appid;
        this.storage = storage;
        this.minReq = minReq;
        this.recReq = recReq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public GameRequirements getMinReq() {
        return minReq;
    }

    public void setMinReq(GameRequirements minReq) {
        this.minReq = minReq;
    }

    public GameRequirements getRecReq() {
        return recReq;
    }

    public void setRecReq(GameRequirements recReq) {
        this.recReq = recReq;
    }
}
