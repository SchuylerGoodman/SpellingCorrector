package spell;

import java.awt.Point;
import java.util.ArrayList;

/** 
 *
 * @author schuyler
 */
public class WordTrie implements Trie {
    
    private int nodeCount = 0;
    private int wordCount = 0;
    private WordNode root = null;
    
    public WordTrie() {
        this.root = new WordNode();
        this.nodeCount++;
    }

    @Override
    public void add(String word) {
//        char[] lWord = word.toLowerCase().toCharArray();
        if (this.root == null) {
            this.root = new WordNode();
            this.nodeCount++;
        }
        WordNode currentNode = root;
//        for (char c : lWord) {
//            boolean nodeAdd = currentNode.add(c);
//            if (nodeAdd) {
//                this.nodeCount++;
//            }
//            if (currentNode.at(c) != null) {
//                currentNode = currentNode.at(c);
//            }
//            else {
//                System.out.printf("In WordTrie.add: Error occured at char %c for string %s.\n", c, word);
//                System.exit(0);
//            }
//        }
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            c = Character.toLowerCase(c);
            boolean nodeAdd = currentNode.add(c);
            if (nodeAdd) {
                this.nodeCount++;
            }
            if (currentNode.at(c) != null) {
                currentNode = currentNode.at(c);
            }
            else {
                System.out.printf("In WordTrie.add: Error occured at char %c for string %s.\n", c, word);
                System.exit(0);
            }
        }
        if (currentNode.getValue() < 1) {
            this.wordCount++;
        }
        currentNode.incrementValue();
    }
    
//    @Override
//    public void add(String word) {
//        if (!word.matches("\\p{Alpha}+")) {
//            System.out.printf("In Trie.add: Word %s contains non-alphabetic characters.", word);
//            System.exit(0);
//        }
//        Point pair = new Point();
//        this.root.insert(word.toLowerCase(), pair);
//        nodeCount += pair.x;
//        wordCount += pair.y;
//    }

    @Override
    public WordNode find(String word) {
        char[] lWord = word.toLowerCase().toCharArray();
        WordNode currentNode = root;
        for (char c : lWord) {
            if (currentNode.at(c) == null) {
                return null;
            }
            currentNode = currentNode.at(c);
        }
        if (currentNode.getValue() < 1) {
            return null;
        }
        return currentNode;
    }

    @Override
    public int getWordCount() {
        return this.wordCount;
    }

    @Override
    public int getNodeCount() {
        return this.nodeCount;
    }
    
    public WordNode getRoot() {
        return this.root;
    }
    
    
    
    @Override
    public String toString() {
       return this.root.toString(new StringBuilder(), new StringBuilder());
    }

    @Override
    public int hashCode() {
        int hash;
        hash = 31 * this.nodeCount;
        hash = hash ^ (37 * this.wordCount);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        WordTrie ob = (WordTrie) o;
        if (ob.getNodeCount() != this.nodeCount || ob.getWordCount() != this.getWordCount()) {
            return false;
        }
        if (!this.root.equals(ob.getRoot())) {
            return false;
        }
        return true;
    }
    
}