package PCB;

import Instruction.*;
import lombok.SneakyThrows;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;

public class PCBUtil {
    public static LinkedList<Instruction> readFile(File file) throws IOException {
        System.out.println("    ttt");
        LinkedList<Instruction> list = new LinkedList<Instruction>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String regex = "[IOWHC][0-9]+";
        String s = reader.readLine();
        while (s != null && !s.equals("")) {
            if (!s.matches(regex)) {
                JOptionPane.showMessageDialog(null, "非法错误", "匹配出错", JOptionPane.ERROR_MESSAGE);
                s = reader.readLine();
                continue;
            }
            char c = s.charAt(0);
            InstructionSet set = null;
            switch (c) {
                case 'I':
                    set = InstructionSet.INPUT;
                    break;
                case 'O':
                    set = InstructionSet.OUTPUT;
                    break;
                case 'H':
                    set = InstructionSet.HALT;
                    break;
                case 'C':
                    set = InstructionSet.CALC;
                    break;

            }
            String[] split = s.split("\\D+");
            int time = 0;
            for (int i = 0; i < split.length; i++) {
                if (!split[i].equals("")) {
                    time = Integer.parseInt(split[i]);
                }

            }

            Instruction instruction = new Instruction(time, set);
            list.add(instruction);
            s = reader.readLine();
        }
        return list;
    }

    public static LinkedList<Instruction> appendInstruction(String s, LinkedList<Instruction> list) {
        String regex = "[CIOWH][0-9]+";
        char c = s.charAt(0);
        int time = 0;
        InstructionSet set = null;
        String[] split = s.split("\\D+");
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("")) {
                time = Integer.parseInt(split[i]);
            }
            switch (c) {
                case 'I':
                    set = InstructionSet.INPUT;
                    break;
                case 'O':
                    set = InstructionSet.OUTPUT;
                    break;
                case 'C':
                    set = InstructionSet.CALC;
                    break;
                case 'W':
                    set = InstructionSet.WAIT;
                    break;
                case 'H':
                    set = InstructionSet.HALT;
                    break;
            }

        }
        Instruction instruction = new Instruction(time, set);
        list.add(instruction);
        return list;

    }

    public static LinkedList<PCB> getPCBFromFile(File file) throws IOException {
        LinkedList<PCB> list = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String regex = "[P][0-9]+";
        String regex1 = "[CIOWH][0-9]+";
        String s = new String();
        s = reader.readLine();
        LinkedList<Instruction> instructionLinkedList = null;
        PCB pcb = null;
        while (s != null && !s.equals("")) {
            if (s.matches((regex))) {
                pcb = new PCB();
                pcb.setM_name(s);
                list.add(pcb);
                instructionLinkedList = new LinkedList<>();
            } else if (s.matches(regex1)) {
                instructionLinkedList=appendInstruction(s,instructionLinkedList);
                pcb.setM_nList(instructionLinkedList);

            }
            else{
                JOptionPane.showMessageDialog(null,"非法指令","警告",JOptionPane.QUESTION_MESSAGE);
            }
            s= reader.readLine();
        }
        return list;

    }




    @SneakyThrows
    public static void main(String[] args) {
        File file=new File("D:\\test.txt");
        LinkedList<Instruction> instructions = PCBUtil.readFile(file);
        for (Instruction instruction : instructions) {
            System.out.println(instruction);
        }
    }
}
