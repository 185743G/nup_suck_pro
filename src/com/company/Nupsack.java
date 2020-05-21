package com.company;

import java.util.ArrayList;

public class Nupsack {
    private static int NupsackCapa;
    private ArrayList<Sinagaki> nup_sack = new ArrayList<>();

    public Nupsack(int capa){
        this.NupsackCapa = capa;
    }
    void show(){
        ArrayList<Integer> showList = new ArrayList<>();
        for(int i = 0; i < nup_sack.size();i++) {
            showList.add(nup_sack.get(i).getNumber());
        }
        System.out.println(showList);
    }
    int totalPrice(){
        int total=0;
        for(int i = 0; i < nup_sack.size();i++) {
            total += nup_sack.get(i).getPrice();
        }
        return total;
    }
    void buy(Sinagaki[] store,int number){
        nup_sack.add(store[number]);
//        System.out.println(store[number].getNumber());
//        System.out.println(store[number].getCapacity());
//        System.out.println(store[number].getPrice());

    }
    void sell(Sinagaki[] store,int number){
        nup_sack.remove(store[number]);
    }
    int getNupsack_length(){
        return nup_sack.size();
    }
    int chkCapacity(){
        int weight=0;
        for(int i = 0; i < nup_sack.size();i++) {
            weight += nup_sack.get(i).getCapacity();
        }
        return weight;
    }

    boolean is_over(){
        boolean judg=false;
        if(chkCapacity() <= NupsackCapa){
            judg = true;
        }
        return judg;
    }
}
