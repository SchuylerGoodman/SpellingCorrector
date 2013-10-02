
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spell;

import java.io.*;
import java.util.*;
import spell.SpellCorrector.NoSimilarWordFoundException;

/**
 *
 * @author schuyler
 */
public class Speller implements SpellCorrector {
    
    private WordTrie trie = null;

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        this.trie = new WordTrie();
        int line = 1;
        try {
            try (Scanner scanimatron = new Scanner(new File(dictionaryFileName))) {
                while (scanimatron.hasNext()) {
                    this.trie.add(scanimatron.next());
                    line++;
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.printf("In Speller.useDictionary: The file %s was not found.\n", dictionaryFileName);
            System.exit(0);
        }
        catch (NoSuchElementException e) {
            System.out.printf("In Speller.useDictionary: File element on line %d out of bounds.\n", line);
            System.exit(0);
        }
        System.out.printf("Node Count: %d\n", this.trie.getNodeCount());
        System.out.printf("Word Count: %d\n", this.trie.getWordCount());
    }

    @Override
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
        if (this.trie == null) {
            System.out.println("In Speller.suggestSimilarWord: Dictionary has not yet been initialized.");
            System.exit(0);
        }
        boolean done = false;
        int maxCount = 0;
        String maxString = null;
        int distance = 0;
        int maxDistance = 2;
        WordNode found = this.trie.find(inputWord);
        if (found != null) {
            return inputWord;
        }
//        System.out.println(this.trie.hashCode());
//        System.out.println(this.trie.hashCode());
//        System.out.println(this.trie.getWordCount());
//        System.out.println(this.trie.getNodeCount());
//        System.out.println(this.trie.toString());
        Set<String> similar = new TreeSet<>();
        similar.add(inputWord);
        int c = 0;
        while (done == false) {
            distance++;
            Set<String> similar2 = new TreeSet<>();
            for (String s : similar) {
                this.getDeletion(s, similar2);
                this.getTransposition(s, similar2);
                this.getAlteration(s, similar2);
                this.getInsertion(s, similar2);
                for (String s2 : similar2) {
                    c++;
                    found = this.trie.find(s2);
                    if (found != null) {
                        done = true;
                        int tCount = found.getValue();
                        boolean isLarger = tCount > maxCount;
                        if (isLarger) {
                            maxCount = found.getValue();
                            maxString = s2;
                        }
                    }
                }
            }
            if (!done && distance >= maxDistance) {
                done = true;
            }
            System.out.printf("Distance: %d Max Distance: %d\n", distance, maxDistance);
            similar.clear();
            similar = similar2;
            System.out.printf("Set size: %d\n", similar.size());
        }
        if (maxString == null)
            throw new spell.SpellCorrector.NoSimilarWordFoundException();

        System.out.printf("words tried: %d\n", c);
        return maxString;
    }
    
    private Set<String> getDeletion(String word, Set<String> dList) {
//        Set<String> dList = new TreeSet<>();
        StringBuilder tWord = new StringBuilder(word);
        for (int i = 0; i < word.length(); i++) {
            char tchar = word.charAt(i);
            dList.add(tWord.deleteCharAt(i).toString());
            tWord.insert(i, tchar);
        }
        return dList;
    }
    
    private Set<String> getTransposition(String word, Set<String> arrr) {
//        Set<String> arrr = new TreeSet<>();
        StringBuilder tWord = new StringBuilder(word);
        for (int i = 0; i < word.length() - 1; i++) {
            char tchar0 = word.charAt(i);
            char tchar1 = word.charAt(i + 1);
            tWord.setCharAt(i, tchar1);
            tWord.setCharAt(i + 1, tchar0);
            arrr.add(tWord.toString());
            tWord.setCharAt(i, tchar0);
            tWord.setCharAt(i + 1, tchar1);
        }
        return arrr;
    }
    
    private Set<String> getAlteration(String word, Set<String> arroo) {
//        Set<String> arroo = new TreeSet<>();
        StringBuilder tWord = new StringBuilder(word);
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            for (int j = 0; j < 26; j++) {
                char tchar = (char) (j + (int) 'a');
                tWord.setCharAt(i, tchar);
                arroo.add(tWord.toString());
            }
            tWord.setCharAt(i, c);
        }
        return arroo;
    }
    
    private Set<String> getInsertion(String word, Set<String> argh) {
//        Set<String> argh = new TreeSet<>();
        StringBuilder tWord = new StringBuilder(word);
        for (int i = 0; i < word.length() + 1; i++) {
            for (int j = 0; j < 26; j++) {
                char tchar = (char) (j + (int) 'a');
                tWord.insert(i, tchar);
                argh.add(tWord.toString());
                tWord.deleteCharAt(i);
            }
        }
        return argh;
    }
    
}
