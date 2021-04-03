// package 圆周率;

// import java.lang.Math;

// public class PI {
//     public static void main(String[] args) {
//         double pi = 0, a = 1.0 / 5.0, b = 1.0 / 239.0;
//         for (int k = 1; k < 10; k += 2) {
//             pi += 4.0 * (Math.pow(a, 2 * k - 1) / (2 * k - 1) - Math.pow(a, 2 * k + 1) / (2 * k + 1))
//                     + (Math.pow(b, 2 * k - 1) / (2 * k - 1) - Math.pow(b, 2 * k + 1) / (2 * k + 1));
//         }
//         System.out.println(pi * 4);
//     }
// }
