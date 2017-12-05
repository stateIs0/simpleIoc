package intecode;

public class Test {

  public static void main(String[] args) {
    String n = "3.72";
    int len = n.length();
    String str = n.substring(n.indexOf("."), len);
    double right = Double.parseDouble(("0" + str));
    System.out.println("" + right);
  }

}
