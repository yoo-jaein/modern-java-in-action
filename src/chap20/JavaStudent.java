package chap20;

public class JavaStudent {

  private String name;
  private int id;

  public static void main(String[] args) {
    JavaStudent s = new JavaStudent("Raoul", 1);
    System.out.println(s.name);
    s.id = 1337;
    System.out.println(s.id);
  }

  public JavaStudent() {}

  public JavaStudent(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public JavaStudent(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
