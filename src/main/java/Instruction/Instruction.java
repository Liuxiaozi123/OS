package Instruction;

import lombok.Data;
@Data
public class Instruction {
    private int N_Runtime;//指令运行时间
    private  int N_sRuntime;//指令需要运行的时间长度


    InstructionSet m_nInstructionID;//指令类型标记

    public Instruction (int N_sRuntime,InstructionSet m_nInstructionID) {
        this.N_sRuntime=N_sRuntime;
        this.m_nInstructionID=m_nInstructionID;

    }
    public void Update(){
        N_Runtime+=1;

    }
}
