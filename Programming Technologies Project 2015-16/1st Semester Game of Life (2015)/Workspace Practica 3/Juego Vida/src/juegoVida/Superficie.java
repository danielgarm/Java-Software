package juegoVida;

import java.io.FileWriter;
import java.io.IOException;


import celulas.Celula;


public class Superficie {
    private Celula[][] celulas;
    private int nFilas, nColumnas;

    public Superficie(int nf, int nc) {
    	this.nFilas = nf;
    	this.nColumnas = nc;
        this.celulas = new Celula[nFilas][nColumnas];

        for (int i = 0; i < nf; i++) {
            for (int j = 0; j < nc; j++) {
                this.celulas[i][j] = null;
            }
        }
    }

	/**
     * Mata una c�lula dadas sus coordenadas por par�metro
     * @param posx Coordenada x de la c�lula
     * @param posy Coordenada y de la c�lula
     */
    public void killCell(int posx, int posy) {
        this.celulas[posx][posy] = null;
    }

    /**
     * Devuelve por booleano si la c�lula existe o la posici�n est� vac�a
     * @param i Coordenada x de la c�lula
     * @param j Coordenada y de la c�lula
     * @return true si no existe la c�lula, false si existe
     */
    public boolean esVacia(int i, int j) {
        return this.celulas[i][j] == null;
    }
    
    /**
     * Introduce una c�lula nueva en la matriz de c�lulas
     * @param fil Coordenada x de la c�lula
     * @param col Coordenada y de la c�lula
     * @param celula C�lula a introducir
     */
    public void nuevaCelula(int fil, int col, Celula celula) {
        this.celulas[fil][col] = celula;
    }

    /**
     * "Mueve" una c�lula a una posici�n que ya se ha comprobado que est� vac�a, creando una nueva c�lula, pasando los pasos que dio la otra y matando la anterior
     * @param fil Coordenada x de la nueva c�lula
     * @param col Coordenada y de la nueva c�lula
     * @param auxi Coordenada x de la antigua c�lula
     * @param auxj Coordenada y de la antigua c�lula
     */
    public void seMueve(int fil, int col, int auxi, int auxj) {
    	this.celulas[auxi][auxj] = this.celulas[fil][col]; // las dos casillas apuntan a la misma celula
    	this.celulas[fil][col] = null; // la casilla antigua se desconecta de la celula
    }
    
    /**
     * Devuelve el valor comestible de una c�lula
     * @param fil posicion x de la c�lula
     * @param col posicion y de la c�lula
     */
	public boolean esComestible(int fil, int col) {
		return celulas[fil][col].esComestible();
	}
	
	/**
     * Dados unas posiciones nf y nc de una celula, se encarga de ver si dicha coordenada se encuentra acotada dentro de la superficie
     * @param nf Coordenada x
     * @param nc Coordenada y
     * @return Devuelve un booleano true en caso de que dicha posicion este acotada, false si esta fuera de rango
     */
    public boolean correctos(int nf, int nc) {
        boolean ok = true;
        if (nf < 0 || nf >= nFilas || nc < 0 || nc >= nColumnas) {
            ok = false;
        }
        return ok;
    }
    
    /**
     * Dados unas posiciones f y c de una celula, devuelve el valor de la casilla a donde ha e moverse
     * @param f Coordenada x
     * @param c Coordenada y
     * @return Devuelve una casilla con las posiciones de la celula a donde debe o moverse, o null en caso contrario
     */
    public Casilla ejecutaMovimiento(int f, int c){ 
    	return celulas[f][c].ejecutaMovimiento(f, c, this);
    }
    
    public int getnFilas() {
		return nFilas;
	}

	public int getnColumnas() {
		return nColumnas;
	}
    
    /**
     * Crea y devuelve el string del tablero en sí, listo para mostrarlo por pantalla
     */
    @Override
    public String toString() {
        String salida = "";
        salida += "\\  ";
        for (int x = 0; x < nColumnas; x++){
        	salida += x + "   ";
        }
        salida += System.getProperty("line.separator");
        for (int i = 0; i < celulas.length; i++) { // i < FILAS
            salida += i;
            for (int j = 0; j < celulas[i].length; j++) { // j < 
                if (esVacia(i, j)) {
                    salida += "    ";
                } else {
                    salida += " " + celulas[i][j].toString();
                }
            }
            salida += System.getProperty("line.separator");
        }
        return salida;
    }
    
	/**
	 * guarda en el fichero las dimensiones y llama al guardar de ceula
	 * @param fw
	 * @throws IOException
	 */
	
	public void guardar(FileWriter fw) throws IOException {
		
		fw.write(nFilas + System.getProperty("line.separator"));
		fw.write(nColumnas + System.getProperty("line.separator"));
		
		for(int i = 0; i < nFilas; i++){
			for(int j = 0; j < nColumnas; j++){
				if(!esVacia(i,j)){
					try {
						fw.write(i + " " + j + " ");
						celulas[i][j].guardar(fw);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}