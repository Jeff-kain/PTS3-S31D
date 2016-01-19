/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import java.io.Serializable;
import java.rmi.registry.Registry;

/**
 *
 * @author jeffrey
 */
public class Manager implements Serializable {

    static Manager manager;
    IRemoteHost remotehost = null;

    public IRemoteHost getRemotehost() {
        return remotehost;
    }

    private int player1;
    private int player2;

    public int getPlayer1() {
        return player1;
    }

    public void setPlayer1(int player1) {
        this.player1 = player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public void setPlayer2(int player2) {
        this.player2 = player2;
    }

    int amplayer = 1;
    int feldx = 15;
    int feldxbml;
    int feldy = 15;
    int feldybml;
    int densWall = 1;
    int highscoreP1 = 0;
    int highscoreP2 = 0;

    boolean randomlvl = true;
    boolean spiegelung = false;
    boolean standardlvl = true;
    boolean bmllevel = false;
    boolean boolKI = false;
    boolean boolLAN = false;
    boolean boolClient = false;
    boolean boolSpectate = false;
    boolean sound = false;
    IRemoteClient remoteclient = null;
    ISpectate remoteSpectate = null;

    String levelpath = "data/levels/level_1.bml";
    String levelname = "";
    String message2P;
    String namePlayer1;
    String namePlayer2;
    String namePlayer3;
    String namePlayer4;

    Registry registry = null;

    public Manager() {

    }

    public static Manager getManager() {
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    public int getAmplayer() {
        return amplayer;
    }

    public void setAmplayer(int amplayer) {
        this.amplayer = amplayer;
    }

    public int getFeldx() {
        return feldx;
    }

    public void setFeldx(int feldx) {
        this.feldx = feldx;
    }

    public int getFeldxbml() {
        return feldxbml;
    }

    public void setFeldxbml(int feldxbml) {
        this.feldxbml = feldxbml;
    }

    public int getFeldy() {
        return feldy;
    }

    public void setFeldy(int feldy) {
        this.feldy = feldy;
    }

    public int getFeldybml() {
        return feldybml;
    }

    public void setFeldybml(int feldybml) {
        this.feldybml = feldybml;
    }

    public int getDensWall() {
        return densWall;
    }

    public void setDensWall(int densWall) {
        this.densWall = densWall;
    }

    public int getHighscoreP1() {
        return highscoreP1;
    }

    public void setHighscoreP1(int highscoreP1) {
        this.highscoreP1 = highscoreP1;
    }

    public int getHighscoreP2() {
        return highscoreP2;
    }

    public void setHighscoreP2(int highscoreP2) {
        this.highscoreP2 = highscoreP2;
    }

    public boolean isRandomlvl() {
        return randomlvl;
    }

    public void setRandomlvl(boolean randomlvl) {
        this.randomlvl = randomlvl;
    }

    public boolean isSpiegelung() {
        return spiegelung;
    }

    public void setSpiegelung(boolean spiegelung) {
        this.spiegelung = spiegelung;
    }

    public boolean isStandardlvl() {
        return standardlvl;
    }

    public void setStandardlvl(boolean standardlvl) {
        this.standardlvl = standardlvl;
    }

    public boolean isBmllevel() {
        return bmllevel;
    }

    public void setBmllevel(boolean bmllevel) {
        this.bmllevel = bmllevel;
    }

    public boolean isBoolKI() {
        return boolKI;
    }

    public void setBoolKI(boolean boolKI) {
        this.boolKI = boolKI;
    }

    public boolean isBoolLAN() {
        return boolLAN;
    }

    public void setBoolLAN(boolean boolLAN) {
        this.boolLAN = boolLAN;
    }

    public boolean isBoolClient() {
        return boolClient;
    }

    public void setBoolClient(boolean boolClient) {
        this.boolClient = boolClient;
    }

    public ISpectate getRemoteSpectate() {
        return remoteSpectate;
    }

    public void setRemoteSpectate(ISpectate remoteSpectate) {
        this.remoteSpectate = remoteSpectate;
    }

    public boolean isBoolSpectate() {
        return boolSpectate;
    }

    public void setBoolSpectate(boolean boolSpectate) {
        this.boolSpectate = boolSpectate;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public IRemoteClient getRemoteclient() {
        return remoteclient;
    }

    public void setRemoteclient(IRemoteClient remoteclient) {
        this.remoteclient = remoteclient;
    }

    public String getLevelpath() {
        return levelpath;
    }

    public void setLevelpath(String levelpath) {
        this.levelpath = levelpath;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public String getMessage2P() {
        return message2P;
    }

    public void setMessage2P(String message2P) {
        this.message2P = message2P;
    }

    public String getNamePlayer1() {
        return namePlayer1;
    }

    public void setNamePlayer1(String namePlayer1) {
        this.namePlayer1 = namePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

    public void setNamePlayer2(String namePlayer2) {
        this.namePlayer2 = namePlayer2;
    }

    public String getNamePlayer3() {
        return namePlayer3;
    }

    public void setNamePlayer3(String namePlayer3) {
        this.namePlayer3 = namePlayer3;
    }

    public String getNamePlayer4() {
        return namePlayer4;
    }

    public void setNamePlayer4(String namePlayer4) {
        this.namePlayer4 = namePlayer4;
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public void setRemotehost(IRemoteHost remotehost) {
        this.remotehost = remotehost;
    }

}
