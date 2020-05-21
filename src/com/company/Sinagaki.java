package com.company;

public class Sinagaki {
//    クラスとして扱うことで辞書型リストのように扱う
    private int number;
    private int capacity;
    private int price;
    private float quality;
    Sinagaki(int n,int c,int p){
        this.number = n;
        this.capacity = c;
        this.price = p;
    }
    public int getNumber(){return number;}
    public int getCapacity(){return capacity;}
    public int getPrice(){return price;}
    public float getQuality(){
        this.quality = (float)price/(float)capacity;
        return quality;
    }
}
