import java.util.Arrays;
import java.util.Scanner;
abstract class Furniture implements Comparable<Furniture>{

 private String name;
 private double price;
 private int setCount;
 private int amount;
 private double discountPercent;

 public Furniture(String name,double price,int setCount,double discountPercent){
  this.name=name;
  this.price=price;
  this.setCount=setCount;
  this.discountPercent=discountPercent;
  amount=0;
 }
 
 public void setAmount(int a) throws IllegalArgumentException{
   if (a<=0) throw new IllegalArgumentException("Positive values required");
   if (a>100) throw new IllegalArgumentException("Amount exceeding 100");
  amount=a;
 }
 
 public int getAmount(){
  return amount;
 }
 
 public void setSetCount(int c){
  setCount=c;
 }
 
 public double cost(int cnt){
  return (cnt/setCount)*setCount*price*(1-discountPercent/100.0)+
	     (cnt%setCount)*price;
 }

 abstract public String getColor();
@Override
 public int compareTo(Furniture f){ 
  int t=getColor().compareTo(f.getColor());
  if (t!=0) return -t; 
    return name.compareTo(f.name);
 }

@Override
 public String toString(){
  return name + ", price:" + String.format("%.2f", price) + 
		 ", set size:"+setCount+", discount:"+String.format("%.2f", discountPercent)+"%";
 }
}

class Table extends Furniture{
 private String kind;
 public Table(String kind,double price){
  super("table",price,1,0);
  this.kind=kind;
 }

@Override
 public String getColor(){
  return "white";  
 }

@Override
 public String toString(){
  return "In stock: "+getAmount()+", "+kind + " " + super.toString();
 }
}

class Chair extends Furniture{
 private String color;
 public Chair(String color,double price,double discount){
  super("chair",price,6,discount);
  this.color=color;
 }

@Override
 public String getColor(){
  return color;
 }
@Override 
 public String toString(){
  return "In stock: "+getAmount()+", "+color + " " + super.toString();	
 }
}
class Stool extends Chair{
 public Stool(double price){
  super("black",price,20);
  setSetCount(4);
 }

@Override
 public String toString(){
  return super.toString()+", no back";
 }
}

public class Furmishing {
 static int n;
 static Furniture[] f;

 private static void ExampleData(){
  n=5;
  f=new Furniture[n];
  f[0]=new Table("round", 118);
  f[0].setAmount(7);
  f[1]=new Chair("white",27.6, 14);
  f[1].setAmount(45);
  f[2]=new Stool(14.33);
  f[2].setAmount(17);
  f[3]=new Chair("yellow",33.24, 17);
  f[3].setAmount(22);
  f[4]=new Chair("white",30, 22);
  f[4].setAmount(25);
 }
 
 private static void inputData(Scanner sc){
  String[] v;
  String s;
  System.out.print("Number of deliveries: ");
  n=sc.nextInt();
  sc.nextLine();
  f=new Furniture[n];
  boolean flag;
  for (int i=0;i<n;i++){
   System.out.println("Delivery #"+ (i+1) + " of " + n);
   flag=true;
   s=sc.nextLine();
   v=s.split(" ");
   switch (Character.toUpperCase(v[0].charAt(0))){
    case  'T': {
     f[i]=new Table(v[1],Double.parseDouble(v[2]));
     break;
    }
    case 'C': {
     f[i]=new Chair(v[1],Double.parseDouble(v[2]),Double.parseDouble(v[3]));
     break;
    }
    case 'S': {
     f[i]=new Stool(Double.parseDouble(v[1]));
    }
   }
   
   do{
	flag=false;
    System.out.print("Amount: ");
	try{  
	 String b=sc.nextLine();
     int a=Integer.parseInt(b);
     f[i].setAmount(a);
	}
	catch(NumberFormatException e){
	 System.out.println("Integer expected "+e.getMessage());
	 flag=true;
	}
	catch(IllegalArgumentException e){
	 System.out.println(e.getMessage());
	 flag=true;
	}
   }while(flag);
  }
 }
 public static void main(String[] args) {
  Scanner sc=new Scanner(System.in);
  String[] v;
  String s;
  inputData(sc);
  Arrays.sort(f,0,n);
  for (Furniture t:f) System.out.println(t);
  System.out.println("Client order");
  s=sc.nextLine();
  sc.close();
  String clName="",clColor;
  int clAmount;
  v=s.split(" ");
  switch (Character.toUpperCase(v[0].charAt(0))){
   case  'T': {
	clName="Table";
    break;
   }
   case 'C': {
	clName="Chair";
	break;
   }
   case 'S': {
	clName="Stool";
   }
  }
  clColor=v[1];
  clAmount=Integer.parseInt(v[2]);
  double minPrice=1e20;
  for (Furniture t:f)
   if (clName.equals(t.getClass().getName()) && 
	  clColor.equals(t.getColor()) && 
	  clAmount<=t.getAmount()) 
	 if (t.cost(clAmount)<minPrice) minPrice=t.cost(clAmount);
  if (minPrice==1e20) System.out.println("NO");
  else System.out.println(String.format("%.2f", minPrice));
 }
}
