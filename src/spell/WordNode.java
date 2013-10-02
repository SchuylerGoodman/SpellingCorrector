/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spell;

import java.awt.Point;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author schuyler
 */
public class WordNode implements Trie.Node {

    private int value = 0;
    
    private WordNode[] nodes = null;
    
    public WordNode() {}
    
    @Override
    public int getValue() {
        return this.value;
    }
    
    public boolean add(char c) {
        boolean added = false;
        if (nodes == null) {
            nodes = new WordNode[26];
        }
        if (!Character.isAlphabetic(c)) {
            System.out.printf("In WordNode.add: %c is not a valid alphabetic character.\n", c);
            return added;
        }
        c = Character.toLowerCase(c);
        int index = (int) c - (int) 'a';
        if (nodes[index] == null) {
            nodes[index] = new WordNode();
            added = true;
        }
        return added;
    }
    
    public WordNode add(String word, int w) {
        WordNode finalNode = this;
        if (word == null) {
            System.out.printf("In WordNode.add: No word input.\n");
            return null;
        }
        if (w == word.length()) {
            this.value++;
        }
        else {
            try {
                char c = Character.toLowerCase(word.charAt(w));
                int index = (int) c - (int) 'a';
                if (index < 0 || index >= 26) {
                    System.out.printf("In WordNode.add: %c is not a valid alphabetic character.\n", (char) (index + (int) 'a'));
                    return null;
                }
                if (this.nodes[index] == null) {
                    this.nodes[index] = new WordNode();
                }
                finalNode = this.nodes[index].add(word, w + 1);
            }
            catch (StringIndexOutOfBoundsException e) {
                System.out.printf("In WordNode.add: Index %d out of bounds in string %s.\n", w, word);
                System.exit(0);
            }
        }
        return finalNode;
    }

    public void insert(String word, Point pair) {
        if (this.nodes == null) {
            this.nodes = new WordNode[26];
        }
        int index = word.charAt(0) - 97;
        
        if (this.nodes[index] == null) {
            this.nodes[index] = new WordNode();
            pair.x++;
        }
        
        if (word.length() > 1) {
            this.nodes[index].insert(word.substring(1), pair);
        }
        else {
            if (this.nodes[index].getValue() == 0) {
                pair.y++;
            }
            this.nodes[index].incrementValue();
        }
    }
    
    public WordNode at(char c) {
        
        if (!Character.isAlphabetic(c)) {
            System.out.printf("In WordNode.at: %c is not a valid alphabetic character.\n", c);
            System.exit(0);
        }
        if (this.nodes != null) {
            c = Character.toLowerCase(c);
            int index = (int) c - (int) 'a';
            if (this.nodes[index] != null) {
                return this.nodes[index];
            }
        }
        return null;
    }
    
    public void incrementValue() {
        this.value++;
    }
    
    public String toString(StringBuilder all, StringBuilder word) {
        if (this.value > 0) {
            all.append(word.toString()).append(" ").append(this.value).append("\n");
        }
        if (this.nodes != null) {
            for (int i = 0; i < this.nodes.length; i++) {
                if (this.nodes[i] != null) {
                    char c = (char) (i + (int) 'a');
                    word.append(c);
                    this.nodes[i].toString(all, word);
                }
            }            
        }
        try {
            word.deleteCharAt(word.length() - 1);
        }
        catch (IndexOutOfBoundsException e) {}
        return all.toString();
    }
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        if (this.nodes == null) {
//            
//        }
//        return sb.toString();
//    }
 
    @Override
    public int hashCode() {
        int hash;
        int tVal = this.value;
        int tNodes = this.nodes != null ? this.nodes.length : 0;
        if (tVal == 0) {
            tVal = 37;
        }
        if (tNodes == 0) {
            tNodes = 31;
        }
        hash = 43 * tVal;
        hash = hash ^ (47 * tNodes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final WordNode other = (WordNode) obj;
        if (this.value != other.value) {
            return false;
        }
        if (!Arrays.deepEquals(this.nodes, other.nodes)) {
            return false;
        }
        return true;
    }

}