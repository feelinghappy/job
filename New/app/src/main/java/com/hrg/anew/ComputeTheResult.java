package com.hrg.anew;
import android.util.Log;

import java.util.Random;

public class ComputeTheResult {
    public double[] DegreeResult = new double[1000];
    int age;
    int degree;
    boolean fatflag;
    double height;
    final int onedegree = 1;
    Random ra = new Random();
    int[] ranq = new int[10];
    public double[] result = new double[1000];
    int rl = 0;
    double scrose;
    double standheight;
    double standweight;
    final int threedegree = 3;
    final int twodegree = 2;
    double weight;
    final int zerodegree = 0;

    private void GetTheItemComputeResult(int minindex, int maxindex, int num1, int num2, int num3) {
        int temp = minindex;
        int ranq_index = 0;
        for (int i = 0; i < ranq.length; i++) {
            ranq[i] = 0;
        }

        for (int i = minindex; i < (maxindex+1); i++) {
            result[i] = computetheitems(i, degree);
            DegreeResult[i] = degree;

        }

        if (num1 > 0) {
            Getitmesresult(ranq_index, 1, maxindex, minindex);
            ranq_index = ranq_index + 1;
        }

        if (num2 > 0) {
            Getitmesresult(ranq_index, 2, maxindex, minindex);
            ranq_index = ranq_index + 1;
        }
        if (num3 > 0) {
            Getitmesresult(ranq_index, 3, maxindex, minindex);
            ranq_index = ranq_index + 1;
        }


    }

    private Boolean checktarry(int[] arry, int v, int index) {
        boolean result = false;
        for (int i = 0; i < arry.length; i++)
        {
           if(i == arry.length)
           {
               return true;
           }
           else if((index == i) || (v != arry[i]))
           {
               return true;
           }
        }
      return result;
    }

    private void Getitmesresult(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if(checktarry(this.ranq, this.ranq[paramInt1], paramInt1).booleanValue()) {
            this.ranq[paramInt1] = (this.ra.nextInt(paramInt3 + 1 - paramInt4) + paramInt4);
        }
        this.result[this.ranq[paramInt1]] = computetheitems(this.ranq[paramInt1], paramInt2);
        this.DegreeResult[this.ranq[paramInt1]] = paramInt2;
        int i = this.ranq[paramInt1];
        Log.d("DegreeResult"+i,DegreeResult[i]+"");
    }






    private double compute0(int paramInt) {
        Log.e("compute0 paramInt",paramInt+"");
        double result;
        if (paramInt == 0) {
            result = computeret(48.264D, 65.37D);
        } else if (paramInt == 1) {
            result = computeret(65.37D, 69.645D);
        } else if (paramInt == 2) {
            result = computeret(69.645D, 73.673D);
        } else {
            result = computeret(100.0D, 73.673D);
        }
        Log.e("compute0",result+"");
        return result;

    }

    private double compute1(int paramInt) {
        Log.e("compute1 paramInt",paramInt+"");
        double result;
        if (paramInt == 0) {
            result = computeret(56.749D, 67.522D);
        } else if (paramInt == 1) {
            result = computeret(56.749D, 67.522D);
        } else if (paramInt == 2) {
            result = computeret(69.447D, 74.927D);
        } else {
            result = computeret(100.0D, 74.927D);
        }
        Log.e("compute1",result+"");
        return result;
    }
    private double compute10(int degree) {
        double ret = 0.0;
        if(degree == 0) {
            ret = computeret(1.99,1.55);
        }
        else if(degree == 1) {
            ret = computeret(1.55, 1.08);
        }
        else if(degree == 2) {
            ret = computeret(1.08, 0.6);
        }
        else
        {
            ret = computeret(0.597D, 0.3D);
        }
        return ret;
    }

    private double compute100(int paramInt) {
        double result;
        if (paramInt == 0) {
            result = computeret(65.424D, 59.786D);
        } else if (paramInt == 1) {
            result = computeret(59.786D, 57.331D);
        } else if (paramInt == 2) {
            result = computeret(57.331D, 54.347D);
        } else {
            result = computeret(51.331D, 54.347D);
        }
        Log.e("compute100",result+"");
        return result;
    }

    private double compute101(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(63.213D, 58.715D);
        } else if (paramInt == 1) {
            ret = computeret(58.715D, 56.729D);
        } else if (paramInt == 2) {
            ret = computeret(56.729D, 52.743D);
        } else {
            ret = computeret(49.729D, 52.743D);
        }
        Log.e("compute101",ret+"");
        return  ret;
    }

    private double compute102(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(37.642D, 33.967D);
        } else if (paramInt == 1) {
            ret = computeret(33.967D, 31.265D);
        } else if (paramInt == 2) {
            ret = computeret(33.967D, 31.265D);
        } else {
            ret = computeret(29.967D, 31.265D);
        }
        return ret;
    }

    private double compute103(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(141.476D, 133.642D);
        } else if (paramInt == 1) {
            ret = computeret(133.642D, 126.619D);
        } else if (paramInt == 2) {
            ret = computeret(126.619D, 123.321D);
        } else {
            ret = computeret(120.619D, 123.321D);
        }
        return ret;
    }

    private double compute104(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(7.4D, 7.0D);}
        else if (paramInt == 1){
            ret = computeret(7.4D, 7.8D);}
        else {
            ret = computeret(5.5D, 7.0D);
        }
        return ret;
    }

    private double compute105(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.464D, 1.213D);
        }
        else if (paramInt == 1){
            ret = computeret(1.464D, 1.213D);}
        else if (paramInt == 2){
            ret = computeret(0.962D, 0.659D);}
        else {
            ret = computeret(0.362D, 0.659D);
        }
        return ret;
    }

    private double compute106(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(7.6D, 6.289D);
        } else if (paramInt == 1) {
            ret = computeret(6.289D, 4.978D);
        } else if (paramInt == 2) {
            ret = computeret(4.978D, 3.709D);
        } else {
            ret = computeret(2.978D, 3.709D);
        }
        return ret;
    }

    private double compute107(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(3.054D, 2.491D);
        } else if (paramInt == 1) {
            ret = computeret(2.491D, 1.928D);
        } else if (paramInt == 2) {
            ret = computeret(1.928D, 1.307D);
        } else {
            ret = computeret(1.028D, 1.307D);
        }
        return ret;
    }

    private double compute108(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.029D, 1.637D);
        } else if (paramInt == 1) {
            ret = computeret(1.637D, 1.245D);
        } else if (paramInt == 2) {
            ret = computeret(1.245D, 0.826D);
        } else {
            ret = computeret(0.545D, 0.826D);
        }
        return ret;
    }

    private double compute109(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.176D, 1.685D);
        } else if (paramInt == 1) {
            ret = computeret(2.176D, 1.685D);
        } else if (paramInt == 2) {
            ret = computeret(1.194D, 0.817D);
        } else {
            ret = computeret(0.594D, 0.817D);
        }
        return ret;
    }

    private double compute11(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.187D, 1.553D);
        } else if (paramInt == 1) {
            ret = computeret(1.553D, 1.182D);
        } else if (paramInt == 2) {
            ret = computeret(1.182D, 0.983D);
        } else {
            ret = computeret(0.983D, 0.7D);
        }
        return ret;
    }

    private double compute110(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(6.732D, 5.657D);
        } else if (paramInt == 1) {
            ret = computeret(5.657D, 4.582D);
        } else if (paramInt == 2) {
            ret = computeret(4.582D, 3.248D);
        } else {
            ret = computeret(2.582D, 3.248D);
        }
        return ret;
    }

    private double compute111(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(11.53D, 9.256D);
        } else if (paramInt == 1) {
            ret = computeret(9.256D, 6.982D);
        } else if (paramInt == 2) {
            ret = computeret(6.982D, 4.579D);
        } else {
            ret = computeret(3.982D, 4.579D);
        }
        return ret;
    }

    private double compute112(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(12.37D, 9.677D);
        }
        else if (paramInt == 1) {
            ret = computeret(9.677D, 6.982D);
        }
        else if (paramInt == 2) {
            ret = computeret(6.982D, 4.892D);
        }
        else {
            ret = computeret(3.982D, 4.892D);
        }
        return ret;
    }

    private double compute113(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(7.403D, 6.258D);
        } else if (paramInt == 1) {
            ret = computeret(6.258D, 5.113D);
        } else if (paramInt == 2) {
            ret =  computeret(5.113D, 4.012D);
        } else {
            ret = computeret(3.113D, 4.012D);
        }
        return  ret;
    }

    private double compute114(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.862D, 2.337D);
        }
        else if (paramInt == 1) {
            ret = computeret(2.337D, 1.812D);
        }
        else if (paramInt == 2) {
            ret = computeret(1.812D, 1.209D);
        } else {
            ret = computeret(1.012D, 1.209D);
        }
        return ret;
    }

    private double compute115(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(9.356D, 8.865D);
        } else if (paramInt == 1) {
            ret = computeret(8.865D, 6.654D);
        } else if (paramInt == 2) {
            ret = computeret(6.654D, 5.489D);
        } else {
            ret = computeret(4.654D, 5.489D);
        }
        return ret;
    }

    private double compute116(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(9.756D, 8.326D);
        } else if (paramInt == 1) {
            ret = computeret(8.326D, 4.687D);
        } else if (paramInt == 2) {
            ret = computeret(4.687D, 3.195D);
        } else {
            ret = computeret(2.687D, 3.195D);
        }
        return ret;
    }
    //

    private double compute117(int degree) {
        double ret = 0.0;
        if(degree == 0) {
            ret = computeret(3.109, 0.51);
        }
        else if(degree == 1) {
            ret = computeret(7.29, 3.109);
            return ret;
        }
        else if(degree == 2) {
            ret = computeret(9.73, 7.29);
            return ret;
        }
        else
        {
            ret = computeret(10.25, 9.73);
        }
        return ret;

    }

    private double compute118(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(3.107D, 2.031D);
        } else if (paramInt == 1) {
            ret = computeret(2.031D, 1.107D);
        } else if (paramInt == 2) {
            ret = computeret(1.107D, 0.486D);
        } else {
            ret = computeret(0.107D, 0.486D);
        }
        return ret;
    }

    private double compute119(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.831D, 3.188D);
        }
        else if (paramInt == 1) {
            ret = computeret(3.188D, 5.615D);
        }
        else if (paramInt == 2) {
            ret = computeret(5.615D, 8.036D);
        } else {
            ret = computeret(9.615D, 8.036D);
        }
        return ret;
    }

    private double compute12(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(7.154D, 8.481D);
        } else if (paramInt == 1) {
            ret = computeret(8.481D, 18.417D);
        } else if (paramInt == 2) {
            ret = computeret(18.417D, 21.274D);
        } else {
            ret = computeret(21.274D, 25.0D);
        }
        return  ret;
    }

    private double compute120(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret =  computeret(1.116D, 4.101D);
        } else if (paramInt == 1) {
            ret = computeret(4.101D, 7.348D);
        } else if (paramInt == 2) {
            ret = computeret(7.348D, 9.907D);
        } else {
            ret = computeret(11.348D, 9.907D);
        }
        return ret;
    }

    private double compute121(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.233D, 0.559D);
        } else if (paramInt == 1) {
            ret = computeret(0.559D, 1.066D);
        } else if (paramInt == 2) {
            ret = computeret(1.066D, 1.549D);
        } else {
            ret = computeret(1.766D, 1.549D);
        }
        return ret;
    }

    private double compute122(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.332D, 0.726D);
        } else if (paramInt == 1) {
            ret = computeret(0.726D, 1.226D);
        } else if (paramInt == 2) {
            ret = computeret(1.226D, 1.708D);
        } else {
            ret = computeret(1.826D, 1.708D);
        }
        return ret;
    }

    private double compute123(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.118D, 0.892D);
        } else if (paramInt == 1) {
            ret = computeret(0.892D, 1.37D);
        } else if (paramInt == 2) {
            ret = computeret(1.37D, 1.892D);
        } else {
            ret = computeret(1.37D, 1.092D);
        }
        return ret;
    }

    private double compute124(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.017D, 5.157D);
        } else if (paramInt == 1) {
            ret = computeret(5.157D, 8.253D);
        } else if (paramInt == 2) {
            ret = computeret(8.253D, 10.184D);
        } else {
            ret = computeret(12.253D, 10.184D);
        }
        return ret;
    }

    private double compute125(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.124D, 3.453D);
        } else if (paramInt == 1) {
            ret = computeret(3.453D, 6.723D);
        } else if (paramInt == 2) {
            ret = computeret(6.723D, 9.954D);
        } else {
            ret = computeret(11.723D, 9.954D);
        }
        return ret;
    }

    private double compute126(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.824D, 1.942D);
        } else if (paramInt == 1) {
            ret = computeret(1.942D, 3.141D);
        } else if (paramInt == 2) {
            ret = computeret(3.141D, 4.231D);
        } else {
            ret = computeret(5.141D, 4.231D);
        }
        return ret;
    }

    private double compute127(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(3.512D, 2.717D);
        } else if (paramInt == 1) {
            ret = computeret(2.717D, 1.521D);
        } else if (paramInt == 2) {
            ret = computeret(1.521D, 0.645D);
        } else {
            ret = computeret(0.321D, 0.645D);
        }
        return  ret;
    }
    //皮肤自由基指数
    private double compute128(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(0.346D, 0.501D);}
        else if (paramInt == 1){
            ret = computeret(0.501D, 0.711D);}
        else if (paramInt == 2){
            ret = computeret(0.711D, 0.845D);}
        else {
            ret = computeret(1.111D, 0.845D);
        }
        return ret;
    }
    //皮肤血红丝指数
    private double compute129(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(0.842D, 1.858D);}
        else if (paramInt == 1) {
            ret = computeret(0.842D, 1.858D);
        }
        else if (paramInt == 2){
            ret = computeret(2.534D, 3.316D);}
        else {
            ret = computeret(3.734D, 3.316D);
        }
        return ret;
    }

    private double compute13(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.942D, 0.708D);
        }
        else if (paramInt == 1) {
            ret = computeret(0.708D, 0.431D);
        }
        else if (paramInt == 2){
            ret = computeret(0.431D, 0.247D);}
        else {
            ret = computeret(0.247D, 0.1D);
        }
        return ret;
    }
    //皮肤胶原蛋白指数
    private double compute130(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(6.079D, 4.471D);}
        else if (paramInt == 1){
            ret = computeret(6.079D, 4.471D);}
        else if (paramInt == 2){
            ret = computeret(2.879D, 1.453D);}
        else {
            ret = computeret(1.079D, 1.453D);
        }
        return ret;
    }
    //皮肤油脂指数
    private double compute131(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(14.477D, 21.348D);}
        else if (paramInt == 1){
            ret = computeret(21.348D, 28.432D);}
        else if (paramInt == 2){
            ret = computeret(28.432D, 35.879D);}
        else {
            ret = computeret(38.4329D, 35.879D);
        }
        return ret;
    }
    //皮肤免疫指数
    private double compute132(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.035D, 3.23D);
        }
        else if (paramInt == 1){
            ret = computeret(3.23D, 5.545D);}
        else if (paramInt == 2) {
            ret = computeret(5.545D, 7.831D);
        }
        else {
            ret = computeret(9.545D, 7.831D);
        }
        return ret;
    }
    //皮肤水分指数
    private double compute133(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.218D, 0.953D);
        }
        else if (paramInt == 1) {
            ret = computeret(0.953D, 1.623D);
        }
        else if (paramInt == 2) {
            ret = computeret(1.623D, 2.369D);
        } else {
            ret = computeret(1.623D, 1.369D);
        }
        return ret;
    }
    //皮肤水分流失量
    private double compute134(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(2.214D, 4.158D);}
        else if (paramInt == 1){
            ret = computeret(4.158D, 6.076D);}
        else if (paramInt == 2){
            ret = computeret(6.076D, 7.983D);}
        else {
            ret = computeret(9.076D, 7.983D);
        }
        return ret;
    }
    //胰岛素
    private double compute135(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(2.845D, 4.017D);}
        else  if (paramInt == 1) {
            ret = computeret(4.317D, 4.017D);
        }
        else {
            ret = computeret(2.845D, 2.345D);
        }
        return ret;
    }
    //胰多肽
    private double compute136(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(3.21D, 6.854D);}
        else if (paramInt == 1) {
            ret = computeret(7.854D, 6.854D);
        }
        else {
            ret = computeret(3.21D, 2.21D);
        }
        return ret;
    }
    //胰高血糖
    private double compute137(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(2.412D, 2.974D);}
        else if (paramInt == 1){
            ret = computeret(3.174D, 2.974D);}
        else {
            ret = computeret(2.412D, 2.212D);
        }
        return ret;
    }
    //血糖 胰岛素分泌系数
    private double compute138(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(2.967D, 3.528D);}
        else if (paramInt == 1){
            ret = computeret(3.728D, 3.528D);}
        else {
            ret = computeret(2.967D, 2.767D);
        }
        return ret;
    }
   //血糖系数
    private double compute139(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0)
            ret = computeret(2.163D, 7.321D);
        else if (paramInt == 1)
            ret = computeret(8.321D, 7.321D);
        else {
            ret = computeret(2.163D, 2.063D);
        }
        return ret;
    }

    private double compute14(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(21.396D, 6.138D);
        } else if (paramInt == 1) {
            ret = computeret(6.138D, 3.219D);
        } else if (paramInt == 2) {
            ret = computeret(3.219D, 1.214D);
        } else {
            ret = computeret(1.214D, 1.0D);
        }
        return ret;
    }
    //尿糖系数49: GLL
    private double compute140(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0)
            ret = computeret(2.204D, 2.819D);
        else if (paramInt == 1)
            ret = computeret(3.074D, 2.819D);
        else {
            ret = computeret(2.204D, 2.012D);
        }
        return ret;
    }

    private double compute141(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(3.309D, 2.074D);
        } else if (paramInt == 1) {
            ret = computeret(2.074D, 1.348D);
        } else if (paramInt == 2) {
            ret = computeret(1.348D, 0.626D);
        } else {
            ret = computeret(0.348D, 0.626D);
        }
        return ret;
    }

    private double compute142(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.979D, 1.833D);
        } else if (paramInt == 1) {
            ret = computeret(1.833D, 1.097D);
        } else if (paramInt == 2) {
            ret = computeret(1.097D, 0.373D);
        } else {
            ret = computeret(0.833D, 1.097D);
        }
        return ret;
    }

    private double compute143(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.101D, 1.116D);
        } else if (paramInt == 1) {
            ret = computeret(1.116D, 0.809D);
        } else if (paramInt == 2) {
            ret = computeret(0.809D, 0.432D);
        } else {
            ret = computeret(0.209D, 0.432D);
        }
        return ret;
    }

    private double compute144(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.246D, 1.449D);
        } else if (paramInt == 1) {
            ret = computeret(1.449D, 1.325D);
        } else if (paramInt == 2) {
            ret = computeret(1.325D, 1.243D);
        } else {
            ret = computeret(1.025D, 1.243D);
        }
        return ret;
    }

    private double compute145(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.588D, 0.831D);
        } else if (paramInt == 1) {
            ret = computeret(0.831D, 0.627D);
        } else if (paramInt == 2) {
            ret = computeret(0.627D, 0.418D);
        } else {
            ret = computeret(0.227D, 0.418D);
        }
        return ret;
    }
    //
    private double compute146(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.281D, 0.726D);
        } else if (paramInt == 1) {
            ret = computeret(0.726D, 0.476D);
        } else if (paramInt == 2) {
            ret = computeret(0.476D, 0.171D);
        } else {
            ret = computeret(0.026D, 0.171D);
        }
        return ret;
    }
   //重金属
    private double compute147(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(86.73D, 180.97D);
        }
        else if (paramInt == 1) {
            ret = computeret(180.97D, 190.37D);
        }
        else if (paramInt == 2) {
            ret = computeret(190.37D, 203.99D);
        } else {
            ret = computeret(210.37D, 203.99D);
        }
        return ret;
    }
    //刺激性饮料
    private double compute148(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.209D, 0.751D);
        } else if (paramInt == 1) {
            ret = computeret(0.751D, 0.844D);
        } else if (paramInt == 2) {
            ret = computeret(0.844D, 0.987D);
        } else {
            ret = computeret(1.244D, 0.987D);
        }
        return ret;
    }
    //电磁波辐射
    private double compute149(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.046D, 0.167D);
        } else if (paramInt == 1) {
            ret = computeret(0.167D, 0.457D);
        } else if (paramInt == 2) {
            ret = computeret(0.457D, 0.989D);
        } else {
            ret = computeret(1.257D, 0.989D);
        }
        return ret;
    }

    private double compute15(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(210.81D, 143.37D);
        } else if (paramInt == 1) {
            ret = computeret(143.37D, 110.24D);
        } else if (paramInt == 2) {
            ret = computeret(110.24D, 100.41D);
        } else {
            ret = computeret(100.41D, 97.0D);
        }
        return ret;
    }
    //农药残留物
    private double compute150(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.013D, 0.313D);
        } else if (paramInt == 1) {
            ret = computeret(0.313D, 0.406D);
        } else if (paramInt == 2) {
            ret = computeret(0.406D, 0.626D);
        } else {
            ret = computeret(0.806D, 0.626D);
        }
        return ret;
    }

    private double compute151(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.202D, 0.991D);
        } else if (paramInt == 1) {
            ret = computeret(0.991D, 1.754D);
        } else if (paramInt == 2) {
            ret = computeret(1.754D, 2.413D);
        } else {
            ret = computeret(2.8D, 2.413D);
        }
        return ret;
    }

    private double compute152(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0)
            ret = computeret(0.713D, 0.992D);
        else if (paramInt == 1)
            ret = computeret(0.992D, 1.478D);
        else if (paramInt == 2)
            ret = computeret(1.478D, 1.897D);
        else {
            ret = computeret(1.897D, 1.677D);
        }
        return ret;
    }

    private double compute153(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.432D, 0.826D);
        }
        else if (paramInt == 1){
            ret = computeret(0.826D, 1.423D);}
        else if (paramInt == 2) {
            ret = computeret(1.423D, 1.991D);
        }
        else {
            ret = computeret(1.991D, 2.2D);
        }
        return ret;
    }

    private double compute154(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(1.684D, 4.472D);}
        else if (paramInt == 1){
            ret = computeret(4.472D, 7.245D);}
        else if (paramInt == 2){
            ret = computeret(7.245D, 10.137D);}
        else {
            ret = computeret(10.137D, 12.0D);
        }
        return ret;
    }

    private double compute155(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(0.433D, 0.796D);}
        else if (paramInt == 1){
            ret = computeret(0.796D, 1.182D);}
        else if (paramInt == 2){
            ret = computeret(1.182D, 1.656D);}
        else{ ret = computeret(1.882D, 1.656D);}
        return ret;
    }

    private double compute156(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(8.84D, 3.296D);
        } else if (paramInt == 1) {
            ret = computeret(3.296D, 1.163D);
        } else if (paramInt == 2) {
            ret = computeret(1.163D, 0.213D);
        } else {
            ret = computeret(0.213D, 73.0D);
        }
        return ret;
    }

    private double compute157(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(8.93D, 4.886D);
        } else if (paramInt == 1) {
            ret = computeret(4.886D, 3.631D);
        } else if (paramInt == 2) {
            ret = computeret(3.631D, 1.843D);
        } else {
            ret = computeret(1.631D, 1.843D);
        }
        return ret;
    }

    private double compute158(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(7.849D, 3.142D);
        } else if (paramInt == 1) {
            ret = computeret(3.142D, 1.167D);
        } else if (paramInt == 2) {
            ret = computeret(1.167D, 0.274D);
        } else {
            ret = computeret(0.167D, 0.274D);
        }
        return ret;
    }

    private double compute159(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(16.743D, 6.818D);
        } else if (paramInt == 1) {
            ret = computeret(6.818D, 4.109D);
        } else if (paramInt == 2) {
            ret = computeret(4.109D, 0.947D);
        } else {
            ret = computeret(0.509D, 0.947D);
        }
        return ret;
    }

    private double compute16(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.103D, 0.642D);
        } else if (paramInt == 1) {
            ret = computeret(0.642D, 0.757D);
        } else if (paramInt == 2) {
            ret = computeret(0.757D, 0.941D);
        } else {
            ret = computeret(0.941D, 1.0D);
        }
        return ret;
    }

    private double compute160(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0)
            ret = computeret(2.204D, 2.819D);
        else if (paramInt == 1)
            ret = computeret(2.819D, 3.421D);
        else if (paramInt == 2)
            ret = computeret(3.421D, 3.948D);
        else {
            ret = computeret(4.221D, 3.948D);
        }
        return ret;
    }

    private double compute161(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.348D, 3.529D);
        }
        else if (paramInt == 1) {
            ret = computeret(3.529D, 5.755D);
        }
        else if (paramInt == 2){
            ret = computeret(5.755D, 7.948D);
        }
        else {
            ret = computeret(8.755D, 7.948D);
        }
        return ret;
    }

    private double compute162(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(2.301D, 4.782D);}
        else if (paramInt == 1){
            ret = computeret(4.782D, 7.213D);}
        else if (paramInt == 2){
            ret = computeret(7.213D, 9.413D);}
        else {
            ret = computeret(10.213D, 9.413D);
        }
        return ret;
    }

    private double compute163(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(2.845D, 4.017D);}
        else if (paramInt == 1){
            ret = computeret(4.017D, 5.327D);}
        else if (paramInt == 2){
            ret = computeret(5.327D, 6.548D);}
        else {
            ret = computeret(6.548D, 7.52D);
        }
        return ret;
    }

    private double compute164(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.012D, 4.892D);
        }
        else if (paramInt == 1){
            ret = computeret(4.892D, 7.033D);}
        else if (paramInt == 2){
            ret = computeret(7.033D, 9.437D);}
        else {
            ret = computeret(10.033D, 9.437D);
        }
        return ret;
    }
    //胶原蛋白 眼睛
    private double compute165(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.325D, 6.352D);}
        else if (paramInt == 1){
            ret = computeret(6.352D, 4.689D);}
        else if (paramInt == 2){
            ret = computeret(4.689D, 3.235D);}
        else {
            ret = computeret(3.235D, 3.0D);
        }
        return ret;
    }
    //胶原蛋白 牙齿
    private double compute166(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.362D, 7.235D);}
        else if (paramInt == 1){
            ret = computeret(7.235D, 6.658D);}
        else if (paramInt == 2){
            ret = computeret(6.658D, 4.265D);}
        else
        {
            ret = computeret(4.265D, 3.437D);
        }
        return ret;
    }
    //胶原蛋白 毛发与皮肤
    private double compute167(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.123D, 7.235D);}
        else if (paramInt == 1){
            ret = computeret(7.235D, 5.354D);}
        else if (paramInt == 2){
            ret = computeret(5.354D, 3.369D);}
        else
        {
            ret =computeret(2.354D, 3.369D);
        }
        return ret;
    }

    private double compute168(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.235D, 6.326D);}
        else if (paramInt == 1){
            ret = computeret(6.326D, 4.365D);}
        else if (paramInt == 2){
            ret = computeret(4.365D, 3.365D);}
        else {
            ret = computeret(2.033D, 3.365D);
        }
        return  ret;
    }

    private double compute169(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.368D, 6.235D);}
        else if (paramInt == 1){
            ret = computeret(6.235D, 4.365D);}
        else if (paramInt == 2){
            ret = computeret(4.365D, 3.256D);}
        else {
            ret = computeret(2.365D, 3.256D);
        }
        return ret;
    }

    private double compute17(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(0.659D, 0.253D);}
        else if (paramInt == 1){
            ret =  computeret(0.253D, 0.115D);
        }
        else if (paramInt == 2){
            ret = computeret(0.115D, 0.053D);
        }
        else
        {
            ret =  computeret(0.03D, 0.053D);
        }
        return ret;
    }

    private double compute170(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.365D, 7.238D);}
        else if (paramInt == 1){
            ret = computeret(7.238D, 5.568D);}
        else if (paramInt == 2){
            ret = computeret(5.568D, 3.365D);}
        else
        {
            ret = computeret(3.365D, 2.437D);
        }
        return  ret;
    }

    private double compute171(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0)
            ret = computeret(8.235D, 6.325D);
        else if (paramInt == 1){
            ret = computeret(6.325D, 4.354D);}
        else if (paramInt == 2){
            ret = computeret(4.354D, 3.629D);}
        else {
            ret = computeret(3.629D, 2.437D);
        }
        return ret;
    }

    private double compute172(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.235D, 6.354D);}
        else if (paramInt == 1){
            ret = computeret(6.354D, 5.568D);}
        else if (paramInt == 2){
            ret = computeret(5.568D, 4.568D);}
        else {
            ret = computeret(4.568D, 3.437D);
        }
        return ret;
    }

    private double compute173(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.368D, 7.358D);}
        else if (paramInt == 1){
            ret = computeret(7.358D, 5.657D);}
        else if (paramInt == 2){
            ret = computeret(5.657D, 4.564D);}
        else {
            ret = computeret(4.564D, 3.437D);
        }
        return ret ;
    }

    private double compute174(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(8.987D, 6.689D);
        }
        else if (paramInt == 1){
            ret = computeret(6.689D, 5.357D);}
        else if (paramInt == 2){
            ret = computeret(5.357D, 3.687D);}
        else {
            ret = computeret(2.357D, 3.687D);
        }
        return ret;
    }

    private double compute175(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.864D, 7.682D);}
        else if (paramInt == 1){
            ret = computeret(7.682D, 4.687D);}
        else if (paramInt == 2){
            ret = computeret(4.687D, 3.258D);}
        else {
            ret = computeret(3.258D, 2.437D);
        }
        return ret;
    }

    private double compute176(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(8.695D, 6.368D);
        }
        else if (paramInt == 1) {
            ret = computeret(6.368D, 5.354D);
        }
        else if (paramInt == 2) {
            ret = computeret(5.354D, 3.234D);
        }
        else {
            ret = computeret(2.354D, 3.234D);
        }
        return ret;
    }

    private double compute177(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.239D, 7.357D);}
        else if (paramInt == 1){
            ret = computeret(7.357D, 5.654D);}
        else if (paramInt == 2){
            ret = computeret(5.654D, 4.562D);}
        else {
            ret = computeret(3.654D, 4.562D);
        }
        return ret;
    }

    private double compute178(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0){
            ret = computeret(8.682D, 6.256D);}
        else if (paramInt == 1){
            ret = computeret(6.256D, 4.235D);}
        else if (paramInt == 2){
            ret = computeret(4.235D, 3.264D);}
        else {
            ret = computeret(2.235D, 3.264D);
        }
        return ret;
    }

    private double compute179(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(9.235D, 8.354D);
        } else if (paramInt == 1) {
            ret = computeret(8.354D, 7.685D);
        } else if (paramInt == 2) {
            ret = computeret(7.685D, 6.235D);
        } else {
            ret = computeret(5.685D, 6.235D);
        }
        return ret;
    }

    private double compute18(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.109D, 0.351D);
        }
        else if (paramInt == 1) {
            ret = computeret(0.351D, 0.483D);
        }
        else if (paramInt == 2) {
            ret = computeret(0.483D, 0.699D);
        } else {
            ret = computeret(0.883D, 0.699D);
        }
        return ret;
    }

    private double compute19(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.817D, 0.442D);
        }
        else if (paramInt == 1) {
            ret = computeret(0.817D, 0.442D);
        }
        else if (paramInt == 2) {
            ret = computeret(0.262D, 0.169D);
        } else {
            ret = computeret(0.062D, 0.169D);
        }
        return ret;
    }

    private double compute2(int paramInt) {
        double result = 0.0;
        if (paramInt == 0) {
            result = computeret(0.481D, 1.043D);
        } else if (paramInt == 1) {
            result = computeret(1.043D, 1.669D);
        } else if (paramInt == 2) {
            result = computeret(1.669D, 1.892D);
        } else {
            result = computeret(1.892D, 2.0D);
        }
        return result;
    }

    private double compute20(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(220.621D, 116.34D);
        } else if (paramInt == 1) {
            ret = computeret(116.34D, 90.36D);
        } else if (paramInt == 2) {
            ret = computeret(90.36D, 60.23D);
        } else {
            ret = computeret(20.36D, 60.23D);
        }
        return ret;
    }

    private double compute21(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.992D, 0.713D);
        } else if (paramInt == 1) {
            ret = computeret(0.713D, 0.475D);
        } else if (paramInt == 2) {
            ret = computeret(0.475D, 0.381D);
        } else {
            ret = computeret(0.2D, 0.381D);
        }
        return ret;
    }

    private double compute22(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.991D, 0.202D);
        } else if (paramInt == 1) {
            ret = computeret(0.202D, 0.094D);
        } else if (paramInt == 2) {
            ret = computeret(0.094D, 0.043D);
        } else {
            ret = computeret(0.024D, 0.043D);
        }
        return ret;
    }

    private double compute23(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.826D, 0.432D);
        } else if (paramInt == 1) {
            ret = computeret(0.432D, 0.358D);
        } else if (paramInt == 2) {
            ret = computeret(0.358D, 0.132D);
        } else {
            ret = computeret(0.06D, 0.132D);
        }
        return ret;
    }

    private double compute24(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.097D, 0.419D);
        } else if (paramInt == 1) {
            ret = computeret(0.419D, 0.582D);
        } else if (paramInt == 2) {
            ret = computeret(0.582D, 0.692D);
        } else {
            ret = computeret(0.782D, 0.692D);
        }
        return ret;
    }

    private double compute25(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(65.234D, 59.847D);
        } else if (paramInt == 1) {
            ret = computeret(59.847D, 58.236D);
        } else if (paramInt == 2) {
            ret = computeret(58.236D, 55.347D);
        } else {
            ret = computeret(50.236D, 55.347D);
        }
        return ret;
    }

    private double compute26(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(61.213D, 58.425D);
        } else if (paramInt == 1) {
            ret = computeret(58.425D, 56.729D);
        } else if (paramInt == 2) {
            ret = computeret(56.729D, 53.103D);
        } else {
            ret = computeret(50.729D, 53.103D);
        }
        return ret;
    }

    private double compute27(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(35.642D, 34.367D);
        } else if (paramInt == 1) {
            ret = computeret(34.367D, 31.467D);
        } else if (paramInt == 2)
            ret = computeret(31.467D, 28.203D);
        else {
            ret = computeret(26.467D, 28.203D);
        }
        return ret;
    }

    private double compute28(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(140.476D, 133.437D);
        } else if (paramInt == 1) {
            ret = computeret(133.437D, 126.749D);
        } else if (paramInt == 2) {
            ret = computeret(126.749D, 124.321D);
        } else {
            ret = computeret(120.749D, 124.321D);
        }
        return ret;
    }

    private double compute29(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(6.483D, 3.572D);
        } else if (paramInt == 1) {
            ret = computeret(3.572D, 3.109D);
        } else if (paramInt == 2) {
            ret = computeret(3.109D, 2.203D);
        } else {
            ret = computeret(2.009D, 2.203D);
        }
        return ret;
    }

    private double compute3(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.327D, 0.937D);
        } else if (paramInt == 1) {
            ret = computeret(0.937D, 1.543D);
        } else if (paramInt == 2)
            ret = computeret(1.543D, 1.857D);
        else {
            ret = computeret(1.857D, 2.0D);
        }
        return ret;
    }
    //胆功能 血清球蛋白
    private double compute30(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(126.0D, 159.0D);
        } else if (paramInt == 1) {
            ret = computeret(175.0D, 159.0D);
        } else {
            ret = computeret(126.0D, 110.0D);
        }
        return ret;
    }

    private double compute31(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.232D, 0.686D);
        } else if (paramInt == 2) {
            ret = computeret(0.12D, 0.232D);
        } else {
            ret = computeret(0.686D, 0.79D);
        }
        return ret;
    }


    private double compute32(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.082D, 0.342D);
        } else if (paramInt == 1) {
            ret = computeret(0.392D, 0.342D);
        } else {
            ret = computeret(0.082D, 0.05D);
        }
        return ret;
    }

    private double compute33(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.317D, 0.695D);
        } else if (paramInt == 1) {
            ret = computeret(0.9D, 0.695D);
        } else {
            ret = computeret(0.317D, 0.28D);
        }
        return ret;
    }

    private double compute34(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.218D, 0.549D);
        } else if (paramInt == 1) {
            ret = computeret(0.76D, 0.549D);
        } else {
            ret = computeret(0.218D, 0.12D);
        }
        return ret;
    }

    private double compute35(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(3348.0D, 3529.0D);
        }
        else if (paramInt == 1) {
            ret = computeret(3759.0D, 3529.0D);
        } else {
            ret = computeret(3348.0D, 3112.0D);
        }
        return ret;
    }
    //肺 肺总量
    private double compute36(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(4301.0D, 4782.0D);
        }
        else if (paramInt == 1) {
            ret = computeret(4982.0D, 4782.0D);
        } else {
            ret = computeret(4301.0D, 4171.0D);
        }
        return ret;
    }

    private double compute37(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.374D, 1.709D);
        } else if (paramInt == 1) {
            ret = computeret(1.909D, 1.709D);
        } else {
            ret = computeret(1.374D, 1.174D);
        }
        return ret;
    }

    private double compute38(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(17.903D, 21.012D);
        } else if (paramInt == 1) {
            ret = computeret(23.012D, 21.012D);
        } else {
            ret = computeret(17.903D, 16.903D);
        }
        return ret;
    }

    private double compute39(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        } else {
            ret = 1.0D;
        }
        return ret;
    }

    private double compute4(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.978D, 1.511D);
        } else if (paramInt == 1) {
            ret = computeret(1.511D, 1.047D);
        } else if (paramInt == 2) {
            ret = computeret(1.047D, 0.915D);
        } else {
            ret = computeret(0.747D, 0.915D);
        }
        return ret;
    }

    private double compute40(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        } else {
            ret = 1.0D;
        }
        return ret;
    }

    private double compute41(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            return 0.0D;
        } else {
            return 1.0D;
        }
    }

    private double compute42(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        }
        else {
            ret = 1.0D;
        }
        return ret;
    }

    private double compute43(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        } else {
            ret = 1.0D;
        }
        return ret;
    }

    private double compute44(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(9.461D, 3.342D);
        } else if (paramInt == 1) {
            ret = computeret(3.342D, 2.79D);
        } else if (paramInt == 2) {
            ret = computeret(2.79D, 1.394D);
        } else {
            ret = computeret(1.09D, 1.394D);
        }
        return ret;
    }

    private double compute45(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(18.741D, 4.111D);
        } else if (paramInt == 1) {
            ret = computeret(4.111D, 2.79D);
        } else if (paramInt == 2) {
            ret = computeret(2.79D, 1.737D);
        } else {
            ret = computeret(1.01D, 1.737D);
        }
        return ret;
    }

    private double compute46(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(9.814D, 3.241D);
        } else if (paramInt == 1) {
            ret = computeret(3.241D, 2.617D);
        } else if (paramInt == 2) {
            ret = computeret(3.241D, 2.617D);
        } else {
            ret = computeret(1.341D, 2.617D);
        }
        return ret;
    }

    private double compute47(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        } else {
            ret = 1.0D;
        }
        return ret;
    }

    private double compute48(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        } else {
            ret = 1.0D;
        }
        return ret;

    }

    private double compute49(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        } else {
            ret = 1.0D;
        }
        return ret;
    }

    private double compute5(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.192D, 0.412D);
        } else if (paramInt == 1) {
            ret = computeret(0.412D, 0.571D);
        } else if (paramInt == 2) {
            ret = computeret(0.571D, 0.716D);
        } else {
            ret = computeret(0.716D, 0.8D);
        }
        return ret;
    }

    private double compute50(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        }
        else {
            ret = 1.0D;
        }
        return ret;
    }

    private double compute51(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        } else {
            ret = 1.0D;
        }
        return ret;
    }

    private double compute52(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.023D, 3.23D);
        } else if (paramInt == 1) {
            ret = computeret(3.23D, 4.258D);
        } else if (paramInt == 2) {
            ret = computeret(4.258D, 6.549D);
        } else {
            ret = computeret(7.258D, 6.549D);
        }
        return ret;
    }

    private double compute53(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.471D, 6.079D);
        }
        else if (paramInt == 1) {
            ret = computeret(6.079D, 14.479D);
        }
        else if (paramInt == 2) {
            ret = computeret(14.479D, 19.399D);
        } else {
            ret = computeret(23.479D, 19.40D);
        }
        return ret;
    }

    private double compute54(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.213D, 2.717D);
        } else if (paramInt == 1) {
            ret = computeret(2.717D, 5.145D);
        } else if (paramInt == 2) {
            ret = computeret(5.145D, 6.831D);
        } else {
            ret = computeret(8.145D, 6.831D);
        }
        return ret;
    }

    private double compute55(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(78.329D, 61.431D);
        } else {
            ret = computeret(61.44D, 57.219D);
        }
        return ret;
    }

    private double compute56(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(73.246D, 66.432D);
        } else if (paramInt == 1) {
            ret = computeret(66.432D, 63.9468D);
        } else if (paramInt == 2) {
            ret = computeret(63.946D, 60.262D);
        } else {
            ret = computeret(57.946D, 60.262D);
        }
        return ret;
    }

    private double compute57(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.989D, 1.143D);
        } else if (paramInt == 1) {
            ret = computeret(1.143D, 0.945D);
        } else if (paramInt == 2) {
            ret = computeret(0.945D, 0.532D);
        } else {
            ret = computeret(0.245D, 0.532D);
        }
        return ret;
    }

    private double compute58(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.045D, 0.847D);
        } else if (paramInt == 1) {
            ret = computeret(0.847D, 0.663D);
        } else if (paramInt == 2) {
            ret = computeret(0.663D, 0.545D);
        } else {
            ret = computeret(0.363D, 0.545D);
        }
        return ret;
    }

    private double compute59(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0)
            ret = computeret(0.842D, 1.643D);
        else if (paramInt == 1)
            ret = computeret(1.643D, 1.721D);
        else if (paramInt == 2)
            ret = computeret(1.721D, 1.943D);
        else {
            ret = computeret(2.121D, 1.943D);
        }
        return  ret;
    }

    private double compute6(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(5.147D, 4.832D);
        } else if (paramInt == 1) {
            ret = computeret(4.832D, 4.177D);
        } else if (paramInt == 2) {
            ret = computeret(4.177D, 4.029D);
        } else {
            ret = computeret(4.029D, 3.9D);
        }
        return ret;
    }

    private double compute60(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.401D, 0.346D);
        } else if (paramInt == 1) {
            ret = computeret(0.345D, 0.311D);
        } else if (paramInt == 2) {
            ret = computeret(0.311D, 0.286D);
        } else {
            ret = computeret(0.231D, 0.286D);
        }
        return ret;
    }

    private double compute61(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(5.023D, 4.543D);
        }
        else if (paramInt == 1) {
            ret = computeret(4.543D, 3.872D);
        }
        else if (paramInt == 2) {
            ret = computeret(3.872D, 3.153D);
        } else {
            ret = computeret(2.872D, 3.153D);
        }
        return ret;
    }

    private double compute62(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(6.013D, 4.826D);
        } else if (paramInt == 1) {
            ret = computeret(4.826D, 4.213D);
        } else if (paramInt == 2) {
            ret = computeret(4.213D, 3.379D);
        } else {
            ret = computeret(3.013D, 3.379D);
        }
        return ret;
    }

    private double compute63(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.486D, 0.717D);
        } else if (paramInt == 1) {
            ret = computeret(0.717D, 0.541D);
        } else if (paramInt == 2) {
            ret = computeret(0.541D, 0.438D);
        } else {
            ret = computeret(0.411D, 0.438D);
        }
        return ret;
    }

    private double compute64(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(4.192D, 2.124D);
        } else if (paramInt == 1) {
            ret = computeret(2.124D, 1.369D);
        } else if (paramInt == 2) {
            ret = computeret(1.369D, 0.643D);
        } else {
            ret = computeret(0.369D, 0.643D);
        }
        return ret;
    }

    private double compute65(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.213D, 1.549D);
        } else if (paramInt == 1) {
            ret = computeret(1.549D, 1.229D);
        } else if (paramInt == 2) {
            ret = computeret(1.229D, 1.147D);
        } else {
            ret = computeret(0.929D, 1.147D);
        }
        return ret;
    }

    private double compute66(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.246D, 1.449D);
        }
        else if (paramInt == 1) {
            ret = computeret(1.449D, 1.325D);
        }
        else if (paramInt == 2) {
            ret = computeret(1.449D, 1.325D);
        } else {
            ret = computeret(1.149D, 1.325D);
        }
        return ret;
    }

    private double compute67(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(21.348D, 14.477D);
        } else if (paramInt == 1) {
            ret = computeret(14.477D, 12.793D);
        } else if (paramInt == 2) {
            ret = computeret(12.793D, 8.742D);
        } else {
            ret = computeret(5.793D, 8.742D);
        }
        return ret;
    }

    private double compute68(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.942D, 0.824D);
        } else if (paramInt == 1) {
            ret = computeret(0.824D, 0.547D);
        } else if (paramInt == 2) {
            ret = computeret(0.547D, 0.399D);
        } else {
            ret = computeret(2.121D, 1.943D);
        }
        return ret;
    }

    private double compute69(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(21.396D, 6.428D);
        } else if (paramInt == 1) {
            ret = computeret(6.428D, 3.219D);
        } else if (paramInt == 2) {
            ret = computeret(3.219D, 1.614D);
        } else {
            ret = computeret(1.219D, 1.614D);
        }
        return ret;
    }

    private double compute7(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(3.321D, 4.244D);
        } else if (paramInt == 1) {
            ret = computeret(4.244D, 5.847D);
        } else if (paramInt == 2) {
            ret = computeret(5.847D, 6.472D);
        } else {
            ret = computeret(6.472D, 6.6D);
        }
        return ret;
    }

    private double compute70(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(7.109D, 5.327D);
        } else if (paramInt == 1) {
            ret = computeret(5.327D, 4.201D);
        }
        else if (paramInt == 2) {
            ret = computeret(4.201D, 2.413D);
        } else {
            ret = computeret(2.001D, 2.413D);
        }
        return ret;
    }

    private double compute71(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(421.0D, 490.0D);
        }
        else if (paramInt == 1) {
            ret = computeret(490.0D, 510.0D);
        }
        else if (paramInt == 2) {
            ret = computeret(510.0D, 540.0D);
        } else {
            ret = computeret(570.0D, 540.0D);
        }
        return ret;
    }

    private double compute72(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(4.326D, 7.531D);
        } else if (paramInt == 1) {
            ret = computeret(7.531D, 8.214D);
        } else if (paramInt == 2) {
            ret = computeret(8.214D, 9.137D);
        } else {
            ret = computeret(10.214D, 9.137D);
        }
        return ret;
    }

    private double compute73(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.954D, 5.543D);
        }
        else if (paramInt == 1) {
            ret = computeret(5.543D, 6.172D);
        }
        else if (paramInt == 2) {
            ret = computeret(6.172D, 7.419D);
        } else {
            ret = computeret(8.572D, 7.419D);
        }
        return ret;
    }

    private double compute74(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(4.023D, 11.627D);
        }
        else if (paramInt == 1) {
            ret = computeret(11.627D, 16.131D);
        }
        else if (paramInt == 2) {
            ret = computeret(16.131D, 19.471D);
        } else {
            ret = computeret(21.131D, 19.471D);
        }
        return ret;
    }

    private double compute75(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(86.73D, 180.97D);
        }
        else if (paramInt == 1) {
            ret = computeret(180.97D, 190.37D);
        }
        else if (paramInt == 2) {
            ret = computeret(190.37D, 203.99D);
        } else {
            ret = computeret(210.37D, 203.99D);
        }
        return ret;
    }

    private double compute76(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.209D, 0.751D);
        } else if (paramInt == 1) {
            ret = computeret(0.751D, 0.844D);
        } else if (paramInt == 2) {
            ret = computeret(0.844D, 0.987D);
        } else {
            ret = computeret(1.132D, 0.987D);
        }
        return ret;
    }

    private double compute77(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.046D, 0.167D);
        } else if (paramInt == 1) {
            ret = computeret(0.167D, 0.457D);
        } else if (paramInt == 2) {
            ret = computeret(0.457D, 0.989D);
        } else {
            ret = computeret(1.257D, 0.989D);
        }
        return ret;
    }

    private double compute78(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.124D, 0.453D);
        } else if (paramInt == 1) {
            ret = computeret(0.453D, 0.525D);
        } else if (paramInt == 2) {
            ret = computeret(0.525D, 0.749D);
        } else {
            ret = computeret(0.925D, 0.749D);
        }
        return ret;
    }

    private double compute79(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.433D, 0.212D);
        } else if (paramInt == 1) {
            ret = computeret(0.212D, 0.212D);
        } else if (paramInt == 2) {
            ret = computeret(0.212D, 0.165D);
        } else {
            ret = computeret(0.112D, 0.165D);
        }
        return ret;
    }

    private double compute8(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.672D, 1.338D);
        } else if (paramInt == 1) {
            ret = computeret(1.338D, 0.647D);
        } else if (paramInt == 2) {
            ret = computeret(0.647D, 0.139D);
        } else {
            ret = computeret(0.139D, 0.06D);
        }
        return ret;
    }

    private double compute80(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        }
        return ret;
    }

    private double compute81(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.1D, 0.2D);
        } else {
            ret = computeret(0.2D, 0.3D);
        }
        return ret;
    }

    private double compute82(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = 0.0D;
        } else if (paramInt == 1) {
            ret = 1.0D;
        } else if (paramInt == 2) {
            ret = 2.0D;
        } else {
            ret = 3.0D;
        }
        return ret;
    }

    private double compute83(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(10.0D, 15.0D);
        } else if (paramInt == 1) {
            ret = computeret(16.0D, 20.0D);
        } else if (paramInt == 2) {
            ret = computeret(21.0D, 28.0D);
        } else {
            ret = computeret(29.0D, 34.0D);
        }
        return ret;
    }

    private double compute84(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(5.543D, 2.954D);
        } else if (paramInt == 1) {
            ret = computeret(2.954D, 1.864D);
        } else if (paramInt == 2) {
            ret = computeret(1.864D, 0.514D);
        } else {
            ret = computeret(0.364D, 0.514D);
        }
        return ret;
    }

    private double compute85(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(4.017D, 2.845D);
        } else if (paramInt == 1) {
            ret = computeret(2.845D, 1.932D);
        }
        else if (paramInt == 2) {
            ret = computeret(1.932D, 1.134D);
        } else {
            ret = computeret(0.932D, 1.134D);
        }
        return ret;
    }

    private double compute86(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(2.974D, 2.412D);
        } else if (paramInt == 1) {
            ret = computeret(2.412D, 1.976D);
        } else if (paramInt == 2) {
            ret = computeret(1.976D, 1.433D);
        } else {
            ret = computeret(1.176D, 1.433D);
        }
        return ret;
    }

    private double compute87(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(7.34D, 2.163D);
        }
        else if (paramInt == 1) {
            ret = computeret(2.163D, 1.309D);
        }
        else if (paramInt == 2) {
            ret = computeret(1.309D, 0.641D);
        } else {
            ret = computeret(0.309D, 0.641D);
        }
        return ret;
    }


    private double compute88(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(6.854D, 3.21D);
        } else if (paramInt == 1) {
            ret = computeret(3.21D, 2.187D);
        } else if (paramInt == 2) {
            ret = computeret(2.187D, 0.966D);
        } else {
            ret = computeret(0.687D, 0.966D);
        }
        return ret;
    }

    private double compute89(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(3.528D, 2.967D);
        }
        else if (paramInt == 1) {
            ret = computeret(2.967D, 2.318D);
        }
        else if (paramInt == 2) {
            ret = computeret(2.318D, 1.647D);
        } else {
            ret = computeret(1.318D, 1.647D);
        }
        return ret;
    }

    private double compute9(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.669D, 1.544D);
        } else if (paramInt == 1) {
            ret = computeret(1.544D, 2.037D);
        } else if (paramInt == 2) {
            ret = computeret(2.037D, 2.417D);
        } else {
            ret = computeret(2.417D, 2.6D);
        }
        return ret;
    }

    private double compute90(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret (2.819D, 2.204D);
        }
        else if(paramInt ==1.0)
        {
            ret = computeret (2.204D, 1.717D);
        }
        else if(paramInt ==2.0){
            ret = computeret(1.717D, 1.028D);
        }
        else {
            ret = computeret(1.028D,0D);
        }
        return  ret;
    }

    private double compute91(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(133.437D, 140.47D);
        } else if (paramInt == 1) {
            ret = computeret(140.47D, 146.926D);
        } else if (paramInt == 2) {
            ret = computeret(146.926D, 153.164D);
        } else {
            ret = computeret(166.926D, 153.164D);
        }
        return ret;
    }

    private double compute92(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(0.453D, 0.124D);
        } else if (paramInt == 1) {
            ret = computeret(0.124D, 0.097D);
        } else if (paramInt == 2) {
            ret = computeret(0.097D, 0.073D);
        } else {
            ret = computeret(0.067D, 0.073D);
        }
        return ret;
    }

    private double compute93(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(3.218D, 0.146D);
        } else if (paramInt == 1) {
            ret = computeret(0.146D, 0.089D);
        } else if (paramInt == 2) {
            ret = computeret(0.089D, 0.052D);
        } else {
            ret = computeret(0.039D, 0.052D);
        }
        return ret;
    }

    private double compute94(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(35.642D, 34.367D);
        } else if (paramInt == 1) {
            ret = computeret(34.367D, 33.109D);
        }
        else if (paramInt == 2) {
            ret = computeret(33.109D, 29.947D);
        } else {
            ret = computeret(23.109D, 29.947D);
        }
        return ret;
    }

    private double compute95(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(61.213D, 58.425D);
        } else if (paramInt == 1) {
            ret = computeret(58.425D, 55.627D);
        } else if (paramInt == 2) {
            ret = computeret(55.627D, 52.518D);
        } else {
            ret = computeret(49.627D, 52.518D);
        }
        return ret;
    }

    private double compute96(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(6.981D, 3.712D);
        } else if (paramInt == 1) {
            ret = computeret(3.712D, 2.476D);
        } else if (paramInt == 2) {
            ret = computeret(2.476D, 1.571D);
        } else {
            ret = computeret(1.276D, 1.571D);
        }
        return ret;
    }

    private double compute97(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(9.814D, 3.241D);
        } else if (paramInt == 1) {
            ret = computeret(3.241D, 2.174D);
        } else if (paramInt == 2) {
            ret = computeret(2.174D, 1.029D);
        } else {
            ret = computeret(0.871D, 1.029D);
        }
        return ret;
    }

    private double compute98(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(1.712D, 0.638D);
        } else if (paramInt == 1) {
            ret = computeret(0.638D, 0.434D);
        } else if (paramInt == 2) {
            ret = computeret(0.434D, 0.218D);
        } else {
            ret = computeret(0.124D, 0.218D);
        }
        return ret;
    }

    private double compute99(int paramInt) {
        double ret = 0.0;
        if (paramInt == 0) {
            ret = computeret(18.741D, 4.111D);
        } else if (paramInt == 1) {
            ret = computeret(4.111D, 2.647D);
        } else if (paramInt == 2) {
            ret = computeret(2.647D, 1.138D);
        } else {
            ret = computeret(0.947D, 1.138D);
        }
        return ret;
    }

    private void compute_0() {
        if ((this.degree == 0) || (!this.fatflag)) {
            GetTheItemComputeResult(0, 14, 0, 0, 0);
            return;
        }
        else if (this.degree == 1) {
            GetTheItemComputeResult(0, 14, 1, 2, 0);
            return;
        }
        else if (this.degree == 2) {
            GetTheItemComputeResult(0, 14, 2, 3, 0);
            return;
        }
        GetTheItemComputeResult(0, 14, 2, 3, 2);
    }

    private void compute_1() {
        if (this.degree == 0) {
            GetTheItemComputeResult(15, 19, 0, 0, 0);
            return;
        } else if (this.degree == 1) {
            GetTheItemComputeResult(15, 19, 2, 0, 0);
            return;
        } else if (this.degree == 2) {
            GetTheItemComputeResult(15, 19, 2, 3, 0);
            return;
        } else {
            GetTheItemComputeResult(15, 19, 1, 3, 1);
            return;
        }
    }

    private void compute_10() {
        this.result[55] = compute55(1);
        this.DegreeResult[55] = 1.0D;
        if ((this.degree == 0) || (this.fatflag)) {
            GetTheItemComputeResult(56, 59, 0, 0, 0);
            return;
        }
        if (this.degree == 1) {
            GetTheItemComputeResult(56, 59, 2, 0, 0);
            return;
        }
        if (this.degree == 2) {
            GetTheItemComputeResult(56, 59, 1, 2, 0);
            return;
        }
        GetTheItemComputeResult(56, 59, 1, 2, 1);
    }

    private void compute_11() {
        if (this.degree == 0) {
            GetTheItemComputeResult(60, 70, 2, 0, 0);
            return;
        }
        if (this.degree == 1) {
            GetTheItemComputeResult(60, 70, 2, 1, 0);
            return;
        }
        if (this.degree == 2) {
            GetTheItemComputeResult(60, 70, 2, 3, 0);
            return;
        }
        else {
            GetTheItemComputeResult(60, 70, 2, 3, 2);
        }
    }

    private void compute_12() {
        if (this.degree == 0) {
            GetTheItemComputeResult(71, 74, 0, 0, 0);
            return;
        }
        else if (this.degree == 1) {
            GetTheItemComputeResult(71, 74, 1, 0, 0);
            return;
        }
        else if (this.degree == 2) {
            GetTheItemComputeResult(71, 74, 1, 2, 0);
            return;
        }
        else {
            GetTheItemComputeResult(71, 74, 1, 2, 1);
        }
    }

    private void compute_13() {
        if (this.degree == 0) {
            GetTheItemComputeResult(75, 79, 0, 0, 0);
            return;
        }
        else if (this.degree == 1) {
            GetTheItemComputeResult(75, 79, 2, 0, 0);
            return;
        }
        else if (this.degree == 2) {
            GetTheItemComputeResult(75, 79, 2, 3, 0);
            return;
        }
        else {
            GetTheItemComputeResult(75, 79, 1, 3, 1);
        }
    }

    private void compute_14() {
        int rand = 0;
        rand = ra.nextInt(3);
        result[80] = compute80(rand);
        if (rand == 0) {
            DegreeResult[80] = 0.0D;
        } else {
            DegreeResult[80] = 1.0D;
        }
        rand = ra.nextInt(2);
        result[81] = compute81(rand);
        DegreeResult[81] = (double) rand;
        rand = ra.nextInt(4);
        result[82] = compute82(rand);
        DegreeResult[82] = (double) rand;
        if (age <= 25) {
            result[83] = compute83(0);
            DegreeResult[83] = 0.0;
            return;
        }
        if ((age > 25) && (age < 30)) {
            result[83] = compute83(1);
            DegreeResult[83] = 1.0;
            return;
        }
        if ((age > 35) && (age < 46)) {
            result[83] = compute83(46);
            DegreeResult[83] = 2.0;
            return;
        }
        result[83] = compute83(3);
        DegreeResult[83] = 3.0;
    }

    private void compute_15() {
        GetTheItemComputeResult(84, 90, 2, 0, 0);
        if (this.degree == 3) {
            this.result[90] = compute90(1);
            this.DegreeResult[90] = 1.0D;
        }
    }

    private void compute_16() {
        GetTheItemComputeResult(91, 99, 1, 2, 1);
    }

    private void compute_17() {
        if (degree == 0) {
            GetTheItemComputeResult(100, 104, 0, 0, 0);
            return;
        } else if (degree == 1) {
            GetTheItemComputeResult(100, 103, 1, 0, 0);
        } else if (degree == 2) {
            GetTheItemComputeResult(100, 103, 1, 2, 0);
        } else {
            GetTheItemComputeResult(100, 103, 1, 2, 1);
        }
        if (fatflag) {
            GetTheItemComputeResult(104, 104, 0, 1, 1);
            return;
        }
        GetTheItemComputeResult(104, 104, 1, 0, 0);
    }

    private void compute_18() {
        if (this.ra.nextInt(2) == 1) {
            GetTheItemComputeResult(105, 114, 2, 0, 0);
            return;
        }
        GetTheItemComputeResult(105, 114, 0, 3, 0);
    }

    private void compute_19() {
        int i = this.ra.nextInt(2);
        if ((this.degree != 3) || (this.fatflag)) {
            GetTheItemComputeResult(115, 116, 0, 0, 0);
            return;
        }
        if (i == 1) {
            GetTheItemComputeResult(115, 115, 1, 0, 0);
            return;
        }
        GetTheItemComputeResult(116, 116, 1, 0, 0);
    }

    private void compute_2() {
        if ((this.degree == 0) || (!this.fatflag)) {
            GetTheItemComputeResult(20, 24, 0, 0, 0);
            return;
        }
        if (this.degree == 1) {
            GetTheItemComputeResult(20, 24, 2, 0, 0);
            return;
        }
        if (this.degree == 2) {
            GetTheItemComputeResult(20, 24, 2, 3, 0);
            return;
        }
        GetTheItemComputeResult(20, 24, 1, 3, 1);
    }

    private void compute_20() {
        if (this.age <= 24) {
            GetTheItemComputeResult(117, 124, 0, 0, 0);
            return;
        }
        else  if ((this.age > 24) && (this.age < 35)) {
            GetTheItemComputeResult(117, 124, 3, 0, 0);
            return;
        }
        else {
            GetTheItemComputeResult(117, 124, 3, 4, 0);
        }
    }
    //皮肤 调试ok
    private void compute_21() {
        if (this.age <= 24) {
            GetTheItemComputeResult(125, 134, 0, 0, 0);
            return;
        }
        if ((this.age > 24) && (this.age < 35)) {
            GetTheItemComputeResult(125, 134, 3, 0, 0);
            return;
        }
        GetTheItemComputeResult(125, 134, 3, 4, 0);
    }
    private void compute_22() {
        int ran = 0;
        ran = ra.nextInt(2);
        int rand1 = 0;
        int rand2 = 0;
        if((degree == 0) || (!fatflag)) {
            GetTheItemComputeResult(135, 137, 0, 0, 0);
            rand1 = 1;
            return;
        }
        if(degree == 1) {
            if(ran == 0) {
                GetTheItemComputeResult(135, 137,1,0, 0);
                rand1 = 1 ;
                return;
            }
            GetTheItemComputeResult(135, 137, 0, 1, 0);
            rand1 = 1;
            return;
        }
        if(degree == 2) {
            rand1=1;
            rand1 = ra.nextInt(3);
            rand2 = ra.nextInt(3);
            if((rand1 + rand2) == 2) {
                GetTheItemComputeResult(135, 137, rand1, rand2, 0);
            }
            return;
        }
        rand1 = ra.nextInt(4);
        rand2 = ra.nextInt(4);
        if((rand1 + rand2) == 3) {
            GetTheItemComputeResult(135, 137, rand1, rand2,0 );
        }

    }

    private void compute_29()
    {
        GetTheItemComputeResult(138, 140, 0,0,0);
    }


  /*  private void compute_23() {
        int i = this.ra.nextInt(2);
        if ((this.degree == 0) || (!this.fatflag)) {
            GetTheItemComputeResult(147, 150, 0, 0, 0);
            return;
        }
        if (this.degree == 1) {
            if (i == 0) {
                GetTheItemComputeResult(147, 150, 1, 0, 0);
                return;
            }
            GetTheItemComputeResult(147, 150, 0, 1, 0);
            return;
        }
        int j;
        if (this.degree == 2) {
            do {
                i = this.ra.nextInt(3);
                j = this.ra.nextInt(3);
            }
            while (i + j != 2);
            GetTheItemComputeResult(147, 150, i, j, 0);
            return;
        }
        do {
            i = this.ra.nextInt(4);
            j = this.ra.nextInt(4);
        }
        while (i + j != 3);
        GetTheItemComputeResult(147, 150, i, j, 0);
    }*/
  private void compute_23() {
      int ran = 0;
      int rand1 = 0;
      int rand2 = 0;
      ran = ra.nextInt(2);
      if((degree == 0) || (!fatflag)) {
          GetTheItemComputeResult(147, 150, 0, 0, 0);
          rand1 = 1;
          return;
      }
      if(degree == 1) {
          if(ran == 0) {
              GetTheItemComputeResult(147, 150, 1, 0, 0);
              rand1 = 1;
              return;
          }
          GetTheItemComputeResult(147, 150, 0, 1, 0);
          rand1 = 1;
          return;
      }
      if(degree == 2) {
          rand1 = 1;
          rand1 = ra.nextInt(3);
          rand2 = ra.nextInt(3);
          if((rand1 + rand2) == 2) {
              GetTheItemComputeResult(147, 150, rand1, rand2, 0);
          }
          return;
      }
      rand1 = ra.nextInt(4);
      rand2 = ra.nextInt(4);
      if((rand1 + rand2) == 3) {
          GetTheItemComputeResult(147, 150, rand1, rand2, 0);
      }
      // Parsing error may occure here :(
  }

    private void compute_24() {
        GetTheItemComputeResult(141, 146, 2, 1, 0);
    }

    private void compute_25() {
        GetTheItemComputeResult(147, 150, 1, 1, 0);
    }

    private void compute_26() {
        if (this.age < 27) {
            GetTheItemComputeResult(151, 155, 0, 0, 0);
            return;
        }
        if ((this.age < 33) && (this.age > 26)) {
            GetTheItemComputeResult(151, 155, 2, 0, 0);
            return;
        }
        if ((this.age > 32) && (this.age < 45)) {
            GetTheItemComputeResult(151, 155, 2, 1, 0);
            return;
        }
        GetTheItemComputeResult(151, 155, 2, 1, 1);
    }

    private void compute_27() {
        if (this.age < 27) {
            GetTheItemComputeResult(156, 164, 0, 0, 0);
            return;
        }
        if ((this.age < 33) && (this.age > 26)) {
            GetTheItemComputeResult(156, 164, 3, 0, 0);
            return;
        }
        if ((this.age > 32) && (this.age < 45)) {
            GetTheItemComputeResult(156, 164, 3, 2, 0);
            return;
        }
        GetTheItemComputeResult(156, 164, 3, 2, 1);
    }

    private void compute_28() {
        if (this.age < 27) {
            GetTheItemComputeResult(165, 179, 0, 0, 0);
            return;
        }
        else if ((this.age < 33) && (this.age > 26)) {
            GetTheItemComputeResult(165, 179, 3, 0, 0);
            return;
        }
        else if ((this.age > 32) && (this.age < 45)) {
            GetTheItemComputeResult(165, 179, 3, 2, 0);
            return;
        }
        else {
            GetTheItemComputeResult(165, 179, 5, 3, 0);
        }
    }

    private void compute_3() {
        if ((this.degree == 0) || (this.fatflag)) {
            GetTheItemComputeResult(25, 29, 0, 0, 0);
            return;
        }
        if (this.degree == 1) {
            GetTheItemComputeResult(25, 29, 2, 0, 0);
            return;
        }
        if (this.degree == 2) {
            GetTheItemComputeResult(25, 29, 2, 3, 0);
            return;
        }
        GetTheItemComputeResult(25, 29, 1, 3, 1);
    }
    private void compute_4() {
        int ran = 0;
        GetTheItemComputeResult(30, 34, 0, 0, 0);
        DegreeResult[34] = 0;
        if(degree == 3) {
            if(fatflag) {
                result[30] = compute30(1);
                DegreeResult[30] = 1.0;
            } else {
                result[30] = compute30(2);
                DegreeResult[30] = 2.0;
            }
        }
        if((degree != 0) && (!fatflag)) {
            ran = ra.nextInt(2) + 1;
            result[31] = compute31(ran);
            DegreeResult[31] = (double)ran;
        }
        if((degree == 3) && (!fatflag)) {
            ran = ra.nextInt(2);
            if(ran == 0) {
                GetTheItemComputeResult(32, 34, 1, 0, 0);
                return;
            }
            GetTheItemComputeResult(32, 34, 0, 1, 0);
        }

    }
/*    private void compute_4() {
        int ran = 0;
        GetTheItemComputeResult(30, 34, 0,0, 0);
        DegreeResult[34] = 0;
        if(degree == 3) {
            if(fatflag) {
                result[30] = compute30(1);
                DegreeResult[30] = 1.0;
            } else {
                result[30] = compute30(2);
                DegreeResult[30] = 2.0;
            }
        }
        if((degree != 0) && (!fatflag)) {
            ran = ra.nextInt(2) + 1;
            result[31] = compute31(ran);
            DegreeResult[31] = (double)ran;
        }
        if((degree == 3) && (!fatflag)) {
            ran = ra.nextInt(2);
            if(ran == 0) {
                GetTheItemComputeResult(32, 34, 1, 0, 0);
                return;
            }
            GetTheItemComputeResult(32, 34, 0, 1, 1);
        }
    }*/


    private void compute_5() {
        if (this.ra.nextInt(2) == 0) {
            GetTheItemComputeResult(35, 38, 0, 0, 0);
        }
        else {
            GetTheItemComputeResult(35, 38, 0, 2, 0);
        }
    }

    private void compute_6() {
        GetTheItemComputeResult(39, 43, 2, 0, 0);
    }

    private void compute_7() {
        if ((this.degree == 0) || (!this.fatflag)) {
            GetTheItemComputeResult(44, 46, 0, 0, 0);
            return;
        } else if (this.degree == 1) {
            GetTheItemComputeResult(44, 46, 1, 0, 0);
            return;
        } else if (this.degree == 2) {
            GetTheItemComputeResult(44, 46, 1, 2, 0);
            return;
        } else {
            GetTheItemComputeResult(44, 46, 0, 2, 1);
        }
    }

    private void compute_8() {
        GetTheItemComputeResult(47, 51, 2, 0, 0);
    }

    private void compute_9() {
        if (this.degree == 0) {
            GetTheItemComputeResult(52, 54, 0, 0, 0);
            return;
        } else if (this.degree == 1) {
            GetTheItemComputeResult(52, 54, 1, 0, 0);
            return;
        } else if (this.degree == 2) {
            GetTheItemComputeResult(52, 54, 1, 2, 0);
            return;
        } else {
            GetTheItemComputeResult(52, 54, 1, 2, 0);
        }
    }


    private double computeret(double paramDouble1, double paramDouble2)
    {
        double ret;
        Random localRandom = new Random();
        if (paramDouble1 >= paramDouble2) {
            ret = localRandom.nextDouble() * (paramDouble1 - paramDouble2) + paramDouble2;
        }
        else {
            ret = localRandom.nextDouble() * (paramDouble2 - paramDouble1) + paramDouble1;
        }
        return  ret;
    }


    private double computetheitems(int paramInt1, int paramInt2) {
        double result = 0.0D;
        switch (paramInt1) {
            case 0:
                result = compute0(paramInt2);
                break;
            case 1:
                result = compute1(paramInt2);
                break;
            case 2:
                result = compute2(paramInt2);
                break;
            case 3:
                result = compute3(paramInt2);
                break;
            case 4:
                result = compute4(paramInt2);
                break;
            case 5:
                result = compute5(paramInt2);
                break;
            case 6:
                result = compute6(paramInt2);
                break;
            case 7:
                result = compute7(paramInt2);
                break;
            case 8:
                result = compute8(paramInt2);
                break;
            case 9:
                result = compute9(paramInt2);
                break;
            case 10:
                result = compute10(paramInt2);
            case 11:
                result = compute11(paramInt2);
                break;
            case 12:
                result = compute12(paramInt2);
                break;
            case 13:
                result = compute13(paramInt2);
                break;
            case 14:
                result = compute14(paramInt2);
                break;
            case 15:
                result = compute15(paramInt2);
                break;
            case 16:
                result = compute16(paramInt2);
                break;
            case 17:
                result = compute17(paramInt2);
                break;
            case 18:
                result = compute18(paramInt2);
                break;
            case 19:
                result = compute19(paramInt2);
                break;
            case 20:
                result = compute20(paramInt2);
                break;
            case 21:
                result = compute21(paramInt2);
                break;
            case 22:
                result = compute22(paramInt2);
                break;
            case 23:
                result = compute23(paramInt2);
                break;
            case 24:
                result = compute24(paramInt2);
                break;
            case 25:
                result = compute25(paramInt2);
                break;
            case 26:
                result = compute26(paramInt2);
                break;
            case 27:
                result = compute27(paramInt2);
                break;
            case 28:
                result = compute28(paramInt2);
                break;
            case 29:
                result = compute29(paramInt2);
                break;
            case 30:
                result = compute30(paramInt2);
                break;
            case 31:
                result = compute31(paramInt2);
                break;
            case 32:
                result = compute32(paramInt2);
                break;
            case 33:
                result = compute33(paramInt2);
                break;
            case 34:
                result = compute34(paramInt2);
                break;
            case 35:
                result = compute35(paramInt2);
                break;
            case 36:
                result = compute36(paramInt2);
                break;
            case 37:
                result = compute37(paramInt2);
                break;
            case 38:
                result = compute38(paramInt2);
                break;
            case 39:
                result = compute39(paramInt2);
                break;
            case 40:
                result = compute40(paramInt2);
                break;
            case 41:
                result = compute41(paramInt2);
                break;
            case 42:
                result = compute42(paramInt2);
                break;
            case 43:
                result = compute43(paramInt2);
                break;
            case 44:
                result = compute44(paramInt2);
                break;
            case 45:
                result = compute45(paramInt2);
                break;
            case 46:
                result = compute46(paramInt2);
                break;
            case 47:
                result = compute47(paramInt2);
                break;
            case 48:
                result = compute48(paramInt2);
                break;
            case 49:
                result = compute49(paramInt2);
                break;
            case 50:
                result = compute50(paramInt2);
                break;
            case 51:
                result = compute51(paramInt2);
                break;
            case 52:
                result = compute52(paramInt2);
                break;
            case 53:
                result = compute53(paramInt2);
                break;
            case 54:
                result = compute54(paramInt2);
                break;
            case 55:
                result = compute55(paramInt2);
                break;
            case 56:
                result = compute56(paramInt2);
                break;
            case 57:
                result = compute57(paramInt2);
                break;
            case 58:
                result = compute58(paramInt2);
                break;
            case 59:
                result = compute59(paramInt2);
                break;
            case 60:
                result = compute60(paramInt2);
                break;
            case 61:
                result = compute61(paramInt2);
                break;
            case 62:
                result = compute62(paramInt2);
                break;
            case 63:
                result = compute63(paramInt2);
                break;
            case 64:
                result = compute64(paramInt2);
                break;
            case 65:
                result = compute65(paramInt2);
                break;
            case 66:
                result = compute66(paramInt2);
                break;
            case 67:
                result = compute67(paramInt2);
                break;
            case 68:
                result = compute68(paramInt2);
                break;
            case 69:
                result = compute69(paramInt2);
                break;
            case 70:
                result = compute70(paramInt2);
                break;
            case 71:
                result = compute71(paramInt2);
                break;
            case 72:
                result = compute72(paramInt2);
                break;
            case 73:
                result = compute73(paramInt2);
                break;
            case 74:
                result = compute74(paramInt2);
                break;
            case 75:
                result = compute75(paramInt2);
                break;
            case 76:
                result = compute76(paramInt2);
                break;
            case 77:
                result = compute77(paramInt2);
                break;
            case 78:
                result = compute78(paramInt2);
                break;
            case 79:
                result = compute79(paramInt2);
                break;
            case 80:
                result = compute80(paramInt2);
                break;
            case 81:
                result = compute81(paramInt2);
                break;
            case 82:
                result = compute82(paramInt2);
                break;
            case 83:
                result = compute83(paramInt2);
                break;
            case 84:
                result = compute84(paramInt2);
                break;
            case 85:
                result = compute85(paramInt2);
                break;
            case 86:
                result = compute86(paramInt2);
                break;
            case 87:
                result = compute87(paramInt2);
                break;
            case 88:
                result = compute88(paramInt2);
                break;
            case 89:
                result = compute89(paramInt2);
                break;
            case 90:
                result = compute90(paramInt2);
                break;
            case 91:
                result = compute91(paramInt2);
                break;
            case 92:
                result = compute92(paramInt2);
                break;
            case 93:
                result = compute93(paramInt2);
                break;
            case 94:
                result = compute94(paramInt2);
                break;
            case 95:
                result = compute95(paramInt2);
                break;
            case 96:
                result = compute96(paramInt2);
                break;
            case 97:
                result = compute97(paramInt2);
                break;
            case 98:
                result = compute98(paramInt2);
                break;
            case 99:
                result = compute99(paramInt2);
                break;
            case 100:
                result = compute100(paramInt2);
                break;
            case 101:
                result = compute101(paramInt2);
                break;
            case 102:
                result = compute102(paramInt2);
                break;
            case 103:
                result = compute103(paramInt2);
                break;
            case 104:
                result = compute104(paramInt2);
                break;
            case 105:
                result = compute105(paramInt2);
                break;
            case 106:
                result = compute106(paramInt2);
                break;
            case 107:
                result = compute107(paramInt2);
                break;
            case 108:
                result = compute108(paramInt2);
                break;
            case 109:
                result = compute109(paramInt2);
                break;
            case 110:
                result = compute110(paramInt2);
                break;
            case 111:
                result = compute111(paramInt2);
                break;
            case 112:
                result = compute112(paramInt2);
                break;
            case 113:
                result = compute113(paramInt2);
                break;
            case 114:
                result = compute114(paramInt2);
                break;
            case 115:
                result = compute115(paramInt2);
                break;
            case 116:
                result = compute116(paramInt2);
                break;
            case 117:
                result = compute117(paramInt2);
                break;
            case 118:
                result = compute118(paramInt2);
                break;
            case 119:
                result = compute119(paramInt2);
                break;
            case 120:
                result = compute120(paramInt2);
                break;
            case 121:
                result = compute121(paramInt2);
                break;
            case 122:
                result = compute122(paramInt2);
                break;
            case 123:
                result = compute123(paramInt2);
                break;
            case 124:
                result = compute124(paramInt2);
                break;
            case 125:
                result = compute125(paramInt2);
                break;
            case 126:
                result = compute126(paramInt2);
                break;
            case 127:
                result = compute127(paramInt2);
                break;
            case 128:
                result = compute128(paramInt2);
                break;
            case 129:
                result = compute129(paramInt2);
                break;
            case 130:
                result = compute130(paramInt2);
                break;
            case 131:
                result = compute131(paramInt2);
                break;
            case 132:
                result = compute132(paramInt2);
                break;
            case 133:
                result = compute133(paramInt2);
                break;
            case 134:
                result = compute134(paramInt2);
                break;
            case 135:
                result = compute135(paramInt2);
                break;
            case 136:
                result = compute136(paramInt2);
                break;
            case 137:
                result = compute137(paramInt2);
                break;
            case 138:
                result = compute138(paramInt2);
                break;
            case 139:
                result = compute139(paramInt2);
                break;
            case 140:
                result = compute140(paramInt2);
                break;
            case 141:
                result = compute141(paramInt2);
                break;
            case 142:
                result = compute142(paramInt2);
                break;
            case 143:
                result = compute143(paramInt2);
                break;
            case 144:
                result = compute144(paramInt2);
                break;
            case 145:
                result = compute145(paramInt2);
                break;
            case 146:
                result = compute146(paramInt2);
                break;
            case 147:
                result = compute147(paramInt2);
                break;
            case 148:
                result = compute148(paramInt2);
                break;
            case 149:
                result = compute149(paramInt2);
                break;
            case 150:
                result = compute150(paramInt2);
                break;
            case 151:
                result = compute151(paramInt2);
                break;
            case 152:
                result = compute152(paramInt2);
                break;
            case 153:
                result = compute153(paramInt2);
                break;
            case 154:
                result = compute154(paramInt2);
                break;
            case 155:
                result = compute155(paramInt2);
                break;
            case 156:
                result = compute156(paramInt2);
                break;
            case 157:
                result = compute157(paramInt2);
                break;
            case 158:
                result = compute158(paramInt2);
                break;
            case 159:
                result = compute159(paramInt2);
                break;
            case 160:
                result = compute160(paramInt2);
                break;
            case 161:
                result = compute161(paramInt2);
                break;
            case 162:
                result = compute162(paramInt2);
                break;
            case 163:
                result = compute163(paramInt2);
                break;
            case 164:
                result = compute164(paramInt2);
                break;
            case 165:
                result = compute165(paramInt2);
                break;
            case 166:
                result = compute166(paramInt2);
                break;
            case 167:
                result = compute167(paramInt2);
                break;
            case 168:
                result = compute168(paramInt2);
                break;
            case 169:
                result = compute169(paramInt2);
                break;
            case 170:
                result = compute170(paramInt2);
                break;
            case 171:
                result = compute171(paramInt2);
                break;
            case 172:
                result = compute172(paramInt2);
                break;
            case 173:
                result = compute173(paramInt2);
                break;
            case 174:
                result = compute174(paramInt2);
                break;
            case 175:
                result = compute175(paramInt2);
                break;
            case 176:
                result = compute176(paramInt2);
                break;
            case 177:
                result = compute177(paramInt2);
                break;
            case 178:
                result = compute178(paramInt2);
                break;
            case 179:
                result = compute179(paramInt2);
                break;
            default:
                result = 0.0D;
                break;
        }
        return result;
    }
    public void ComputeTheResult(double uscrose, double uheight, double uweight, double ustandheight, double ustandweight, int uage)
    {
        this.scrose = uscrose;
        this.height = uheight;
        this.weight = uweight;
        this.standheight = ustandheight;
        this.standweight = ustandweight;
        this.age = uage;
        if (this.standheight > this.weight) {
            this.fatflag = false;
            if (this.scrose >= 57.0D)
            {
                this.degree = 0;
            }
            else if ((this.scrose >= 52.0D) && (this.scrose < 57.0D))
            {
                this.degree = 1;
            }
            else if ((this.scrose >= 43.0D) && (this.scrose < 52.0D))
            {
                this.degree = 2;
            }
            else{
                this.degree = 3;
            }
        }
        else
        {
            this.fatflag = true;
            if ((this.scrose < 73.0D) || (this.age >= 40))
            {
                if (((this.scrose >= 42.0D) && (this.scrose < 73.0D)) || ((this.scrose >= 73.0D) && (this.age >= 40)))
                {
                    this.degree = 1;
                }
                if ((this.scrose >= 29.0D) && (this.scrose < 42.0D))
                {
                    this.degree = 2;
                }
                else {
                    this.degree = 3;
                }
            }
        }
        compute_0();
        compute_1();
        compute_2();
        compute_3();
        compute_4();
        compute_5();
        compute_6();
        compute_7();
        compute_8();
        compute_9();
        compute_10();
        compute_11();
        compute_12();
        compute_13();
        compute_14();
        compute_15();
        compute_16();
        compute_17();
        compute_18();
        compute_19();
        compute_20();
        compute_21();
        compute_22();
        compute_23();
        compute_24();
        compute_25();
        compute_26();
        compute_27();
        compute_28();
        compute_29();
    }

}