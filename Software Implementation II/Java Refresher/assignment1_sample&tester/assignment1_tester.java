package assignment1;

import static assignment1.Problem1.problem1;
import static assignment1.Problem2.problem2;
import static assignment1.Problem3.problem3;

/*Function Headers
public static int problem1(int n, String s);
public static String[] problem2(String s);
public static String problem3(String s);
*/

public class assignment1_tester  {

public static void main(String[] args) {
        String s1 = "7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450";
        int n1 = 4;
        if(problem1(n1,s1) == 5832)
            System.out.println("P1 Test 1 Success");
        else
            System.out.println("P1 Test 1 Failure");

        String s2 = "The wicked wizard's wily wraith garnishes his master`s pasta with garlic.";
        String[] o2 = {"wizard's", "garnishes"};
        boolean check = true;
        String[] output = problem2(s2);
        for(int i = 0; i < o2.length && i <output.length; i ++)
        if(!((o2[i].equals(output[i]))))
            check = false;

        if(check)
            System.out.println("P2 Test 1 Success");
        else
            System.out.println("P2 Test 1 Failure");

        String s3 = "I was slowly walking to the park with my over enthusiastic dog when he bit me, and I shouted, Ouch!";
        String o3 = "I_PRP was_VBD slowly_RB walking_VBG to_TO the_DT park_NN with_IN my_PRP$ over_IN enthusiastic_JJ dog_NN when_WRB he_PRP bit_VBD me_PRP ,_, and_CC I_PRP shouted_VBD ,_, Ouch_NNP !_.";
        if(problem3(s3).equals(o3))
            System.out.println("P3 Test 1 Success");
        else
            System.out.println("P3 Test 1 Failure");
        }
}