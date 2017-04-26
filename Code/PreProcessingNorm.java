package org.terrier.utility;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.terrier.matching.models.TF_IDF2;
import org.terrier.structures.DirectIndex;
import org.terrier.structures.Index;
import org.terrier.structures.Lexicon;

@SuppressWarnings("serial")
public class PreProcessingNorm implements Serializable {
	Index index;
	String dirIndex;
	boolean initialized=false;
    double[] norme;
    TF_IDF2 TfIdf;
	
	public PreProcessingNorm(String dirIndex,Index index, TF_IDF2 TfIdf){
		this.dirIndex=dirIndex;
		this.index = index;
		this.TfIdf = TfIdf;
		initialized=true;
		
	}
	
	public PreProcessingNorm(){
		
	}
	
	public void makeL2Norm(){
		try {
			if(initialized==false){
				System.out.println("Classe di preprocessing non inizializzata correttamente");
								}
			
			DirectIndex directIndex = new DirectIndex(index, dirIndex);
			double numberOfDocs = index.getCollectionStatistics().getNumberOfDocuments();
			int numberOfDocuments = index.getCollectionStatistics().getNumberOfDocuments();
			Lexicon<String> x = index.getLexicon();
		    double[] norm = new double[numberOfDocuments];
			int indiceDocs=0;
			double tmp=0;
			double squareNorm=0;
			for(indiceDocs=0;indiceDocs<numberOfDocs;indiceDocs++) //Scansione indice
					{
					   int[][] terms = directIndex.getTerms(indiceDocs); //Prendo termini e frequenze del documento
					   squareNorm=0;
					   for(int j=0;j<terms[0].length;j++)//Scansione termini del documento
						       {
						    	   	//Costruisco la norma quadrata
						    	   //double documentFrequency=index.getLexicon().getIthLexiconEntry(terms[0][j]).getValue().getDocumentFrequency();
						    	   double documentFrequency=x.getIthLexiconEntry(terms[0][j]).getValue().getDocumentFrequency();
						    	   tmp= TfIdf.score((double)terms[1][j],numberOfDocs,documentFrequency);
						    	   tmp=tmp*tmp;
						    	   squareNorm+=tmp;
						       }
						     //completamento della norma del documento "indiceDocs"
					    norm[indiceDocs] = (double)Math.sqrt(squareNorm);
					    if(indiceDocs<30){
							   System.out.println(norm[indiceDocs]);
							   }
			   		}// fine scansione
			
			setNorme(norm); //Aggiorno il vettore di norme
		} catch (IOException e) {
			System.out.println("Failed norm processing procedure");
			e.printStackTrace();
		}
	
		
	}
	//Path del file di default
	public void save(){
		String TerrierAddress = "/home/rob/terrier-3.6/";
		try {
			FileOutputStream saveNorms = new FileOutputStream (TerrierAddress+"norme.data");
			ObjectOutputStream out = new ObjectOutputStream(saveNorms);
			out.writeObject(norme);
			out.close();
			saveNorms.close();
			System.out.println("Norm's file saved in "+TerrierAddress+"under norma.data's name");
		} catch (Exception e) {
			System.out.println("Norm's file not saved, throwed possible multiple exception");
		    //Catching 2 different exception, FileNotFound and IoException
			e.printStackTrace();
		}
		
	}

	//Path del file settabile
	public void save(String address){
		String TerrierAddress = address;
		try {
			FileOutputStream saveNorms = new FileOutputStream (TerrierAddress+"norme.data");
			ObjectOutputStream out = new ObjectOutputStream(saveNorms);
			out.writeObject(norme);
			out.close();
			saveNorms.close();
			System.out.println("Norm's file saved in "+TerrierAddress+"under norma.data's name");
		} catch (Exception e) {
			System.out.println("Norm's file not saved, throwed possible multiple exception");
		    //Catching 2 different exception
			e.printStackTrace();
		}
		
	}
	
	//Path del file di default
	public double[] load() throws Exception{
		String TerrierAddress = "/home/rob/terrier-3.6/";
		double[] load;
		Object tmp;
		FileInputStream loadNorms;
		
		loadNorms = new FileInputStream(TerrierAddress+"norme.data");	
		ObjectInputStream in = new ObjectInputStream(loadNorms);
		tmp = in.readObject();
		load = (double[])tmp;
		
		in.close();
		loadNorms.close();
	    System.out.println("Norms array succesfully loaded");
		return  load;
	}
	
	//Path del file settabile
	public int[] load(String Address) throws Exception{
		String TerrierAddress = Address;
		int[] load;
		Object tmp;
		
		FileInputStream loadNorms;
		loadNorms = new FileInputStream(TerrierAddress+"norme.data");	
		ObjectInputStream in = new ObjectInputStream(loadNorms);
		tmp = in.readObject();
		load = (int[])tmp;
		
		in.close();
		loadNorms.close();
	    System.out.println("Norms array succesfully loaded");
		return  load;
			
	}
	
	public void setNorme(double[] norme){
		this.norme=norme;
	}
	
	public double[] getNorme(){
		return norme;
	}
	
}
