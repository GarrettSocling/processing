/*
 * Copyright (C) 2012-14 Manindra Moharana <me@mkmoharana.com>
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package processing.mode.experimental;

import java.util.ArrayList;

/**
 * A class containing multiple utility methods
 * 
 * @author Manindra Moharana <me@mkmoharana.com>
 * 
 */

public class Utils {

  public ArrayList<Utils.OfsSetTemp> offsetMatch;
  String word1, word2;
  public static String reverse(String s) {
    char w[] = s.toCharArray();
    for (int i = 0; i < w.length / 2; i++) {
      char t = w[i];
      w[i] = w[w.length - 1 - i];
      w[w.length - 1 - i] = t;
    }
    return new String(w);
  }
  
  public void getPdeOffForJavaOff(int start, int length){
    System.out.println("PDE <-> Java" );
    for (int i = 0; i < offsetMatch.size(); i++) {
      System.out.print(offsetMatch.get(i).pdeOffset + " <-> " + offsetMatch.get(i).javaOffset);
      System.out.println(", " + word1.charAt(offsetMatch.get(i).pdeOffset) + " <-> "
          + word2.charAt(offsetMatch.get(i).javaOffset));
    }
    System.out.println("Length " + offsetMatch.size());
    System.out.println(start + " java start off, pde start off "
        + getPdeOffForJavaOff(start));
    System.out.println((start + length - 1) + " java end off, pde end off "
        + getPdeOffForJavaOff(start + length - 1));
  }
  
  public void getJavaOffForPdeOff(int start, int length){
//    System.out.println("PDE <-> Java" );
//    for (int i = 0; i < offsetMatch.size(); i++) {
//      System.out.print(offsetMatch.get(i).pdeOffset + " <-> " + offsetMatch.get(i).javaOffset);
//      System.out.println(", " + word1.charAt(offsetMatch.get(i).pdeOffset) + " <-> "
//          + word2.charAt(offsetMatch.get(i).javaOffset));
//    }
//    System.out.println("Length " + offsetMatch.size());
    System.out.println(start + " pde start off, java start off "
        + getJavaOffForPdeOff(start));
    System.out.println((start + length - 1) + " pde end off, java end off "
        + getJavaOffForPdeOff(start + length - 1));
  }
  
  public int getPdeOffForJavaOff(int javaOff){
    for (int i = offsetMatch.size() - 1; i >= 0;i--) {
      if(offsetMatch.get(i).javaOffset < javaOff){
        continue;
      }
      else
      if(offsetMatch.get(i).javaOffset == javaOff){
//        int j = i;
        while(offsetMatch.get(--i).javaOffset == javaOff){
          System.out.println("MP " + offsetMatch.get(i).javaOffset + " "
              + offsetMatch.get(i).pdeOffset); 
        }
        int pdeOff = offsetMatch.get(++i).pdeOffset;
        while(offsetMatch.get(--i).pdeOffset == pdeOff);
        int j = i + 1;
        if (j > -1 && j < offsetMatch.size())
          return offsetMatch.get(j).pdeOffset;      
      }
      
    }
    return -1;
  }
  
  public int getJavaOffForPdeOff(int pdeOff){
    for (int i = offsetMatch.size() - 1; i >= 0;i--) {
      if(offsetMatch.get(i).pdeOffset < pdeOff){
        continue;
      }
      else
      if(offsetMatch.get(i).pdeOffset == pdeOff){
//        int j = i;
        while(offsetMatch.get(--i).pdeOffset == pdeOff){
//          System.out.println("MP " + offsetMatch.get(i).javaOffset + " "
//              + offsetMatch.get(i).pdeOffset); 
        }
        int javaOff = offsetMatch.get(++i).javaOffset;
        while(offsetMatch.get(--i).javaOffset == javaOff);
        int j = i + 1;
        if (j > -1 && j < offsetMatch.size())
          return offsetMatch.get(j).javaOffset;
      }
      
    }
    return -1;
  }

  public int minDistance(String word1, String word2) {
    this.word1 = word1;
    this.word2 = word2;
//    word1 = reverse(word1);
//    word2 = reverse(word2);
    int len1 = word1.length();
    int len2 = word2.length();
    System.out.println(word1 + " len: " + len1);
    System.out.println(word2 + " len: " + len2);
    // len1+1, len2+1, because finally return dp[len1][len2]
    int[][] dp = new int[len1 + 1][len2 + 1];

    for (int i = 0; i <= len1; i++) {
      dp[i][0] = i;
    }

    for (int j = 0; j <= len2; j++) {
      dp[0][j] = j;
    }

    //iterate though, and check last char
    for (int i = 0; i < len1; i++) {
      char c1 = word1.charAt(i);
      for (int j = 0; j < len2; j++) {
        char c2 = word2.charAt(j);
        //System.out.print(c1 + "<->" + c2);
        //if last two chars equal
        if (c1 == c2) {
          //update dp value for +1 length
          dp[i + 1][j + 1] = dp[i][j];
//          System.out.println();
        } else {
          int replace = dp[i][j] + 1;
          int insert = dp[i][j + 1] + 1;
          int delete = dp[i + 1][j] + 1;
//          if (replace < delete) {
//            System.out.println(" --- D");
//          } else
//            System.out.println(" --- R");
          int min = replace > insert ? insert : replace;
          min = delete > min ? min : delete;
          dp[i + 1][j + 1] = min;
        }
      }
    }

//    for (int i = 0; i < dp.length; i++) {
//      for (int j = 0; j < dp[0].length; j++) {
//        System.out.print(dp[i][j] + " ");
//      }
//      System.out.println();
//    }
//    int maxLen = Math.max(len1, len2)+2;
//    int pdeCodeMap[] = new int[maxLen], javaCodeMap[] = new int[maxLen];
//    System.out.println("Edit distance1: " + dp[len1][len2]);
    ArrayList<OfsSetTemp> alist = new ArrayList<Utils.OfsSetTemp>();
    offsetMatch = alist;
    minDistInGrid(dp, len1, len2, 0, 0, word1.toCharArray(),
                  word2.toCharArray(), alist);
//    System.out.println("PDE-to-Java");
//    for (int i = 0; i < maxLen; i++) {
//      System.out.print(pdeCodeMap[i] + " <-> " + javaCodeMap[i]);
//      System.out.println(", " + word1.charAt(pdeCodeMap[i]) + " <-> "
//          + word2.charAt(javaCodeMap[i]));
//    }
//    for (int i = 0; i < alist.size(); i++) {
//      System.out.print(alist.get(i).pdeOffset + " <-> " + alist.get(i).javaOffset);
//      System.out.println(", " + word1.charAt(alist.get(i).pdeOffset) + " <-> "
//          + word2.charAt(alist.get(i).javaOffset));
//    }
//    System.out.println("Length " + alist.size());
    return dp[len1][len2];
  }

  public static int distance(String a, String b) {
//    a = a.toLowerCase();
//    b = b.toLowerCase();

    // i == 0
    int[] costs = new int[b.length() + 1];
    for (int j = 0; j < costs.length; j++)
      costs[j] = j;
    for (int i = 1; i <= a.length(); i++) {
      // j == 0; nw = lev(i - 1, j)
      costs[0] = i;
      int nw = i - 1;
      for (int j = 1; j <= b.length(); j++) {
        int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                          a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
        nw = costs[j];
        costs[j] = cj;
      }
    }
    System.out.println("Edit distance2: " + costs[b.length()]);
    return costs[b.length()];
  }

  public void minDistInGrid(int g[][], int i, int j, int fi, int fj,
                                   char s1[], char s2[], ArrayList set) {
//    if(i < s1.length)System.out.print(s1[i] + " <->");
//    if(j < s2.length)System.out.print(s2[j]);
    if (i < s1.length && j < s2.length) {
//      pdeCodeMap[k] = i;
//      javaCodeMap[k] = j;
      //System.out.print(s1[i] + " " + i + " <-> " + j + " " + s2[j]);
      set.add(new OfsSetTemp(i, j));
//      if (s1[i] != s2[j])
//        System.out.println("--");
    }
    //System.out.println();
    if (i == fi && j == fj) {
      //System.out.println("Reached end.");
    } else {
      int a = Integer.MAX_VALUE, b = a, c = a;
      if (i > 0)
        a = g[i - 1][j];
      if (j > 0)
        b = g[i][j - 1];
      if (i > 0 && j > 0)
        c = g[i - 1][j - 1];
      int mini = Math.min(a, Math.min(b, c));
      if (mini == a) {
        //System.out.println(s1[i + 1] + " " + s2[j]);
        minDistInGrid(g, i - 1, j, fi, fj, s1, s2,set);
      } else if (mini == b) {
        //System.out.println(s1[i] + " " + s2[j + 1]);
        minDistInGrid(g, i, j - 1, fi, fj, s1, s2, set);
      } else if (mini == c) {
        //System.out.println(s1[i + 1] + " " + s2[j + 1]);
        minDistInGrid(g, i - 1, j - 1, fi, fj, s1, s2, set);
      }
    }
  }
  
  public class OfsSetTemp {
    public final int pdeOffset, javaOffset;
    public OfsSetTemp(int pde, int java){
      pdeOffset = pde;
      javaOffset = java;
    }
  }
  
//  public class OffsetMatch{
//    public final ArrayList<Integer> pdeOffset, javaOffset;
//    
//    public OffsetMatch(){
//      pdeOffset = new ArrayList<Integer>();
//      javaOffset = new ArrayList<Integer>();
//    }
//  }

  public static void main(String[] args) {
//    minDistance("c = #qwerty;", "c = 0xffqwerty;");
    Utils a = new Utils();
    
    a.minDistance("int a = int(can); int ball;", "int a = PApplet.parseInt(can); int ball;");
    a.getPdeOffForJavaOff(25, 3);
    a.getJavaOffForPdeOff(12,3);
//    minDistance("static void main(){;", "public static void main(){;");
//      minDistance("#bb00aa", "0xffbb00aa");
    //a.minDistance("color g = #qwerty;", "int g = 0xffqwerty;");
    System.out.println("--");
    a.minDistance("color abc = #qwerty;", "int abc = 0xffqwerty;");
    a.getPdeOffForJavaOff(4, 3);
    a.getJavaOffForPdeOff(6,3);
//    distance("c = #bb00aa;", "c = 0xffbb00aa;");
  }
}
