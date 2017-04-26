package org.terrier.matching.dsms;

import org.terrier.matching.MatchingQueryTerms;
import org.terrier.matching.ResultSet;
import org.terrier.matching.models.TF_IDF2;
import org.terrier.structures.Index;
import org.terrier.utility.PreProcessingNorm;

public class VsmOffline implements DocumentScoreModifier {

	
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
		PreProcessingNorm processer = new PreProcessingNorm();
		double norm[];
		//int numberOfDocs=index.getCollectionStatistics().getNumberOfDocuments();
		
		try {
			//Caricamento norme da oggetto serializzato
			norm = processer.load();
			//CHECK
		/**if(numberOfDocs== norm.length){
										System.out.println("Vettori norma-score di egual dimensione");
										}
								else{
									System.out.println("Vettori norma-score di diversa dimensione");
									System.out.println(numberOfDocs+" "+norm.length);
								}
		**/
			System.out.println("lunghezza ids recuperati"+Ids.length);
		    System.out.println("lunghezza array norme "+norm.length);
		    System.out.println("lunghezza scores"+scores.length);
		double newScore=0.0;
			
			int indiceDocs=0;
			for(indiceDocs=0;indiceDocs<Ids.length;indiceDocs++) //Scansione id documenti
					{
				 if(scores[indiceDocs]>0){
			    	   System.out.println("norm["+Ids[indiceDocs]+"] "+norm[Ids[indiceDocs]]+ " Docid "+Ids[indiceDocs]);
			       }
						     //Aggiorno lo score del documento K con la norma di posizione K
							 //Suppongo i docid ordinati
						       newScore=(double)scores[indiceDocs]/norm[Ids[indiceDocs]];
						       
						     //sostituisco la distanza-coseno allo score precedente  
						       scores[indiceDocs]=(double)newScore;
						      
						       
            		}// fine scansione
			resultSet.initialise(scores); //aggiorno gli score di resultSet
		} catch (Exception e) {
			System.out.println("Cannot load norms file ");
			e.printStackTrace();
		}
		return true;
	}

	@Override
	
	public String getName() {
		String name="VsmOffline";
		return name;
	}

}
