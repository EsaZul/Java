import java.util.Arrays;

public class Assignment3 {
    int [][] optFinal;
    int size = 0;
    int originalLeftIndex = 0;
    int originalRightIndex = 0;
    int tempWall = 0;

    public int maxFruitCount (int[] sections) {
        size = sections.length;
        int sum = 0;
        int [][] optimalArray = new int [size][size];

        //Initializes an empty array filled with -1
        for(int[] arr : optimalArray){
            Arrays.fill(arr,-1);
        }

        optFinal = optimalArray.clone();
        int [][] subSum = new int [size][size];

        //Creates a 2D Array for the Sum of every subset possibility
        //Time Complexitiy O(n^2)
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (i == 0){
                    //System.out.println("J: " + j);
                    sum = sum + sections[j];
                    subSum[i][j] = sum;
                }
                else if (i <= j){
                    if (i == j){
                        subSum[i][j] = sections[i];
                    }
                    
                    else {
                        subSum[i][j] = subSum[i-1][j] - sections[i-1];
                    }
                }
            }
        }

        //Setting optimal array for one field as 0 for optimal
        for(int i=0; i < size; i++) {
            optFinal[i][i] = 0;
        }

        //Calculates every subset OPT possible and determines the maxFruitCount based off OPT array
        //Time Complexity O(n^2)
        int i = 0;
        int aReset = 0;
        int bReset = 1;
        int a = 0;
        int b = 1;
        while(optFinal[0][size-1] == -1){
            optFinal[a][b] = opt(a, b, subSum);
            a++;
            b++;
            if (b == size){
                bReset++;
                b = bReset;
                a = aReset;
            }
        }
        return optFinal[0][size-1];
    }

    //OPT Formula for maximizing fruit depening on subset boundaries
    public int opt(int i, int j, int [][] subSum) {

        //OPT of one section is 0 since humans claim fruit, return in O(1) time
        if (i == j) {
            optFinal[i][j] = 0;
            return optFinal[i][j];
        }

        //If OPT was already found for subset, return in O(1) time
        else if (optFinal[i][j] != -1){
            return  optFinal[i][j];
        }

        //The OPT of two sections is the minimum of the two, return in O(1) time
        else if (i+1 == j) {
            return Math.min(subSum[i][i], subSum[j][j]);
        }

        //Solving for OPT through recursion until it meets a base case
        else {
            originalLeftIndex = i;
            originalRightIndex = j;
            int wall = binarySearchWall(i, j, subSum);
            int max = 0;

            //checks Wall as a left edge case, and the one after
            if (wall == i){
                int indexBeforeWall = wall; //getIndexBeforeWall(i, j, subSum);
                int indexAfterWall = indexBeforeWall + 1;
                int leftResult = subSum[i][indexBeforeWall] + opt(i, indexBeforeWall, subSum);
                int rightResult = subSum[indexAfterWall][j] + opt(indexAfterWall, j, subSum);
                max = Math.min(leftResult, rightResult);
            }

            //checks wall as a right edge case, and the one before
            else if(wall == j){
                int indexBeforeWall = wall-1; //penultimate of array
                int indexAfterWall = wall; //last section of array
                int leftResult = subSum[i][indexBeforeWall] + opt(i, indexBeforeWall, subSum);
                int rightResult = subSum[indexAfterWall][j] + opt(indexAfterWall, j, subSum);
                max = Math.min(leftResult, rightResult);
            }

            //checks current position based off predicted wall
            //then checks the left and right side to determine if a greater max opt is found
            else{
                int indexBeforeWall = wall; //penultimate of array
                int indexAfterWall = wall+1; //last section of array
                int leftResult = subSum[i][indexBeforeWall] + opt(i, indexBeforeWall, subSum);
                int rightResult = subSum[indexAfterWall][j] + opt(indexAfterWall, j, subSum);

                max = Math.min(leftResult, rightResult);
                max = checkRightSide(i, j, wall, subSum, max);
                max = checkLeftSide(i, j, wall, subSum, max);
            }
            return max;
        }
    }
    //Predict wall location through binary search
    //Time Complexity O(log n)
    public int binarySearchWall(int i, int j, int [][]subSum){
        if (i == j){
            return j;
        }
        else {

            int difference = Math.abs(i - j);
            int halfwayPoint = 0;
            int leftHalf = 0;
            int rightHalf = 0;
            int leftIndex = 0;
            int rightIndex = 0;


            if (difference == 1){
                //leftIndex = 0;
            }
            else if (difference % 2 == 1) {
                halfwayPoint = difference / 2 + 1;
            } else {
                halfwayPoint = difference / 2;
            }

            leftIndex = i + halfwayPoint;
            rightIndex = leftIndex + 1;
            leftHalf = subSum[originalLeftIndex][leftIndex];
            rightHalf = subSum[rightIndex][originalRightIndex];

            if (leftHalf < rightHalf) {
                if (difference == 1){
                    return rightIndex;
                }
                else {
                    tempWall = binarySearchWall(rightIndex, j, subSum);
                }
            }
            else {
                if (difference == 1){
                    return leftIndex;
                }
                else {
                    tempWall = binarySearchWall(i, leftIndex, subSum);
                }
            }
        }
        return tempWall;
    }

    //Checks if a max OPT exists on the right of the predicted wall
    public int checkRightSide(int i, int j, int wall, int[][] subSum, int max){
        if (wall+1 != j){
            int indexBeforeWall = wall + 1; //penultimate of array
            int indexAfterWall = wall + 2; //last section of array
            int leftResult = subSum[i][indexBeforeWall] + opt(i, indexBeforeWall, subSum);
            int rightResult = subSum[indexAfterWall][j] + opt(indexAfterWall, j, subSum);
            int temp = Math.min(leftResult, rightResult);
            if (max > temp){
                return max;
            }
            else{
                max = temp;
                return checkRightSide(i, j, wall+1, subSum, max);
            }
        }
        else{
            return max;
        }
    }

    //Checks if a max OPT exists on the left of the predicted wall
    public int checkLeftSide(int i, int j, int wall, int[][] subSum, int max){
        if (wall-1 >= i) {
            int indexBeforeWall = wall - 1;
            int indexAfterWall = wall;
            int leftResult = subSum[i][indexBeforeWall] + opt(i, indexBeforeWall, subSum);
            int rightResult = subSum[indexAfterWall][j] + opt(indexAfterWall, j, subSum);
            int temp = Math.min(leftResult, rightResult);
            if (max > temp) {
                return max;
            } else {
                max = temp;
                return checkLeftSide(i, j, wall-1, subSum, max);
            }
        }
        else{
            return max;
        }
    }
}

