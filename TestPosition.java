public class TestPosition {
    public static void main(String[] args) {
        // test 1 : Position(int,int)
        Position p1 = new Position(3, 5);
        System.out.println("p1 = " + p1); 

        // test 2 : Position(String)
        Position p2 = new Position("D4");
        System.out.println("p2 = " + p2); 

        // test 3 : Position + translation
        Position p3 = new Position(p2, 1, -2);
        System.out.println("p3 = " + p3); 
    }
}
