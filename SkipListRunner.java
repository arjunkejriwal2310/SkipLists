public class SkipListRunner
{
    public static void main(String[] args)
    {
        //Generating CSV file for Question 1
        SkipList<Integer> list1 = new SkipList<Integer>();
        list1.generateStats1();

        //Generating CSV file for Question 2
        SkipList<Integer> list2 = new SkipList<Integer>();
        double[] results = list2.generateStats2();

        System.out.println("The probabilities are...");
        System.out.print("For 10 students: ");
        System.out.printf("%.5f", results[0]);
        System.out.println();
        System.out.print("For 100 students: ");
        System.out.printf("%.5f", results[1]);
        System.out.println();
        System.out.print("For 1000 students: ");
        System.out.printf("%.5f", results[2]);
        System.out.println();

        System.out.println("The expected values are...");
        System.out.print("For 10 students: ");
        System.out.printf("%.5f", results[3]);
        System.out.println();
        System.out.print("For 100 students: ");
        System.out.printf("%.5f", results[4]);
        System.out.println();
        System.out.print("For 1000 students: ");
        System.out.printf("%.5f", results[5]);
        System.out.println();

    }
}
