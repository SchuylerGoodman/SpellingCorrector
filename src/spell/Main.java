package spell;

import java.io.IOException;

import spell.SpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
            if (args.length != 2) {
                System.out.println("Usage: java spell.Main text-file.txt input-word");
                System.exit(0);
            }
            String dictionaryFileName = args[0];
            if (!dictionaryFileName.endsWith(".txt")) {
                System.out.printf("In Main.main: %s is not a valid file type.\n", args[0]);
                System.exit(0);
            }            
            if (args[1].isEmpty()) {
                System.out.println("In Main.main: I am NOT wasting memory on that empty string. It ain't here.");
                System.exit(0);
            }

            String inputWord = args[1];

            SpellCorrector corrector = new Speller();
            
            long uTime1 = System.currentTimeMillis();            
            corrector.useDictionary(dictionaryFileName);
            long uTime2 = System.currentTimeMillis();
            System.out.printf("Elapsed Upload Time: %d milliseconds.\n", uTime2 - uTime1);
            
            long sTime1 = System.currentTimeMillis();
            String suggestion = corrector.suggestSimilarWord(inputWord);
            long sTime2 = System.currentTimeMillis();
            System.out.printf("Elapsed Search Time: %d milliseconds.\n", sTime2 - sTime1);
            if (suggestion == null) {
                System.out.println("No suggestions. You are very bad at spelling.");
            }
            else {
                System.out.println("Suggestion is: " + suggestion);
            }
	}

}