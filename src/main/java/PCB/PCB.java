package PCB;

import Instruction.Instruction;
import Instruction.InstructionSet;
import lombok.Data;

import java.util.LinkedList;
import java.util.UUID;

@Data
public class PCB {
    static int m_nRemainTime;//时间片大小
    private String m_name;//进程名称

    public PCB() {
        m_UID=UUID.randomUUID().toString();
    }

    public PCB(String m_name) {
        this.m_name = m_name;
        m_UID=UUID.randomUUID().toString();
    }

    private String m_UID;//进程标识符
    private int m_runtime;//当前指令运行所需时间
    private LinkedList<Instruction>m_nList;//指向本进程的指令序列
    private Instruction m_nRunIC;//指令指向将要运行的指令或正在运行的指令
    private PCB m_nextPCB;//进程队列的指针

    public static int getM_nRemainTime() {
        return m_nRemainTime;
    }

    public static void setM_nRemainTime(int m_nRemainTime) {
        PCB.m_nRemainTime = m_nRemainTime;
    }

    public void loadPCBInfo(){
        this.m_nRunIC=getM_nRunIC();//将要运行的指令
        if(m_nRunIC!=null)
            this.m_runtime =m_nRunIC.getN_sRuntime();
        else{
            this.m_runtime=0;
        }

    }
    public Instruction getInstruction(){
        if(m_nList.size()>0)
            return m_nList.poll();
        else
            return null;
    }
}


