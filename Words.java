import java.util.regex.*;
import java.util.Scanner;
import java.util.TreeSet;
class Word implements Comparable<Word>{
 private String word;
 public Word(String s){
  word=s;
 }
 public String getWord(){
  return word;
 }
 "
 public boolean isSinging(){
  Pattern p=Pattern.compile(".*[^AEIOU]{2}.*");
    Matcher m=p.matcher(word);
  return !m.find();
 }
@Override
 public int compareTo(Word w) {
  if (this.isSinging()){
    if (w.isSinging()) return word.compareTo(w.word);
    return -1;
  }
   if (w.isSinging()) return 1;
  int t=word.length()-w.word.length();
  if (t!=0) return -t; 
  return word.compareTo(w.word);
 }
}
public class Words {
 public static void main(String[] args) {
  Scanner sc=new Scanner(System.in);
  TreeSet<Word> ts=new TreeSet<>();
  String s;
  do{
   s=sc.nextLine();
   if (s.equals("")) break;	
   ts.add(new Word(s));
  }while (true);
  sc.close();
 
  for (Word w:ts) System.out.println(w.getWord());
 }
}
