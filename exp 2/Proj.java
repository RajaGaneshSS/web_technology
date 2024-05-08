import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

/**
 *
 * Extracts noun phrases from a sentence. To create sentences using OpenNLP use
 * the SentenceDetector classes.
 */
public class OpenNLPNounPhraseExtractor {

  static final int N = 2;

  public static void main(String[] args) {

    try {
      HashMap<String, Integer> termFrequencies = new HashMap<>();
      String modelPath = "c:\\temp\\opennlpmodels\\";
      TokenizerModel tm = new TokenizerModel(new FileInputStream(new File(modelPath + "en-token.zip")));
      TokenizerME wordBreaker = new TokenizerME(tm);
      POSModel pm = new POSModel(new FileInputStream(new File(modelPath + "en-pos-maxent.zip")));
      POSTaggerME posme = new POSTaggerME(pm);
      InputStream modelIn = new FileInputStream(modelPath + "en-chunker.zip");
      ChunkerModel chunkerModel = new ChunkerModel(modelIn);
      ChunkerME chunkerME = new ChunkerME(chunkerModel);
      //this is your sentence
      String sentence = "Barack Hussein Obama II  is the 44th awesome President of the United States, and the first African American to hold the office.";
      //words is the tokenized sentence
      String[] words = wordBreaker.tokenize(sentence);
      //posTags are the parts of speech of every word in the sentence (The chunker needs this info of course)
      String[] posTags = posme.tag(words);
      //chunks are the start end "spans" indices to the chunks in the words array
      Span[] chunks = chunkerME.chunkAsSpans(words, posTags);
      //chunkStrings are the actual chunks
      String[] chunkStrings = Span.spansToStrings(chunks, words);
      for (int i = 0; i < chunks.length; i++) {
        String np = chunkStrings[i];
        if (chunks[i].getType().equals("NP")) {
          if (termFrequencies.containsKey(np)) {
            termFrequencies.put(np, termFrequencies.get(np) + 1);
          } else {
            termFrequencies.put(np, 1);
          }
        }
      }
      System.out.println(termFrequencies);

    } catch (IOException e) {
    }
  }

}