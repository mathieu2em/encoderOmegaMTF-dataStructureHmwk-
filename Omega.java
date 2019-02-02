// par Mathieu Perron et Audrey Pepin

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class Omega {

    BinaryOut binaryOut = new BinaryOut();

    private LinkedList<Integer> permutations;

    public static void main(String[] args) {

    }
    // le constructeur fera d'un seul coup la lecture des entiers d'un texte
    public Omega(String fichier){

        permutations = new LinkedList<Integer>();

        for( var i=0; i<65536; i++) {
            permutations.add(i);
        }

        try(FileReader reader = new FileReader(fichier)){
            int integer;
            while( ( integer = reader.read())  != -1 ){
                encodeOmega(encodeMTF(integer));
                ecrire(0,1);
            }
            binaryOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // cette methode verifie premierement si le int est dans la liste,
    // sinon l'ajoute à la liste et retourne sa valeur
    public int encodeMTF(int entier){
        // on va chercher la position de l'entier dans notre Liste de permutations
        int position = permutations.indexOf(entier);
        if ( position > 0 ) {
            permutations.remove(position);
            permutations.addFirst(entier);
        }
        return position;
    }
    // méthode récursive d'encodage selon la technique d'encodage omega
    public void encodeOmega(int integer){
        if(integer <= 0){
            return;
        } else {
            // on extrait le nombre de bits de l'entier
            int longueur = bitcount(integer);
            encodeOmega(longueur-1);
            // appel la fonction qui ecrit dans system.out
            ecrire(integer, longueur);
        }
    }
    //compte la longueur binaire du nombre
    private int bitcount(int n){
        int count = 0;
        while( n >= 1  ){
            n /= 2;
            count++;
        }
        return count;
    }
    // cette methode prend l'entier et l'ecris dans un fichier binaire sous system.out
    // l'equation utilisant les operateurs binaires est inspiree du code de sedgewick
    private void ecrire(int integer, int size){
        for ( var i=0; i<size; i++) {
            boolean bit = ((integer >>> (size - i - 1)) & 1) == 1;
            binaryOut.writeBit(bit);
        }
    }
}
