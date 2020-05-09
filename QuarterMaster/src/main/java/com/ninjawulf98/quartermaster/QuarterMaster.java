package com.ninjawulf98.quartermaster;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ninjawulf98.quartermaster.commands.ListCommand;
import com.ninjawulf98.quartermaster.commands.LockCommand;
import com.ninjawulf98.quartermaster.listeners.MenuListeners;
import com.ninjawulf98.quartermaster.listeners.ChestListeners;
import com.ninjawulf98.quartermaster.utils.LockMenuSystem;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class QuarterMaster extends JavaPlugin {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private static MongoCollection<Document> col;

    public static HashMap<Player, LockMenuSystem> lockMenuSystemHashMap = new HashMap<>();

    @Override
    public void onEnable() {

        mongoClient = MongoClients.create("mongodb://mongodb:mongodb@localhost:27017");
        database = mongoClient.getDatabase("quartermaster");
        col = database.getCollection("locks");


        getCommand("lock").setExecutor(new LockCommand());
        getCommand("list").setExecutor(new ListCommand());

        Bukkit.getPluginManager().registerEvents(new MenuListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ChestListeners(), this);
    }

    @Override
    public void onDisable() {
    }

    public static MongoCollection<Document> getDatabaseCollection() {
        return col;
    }

    public static LockMenuSystem getPlayerMenuSystem(Player p) {

        if(QuarterMaster.lockMenuSystemHashMap.containsKey(p)){
            return lockMenuSystemHashMap.get(p);
        } else {
            LockMenuSystem lockMenuSystem = new LockMenuSystem(p);
            lockMenuSystemHashMap.put(p, lockMenuSystem);

            return lockMenuSystem;
        }
    }
}
