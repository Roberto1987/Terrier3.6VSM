package org.terrier.matching.dsms;

import java.io.IOException;

import org.terrier.matching.MatchingQueryTerms;
import org.terrier.matching.ResultSet;
import org.terrier.matching.models.TF_IDF2;
import org.terrier.structures.DirectIndex;
import org.terrier.structures.Index;
import org.terrier.structures.Lexicon;

public class Vsm implements DocumentScoreModifier {

	
	public Object clone() {
		try{
			return super.clone();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@Override
	public boolean modifyScores(Index index, MatchingQueryTerms queryTerms,	ResultSet resultSet) {
		int[] Ids=resultSet.getDocids(); //array degli id dei documenti recuperati
		double[] scores=resultSet.getScores(); //Array degli score dei documenti
		System.out.println(scores.length+" "+Ids.length);
				
	    TF_IDF2 TfIdf = new TF_IDF2(); //modello di pesatura dei termini
		DirectIndex directIndex;
		double squareNorm=0.0;
		double norm=0.0;
		//double newScore=0.0;
		double tmp=0;
		String filenameDirectIndex = "direct"; //estensione .bf e percorso sono forniti da terrier
			
		try {
			directIndex = new DirectIndex(index, filenameDirectIndex); //creazione indice diretto
			double numberOfDocs = index.getCollectionStatistics().getNumberOfDocuments();
			Lexicon<String> x = index.getLexicon();
			int indiceDocs=0;
			for(indiceDocs=0;indiceDocs<Ids.length;indiceDocs++) //Scansione id documenti
					{
						//double documentFrequency = index.getLexicon().getLexiconEntry(indiceDocs).getValue().getDocumentFrequency();
						//System.out.println(documentFrequency);
				       int[][] terms = directIndex.getTerms(Ids[indiceDocs]); //Prendo termini e frequenze del documento
				       //double documentLength=0; 
						       /*for(int k=0;k<terms[0].length;k++)
						       {
						        	documentLength+= terms[1][k];//sommo le frequenze ottenendo il doclength
						       }*/
				       			squareNorm=0;
						       for(int j=0;j<terms[0].length;j++)//Scansione termini del documento
						       {
						    	   
						    	   	//Costruisco la norma quadrata
						    	   //double documentFrequency=index.getLexicon().getIthLexiconEntry(terms[0][j]).getValue().getDocumentFrequency();
						    	   double documentFrequency=x.getIthLexiconEntry(terms[0][j]).getValue().getDocumentFrequency();
						    	   tmp= TfIdf.score((double)terms[1][j],numberOfDocs,documentFrequency);
						    	   //System.out.println("peso"+tmp);
						    	   tmp=tmp*tmp;
						    	   squareNorm+=tmp;
						    		 
						       }
						     //completamento della norma del documento "indiceDocs"
						       norm=(double)Math.sqrt(squareNorm);
						       if(Ids[indiceDocs]==49279){
								   System.out.println(norm);
								   }
						     //Costruisco la distanzaCoseno del documento  
						     //sostituisco la distanza-coseno allo score precedente  
						       scores[indiceDocs]=(double)scores[indiceDocs]/norm; 
						       if(scores[indiceDocs]>0){
						    	   System.out.println("norm["+indiceDocs+"] "+norm+ " Docid "+Ids[indiceDocs]);
						       } 
            		}// fine scansione
			resultSet.initialise(scores); //aggiorno gli score di resultSet
			return true;
		} catch (IOException e) {
			System.out.println("Errore nel modifyScore VSM");
			e.printStackTrace();
		}
		       	
		return true;
	}

	@Override
	
	public String getName() {
		String name="Vsm";
		return name;
	}

}

