package com.example.dosificapp.data;

import com.example.dosificapp.dominio.Dosis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DosisRepository {

    private static volatile DosisRepository instance;
    private ArrayList<Dosis> listaDosis = new ArrayList<>();

    public static DosisRepository getInstance() {
        if (instance == null) {
            instance = new DosisRepository();
        }
        return instance;
    }

    public void addDosis(Dosis dosis){
        listaDosis.add(dosis);
    }

    public ArrayList<Dosis> getListaDosis(){ return this.listaDosis; }

    public Dosis getDosisById(int id){
        for (Dosis d: getListaDosis()) {
            if(d.getDoseId() == id){
                return d;
            }
        }
        return null;
    }

    public Dosis getDosisTomaById(Long id){
        for (Dosis d: getListaDosis()) {
            if(Objects.equals(d.getDoseTakeid(), id)){
                return d;
            }
        }
        return null;
    }

    public void clearDosis(){this.listaDosis = new ArrayList<>(); }

    public void updateDosis(Dosis dosis) throws Exception {
        for (Dosis d: getListaDosis()) {
            if(d.getDoseId() == dosis.getDoseId()){
                listaDosis.remove(d);
                listaDosis.add(dosis);
            }
        }
        throw new Exception("Dosis no encontrada");
    }

    public void deleteDosis(Dosis dosis){
        for (Dosis d: getListaDosis()) {
            if(d.getDoseId() == dosis.getDoseId()){
                listaDosis.remove(d);
            }
        }
    }

    public ArrayList<Dosis> getDosisVigentes(){
        ArrayList<Dosis> dosisVigentes = new ArrayList<>();
        for(Dosis d: listaDosis){
            if(d.getCalendar().getTimeInMillis() - System.currentTimeMillis() > 0){
                dosisVigentes.add(d);
            }
        }
        return dosisVigentes;
    }

    public void setDoses(@NotNull List<? extends Dosis> doses) {
        listaDosis.addAll(doses);
    }
}
