//template routine Java
package routines;

public class Template {
    public static void helloExemple(String message) {
        if (message == null) {
            message = "World";
        }
        System.out.println("Hello "+message+" !");
    }
}
