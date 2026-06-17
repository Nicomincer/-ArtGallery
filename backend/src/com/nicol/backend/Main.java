package com.nicol.backend;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        int n1;
        int n2;
        int soma;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Digite o primeiro operando: ");
        n1 = teclado.nextInt();
        System.out.println("Digite o segundo operando: ");
        n2 = teclado.nextInt();
        soma = n1+n2;
        System.out.printf("A soma deu %d reais", soma);
    }
}
