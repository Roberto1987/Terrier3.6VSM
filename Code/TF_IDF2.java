
package org.terrier.matching.models;


/**
 *Standard Tf-Idf weighitng model 
 **/
public class TF_IDF2 extends WeightingModel {
	
	private static final long serialVersionUID = 1L;

	/** model name */
	private static final String name = "TF_IDF2";

	/** The constant k_1.*/
	private double k_1 = 1.2d;
	
	/** The constant b.*/
	private double b = 0.75d;

	/** 
	 * A default constructor to make this model.
	 */
	public TF_IDF2() {
		super();
	}
	/** 
	 * Constructs an instance of TF_IDF
	 * @param _b
	 */
	public TF_IDF2(double _b) {
		this();
		this.b = _b;
	}

	/**
	 * Returns the name of the model, in this case "TF_IDF"
	 * @return the name of the model
	 */
	public final String getInfo() {
		return name;
	}
	/**
	 * Uses TF_IDF to compute a weight for a term in a document.
	 * @param tf The term frequency of the term in the document
	 * @param docLength the document's length
	 * @return the score assigned to a document with the given 
	 *		 tf and docLength, and other preset parameters
	 */
	public final double score(double tf, double numberOfDocs, double docFreq) {
		//System.out.println(number +","+  documentFrequency);
		double idf = Math.log(numberOfDocs/docFreq+1);
		return tf * idf;
	}
	/**
	 * This method provides the contract for implementing weighting models.
	 * 
	 * As of Terrier 3.6, the 5-parameter score method is being deprecated
	 * since it is not used. The two parameter score method should be used
	 * instead. Tagged for removal in a later version.
	 * 
	 * @param tf The term frequency in the document
	 * @param docLength the document's length
	 * @param documentFrequency The document frequency of the term
	 * @param termFrequency the term frequency in the collection
	 * @param keyFrequency the term frequency in the query
	 * @return the score returned by the implemented weighting model.
	 */
	@Deprecated
	@Override
	public final double score(
		double tf,
		double docLength,
		double documentFrequency,
		double termFrequency,
		double keyFrequency) 
	{
		double Robertson_tf = k_1*tf/(tf+k_1*(1-b+b*docLength/averageDocumentLength));
		double idf = WeightingModelLibrary.log(numberOfDocuments/documentFrequency+1);
		return keyFrequency*Robertson_tf * idf;

	}

	/**
	 * Sets the b parameter to ranking formula
	 * @param _b the b parameter value to use.
	 */
	public void setParameter(double _b) {
		this.b = _b;
	}


	/**
	 * Returns the b parameter to the ranking formula as set by setParameter()
	 */
	public double getParameter() {
		return this.b;
	}
	@Override
	public double score(double tf, double docLength) {
		double idf = WeightingModelLibrary.log(numberOfDocuments/documentFrequency+1);
		return tf*idf;
		
	}
}
