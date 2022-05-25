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
    //单例模式
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

    //写入日志
    public void writeLog(PCB pcb, String model, Date date) throws IOException {
        log.WriteLog(pcb, model, date);
    }

    public PCB getPCBFromQue(String m) {
        return que.removePCB(m);
    }

    public void toQue(PCB pcb) throws InterruptedException, IOException {
        if (pcb != null) {
            //当前运行的指令
            Instruction instruction = pcb.getM_nRunIC();
            if (instruction!=null) {
                InstructionSet set = instruction.getM_nInstructionID();
                switch (set) {
                    case INPUT:
                        //调入输入等待队列
                        appendQue(pcb, "I");
                        System.out.println("调入输入等待队列：" + que.getM_InputWaiting().size());
                        break;
                    case OUTPUT:
                        //调入输出等待队列
                        appendQue(pcb, "O");
                        System.out.println("调入输出等待队列：" + que.getM_OUTPutWaiting().size());
                        break;
                    case WAIT:
                        //调入等待队列
                        appendQue(pcb, "W");
                        System.out.println("调入等待队列：" + que.getM_BackReady().size());
                        break;
                    case HALT:
                        //结束进程
                        appendQue(pcb,"D");
                        System.out.println("进程结束");
                        break;
                }
            }
        }
    }

    //处理就绪队列
    public void treadReadyQue(PCB pcb) throws IOException {
        if (pcb!=null&&pcb.getM_nRunIC()!=null&&pcb.getM_nRunIC().getM_nInstructionID()== InstructionSet.CALC) {
            //获得一个PCB进行
            //运行时间片加1
            Instruction instruction = pcb.getM_nRunIC();
            instruction.Update();
            //指令还需要时间
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
            //指令还需要时间
            int i = instruction.getN_sRuntime() - instruction.getN_Runtime();
            if (i == 0) {
                pcb.loadPCBInfo();
                pcb.setM_runtime(0);
                //添加到就绪队列
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
