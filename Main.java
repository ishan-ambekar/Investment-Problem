
import java.util.*;
import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args){

        //Table for storing values for all states (Shape - 11 rows for all possible s2 values 0 to 10)
        double[][] cost_matrix = new double[11][9];
        //To trace the previous states selected for current computation
        int[][] prev_index = new int[11][9];
        //Input matrix with returns for all the nine oppotunities
        double[][] inp_mat = new double[][]{{0,0,0,0,0,0,0,0,0},
            {4.1,1.8,1.5,2.2,1.3,4.2,2.2,1.0,0.5},
            {5.8,3.0,2.5,3.8,2.4,5.9,3.5,1.7,1},
            {6.5,3.9,3.3,4.8,3.2,6.6,4.2,2.3,1.5},
            {6.8,4.5,3.8,5.5,3.9,6.8,4.6,2.8,2}};

        int index = 0;
        /*Backward working, start from 9th opportunity and calculate till 1st opportunity
        s1 - opportunity
        s2 - represents the amount that has not been invested (indirectly represents the amount that can be invested for a state)*/
        for(int i = 0; i < 11; i++){
            for(int j=8;j>=0;j--){
                //Case when 0 money has been invested, represents first row
                if(i == 0) {
                    cost_matrix[i][j] = 0;
                    prev_index[i][j] = 0;
                }
                //Case for 9th opportunity, where returns will be as in the input table as it is the starting state
                else if(j == 8) {
                    cost_matrix[i][j] = cost_matrix[i - 1][j] + 0.5;
                    prev_index[i][j] = i-1;
                }
                //Case for all the other computations, Recursive equation used - max(r + cost_mat(s1+1, s2+d)) 
                else
                    cost_matrix[i][j] = calc_max(cost_matrix, inp_mat,prev_index, i, j);
            }
        }



        display(cost_matrix, inp_mat);

        System.out.println();
        //Path gives an idea about how much has been invested in an opportunity
        System.out.println("Path generated : ");
        System.out.print(10 + " " + prev_index[10][0] + " ");
        int i = 10;
        for(int j=0;j<7;j++) {
            System.out.print(prev_index[prev_index[i][j]][j + 1] + " ");
            i = prev_index[i][j];
        }
        System.out.println();

        System.out.print("Maximum return possible : ");
        System.out.println(cost_matrix[10][0] + " Million");


    }

    static double calc_max(double[][] cost_matrix, double[][] inp_mat,int[][] prev_index, int row, int col){

        ArrayList<Double> values = new ArrayList<>();
        ArrayList<Integer> prev = new ArrayList<>();
        //Conditions for d : 0<= d <= 4 and s2+d <= 10, also s2 is 10 - row 
        for(int d=0; d<=4 && (10 - row + d)<=10; d++) {
            values.add(inp_mat[d][col] + cost_matrix[row - d][col + 1]);
            prev.add(d);
        }
        for(int i=0;i<values.size();i++)
            if(Collections.max(values) == values.get(i))
                prev_index[row][col] = row - prev.get(i);
        //Return max value from values arraylist
        return Double.parseDouble(new DecimalFormat("##.#").format(Collections.max(values)));
    }



    static void display(double[][] cost_matrix, double[][] inp_mat){
        System.out.println("Table of maximum returns for a given investment plan and some amount of money: ");
        for(int i=0;i<11;i++){
            for(int j=0;j<9;j++)
                System.out.print(cost_matrix[i][j] + "   ");
            System.out.println();
        }
    }



}


/* OUTPUT : 
Table of maximum returns for a given investment plan and some amount of money: 
0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   
4.2   4.2   4.2   4.2   4.2   4.2   2.2   1.0   0.5   
8.3   6.4   6.4   6.4   6.4   6.4   3.5   1.7   1.0   
10.5   8.6   8.6   8.6   8.1   8.1   4.5   2.3   1.5   
12.7   10.4   10.3   10.3   9.4   9.4   5.2   2.8   2.0   
14.5   12.1   11.9   11.9   10.7   10.4   5.9   3.3   2.5   
16.2   13.7   13.4   13.2   11.8   11.1   6.5   3.8   3.0   
17.9   15.2   14.7   14.5   12.8   11.8   7.0   4.3   3.5   
19.5   16.5   16.0   15.6   13.6   12.5   7.5   4.8   4.0   
21.0   17.8   17.1   16.6   14.3   13.1   8.0   5.3   4.5   
22.3   19.0   18.1   17.6   15.0   13.6   8.5   5.8   5.0   

Path generated : 
10 8 7 6 4 4 2 0 0 
Maximum return possible : 22.3 Million
*/