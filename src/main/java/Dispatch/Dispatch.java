package Dispatch;

import Instruction.*;
import Log.Log;
import PCB.*;
import Queue.Que;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

public class Dispatch {
    private Que que;
    private int NTime;
    private Log log;
    //����ģʽ
    private static Dispatch dispatch = new Dispatch();

    private Dispatch() {
        que = Que.getInstance();
        File file = new File("D:\\log.docx");
        if(file.exists())
            file.delete();
        log = new Log(file);
    }

    public static Dispatch getInstance() {
        return dispatch;
    }

    //д����־
    public void writeLog(PCB pcb, String model, Date date) throws IOException {
        log.WriteLog(pcb, model, date);
    }

    public PCB getPCBFromQue(String m) {
        return que.removePCB(m);
    }

    public void toQue(PCB pcb) throws InterruptedException, IOException {
        if (pcb != null) {
            //��ǰ���е�ָ��
            Instruction instruction = pcb.getM_nRunIC();
            if (instruction!=null) {
                InstructionSet set = instruction.getM_nInstructionID();
                switch (set) {
                    case INPUT:
                        //��������ȴ�����
                        appendQue(pcb, "I");
                        System.out.println("��������ȴ����У�" + que.getM_InputWaiting().size());
                        break;
                    case OUTPUT:
                        //��������ȴ�����
                        appendQue(pcb, "O");
                        System.out.println("��������ȴ����У�" + que.getM_OUTPutWaiting().size());
                        break;
                    case WAIT:
                        //����ȴ�����
                        appendQue(pcb, "W");
                        System.out.println("����ȴ����У�" + que.getM_BackReady().size());
                        break;
                    case HALT:
                        //��������
                        appendQue(pcb,"D");
                        System.out.println("���̽���");
                        break;
                }
            }
        }
    }

    //�����������
    public void treadReadyQue(PCB pcb) throws IOException {
        if (pcb!=null&&pcb.getM_nRunIC()!=null&&pcb.getM_nRunIC().getM_nInstructionID()== InstructionSet.CALC) {
            //���һ��PCB����
            //����ʱ��Ƭ��1
            Instruction instruction = pcb.getM_nRunIC();
            instruction.Update();
            //ָ���Ҫʱ��
            int i = instruction.getN_sRuntime() - instruction.getN_Runtime();
            if (i == 0) {
               pcb.loadPCBInfo();
                pcb.setM_runtime(0);
            } else {
                pcb.setM_runtime(i);
                pcb.setM_nRunIC(instruction);
            }
            appendQue(pcb, "R");
        }
    }

    public void treadQue(String m, PCB pcb) throws IOException {
        int size = 0;
        switch (m) {
            case "I":
                size = que.getM_InputWaiting().size();
                break;
            case "O":
                size = que.getM_OUTPutWaiting().size();
                break;
            case "W":
                size = que.getM_BackReady().size();
                break;
            case "R":
                treadReadyQue(pcb);
                break;
        }
        while (size > 0) {
            size--;
            pcb = getPCBFromQue(m);
            Instruction instruction = pcb.getM_nRunIC();
            if(instruction==null){
                que.removePCB(m);
                break;
            }
            instruction.Update();
            //ָ���Ҫʱ��
            int i = instruction.getN_sRuntime() - instruction.getN_Runtime();
            if (i == 0) {
                pcb.loadPCBInfo();
                pcb.setM_runtime(0);
                //��ӵ���������
                appendQue(pcb, "R");
            } else {
                pcb.setM_runtime(i);
                pcb.setM_nRunIC(instruction);
                que.addPCBToQue(m,pcb);
            }

        }

    }

    public void appendQue(PCB pcb, String m) throws IOException {
        que.addPCBToQue(m, pcb);
        dispatch.writeLog(pcb, m, new Date());
    }
}
