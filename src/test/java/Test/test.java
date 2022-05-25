package Test;

import Instruction.Instruction;
import Log.Log;
import PCB.PCB;
import View.Frame;
import lombok.Data;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
public class test {
    @Test
    public void test2() throws IOException {
        PCB pcb=new PCB();
        pcb.setM_name("≤‚ ‘");
        File file=new File("D:\\test.txt");
        Log log=new Log(file);
        log.WriteLog(pcb,"C",new Date());
    }



}
