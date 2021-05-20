package InterfaceExams;

import java.util.ArrayList;
import java.util.Scanner;

import InterfaceExams.StudentTable.DataType;
import InterfaceExams.StudentTable.StudentState;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<String>();
        names.add("张三");
        names.add("李四");
        names.add("王五");
        names.add("沈六");
        StudentTable studentTable = new StudentTable(names, DataType.Name);
        // StudentTable studentTable = new StudentTable();
        System.out.println("---表信息如下：");
        studentTable.show();
        System.out.println("---更新信息后：");
        for (int i = 0; i < 4; i++) {
            studentTable.update(320060 + i, i, DataType.ID);
            studentTable.update(1.0 + i, i, DataType.Score);
            studentTable.update(StudentState.Live, i, DataType.State);
        }
        studentTable.show();
        System.out.println("---增加表信息后：");
        studentTable.add(100000, DataType.State);
        studentTable.add(100000, DataType.ID);
        studentTable.update("管理员", 4, DataType.Name);
        studentTable.update(StudentState.Holiday, 4, DataType.State);
        studentTable.show();
        Scanner in = new Scanner(System.in);
        System.out.println(studentTable.queryByName(in.nextLine()));
        in.close();
    }
}
