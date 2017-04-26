package org.terrier.utility;

import java.io.IOException;

import org.terrier.matching.models.TF_IDF2;
//import org.terrier.structures.DirectIndex;
import org.terrier.structures.Index;

public class VectorNormalizer {

	public static void main(String[] args) throws IOException {
		String dirIndex ="direct";
		Index index =Index.createIndex("/home/rob/terrier-3.6/var/index","data");
		//DirectIndex directIndex = new DirectIndex(index, dirIndex);
		//String indexName = "direct";
		TF_IDF2 TfIdf = new TF_IDF2();
		PreProcessingNorm processer = new PreProcessingNorm(dirIndex,index,TfIdf);
		processer.makeL2Norm();
		processer.save();
		System.exit(0);
	}

}
