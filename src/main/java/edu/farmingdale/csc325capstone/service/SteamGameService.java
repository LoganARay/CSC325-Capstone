package edu.farmingdale.csc325capstone.service;

public class SteamGameService {
    private String name;
    private String appid;
    private String storage;

    private GameRequirementsService minReq;
    private GameRequirementsService recReq;

    public SteamGameService(String name, String appid, String storage, GameRequirementsService minReq, GameRequirementsService recReq) {
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

    public GameRequirementsService getMinReq() {
        return minReq;
    }

    public void setMinReq(GameRequirementsService minReq) {
        this.minReq = minReq;
    }

    public GameRequirementsService getRecReq() {
        return recReq;
    }

    public void setRecReq(GameRequirementsService recReq) {
        this.recReq = recReq;
    }
}
