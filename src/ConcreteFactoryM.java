import java.awt.*;


public class ConcreteFactoryM implements Creator {
    public Object factoryMethod(String name, Color color, Player pl) throws Exception {
        if (name.equals("pawn"))
            return new Pawn(color, pl);
        else if (name.equals("archer"))
            return new Archer(color, pl);
        else if (name.equals("checkers"))
            return new Checkers(color, pl);
        else
            return new Player(color, name);
    }
}
