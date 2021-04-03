package InterfaceExams;

import java.util.ArrayList;

public class StudentTable extends Table {
    public enum StudentState {
        Live, TempLeave, Holiday, Graduated, Exited
    };

    public enum DataType {
        Name, ID, Score, State
    };

    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Integer> IDs = new ArrayList<Integer>();
    private ArrayList<Double> scores = new ArrayList<Double>();
    private ArrayList<StudentState> states = new ArrayList<StudentState>();

    StudentTable() {
        System.out.println("建立空表");
    }

    StudentTable(ArrayList<?> datas, DataType type) {
        try {
            switch (type) {
            case Name:
                if (TypeCheck(datas.get(0), DataType.Name)) {
                    names = new ArrayList<String>((ArrayList<String>) datas);
                    size = names.size();
                } else {
                    throw new Exception();
                }
                break;
            case ID:
                if (TypeCheck(datas.get(0), DataType.ID)) {
                    IDs = new ArrayList<Integer>((ArrayList<Integer>) datas);
                    size = IDs.size();
                } else {
                    throw new Exception();
                }
                break;
            case Score:
                if (TypeCheck(datas.get(0), DataType.Score)) {
                    scores = new ArrayList<Double>((ArrayList<Double>) datas);
                    size = scores.size();
                } else {
                    throw new Exception();
                }
                break;
            case State:
                if (TypeCheck(datas.get(0), DataType.State)) {
                    states = new ArrayList<StudentState>((ArrayList<StudentState>) datas);
                    size = states.size();
                } else {
                    throw new Exception();
                }
                break;
            default:
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("传入了错误的数据类型！建立空表");
        }
        CheckAll();
    }

    private void CheckAll() {
        Check(names);
        Check(IDs);
        Check(scores);
        Check(states);
    }

    private void Check(ArrayList<?> datas) {
        for (int i = datas.size(); i < size; i++) {
            datas.add(null);
        }
    }

    private boolean TypeCheck(Object data, DataType type) {
        switch (type) {
        case Name:
            return data.getClass() == String.class;
        case ID:
            return data.getClass() == Integer.class;
        case Score:
            return data.getClass() == Double.class;
        case State:
            return data.getClass() == StudentState.class;
        default:
            System.out.println("TypeCheck:传入错误的类型");
            return false;
        }
    }

    private ArrayList GetTypeData(DataType type) {
        switch (type) {
        case Name:
            return names;
        case ID:
            return IDs;
        case Score:
            return scores;
        case State:
            return states;
        default:
            System.out.println("GetTypeData:传入错误的类型");
            return null;
        }
    }

    public void update(Object data, int index, DataType type) {
        if (TypeCheck(data, type)) {
            GetTypeData(type).set(index, data);
        } else {
            System.out.println("update:数据类型不符");
        }
    }

    public void add(Object data,DataType type) {
        if (TypeCheck(data, type)) {
            GetTypeData(type).add(data);
            size += 1;
            CheckAll();
        } else {
            System.out.println("add:数据类型不符");
        }
    }

    @Override
    public void show() {
        if (size == 0) {
            System.out.println("<--空表-->");
        } else {
            System.out.println("姓名\t学号\t绩点\t状态");
            for (int i = 0; i < size; i++) {
                System.out.println(names.get(i) + "\t" + IDs.get(i) + "\t" + scores.get(i) + "\t" + states.get(i));
            }
        }
    }

    @Override
    public void clean() {
        names = new ArrayList<String>();
        IDs = new ArrayList<Integer>();
        scores = new ArrayList<Double>();
        states = new ArrayList<StudentState>();
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(names.get(i));
            sb.append("\t");
            sb.append(IDs.get(i));
            sb.append("\t");
            sb.append(scores.get(i));
            sb.append("\t");
            sb.append(states.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    public int compareTo(Object other) {
        System.out.println("调用compareTo()方法");
        return 0;
    }

    public void sort() {
        System.out.println("调用sort()方法");
    }
}
