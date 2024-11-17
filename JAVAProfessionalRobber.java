abstract class Robber {
    
    public abstract void RobbingClass();

    public abstract int RowHouses(int[] money);
    public abstract int RoundHouses(int[] money);
    public abstract int SquareHouse(int[] money);
    public abstract int MultiHouseBuilding(int[] money);

    
    public void MachineLearning() {
        System.out.println("I love MachineLearning.");
    }
}

class JAVAProfessionalRobber extends Robber {

    
    @Override
    public void RobbingClass() {
        System.out.println("MScAI&ML");
    }

    @Override
    public int RowHouses(int[] money) {
        return simpleRob(money);
    }

    @Override
    public int RoundHouses(int[] money) {
        if (money.length == 1) return money[0];
        return Math.max(simpleRobHelper(money, 0, money.length - 2),
                        simpleRobHelper(money, 1, money.length - 1));
    }

    @Override
    public int SquareHouse(int[] money) {
        return simpleRob(money);
    }

    @Override
    public int MultiHouseBuilding(int[] money) {
        return simpleRob(money);
    }

    private int simpleRob(int[] money) {
        return simpleRobHelper(money, 0, money.length - 1);
    }

    private int simpleRobHelper(int[] money, int start, int end) {
        int prevTwo = 0, prevOne = 0;
        for (int i = start; i <= end; i++) {
            int current = Math.max(prevOne, prevTwo + money[i]);
            prevTwo = prevOne;
            prevOne = current;
        }
        return prevOne;
    }

    public static void main(String[] args) {
        JAVAProfessionalRobber robber = new JAVAProfessionalRobber();

        robber.RobbingClass();
        robber.MachineLearning();

        int[] rowHouses = {2, 7, 9, 3, 1};
        int[] roundHouses = {2, 3, 2};
        int[] squareHouses = {1, 2, 3, 1};
        int[] multiHouse = {5, 3, 4, 11, 2};

        System.out.println("Row Houses: " + robber.RowHouses(rowHouses));
        System.out.println("Round Houses: " + robber.RoundHouses(roundHouses));
        System.out.println("Square Houses: " + robber.SquareHouse(squareHouses));
        System.out.println("MultiHouse Building: " + robber.MultiHouseBuilding(multiHouse));
    }
}
