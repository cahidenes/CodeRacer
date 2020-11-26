package com.cenes.coderacer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

import java.awt.Image;

// Can we see this

public class ShopItem {

    private String name;
    private Texture image;
    private int requiredLevel;

    //contains name of other shop items that we have to have to unlock this item
    private String[] requiredItems;

    private boolean is_locked;
    private boolean is_equipped;
    private boolean is_unequippable;

    public ShopItem(String name, int reqLvl , boolean is_unequippable){
        this.name = name;
        Initialize();
        this.requiredLevel = reqLvl;
        this.is_unequippable = is_unequippable;
        Save();
    }

    public ShopItem(String name, int reqLvl, String[] reqItems , boolean is_unequippable){
        this.name = name;
        Initialize();
        this.requiredLevel = reqLvl;
        this.requiredItems = reqItems;
        this.is_unequippable = is_unequippable;
        Save();
    }

    // do not call this before setting name
    private void Initialize(){
        if (this.name == null){
            System.out.println("Cannot call initialize without name");
            return;
        }

        this.image = new Texture(Gdx.files.internal("shop_"+name+".png" ));
        image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.requiredItems = new String[0];
        this.is_locked = IsLocked(this.name);
        System.out.print(this.is_equipped);
        System.out.print( this.is_locked);
        this.is_equipped = IsEquipped(this.name);
        Save();
    }

    public void Unlock(){
        if (this.name == null) {
            System.out.println("You are trying to unlock an undefined shop item. Don't do that");
            return;
        }
        this.is_locked = false;
        Equip();// this method will save
    }

    public void Equip(){
        if (this.name == null){
            System.out.println("You are trying to equip an undefined shop item. Don't do that");
            return;
        }
        this.is_equipped = true;
        Save();
    }
    public void Unequip(){
        if (this.name == null){
            System.out.println("You are trying to unequip an undefined shop item. Don't do that");
            return;
        }
        this.is_equipped = false;
        Save();
    }
    public boolean IsUnequippable(){
        return this.is_unequippable;
    }
    public boolean IsLocked(){
        return this.is_locked;
    }
    public boolean IsEquipped(){
        return this.is_equipped;
    }
    public int GetRequiredLevel(){
        return this.requiredLevel;
    }
    public String[] GetRequiredItems(){
        return this.requiredItems;
    }
    public Texture GetImage(){
        return this.image;
    }
    public boolean IsRequiredItemsSatisfies(){
        Preferences preferences = Gdx.app.getPreferences("coderacer");
        boolean flag = true;
        for (int i = 0; i < GetRequiredItems().length; i ++){
            String required = GetRequiredItems()[i];
            if (preferences.getBoolean(required+"locked")){
                flag = false;
                break;
            }
        }

        return flag;
    }


    public static boolean IsLocked(String name){
        Preferences preferences = Gdx.app.getPreferences("coderacer");
        if (preferences.contains(name + "locked")){
            return preferences.getBoolean(name + "locked");
        }else {
            preferences.putBoolean(name + "locked" , true);
            preferences.flush();
            return true;
        }
    }
    public static boolean IsEquipped(String name){
        Preferences preferences = Gdx.app.getPreferences("coderacer");
        if (preferences.contains(name + "equipped")){
            return preferences.getBoolean(name + "equipped");
        }else {
            preferences.putBoolean(name + "equipped",false);
            preferences.flush();
            return false;
        }
    }

    private void Save(){
        Preferences preferences = Gdx.app.getPreferences("coderacer");
        preferences.putBoolean(this.name + "locked", this.is_locked);
        preferences.putBoolean(this.name + "equipped", this.is_equipped);
        preferences.flush();
    }
}
