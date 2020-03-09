package Equipment;

import ArchivesBuilder.SQLite;
import Equipment.Armor.Armor;
import Equipment.Armor.Shields;
import Equipment.Weapons.Ammo;
import Equipment.Weapons.SolarianCrystal;
import Equipment.Weapons.Weapon;
import Equipment.Weapons.WeaponAccessory;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores the equipment objects
 */
public class EquipmentStore {

    private ArrayList<Weapon> weapons;
    private ArrayList<Armor> armor;
    private ArrayList<Ammo> ammo;
    private ArrayList<SolarianCrystal> crystals;
    private ArrayList<WeaponAccessory> accessories;
    private ArrayList<Shields> shields;
    public enum size{Tiny, Small, Average, large, Huge, All}
    private int sizeMod;
    private boolean all;
    private int levelLow;
    private int levelHigh;
    private String type;
    private String eqType;
    private String sort;
    private String[] sortTypes = {"By Level"};


    public EquipmentStore(int levelLow,int levelHigh, String type,String eqType, size size){
        this.levelLow = levelLow;
        this.levelHigh = levelHigh;
        this.type = type;
        this.eqType = eqType;
        this.sort = sortTypes[0];
        sizeMod = 0;
        all = false;
        switch (size){
            case Huge:
                sizeMod = sizeMod + 40;
                break;
            case Tiny:
                sizeMod = sizeMod + 5;
                break;
            case large:
                sizeMod = sizeMod + 30;
                break;
            case Small:
                sizeMod = sizeMod + 10;
                break;
            case Average:
                sizeMod = sizeMod + 20;
                break;
            case All:
                all = true;
        }
    }

    public void fillArmor()throws SQLException {
        ArrayList<String> armorTemp = SQLite.GetNamesByLevel("Armor", levelLow, levelHigh);
        armor = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")) {
                for (String s : armorTemp) {
                    Armor a = new Armor();
                    SQLite.Read(a, a.getTableName(), s, a.getKeys());
                    if (a.getType().contains(type)) {
                        armor.add(a);
                    }
                }
            }
        }
    }
    public void fillWeapons() throws SQLException {
        ArrayList<String> weaponsTemp = SQLite.GetNamesByLevel("Weapons",levelLow,levelHigh);
        weapons = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")){
                for (String s:weaponsTemp) {
                    Weapon w = new Weapon();
                    SQLite.Read(w,"Weapons",s,Weapon.keys);
                    if (w.getType().contains(type)){
                        weapons.add(w);
                    }
                }
            }
        } else {
            //Make a size based random store
        }
    }
    public void fillAmmo() throws SQLException {
        ArrayList<String> ammoTemp = SQLite.GetNamesByLevel(Ammo.tableName, levelLow, levelHigh);
        ammo = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")) {
                for (String s : ammoTemp) {
                    Ammo a = new Ammo();
                    SQLite.Read(a, Ammo.tableName, s, Ammo.keys);
                    if (a.getType().contains(type)) {
                        ammo.add(a);
                    }
                }
            }
        }
    }
    public void fillCrystals() throws SQLException {
        ArrayList<String> temp = SQLite.GetNamesByLevel(SolarianCrystal.tableName, levelLow, levelHigh);
        crystals = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")) {
                for (String s : temp) {
                    SolarianCrystal a = new SolarianCrystal();
                    SQLite.Read(a, a.getTableName(), s, a.getKeys());
                    if (a.getType().contains(type)) {
                        crystals.add(a);
                    }
                }
            }
        }
    }
    public void fillAccessories() throws SQLException {
        ArrayList<String> temp = SQLite.GetNamesByLevel(WeaponAccessory.tableName, levelLow, levelHigh);
        accessories = new ArrayList<>();
        if (all) {
            if (!type.equalsIgnoreCase("All")) {
                for (String s : temp) {
                    WeaponAccessory a = new WeaponAccessory();
                    SQLite.Read(a, a.getTableName(), s, a.getKeys());
                    if (a.getType().contains(type)) {
                        accessories.add(a);
                    }
                }
            }
        }
    }
    public void fillShields() throws SQLException {
        ArrayList<String> temp = SQLite.GetNamesByLevel(Shields.tableName, levelLow, levelHigh);
        System.out.println(temp.size());
        shields = new ArrayList<>();
        if (all) {
            System.out.println(type);
            if (!type.equalsIgnoreCase("All")) {
                for (String s : temp) {
                    Shields a = new Shields();
                    SQLite.Read(a, a.getTableName(), s, a.getKeys());
                    if (a.getType().contains(type)) {
                        shields.add(a);
                    }
                }
            }
        }
    }

    public void getEquipment(String name, Equipment equipment,String table,String[] keys){
        try {
            SQLite.Read(equipment,table,name,keys);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public String[] getWeaponsNames() {
        String[] names = new String[weapons.size()];
        for (int i = 0; i < weapons.size(); i++) {
            names[i]=weapons.get(i).getName();
        }
        return names;
    }
    public String[] getArmorNames() {
        String[] names = new String[armor.size()];
        for (int i = 0; i < armor.size(); i++) {
            names[i] = armor.get(i).getName();
        }
        return names;
    }
    public ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<>();
        if (weapons!=null){
            for (int i = 0; i < weapons.size(); i++) {
                names.add(weapons.get(i).getName());
            }
        }
        if (armor!=null){
            for (int i = 0; i < armor.size(); i++) {
                names.add(armor.get(i).getName());
            }
        }
        if (ammo!=null){
            for (int i = 0; i < ammo.size(); i++) {
                names.add(ammo.get(i).getName());
            }
        }
        if (crystals != null) {
            for (int i = 0; i < crystals.size(); i++) {
                names.add(crystals.get(i).getName());
            }
        }
        if (accessories != null){
            for (int i = 0; i < accessories.size(); i++) {
                names.add(accessories.get(i).getName());
            }
        }
        return names;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----Weapons-----\n");
        for (Weapon w:weapons) {
            sb.append("\t"+w.getName()+"\n");
        }
        sb.append(weapons.size());

        return sb.toString();
    }
    public int getLevelHigh() {
        return levelHigh;
    }
    public int getLevelLow() {
        return levelLow;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getEqType() {
        return eqType;
    }
    public String getSort(){
        return sort;
    }
    public void setSort(String sort){
        this.sort = sort;
    }

    public String[] getSortTypes() {
        return sortTypes;
    }
}
