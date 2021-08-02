package calculator.calulation.lesson2.lesson4;

import android.util.Log;

public class LambdaJava {
    public static void main(){
        TestInterface testInterface = new TestInterface(){

            @Override
            public int test(int x, int y) {
                return x+y;
            }
        };
        TestInterface testInterfaceL;
        testInterfaceL = (x,y)->x+2*y;
        int zUsual = testInterface.test(1,3);
        int zLambda = testInterfaceL.test(1,3);
        Log.d("mylogs","zUsual "+zUsual+" zLambda"+zLambda);
    }
}

interface TestInterface{
    int test(int x,int y);
}
