package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.HashSet;
import java.util.Arrays;
import android.util.Log;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> wordSet =  new HashSet<String>();
    private ArrayList<String> wordList=new ArrayList<String>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<String, ArrayList<String>>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            //Log.d("tagnamezhi", word);
            String sorted= Helper(word);
            if(lettersToWord.containsKey(sorted)){
                lettersToWord.get(sorted).add(word);
            }
            else{
                lettersToWord.put(sorted, new ArrayList<String >());
                lettersToWord.get(sorted).add(word);
            }
        }

       // minNumber+  Math.round( Math.random() )
        //Random rnd = new Random();

    }

    public String Helper(String word){
        char[] result = word.toCharArray();
        Arrays.sort(result);
        return new String(result);
    }

    public boolean isGoodWord(String word, String base) {
        if(word.toLowerCase().contains(base.toLowerCase())  ){ return false;}
        else if(!wordSet.contains(word))  {
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char j = 'a'; j <= 'z' ; j++) {
            String given=word;
            given=given+j;

            given=Helper(given);
            Log.d("tagnamezhi", given);
            if( lettersToWord.containsKey(given) ){

                result.addAll(lettersToWord.get(given));

            }
        }

        Log.d("tagnamezhi", Integer.toString(result.size()));
        return result;


    }

    public String pickGoodStarterWord() {
        int wordListSize= wordList.size();
        Random rand = new Random();
        int index=rand.nextInt((wordListSize-1)+0);
       //long index= Math.round( Math.random() );
        while( wordList.get(index).length()<3 || wordList.get(index).length()>5||
                getAnagramsWithOneMoreLetter(wordList.get(index)).size()< MIN_NUM_ANAGRAMS){
            index=rand.nextInt((wordListSize-1)+0);

        }

        return wordList.get(index);


    }
}
