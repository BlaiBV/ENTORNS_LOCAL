package Recursivitat;

import java.util.Arrays;
import java.util.Scanner;

public class Pescamines {
	static Scanner sc = new Scanner(System.in);
	public static int files;
	public static int columnes;
	public static int mines;
	public static char[][] taulell;
	public static boolean[][] visibles;
	public static boolean gameover = false;
	public static int comptador = 0;
	
	public static void main(String[] args) {
		
		MenuNivell(MenuInicial());
		taulell = new char[files][columnes];
		visibles = new boolean[files][columnes];
		files--;
		columnes--;
		System.out.println(files + " " + columnes);
		InicialitzaTaulell();
		ImprimirTaulelljugador();
		Partida();
	}
	
	public static int MenuInicial() {
		System.out.println("MENU PESCAMINES");
		System.out.println("===============");
		System.out.println("1. Triar nivell (nivell actual = 1)");
		System.out.println("2. Iniciar joc");
		int menu_inicial = sc.nextInt();
		return menu_inicial;
	}
	
	public static void MenuNivell (int menu_inicial) {
		if (menu_inicial == 1) {
			System.out.println("MENU PESCAMINES");
			System.out.println("===============");
			System.out.println("1. Nivell principiant: 8 x 8 cel·les i 10 mines");
			System.out.println("2. Nivell intermig:  16 x 16 cel·les i 40 mines");
			System.out.println("3. Nivell expert:  16 x 30 cel·les i 99 mines");
			System.out.println("4. Nivell personalitzat");
			switch (sc.nextInt()) {
				case 1:
					files = columnes = 8;
					mines = 10;
					break;
				case 2: 
					files = columnes = 16;
					mines = 40;
					break;
				case 3: 
					files = 16;
					columnes = 30;
					mines = 99; 
					break;
				case 4: 
					System.out.println("Indica el nombre de files:");
					files = sc.nextInt();
					System.out.println("Indica el nombre de columnes:");
					columnes = sc.nextInt();
					System.out.println("Inidca el nombre de mines:");
					mines = sc.nextInt();
					
					if (files*columnes <= mines) {
						System.out.println("El nombre de caselles és igual o inferior al nombre de mines! No és possible!");
						MenuNivell(menu_inicial);
					}
					break;
			}
		}
		else if (menu_inicial == 2) {
			files = columnes = 8;
			mines = 10;
		}
	}
	
	public static void InicialitzaTaulell() {
		for (int i=0; i < mines; i++) {
			int fila = (int) (Math.random()*(files-0+1)+0);
			int columna  = (int) (Math.random()*(columnes-0+1)+0);
			if (taulell[fila][columna] == 'M') {
				i--;
			}
			else {
				taulell[fila][columna] = 'M';
			}
		}
		Adjacencies();
	}
	
	public static void ImprimirTaulelladmin() {
		for (int i=0; i < files+1; i++) {
			for (int j=0; j < columnes+1; j++) {
				System.out.print(taulell[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("======================");
	}
	
	public static void ImprimirTaulelljugador() {
		for (int i=-Xifres(columnes); i < files+1; i++) {
			if (i >= 0 && i <= 9) {
				System.out.print(i + ":  ");
			}
			else if (i > 9) {
				System.out.print(i + ": ");
			}
			else {
				System.out.print("    ");
			}
			for (int j=0; j < columnes+1; j++) {
				if (i > -1) {
					if (visibles[i][j]) {
						System.out.print(taulell[i][j] + " ");
					}
					else {
						System.out.print("  ");
					}
				}
				else {
					if (i == -Xifres(columnes)) {
						System.out.print(Integer.toString(j).charAt(0) + " ");
					}
					else {
						if (Xifres(j) == 2) {
							System.out.print(Integer.toString(j).charAt(1) + " ");
						}
						else if (Xifres(j) == 1) {
							System.out.print("  ");
						}
					}
				}
			}
			System.out.println();
		}
		System.out.println("======================");
	}
	
	public static int Xifres(int num) {
		return Integer.toString(num).length();
	}
	
	public static void Adjacencies() {
		for (int row=0; row < files+1; row++) {
			for (int col=0; col < columnes+1; col++) {
				if (taulell[row][col] != 'M') {
					int casella = 0;
					casella += comprovarMina(row-1, col-1);
					casella += comprovarMina(row-1, col);
					casella += comprovarMina(row-1, col+1);
					casella += comprovarMina(row, col+1);
					casella += comprovarMina(row+1, col+1);
					casella += comprovarMina(row+1, col);
					casella += comprovarMina(row+1, col-1);
					casella += comprovarMina(row, col-1);
					taulell[row][col] = Integer.toString(casella).charAt(0);
				}
			}
		}
	}
	
	public static int comprovarMina(int row, int col) {
		if (row == -1 || row > files) {
			return 0;
		}
		if (col == -1 || col > columnes) {
			return 0;
		}
		if (taulell[row][col] == 'M') {
			return 1;
		}
		return 0;
	}
	
	public static void Partida() {
		int fila, columna;
		while (gameover == false && comptador < ((files+1)*(columnes+1) - mines)) {
			System.out.println("Introdueix el número de fila:");
			fila = sc.nextInt();
			System.out.println("Introdueix el número de columna:");
			columna = sc.nextInt();
			Jugada(fila, columna);
			ImprimirTaulelljugador();
		}
		if (comptador == ((files+1)*(columnes+1) - mines)) {
			System.out.println("Has guanyat!");
		}
		else {
			System.out.println("Has perdut!");
		}
	}
	
	public static void Jugada(int fila, int columna) {
		if(taulell[fila][columna] == '0' && visibles[fila][columna] == false) {
			DestaparZeros(fila, columna);
		}
		else if (taulell[fila][columna] == 'M' && visibles[fila][columna] == false) {
			visibles[fila][columna] = true;
			DestaparMines();
			comptador++;
			gameover = true;
		}
		else if (visibles[fila][columna] == false) {
			visibles[fila][columna] = true;
			comptador++;
		}
		else {
			System.out.println("Aquesta casella ja és visible");
		}
	}
	
	public static void DestaparZeros(int fila, int columna) {
		if (taulell[fila][columna] != 'M' && taulell[fila][columna] != '0' && visibles[fila][columna] == false) {
			visibles[fila][columna] = true;
			comptador++;
		}
		else if (taulell[fila][columna] == 'M'  && visibles[fila][columna] == false) {
			
		}
		else if (taulell[fila][columna] == '0'  && visibles[fila][columna] == false) {
			visibles[fila][columna] = true;
			comptador++;
			if (CasellaExisteix(fila-1, columna-1)) {
				DestaparZeros(fila-1, columna-1);
			}
			if (CasellaExisteix(fila-1, columna)) {
				DestaparZeros(fila-1, columna);
			}
			if (CasellaExisteix(fila-1, columna+1)) {
				DestaparZeros(fila-1, columna+1);
			}
			if (CasellaExisteix(fila, columna+1)) {
				DestaparZeros(fila, columna+1);
			}
			if (CasellaExisteix(fila+1, columna+1)) {
				DestaparZeros(fila+1, columna+1);
			}
			if (CasellaExisteix(fila+1, columna)) {
				DestaparZeros(fila+1, columna);
			}
			if (CasellaExisteix(fila+1, columna-1)) {
				DestaparZeros(fila+1, columna-1);
			}
			if (CasellaExisteix(fila, columna-1)) {
				DestaparZeros(fila, columna-1);
			}
		}
	}
	
	public static void DestaparMines() {
		for (int row = 0; row < files+1; row++) {
			for (int col = 0; col < columnes+1; col++) {
				if (taulell[row][col] == 'M' && visibles[row][col] == false) {
					visibles[row][col] = true;
				}
			}
		}
	}
	
	public static boolean CasellaExisteix(int fila, int columna) {
		if (fila == -1 || fila > files) {
			return false;
		}
		if (columna == -1 || columna > columnes) {
			return false;
		}
		return true;
	}
	
}
