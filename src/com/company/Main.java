package com.company;
import java.util.ArrayList;

public class Main {
    static final int NUPCAPA = 55;
//    static final int NUPCAPA = 25;
//    static final int[][] MENU={{1,4,6},{2,8,12},{3,3,4},{4,5,3},{5,9,7},
//            {6,2,1},{7,3,3}, {8,1,2},{9,5,7},{10,2,3},{11,4,4},{12,2,2},
//            {13,7,10},{14,10,13},{15,3,5},{16,13,16},{17,11,14},{18,8,9}};
//    static final int[][] MENU={{1,4,6},{2,8,12},{3,3,4},{4,5,3},{5,9,7},
//            {6,2,1},{7,3,3}, {8,1,2},{9,5,7},{10,2,3},{11,4,4},{12,2,2},
//            {13,7,10},{14,10,13},{15,3,5}, {16,13,16},{17,11,14},{18,8,9},
//            {19,5,7},{20,9,13},{21,4,5},{22,6,4},{23,10,8},
//            {24,3,2},{25,4,4}};

//    数理計画アルゴリズム用メニュー
//    static final int[][] MENU={{1,3,7},{2,6,12},{3,5,9},{4,4,7},{5,8,13},
//            {6,5,8},{7,3,4},{8,4,5}};//a
static final int[][] MENU={{1,3,7},{2,6,12},{3,5,9},{4,4,7},{5,8,13},
        {6,5,8},{7,3,4}, {8,4,5},{9,3,3},{10,5,10},{11,6,7},{12,4,5},
        {13,8,6},{14,7,14},{15,11,5}, {16,8,9},{17,14,6},{18,6,12},
        {19,12,5},{20,4,9}};

    static final int number = 0;
    static final int capacity = 1;
    static final int price = 2;
    static final int quality = 1;
    static final int numProd = MENU.length;
    static final int numNupsack = (int) Math.pow(2,MENU.length);//2^MENU.length

    static private Sinagaki[] MiyazatoStore = new Sinagaki[numProd];
    static private Sinagaki[] NakamuraStore = new Sinagaki[numProd];

    static private ArrayList<Nupsack> bestNupsacks = new ArrayList<>();//Provisional　best nup
    static void clear_bestNupsacks(){bestNupsacks = new ArrayList<>();}

    static private float[][] QbaseMENU = new float[numProd][2];
    static void InitNakamuraStore(){
        InitMiyazatoStore();
        for(int i = 0;i<numProd;i++){
            QbaseMENU[i] = new float[] {(float)(MiyazatoStore[i].getNumber()), MiyazatoStore[i].getQuality()};
        }

        for (int i = 0; i < numProd; i++) {
            for(int j = 0; j <  numProd;j++){
                Selsectoin_Sort(QbaseMENU,i,j);
            }
        }
        for(int i = 0;i<numProd;i++){
            for(int j = 0;j<numProd;j++) {
                if ((int) QbaseMENU[i][number] == MiyazatoStore[j].getNumber()) {
                    NakamuraStore[i] = MiyazatoStore[j];
                    break;
                }
            }
        }
//        for(int i = 0;i<numProd;i++){
//            System.out.println(NakamuraStore[i].getNumber());
//        }
    }
    static void Selsectoin_Sort(float[][] Menu,int x,int y){
        float[] i;
        if(Menu[y][quality] < Menu[x][quality]){
            i = Menu[x];
            Menu[x] = Menu[y];
            Menu[y] = i;
        }else if(Menu[y][quality] == Menu[x][quality] && Menu[y][quality] == Menu[x][quality]){
        }
    }
    static void InitMiyazatoStore(){
        for(int i = 0;i<numProd;i++){
            Sinagaki sinamono =
                    new Sinagaki(MENU[i][number],MENU[i][capacity],MENU[i][price]);
            MiyazatoStore[i] = sinamono;
        }
    }
    static Nupsack getNupsack(int roop){
        Nupsack nup = new Nupsack(NUPCAPA);
        for(int i = 0;i<numProd;i++) {
            if (roop % 2 == 1) {
                nup.buy(MiyazatoStore,i);
            }
            roop = roop/2;
        }
        return nup;
    }
    static Nupsack search_alikeNup(Nupsack nup, int behind) {
        Nupsack nup_i_behind = nup;
        nup_i_behind.sell(NakamuraStore,nup.getNupsack_length());
        nup.buy(NakamuraStore,nup.getNupsack_length()+behind);
        return nup_i_behind;
    }
    static void QprioritySearch(){
        Nupsack bestnup = new Nupsack(NUPCAPA);
        for(int i = 0;i<numProd;i++) {
            if (bestnup.is_over()) {
                bestnup.buy(NakamuraStore, i);
            } else {
                bestnup.sell(NakamuraStore, i-1);
                break;
            }
        }
        bestNupsacks.add(bestnup);

//        最後に詰めた品物の次に同じ評価の場合は重さを超えない限り最後の品物と入れ替えこちらの袋もベストナップサックとする
        int j = 0;
        while(NakamuraStore[bestnup.getNupsack_length()].getQuality() ==
                NakamuraStore[bestnup.getNupsack_length()+j].getQuality()) {
            if(search_alikeNup(bestnup,j).is_over()) {
                bestNupsacks.add(search_alikeNup(bestnup, j));
            }
            j++;
        }
    }
    static int brute_force(){
        Nupsack nup = new Nupsack(NUPCAPA);
        int bestPrice = 0;
        for(int i = 0;i<numNupsack;i++){
            nup = getNupsack(i);
            if(nup.is_over()){
                if(nup.totalPrice()> bestPrice){
                    bestPrice = nup.totalPrice();
                }
            }
        }
        return bestPrice;
    }

    static void showBestNupsacks(){
        for(int i = 0; i < bestNupsacks.size();i++){
            if(bestNupsacks.get(i).totalPrice() != 0) {
                bestNupsacks.get(i).show();
                System.out.print("totalprice = ");
                System.out.println(bestNupsacks.get(i).totalPrice());
                System.out.print("totalweight = ");
                System.out.println(bestNupsacks.get(i).chkCapacity());
            }
        }
    }
    static void Collect_bestNupsack(int best){
        Nupsack nup;
        for(int i = 0;i < numNupsack;i++){
            nup = getNupsack(i);
            if(nup.is_over()) {
                if (nup.totalPrice() == best) {
                    bestNupsacks.add(nup);
                }
            }
        }
    }
    public static void main(String[] args) {
	//nakamura式(品物の評価の高い順に袋に詰める)
        System.out.println("改良型");
        long start1 = System.currentTimeMillis();
        InitNakamuraStore();
        QprioritySearch();
        showBestNupsacks();
        long end1 = System.currentTimeMillis();
        clear_bestNupsacks();
//        miyazato式(総当たりで袋に詰めて評価する)
        System.out.println("総当たり");
        long start2 = System.currentTimeMillis();
        InitMiyazatoStore();
        Collect_bestNupsack(brute_force());
        showBestNupsacks();
        long end2 = System.currentTimeMillis();

        System.out.println("改良型"+(end1 - start1)  + "ms");
        System.out.println("総当たり"+(end2 - start2)  + "ms");
    }

}
